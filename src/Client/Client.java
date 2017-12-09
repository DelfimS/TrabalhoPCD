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
import java.util.List;

public class Client extends SwingWorker<ArrayList<String>, String>{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running=true;
    private Client_GUI GUI;


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
        out.writeObject("Client");
    }

    synchronized void requestSearch(String search){
        try {
            GUI.getTitle_List().clear();
            out.writeObject("Search");
            out.flush();
            out.writeObject(search);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorWindow.init("Falha no envio",GUI.getFrame());
        }
    }

    void requestText(String title){
        try{
            out.flush();
            out.writeObject("GetText");
            out.flush();
            out.writeObject(title);
            out.flush();
        } catch (IOException e) {
            ErrorWindow.init("Falha no envio",GUI.getFrame());
        }
    }
	@Override
	protected ArrayList<String> doInBackground() throws Exception {
        while (running){
            String s=(String) in.readObject();
            if (s.equals("list")){
                System.out.println("lista recebida");
                readList();
            }else{
                readText();
            }
        }
		return null;
	}

    @Override
    protected void process(List<String> chunks) {
        GUI.getTitle_List().addElement(String.valueOf(chunks));
    }

    private void readText() throws IOException, ClassNotFoundException {
        String s=(String)in.readObject();
        publish(s);
    }

    private void readList() throws IOException, ClassNotFoundException {
        ArrayList<String> list=(ArrayList<String>)in.readObject();
        for (String str:list) {
            System.out.println(str);
            publish(str);
        }
    }
}
