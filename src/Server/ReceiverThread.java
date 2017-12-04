package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ReceiverThread extends Thread{
    protected int id;
    protected Socket socket;
    protected ServerSocket serverSocket;
    protected ObjectInputStream in;
    protected Server server;

    ReceiverThread(Socket socket, Server server, int id) {
        this.socket = socket;
        this.server=server;
        this.id =id;
        doConnections();
    }

    private void doConnections() {
        try {
            this.in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
