package Connection;

import java.util.HashMap;

/**
 * Project Name : TL_crypto
 */
public final class SocketBody {
    private int status = 200;
    private int option = 1;
    private String name;
    private int port;
    private HashMap<String, Object> body = new HashMap<String, Object>();

    public SocketBody(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
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
}
