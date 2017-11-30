package Server;

public class Tarefa {
    private String request;
    private Enum tipo;
    private boolean done=false;
    public enum tipo{
        SEARCH,GET_TEXT
    }
    Tarefa(Enum<tipo> tipo,String request){
        this.tipo=tipo;
        this.request=request;
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
}
