package Server;




import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 8080;
    protected final ArrayList<News_File> repository = File_Handler.getFiles("..\\TrabalhoPCD\\news29out");
    private ArrayList<IOThreads> connections = new ArrayList<>();
    private ArrayList<Tarefa> tasks = new ArrayList<Tarefa>();
    private Server server = this;
    private ServerSocket serverSocket;

    void init() {
        ConnectionMaker cm = new ConnectionMaker();
        cm.start();
    }

    synchronized void removeThread(int i) {
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

    synchronized void createTask(String tipo, String request) {
        if (tipo.equals("search")) {
            assert repository != null;
            for (News_File news_file : repository) {
                tasks.add(new Tarefa(Tarefa.tipo.SEARCH, request, news_file));
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
                    serverSocket= s;
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
                } catch (InstantiationException e) {
                    return;
                }
                addThread(st);
                st.init();
            }

    }
    private class IOThreads{
        private ReceiverThread in;
        private SenderThread out;
        private boolean workerOrClient;
        int id;
        private Socket socket;

        IOThreads(Socket socket,int id) throws InstantiationException {
            this.socket=socket;
            this.id=id;
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            String test = null;
            try {
                outputStream=new ObjectOutputStream(socket.getOutputStream());
                inputStream=new ObjectInputStream(socket.getInputStream());
                test=(String)inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(test.equals("Client")){
                workerOrClient=true;
            }else if (test.equals("Worker")){
                workerOrClient=false;
            }else {
                throw new InstantiationException();
            }
            out=new SenderThread(socket,server,id,outputStream);
            in=new ReceiverThread(socket,server,id,inputStream);
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


