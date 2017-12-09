package Client.Task;

import Client.ConnectionHandler;

import java.util.List;

public class GetTextFromTitle extends Task{

    public GetTextFromTitle(ConnectionHandler connectionHandler) {
        super(connectionHandler);
    }

    @Override
    protected void process(List<String> chunks) {
        connectionHandler.getGUI().getFile_Text().setText(chunks.get(0));
    }


    @Override
    protected List<String> doInBackground() throws Exception {
        readText();
        return null;
    }
}