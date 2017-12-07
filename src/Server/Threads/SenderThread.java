package Server.Threads;

import Server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SenderThread extends Thread{
    protected Socket socket;
    protected ObjectOutputStream out;
    protected Server server;
    private  int id;
    protected Object toSend;

    public SenderThread(Socket socket, Server server, int id, ObjectOutputStream out) {
        this.socket = socket;
        this.server=server;
        this.id = id;
        this.out=out;
    }


    void send(Object object){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            out.writeObject(object);
        } catch (IOException e) {
            destroyThread();
            e.printStackTrace();
        }
    }

    void destroyThread() {
        server.removeThread(this.id);
        this.interrupt();
    }

    public void setToSend(Object toSend) {
        this.toSend = toSend;
    }

    @Override
    public void run() {
        while (true){
            send(toSend);
        }
    }
}
