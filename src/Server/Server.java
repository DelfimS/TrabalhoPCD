package Server;




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Utilities.ErrorWindow;

public class Server {
    public static final int PORT=8080;
    protected final ArrayList<News_File> repository = File_Handler.getFiles("..\\TrabalhoPCD\\news29out");
    private ArrayList<Thread> connections=new ArrayList<>();
    private  ArrayList<Tarefa> tasks=new ArrayList<Tarefa>();
    private Server server=this;

    void init(){
        ConnectionMaker cm=new ConnectionMaker();
        cm.start();
    }
    void removeThread(ServerThread s){
        connections.remove(s);
    }
    void createTask(String tipo,String request){
            if(tipo.equals("search")){

            }else {

            }
    }
    private class ConnectionMaker extends Thread{
        boolean interrupted=false;
        @Override
        public void run() {
            ServerSocket s = null;
            Socket socket;
            try {
                s=new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!interrupted) {

                System.out.println("ServerSocket criado "+s.toString());
                try{
                    socket=s.accept();
                    System.out.println("Cliente ligado "+socket.toString());
                    createServerThread(s,socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void createServerThread(ServerSocket s, Socket socket) {
            ServerThread st=new ServerThread(socket,s,server);
            connections.add(st);
            st.start();
        }

    }

}
