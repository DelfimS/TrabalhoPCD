package Client;

import DataTransferType.RequestMessage;
import Server.Server;
import Utilities.ErrorWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public Client_GUI getGUI() {
        return GUI;
    }

    private Client_GUI GUI;

    ConnectionHandler(Client_GUI gui) {
        GUI = gui;
    }

    protected ConnectionHandler init(){
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

    synchronized void requestSearch(String search,Client_GUI GUI){
        try {
            GUI.getTitle_List().clear();
            RequestMessage rm=new RequestMessage("search",search);
            out.writeObject(rm);
            //new GetSearchListWorker(this).execute();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorWindow.init("Falha no envio",GUI.getFrame());
        }
    }

    void requestText(String title, Client_GUI GUI) {
        try {
            String [] strings=title.split("=>");
            RequestMessage rm=new RequestMessage("text",strings[1]);
            out.writeObject(rm);
            //new GetTextFromTitle(this).execute();
        } catch (IOException e) {
            ErrorWindow.init("Falha no envio", GUI.getFrame());
        }
    }

    protected void readText() throws IOException, ClassNotFoundException {
        String s=(String)this.getIn().readObject();
        this.getGUI().getFile_Text().setText(s);
    }

    protected List<String> readList() throws IOException, ClassNotFoundException {
        ArrayList<String> list=(ArrayList<String>)this.getIn().readObject();
        for (String str:list) {
            System.out.println(str);
            this.getGUI().getTitle_List().addElement(str);
        }
        return list;
    }
}
