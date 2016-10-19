package Connection;

import java.util.HashMap;

/**
 * Project Name : TL_crypto
 */
public final class SocketBody {
    private int status = 200;
    private int option = 1;
    private HashMap<String, String> body = new HashMap<String, String>();

    public boolean isSuccess() {
        return status == 200;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public void setBody(HashMap<String, String> body) {
        this.body = body;
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
}
