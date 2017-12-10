package Client.Task;


import Client.ConnectionHandler;
import DataTransferType.RequestMessage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GetSearchListWorker extends SwingWorker<List<String>,String> {
    private final String search;
    private final ConnectionHandler connectionHandler;

    public GetSearchListWorker(ConnectionHandler connectionHandler, String search) {
        this.connectionHandler = connectionHandler;
        this.search=search;
    }

    @Override
    protected List<String> doInBackground() throws Exception {
        connectionHandler.getGUI().getTitle_List().clear();
        //connectionHandler.getGUI().getTitle_List().addElement("A Procurar ...");
        //connectionHandler.getGUI().getTitle_List().clear();
        RequestMessage rm=new RequestMessage("search",search);
        connectionHandler.getOut().writeObject(rm);
        connectionHandler.getOut().flush();
        ArrayList<String> list=(ArrayList<String>)connectionHandler.getIn().readObject();
        connectionHandler.getGUI().getTitle_List().clear();
        for (String str:list) {
            System.out.println(str);
            publish(str);
        }
        System.out.println(list.size());
        connectionHandler.getGUI().setSearchLock(true);
        return list;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String str : chunks) {
            connectionHandler.getGUI().getTitle_List().addElement(str);
            System.out.println(str);
        }
    }
}

