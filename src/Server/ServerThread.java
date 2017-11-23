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
            this.in=new ObjectInputStream(socket.getInputStream());
            this.out=new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            new ErrorWindow("Algo correu mal");
        }
    }

    void send(Object obj){
        try {
            out.writeObject(obj);
        } catch (IOException e) {
            new ErrorWindow("Erro No Envio");
        }
    }

    Object read(){
        Object obj = null;
        try {
            obj=in.readObject();
        } catch (IOException e) {
            new ErrorWindow("Falha a Ler");
        } catch (ClassNotFoundException e) {
            new ErrorWindow("Classe nao Reconhecida");
        }
        return obj;
    }
}
