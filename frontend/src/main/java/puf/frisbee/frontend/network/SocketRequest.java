package puf.frisbee.frontend.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SocketRequest.class)
public class SocketRequest {
    /**
     * The type of the request.
     */
    private SocketRequestType requestType;
    /**
     * The value of the request.
     */
    private String value;

    /**
     * Default constructor. Needed for jackson.
     */
    public SocketRequest() {
    }

    /**
     * Constructs a request with given parameters.
     *
     * @param requestType the type of the request
     * @param value       the value of the request
     */
    public SocketRequest(SocketRequestType requestType, String value) {
        this.requestType = requestType;
        this.value = value;
    }

    /**
     * Returns the type of the request.
     *
     * @return the type of the request
     */
    public SocketRequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the type of the request.
     *
     * @param requestType the type of the request
     */
    public void setRequestType(SocketRequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * Returns the value of the request.
     *
     * @return the value of the request
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the request.
     *
     * @param value the value of the request
     */
    public void setValue(String value) {
        this.value = value;
    }
}
