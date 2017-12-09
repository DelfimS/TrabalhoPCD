package Server.Threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import Server.Server;

public class ThreadToClient extends ServerThread{
    int id;
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socket;
    Server server;
    ArrayList<String> ToSend=null;
    int verefiedNews=0;

    public ThreadToClient(Server server,Socket socket,ObjectInputStream in,ObjectOutputStream out,int id){
        this.id=id;
        this.server = server;
        this.in=in;
        this.out=out;
        this.socket=socket;
    }

    @Override
    public void run() {
        while (true){
            serve();
        }
    }

    private void serve() {
        String type;
        String content;
        try {
            System.out.println("a escuta");
            type=(String) in.readObject();
            content=(String) in.readObject();
            System.out.println("content read");
            if (type.equals("Search")) {
                System.out.println("search");
                ToSend=new ArrayList<>();
                server.createTask(content, id);
                server.notifyThreads();
                waitforWorker();
                System.out.println("ready");
                Collections.sort(ToSend);
                Collections.reverse(ToSend);
                send("list");
                send(ToSend);
                System.out.println("search done");
                verefiedNews=0;
            }
            else if (type.equals("GetText"))
                send(server.getText(content));
            // add some exception
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        verefiedNews++;
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
