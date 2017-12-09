package Client.Task;


import Client.ConnectionHandler;

import java.util.List;

public class GetSearchListWorker extends Task{
    public GetSearchListWorker(ConnectionHandler connectionHandler) {
        super(connectionHandler);
    }

    @Override
    protected List<String> doInBackground() throws Exception {
        return readList();
    }

    @Override
    protected void process(List<String> chunks) {
        for (String str : chunks) {
            connectionHandler.getGUI().getTitle_List().addElement(str);
        }
    }
}

