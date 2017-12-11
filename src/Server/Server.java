package Server;




import Server.DataTypes.News_File;
import Server.DataTypes.Tarefa;
import Server.ServerUtil.File_Handler;
import Server.Threads.ServerThread;
import Server.Threads.ThreadToClient;
import Server.Threads.ThreadToWorker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    public static final int PORT = 8080;
    private final ArrayList<News_File> repository;
    private ArrayList<ServerThread> workers = new ArrayList<>();
    private ArrayList<ThreadToClient> clients = new ArrayList<>();
    private BlockingQueue<Tarefa> tasks= new LinkedBlockingQueue<>();
    private Server server = this;

    Server(String path){
        this.repository=File_Handler.getFiles(path);
    }

    void init() {
        ConnectionMaker cm = new ConnectionMaker();
        cm.start();
    }

    public synchronized void removeThreadClient(Thread thread) {clients.remove(thread);}
    public synchronized void removeThreadWorker(Thread thread){workers.remove(thread);}

    private void addClient(ThreadToClient thread){
        clients.add(thread);
    }
    private void addWorker(ThreadToWorker thread){workers.add(thread);}

    public void notifyWorkers(){
        for (ServerThread s : workers) {
            s.notifyThread();
        }
    }

    public void notifyById(int id){
        for (ServerThread s : clients) {
            if (s.getIdentity()==id)s.notifyThread();
        }
    }

    public void createTask(String request,int id) {
            assert repository != null;
            for (News_File news_file : repository) {
                tasks.offer(new Tarefa(request,news_file,id));
            }
    }

    public String getText(String content) {
        if (repository != null) {
            for (News_File nf :repository) {
                if (nf.getTitle().equals(content))return nf.getShowText();
            }
        }
        return null;
    }

    public Tarefa getTask() {
        return tasks.poll();
    }

    public int getNewsListSize(){
        return repository.size();
    }

    public void addToDoneList(Tarefa t,int num){
        for (ServerThread thread : clients) {
            if (t.getRequesterId()==thread.getIdentity()){
                ((ThreadToClient)thread).addToSend(t.getNews_file().getTitle(),num);
                break;
            }
        }
    }
    private class ConnectionMaker extends Thread {
            boolean interrupted = false;

            @Override
            public void run() {
                ServerSocket s = null;
                Socket socket;
                int id=0;
                try {
                    s = new ServerSocket(PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s!=null) {
                    while (!interrupted) {

                        System.out.println("ServerSocket criado " + s.toString());
                        try {
                            socket = s.accept();
                            System.out.println("Cliente ligado " + socket.toString());
                            createServerThreads(socket,id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        id++;
                    }
                }else{
                    System.out.println("ServerSocket creation error");
                }

            }

            private void createServerThreads(Socket socket,int id) {
                ObjectOutputStream out;
                ObjectInputStream in;
                String type;
                try {
                    out=new ObjectOutputStream(socket.getOutputStream());
                    in=new ObjectInputStream(socket.getInputStream());
                    type=(String) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    if (type.equals("Client")) {
                        ThreadToClient threadToClient=new ThreadToClient(server, socket, in, out, id);
                        addClient(threadToClient);
                        threadToClient.start();
                    }
                    else if (type.equals("Worker")){
                        ThreadToWorker threadToWorker=new ThreadToWorker(server, socket, in, out, id);
                        addWorker(threadToWorker);
                        threadToWorker.start();
                    }
                } catch (IllegalArgumentException e) {
                }
            }

    }
}


