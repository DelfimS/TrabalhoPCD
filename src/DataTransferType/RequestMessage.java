package DataTransferType;

import java.io.Serializable;

public class RequestMessage implements Serializable{
    private String type;
    private String content;

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public RequestMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }
}
