package Client.Task;

import Client.ConnectionHandler;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Task extends SwingWorker<List<String>,String>{
    protected ConnectionHandler connectionHandler;
    Task(ConnectionHandler connectionHandler){
        this.connectionHandler=connectionHandler;
    }

    protected void readText() throws IOException, ClassNotFoundException {
        String s=(String)connectionHandler.getIn().readObject();
        publish(s);
    }

    protected List<String> readList() throws IOException, ClassNotFoundException {
        ArrayList<String> list=(ArrayList<String>)connectionHandler.getIn().readObject();
        for (String str:list) {
            System.out.println(str);
            publish(str);
        }
        return list;
    }




}
