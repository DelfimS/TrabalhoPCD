package DataTransferType;

import java.io.Serializable;

public class RequestMessage implements Serializable{
    private String type;
    private Object content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public RequestMessage(String type, Object content) {

        this.type = type;
        this.content = content;
    }
}
