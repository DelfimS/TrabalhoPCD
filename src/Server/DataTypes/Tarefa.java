package Server.DataTypes;

public class Tarefa {
    private String request;
    private News_File news_file;
    private int requesterId;
    public Tarefa(String request, News_File news_file,int id){
        this.request=request;
        this.news_file=news_file;
        requesterId=id;
    }

    public String getRequest() {
        return request;
    }

    public News_File getNews_file() {
        return news_file;
    }

    public int getRequesterId() {
        return requesterId;
    }
}
