package puf.frisbee.frontend.core;

public final class Constants {
    private Constants() {
    }

    /**
     * The window width of the game.
     */
    public static final int SCENE_WIDTH = 1280;

    /**
     * The window height of the game.
     */
    public static final int SCENE_HEIGHT = 720;

    /**
     * The radius if the frisbee on the scene. Needed for calculations.
     */
    public static final double FRISBEE_RADIUS = 30;

    /**
     * The character height on the scene. Needed for calculations.
     * Both characters have the same height.
     */
    public static final double CHARACTER_HEIGHT = 200;

    /**
     * The character width on the scene. Needed for calculations.
     * Both characters have the same width.
     */
    public static final double CHARACTER_WIDTH = 172;

    /**
     * The x position of the right character's left hand.
     */
    public static final double CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X = 15;
    /**
     * The y position of the right character's left hand.
     */
    public static final double CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y = 106;
    /**
     * The x position of the right character's right hand.
     */
    public static final double CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X = 157;
    /**
     * The y position of the right character's right hand.
     */
    public static final double CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y = 97;

    /**
     * The x position of the left character's left hand.
     */
    public static final double CHARACTER_LEFT_CATCHING_ZONE_LEFT_X = 35;
    /**
     * The y position of the left character's left hand.
     */
    public static final double CHARACTER_LEFT_CATCHING_ZONE_LEFT_Y = 70;
    /**
     * The x position of the left character's right hand.
     */
    public static final double CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X = 136;
    /**
     * The y position of the left character's right hand.
     */
    public static final double CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y = 76;

    /**
     * The radius of the character's hand.
     * Both characters have the same hand radius.
     */
    public static final double CHARACTER_CATCHING_RADIUS = 21;
}
