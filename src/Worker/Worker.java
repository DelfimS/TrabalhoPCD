package Worker;

import DataTransferType.RequestMessage;
import Server.Server;

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
    private InetAddress IA;


    Worker init(String ia){
        try {
            this.IA=InetAddress.getByName(ia);
            connectToServer();
        } catch (IOException e) {
            tryReconnect();
        }
        return this;
    }
    private void connectToServer() throws IOException {
        try {
            this.socket=new Socket(IA,Server.PORT);
        } catch (IOException e) {
            System.out.println("Falha na criacao do socket");
        }
        this.out=new ObjectOutputStream(socket.getOutputStream());
        this.in=new ObjectInputStream(socket.getInputStream());
        out.writeObject("Worker");
    }
    private void tryReconnect(){
        try {
            connectToServer();
        } catch (IOException e) {
            System.out.println("Conexao Perdida");
        }
    }

    @Override
    public void run() {
        String searchWord;
        String text;
        while (true) {
            try {
                RequestMessage rm = (RequestMessage)in.readObject();
                searchWord=rm.getType();
                text= rm.getContent();
                out.writeObject(search(searchWord,text));
            } catch (IOException e) {
                tryReconnect();
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
        sc.close();
        return count;
    }

}
