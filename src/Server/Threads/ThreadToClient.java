package Server.Threads;

import DataTransferType.RequestMessage;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;

public class ThreadToClient extends ServerThread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Server server;
    private ArrayList<String> ToSend=null;
    private boolean interrupted=false;
    private int verefiedNews=0;

    public ThreadToClient(Server server,Socket socket,ObjectInputStream in,ObjectOutputStream out,int id){
        super(id);
        this.server = server;
        this.in=in;
        this.out=out;
        this.socket=socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                serve();
            } catch (IOException | ClassNotFoundException e) {
                this.interrupt();
                server.removeThreadClient(this);
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void serve() throws IOException, ClassNotFoundException {
            RequestMessage rm=(RequestMessage)in.readObject();
            if (rm.getType().equals("search")) {
                ToSend=new ArrayList<>();
                server.createTask(rm.getContent(), id);
                server.notifyWorkers();
                waitforWorker();
                sortList(ToSend);
                send(ToSend);
                verefiedNews=0;
            }
            else if (rm.getType().equals("text"))
                send(server.getText(rm.getContent()));
    }

    private void sortList(ArrayList<String> toSend) {
        toSend.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] str1 = o1.split("=>");
                String[] str2 = o2.split("=>");
                int num1 = Integer.parseInt(str1[0]);
                int num2 = Integer.parseInt(str2[0]);
                return num2 - num1;
            }
        });
    }

    private synchronized void waitforWorker() {
        while (getVerefiedNews()!=server.getNewsListSize()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(Object object){
        try {
            out.flush();
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void addToSend(String news_file, int num) {
        if(num>0)
        ToSend.add(num+"=>"+news_file);
        IncreaseVerefiedNews();
        if (verefiedNews==server.getNewsListSize()){
            notifyThread();
        }
    }


    private int getVerefiedNews() {
        return verefiedNews;
    }

    private void IncreaseVerefiedNews() {
        this.verefiedNews++;
    }

    @Override
    public synchronized void notifyThread(){
        notify();
    }
}
