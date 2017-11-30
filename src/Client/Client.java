package Client;

import Server.Server;
import Utilities.ErrorWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends SwingWorker<ArrayList<String>, String>{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running;
    private Client_GUI GUI;
    private boolean searchLock=false;

    Client(Client_GUI GUI){
        this.GUI=GUI;
    }

    Client init(){
        try {
            connectToServer();
        } catch (IOException e) {
            ErrorWindow.init("Algo correu mal a ligar ao server",GUI.getFrame());
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
    }

    synchronized void requestSearch(String search){
        try {
            out.writeObject("search");
            out.writeObject(search);
        } catch (IOException e) {
            e.printStackTrace();
            ErrorWindow.init("Falha no envio",GUI.getFrame());
        }
    }

    void requestText(String title){
        try{
            out.writeObject("getText");
            out.writeObject(title);
        } catch (IOException e) {
            ErrorWindow.init("Falha no envio",GUI.getFrame());
        }
    }
	@Override
	protected ArrayList<String> doInBackground() throws Exception {
        while (running){
            String s=(String) in.readObject();
            if (s.equals("list")){
                readList();
            }else{
                readText();
            }
        }
		return null;
	}

    private void readText() throws IOException, ClassNotFoundException {
        String s=(String)in.readObject();
    }

    private void readList() throws IOException, ClassNotFoundException {
        ArrayList<String> list=(ArrayList<String>)in.readObject();
    }
}
