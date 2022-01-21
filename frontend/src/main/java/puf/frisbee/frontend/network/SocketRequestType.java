package puf.frisbee.frontend.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SocketRequestType {
    @JsonProperty("INIT")
    INIT,
    @JsonProperty("DISCONNECT")
    DISCONNECT,
    @JsonProperty("READY")
    READY,
    @JsonProperty("GAME_RUNNING")
    GAME_RUNNING,
    @JsonProperty("MOVE")
    MOVE,
    @JsonProperty("THROW")
    THROW,
    ERROR;
}
