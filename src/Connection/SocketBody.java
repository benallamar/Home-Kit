package Connection;

import java.util.HashMap;

/**
 * Project Name : TL_crypto
 */
public final class SocketBody {
    private int status = 200;
    private int option = 1;
    private HashMap<String, Object> body = new HashMap<String, Object>();

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
}
