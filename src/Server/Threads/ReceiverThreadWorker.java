package Server.Threads;

import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiverThreadWorker extends ReceiverThread {

    public ReceiverThreadWorker(Socket socket, Server server, int id, ObjectInputStream in) {
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
            if (tipo.equals("search"))
                server.createTask(tipo,request);
        }
    }
}
