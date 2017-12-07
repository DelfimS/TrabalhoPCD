package Server.Threads;

import Server.Server;
import Server.Threads.ReceiverThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiverThreadClient extends ReceiverThread {

    public ReceiverThreadClient(Socket socket, Server server, int id, ObjectInputStream in) {
        super(socket, server, id, in);
    }

    Object read(){
        Object obj = null;
        try {
            obj=in.readObject();
        } catch (IOException e) {
            super.destroyThread();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void run() {
        String tipo;
        String request;
        while (!isInterrupted()){
            tipo=(String)read();
            request=(String)read();
            server.createTask(tipo,request);
        }
    }
}
