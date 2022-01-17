package puf.frisbee.frontend.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SocketRequest.class)
public class SocketRequest {
    private SocketRequestType requestType;
    private String value;

    public SocketRequest(){}

    public SocketRequest(SocketRequestType requestType, String value) {
        this.requestType = requestType;
        this.value = value;
    }

    public SocketRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(SocketRequestType requestType) {
        this.requestType = requestType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
