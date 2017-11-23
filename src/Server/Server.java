package Server;


import Utilities.ErrorWindow;
import Utilities.File_Handler;
import Utilities.News_File;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT=5555;
    protected final ArrayList<News_File> repository = new File_Handler("PATH").getFiles();
    protected ArrayList<Thread> connections=new ArrayList<>();


    public void init(){
        ConnectionMaker cm=new ConnectionMaker();
        cm.start();

    }


    private class ConnectionMaker extends Thread{
        boolean interrupted=false;
        @Override
        public void run() {
            while (!interrupted) {
                ServerSocket s = null;
                try {
                    s=new ServerSocket(PORT);
                } catch (IOException e) {
                    new ErrorWindow("Algo Correu Mal");
                }
                try(Socket socket=s.accept()){
                    createServerThread(s,socket);
                } catch (IOException e) {
                    new ErrorWindow("Algo Correu Mal");
                }
            }

        }

        private void createServerThread(ServerSocket s, Socket socket) {
            connections.add(new ServerThread(socket,s));
        }

    }
}
