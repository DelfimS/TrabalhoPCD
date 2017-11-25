package Server;




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Utilities.ErrorWindow;

public class Server {
    public static final int PORT=5555;
    protected final ArrayList<News_File> repository = File_Handler.getFiles("C:\\Users\\DElfim\\IdeaProjects\\TrabalhoPCD\\news29out");
    private ArrayList<Thread> connections=new ArrayList<>();


    public void init(){
        ConnectionMaker cm=new ConnectionMaker();
        cm.start();
    }


    private class ConnectionMaker extends Thread{
        boolean interrupted=false;
        @Override
        public void run() {
            ServerSocket s = null;
            try {
                s=new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
                ErrorWindow.init("Algo Correu Mal");
            }
            while (!interrupted) {

                System.out.println("ServerSocket criado "+s.toString());
                try(Socket socket=s.accept()){
                    System.out.println("Cliente ligado "+socket.toString());
                    createServerThread(s,socket);
                } catch (IOException e) {
                    e.printStackTrace();
                	 ErrorWindow.init("Algo Correu Mal");
                }
            }

        }

        private void createServerThread(ServerSocket s, Socket socket) {
            ServerThread st=new ServerThread(socket,s);
            connections.add(st);
            st.start();
        }

    }
}
