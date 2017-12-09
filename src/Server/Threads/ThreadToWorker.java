package Server.Threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Server.DataTypes.Tarefa;
import Server.Server;

public class ThreadToWorker extends ServerThread{
    private Server server;
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socket;
    private int id;

    public ThreadToWorker(Server server,Socket socket,ObjectInputStream in,ObjectOutputStream out,int id){
        this.server = server;
        this.in=in;
        this.out=out;
        this.socket=socket;
        this.id = id;
    }

    private void send(Object object){
        try {
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                serve();
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void serve() throws InterruptedException, IOException, ClassNotFoundException {
        Tarefa tarefa=server.getTask();
        while (tarefa==null){
            wait();
            tarefa=server.getTask();
        }
        send(tarefa.getRequest());
        send(tarefa.getNews_file().toString());
        server.addToDoneList(tarefa,(Integer)in.readObject());
    }

    @Override
    public synchronized void notifyThread(){
        notifyAll();
    }
}
