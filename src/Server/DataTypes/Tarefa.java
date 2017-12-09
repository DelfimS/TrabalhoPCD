package Server.DataTypes;

import Server.DataTypes.News_File;
import Server.Server;

public class Tarefa {
    private String request;
    private News_File news_file;
    private int requesterId;
    private boolean done=false;
    public Tarefa(String request, News_File news_file,int id){
        this.request=request;
        this.news_file=news_file;
        requesterId=id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public News_File getNews_file() {
        return news_file;
    }

    public void setNews_file(News_File news_file) {
        this.news_file = news_file;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }
}
