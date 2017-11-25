package Server;

import Utilities.ErrorWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ServerThread extends Thread{
    protected Socket socket;
    protected ServerSocket serverSocket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    ServerThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket=serverSocket;
        doConnections();
    }

    private void doConnections() {
        try {
            this.out=new ObjectOutputStream(socket.getOutputStream());
            this.in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        	 ErrorWindow.init("Algo Correu Mal");
        }
    }

    void send(Object obj){
        try {
            out.writeObject(obj);
        } catch (IOException e) {
        	 ErrorWindow.init("Algo Correu Mal");
        }
    }

    Object read(){
        Object obj = null;
        try {
            if(!socket.isClosed())
            obj=in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        	 ErrorWindow.init("Algo Correu Mal");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        	 ErrorWindow.init("Algo Correu Mal");
        }
        return obj;
    }

    @Override
    public void run() {
        while (!interrupted()){
            read();
        }
    }
}
