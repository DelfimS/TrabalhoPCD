package Server;




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Utilities.ErrorWindow;

public class Server {
    public static final int PORT=5555;
    protected final ArrayList<News_File> repository = File_Handler.getFiles("PATH");
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
                    ErrorWindow.errorWindow("Algo Correu Mal");
                }
                try(Socket socket=s.accept()){
                    createServerThread(s,socket);
                } catch (IOException e) {
                	 ErrorWindow.errorWindow("Algo Correu Mal");
                }
            }

        }

        private void createServerThread(ServerSocket s, Socket socket) {
            connections.add(new ServerThread(socket,s));
        }

    }
}
