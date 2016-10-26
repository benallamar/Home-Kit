package Connection;

import java.util.HashMap;

/**
 * Project Name : TL_crypto
 */
public final class SocketBody {
    private int status = 200;
    private int option = 1;
    private String name;
    private int from;
    private int to;
    private HashMap<String, Object> body = new HashMap<String, Object>();

    public SocketBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFromPort() {
        return from;
    }

    public int getToPort() {
        return to;
    }

    public void setFromPort(int port) {
        //We set the port Sender
        from = port;
    }

    public void setToPort(int port) {
        //We set the port of destination
        to = port;
    }

    public boolean isSuccess() {
        return status == 200;
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    public Object getKey(String key) {
        return body.get(key);
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        int[]
        this.option = option;
    }

    public void setSuccess() {
        this.status = 200;
    }

    public void setFailed() {
        this.status = 500;
    }

    public String toString() {
        return body.toString();
    }

    public void setNewBody() {
        body = new HashMap<String, Object>();
    }

    public void setKey(String key, Object object) {
        body.put(key, object);
    }

    public void setHeader(SocketBody request) {
        to = request.from;
        from = request.to;
    }
}
