package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ReceiverThread extends Thread{
    private int id;
    private Socket socket;
    protected ObjectInputStream in;
    private Server server;

    ReceiverThread(Socket socket, Server server, int id,ObjectInputStream in) {
        this.in=in;
        this.socket = socket;
        this.server=server;
        this.id =id;
    }

    Object read(){
        Object obj = null;
        try {
            obj=in.readObject();
        } catch (IOException e) {
            destroyThread();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void destroyThread() {
        server.removeThread(this.id);
        this.interrupt();
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
