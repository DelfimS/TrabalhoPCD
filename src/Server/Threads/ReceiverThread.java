package Server.Threads;

import Server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverThread extends Thread{
    private int id;
    private Socket socket;
    protected ObjectInputStream in;
    protected Server server;

    public ReceiverThread(Socket socket, Server server, int id, ObjectInputStream in) {
        this.in=in;
        this.socket = socket;
        this.server=server;
        this.id =id;
    }

    public int getIdentity() {
        return id;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    void destroyThread() {
        server.removeThread(this.id);
        this.interrupt();
    }
}
