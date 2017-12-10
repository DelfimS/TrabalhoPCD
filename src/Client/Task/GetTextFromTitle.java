package Client.Task;

import Client.ConnectionHandler;
import DataTransferType.RequestMessage;

import javax.swing.*;
import java.util.List;

public class GetTextFromTitle extends SwingWorker<List<String>,String> {
    private final ConnectionHandler connectionHandler;
    private final String title;

    public GetTextFromTitle(ConnectionHandler connectionHandler, String title) {
        this.connectionHandler=connectionHandler;
        this.title=title;
    }

    @Override
    protected void process(List<String> chunks) {
        connectionHandler.getGUI().getFile_Text().setText("");
        connectionHandler.getGUI().getFile_Text().setText(chunks.get(0));
        connectionHandler.getGUI().getFile_Text().setCaretPosition(0);
    }


    @Override
    protected List<String> doInBackground() throws Exception {
        String [] strings=title.split("=>");
        RequestMessage rm=new RequestMessage("text",strings[1]);
        connectionHandler.getOut().writeObject(rm);
        connectionHandler.getOut().flush();
        String s=(String)connectionHandler.getIn().readObject();
        publish(s);
        connectionHandler.getGUI().setGetTextLock(true);
        return null;
    }
}