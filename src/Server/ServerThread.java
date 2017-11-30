package Server;

import Utilities.ErrorWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

class ServerThread extends Thread{
    protected Socket socket;
    protected ServerSocket serverSocket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected Server server;

    ServerThread(Socket socket, ServerSocket serverSocket,Server server) {
        this.socket = socket;
        this.server=server;
        this.serverSocket=serverSocket;
        doConnections();
    }

    private void doConnections() {
        try {
            this.out=new ObjectOutputStream(socket.getOutputStream());
            this.in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(Object obj){
        try {
            out.writeObject(obj);
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
        server.removeThread(this);
        this.interrupt();
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
