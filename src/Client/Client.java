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
    private boolean running;

    void init(){
        try {
            connectToServer();
        } catch (IOException e) {
            ErrorWindow.errorWindow("Algo correu mal");
        }
    }
    private void connectToServer() throws IOException {
        InetAddress adress=InetAddress.getByName(null);
        this.socket=new Socket(adress,Server.PORT);
        this.in=new ObjectInputStream(socket.getInputStream());
        this.out=new ObjectOutputStream(socket.getOutputStream());
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
