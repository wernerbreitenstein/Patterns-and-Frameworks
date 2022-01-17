package puf.frisbee.frontend.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SocketRequestType {
    @JsonProperty("INIT")
    INIT,
    @JsonProperty("MOVE")
    MOVE,
    @JsonProperty("THROW")
    THROW;
}
