package Worker;

import Server.Server;
import Utilities.ErrorWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Worker extends Thread{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running;


    Worker init(){
        try {
            connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    private void connectToServer() throws IOException {
        InetAddress adress=InetAddress.getByName(null);
        try {
            this.socket=new Socket(adress,Server.PORT);
        } catch (IOException e) {
            System.out.println("Falha na criacao do socket");
        }
        this.out=new ObjectOutputStream(socket.getOutputStream());
        this.in=new ObjectInputStream(socket.getInputStream());
        out.writeObject("Worker");
    }


    @Override
    public void run() {
        while (true) {
            String searchWord="";
            String text="";
            try {
                searchWord=(String) in.readObject();
                text=(String) in.readObject();
                out.writeObject(search(searchWord,text));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    int search(String searchWord,String text){
        int count=0;
        Scanner sc = new Scanner(text);
        while (sc.findInLine(searchWord) != null) {
            count++;
        }
        return count;
    }
}
