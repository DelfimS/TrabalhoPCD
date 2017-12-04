package Server;

public class Tarefa {
    private String request;
    private Enum tipo;
    private News_File news_file;
    private boolean done=false;
    public enum tipo{
        SEARCH,GET_TEXT
    }
    Tarefa(Enum<tipo> tipo,String request,News_File news_file){
        this.tipo=tipo;
        this.request=request;
        this.news_file=news_file;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Enum getTipo() {
        return tipo;
    }

    public void setTipo(Enum tipo) {
        this.tipo = tipo;
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
}
