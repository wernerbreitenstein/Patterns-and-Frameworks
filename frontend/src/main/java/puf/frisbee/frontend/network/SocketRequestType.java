package puf.frisbee.frontend.network;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum for the request type used for socket connection.
 */
public enum SocketRequestType {
    /**
     * Type used for socket init.
     */
    @JsonProperty("INIT")
    INIT,
    /**
     * Type used for socket disconnect.
     */
    @JsonProperty("DISCONNECT")
    DISCONNECT,
    /**
     * Type used for ready status.
     */
    @JsonProperty("READY")
    READY,
    /**
     * Type used for game running status.
     */
    @JsonProperty("GAME_RUNNING")
    GAME_RUNNING,
    /**
     * Type used for character movement.
     */
    @JsonProperty("MOVE")
    MOVE,
    /**
     * Type used for frisbee throw.
     */
    @JsonProperty("THROW")
    THROW
}
