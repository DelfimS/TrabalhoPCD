package Client;

import Client.Task.GetSearchListWorker;
import Client.Task.GetTextFromTitle;
import Server.Server;
import Utilities.ErrorWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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

    synchronized void requestSearch(String search){
        new GetSearchListWorker(this,search).execute();
    }

    void requestText(String title) {
        new GetTextFromTitle(this,title).execute();
    }

}
