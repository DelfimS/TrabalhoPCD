package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SenderThread extends Thread{
    protected Socket socket;
    protected ObjectOutputStream out;
    protected Server server;
    private  int id;

    SenderThread(Socket socket, Server server, int id) {
        this.socket = socket;
        this.server=server;
        this.id = id;
        doConnections();
    }

    private void doConnections() {
        try {
            this.out=new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(){

    }

    private void destroyThread() {
        server.removeThread(this.id);
        this.interrupt();
    }


    @Override
    public void run() {
        while (true){
            try {
                wait();
                send();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
