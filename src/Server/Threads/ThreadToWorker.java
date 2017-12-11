package Server.Threads;

import DataTransferType.RequestMessage;
import Server.DataTypes.Tarefa;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadToWorker extends ServerThread{
    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public ThreadToWorker(Server server,Socket socket,ObjectInputStream in,ObjectOutputStream out,int id){
        super(id);
        this.server = server;
        this.in=in;
        this.out=out;
        this.socket=socket;
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
                this.interrupt();
                server.removeThreadWorker(this);
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private synchronized void serve() throws InterruptedException, IOException, ClassNotFoundException {
        Tarefa tarefa=server.getTask();
        while (tarefa==null){
            wait();
            tarefa=server.getTask();
        }
        RequestMessage rm=new RequestMessage(tarefa.getRequest(),tarefa.getNews_file().toString());
        send(rm);
        server.addToDoneList(tarefa,(Integer)in.readObject());
    }

    @Override
    public synchronized void notifyThread(){
        notify();
    }
}
