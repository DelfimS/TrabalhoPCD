package Server.Threads;

import DataTransferType.RequestMessage;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThreadToClient extends ServerThread{
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socket;
    Server server;
    ArrayList<String> ToSend=null;
    boolean interrupted=false;
    int verefiedNews=0;

    public ThreadToClient(Server server,Socket socket,ObjectInputStream in,ObjectOutputStream out,int id){
        super(id);
        this.server = server;
        this.in=in;
        this.out=out;
        this.socket=socket;
    }

    @Override
    public void run() {
        while (!interrupted){
            serve();
        }
    }

    private void serve() {
        try {
            System.out.println("a escuta");
            RequestMessage rm=(RequestMessage)in.readObject();
            System.out.println("content read");
            if (rm.getType().equals("search")) {
                System.out.println("search");
                ToSend=new ArrayList<>();
                server.createTask((String) rm.getContent(), id);
                server.notifyThreads();
                waitforWorker();
                System.out.println("ready");
                sortList(ToSend);
                send(ToSend);
                System.out.println("search done");
                verefiedNews=0;
            }
            else if (rm.getType().equals("text"))
                send(server.getText((String) rm.getContent()));
            // add some exception
        } catch (IOException | ClassNotFoundException e) {
            this.interrupt();
            interrupted=true;
            server.removeThread(this);
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void sortList(ArrayList<String> toSend) {
        Collections.sort(toSend, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] str1=o1.split("=>");
                String[] str2=o2.split("=>");
                int num1=Integer.parseInt(str1[0]);
                int num2=Integer.parseInt(str2[0]);
                return num2-num1;
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

    public ArrayList<String> getToSend() {
        return ToSend;
    }

    public synchronized void addToSend(String news_file, int num) {
        if(num>0)
        ToSend.add(num+"=>"+news_file);
        IncreaseVerefiedNews();
        if (verefiedNews==server.getNewsListSize()){
            notifyThread();
        }
    }


    public int getVerefiedNews() {
        return verefiedNews;
    }

    public void IncreaseVerefiedNews() {
        this.verefiedNews++;
    }

    @Override
    public synchronized void notifyThread(){
        notifyAll();
    }
}
