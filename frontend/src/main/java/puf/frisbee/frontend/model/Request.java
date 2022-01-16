package puf.frisbee.frontend.model;

import java.io.Serializable;

public class Request implements Serializable {
    public String type;
    public Object value;

    public Request(String type, Object value) {
        this.type = type;
        this.value = value;
    }
}
