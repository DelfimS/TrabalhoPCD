package Server;




import Server.DataTypes.News_File;
import Server.DataTypes.Tarefa;
import Server.Threads.ReceiverThread;
import Server.Threads.ReceiverThreadClient;
import Server.Threads.SenderThread;
import Server.ServerUtil.File_Handler;
import Server.Threads.SenderThreadClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    public static final int PORT = 8080;
    protected final ArrayList<News_File> repository = File_Handler.getFiles("..\\TrabalhoPCD\\news29out");
    private ArrayList<IOThreads> connections = new ArrayList<>();
    private BlockingQueue<Tarefa> tasks= new LinkedBlockingQueue<>();
    private Server server = this;

    void init() {
        ConnectionMaker cm = new ConnectionMaker();
        cm.start();
    }

    public synchronized void removeThread(int i) {
        for (IOThreads io :
                connections) {
            if (io.id == i){
                try {
                    io.socket.close();
                } catch (IOException ignored) {
                }
                connections.remove(io);
            }
        }
    }

    synchronized void addThread(IOThreads ioThreads){
        connections.add(ioThreads);
    }

    public void createTask(String tipo, String request,int id) {
        if (tipo.equals("search")) {
            assert repository != null;
            for (News_File news_file : repository) {
                tasks.offer(new Tarefa(Tarefa.tipo.SEARCH, request, news_file,id));
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
                    ServerSocket serverSocket = s;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (!interrupted) {

                    System.out.println("ServerSocket criado " + s.toString());
                    try {
                        socket = s.accept();
                        System.out.println("Cliente ligado " + socket.toString());
                        createServerThreads(socket, id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    id++;
                }

            }

            private void createServerThreads(Socket socket,int id) {
                IOThreads st;
                try {
                    st = new IOThreads(socket,id);
                } catch (IllegalArgumentException e) {
                    return;
                }
                addThread(st);
                st.init();
            }

    }
    private class IOThreads{
        private ReceiverThread in;
        private SenderThread out;
        private String type;
        int id;
        private Socket socket;

        IOThreads(Socket socket,int id) throws IllegalArgumentException {
            this.socket=socket;
            this.id=id;
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            try {
                outputStream=new ObjectOutputStream(socket.getOutputStream());
                inputStream=new ObjectInputStream(socket.getInputStream());
                type=(String)inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(type.equals("Client")){
                out=new SenderThreadClient(socket,server,id,outputStream);
                in=new ReceiverThreadClient(socket,server,id,inputStream);
            }else if (type.equals("Worker")){
                out=new SenderThread(socket,server,id,outputStream);
                in=new ReceiverThread(socket,server,id,inputStream);
            }else {
                throw new IllegalArgumentException();
            }
        }

        void init(){
            out.start();
            in.start();
        }

        public ReceiverThread getIn() {
            return in;
        }

        public SenderThread getOut() {
            return out;
        }

        public int getId() {
            return id;
        }
    }
}


