package puf.frisbee.frontend.model;

import puf.frisbee.frontend.core.Constants;

public class LevelModel implements Level {
    /**
     * Left border of the scene. Can be different for each level.
     */
    private final double sceneBoundaryLeft = 80;
    /**
     * Right border of the scene. Can be different for each level.
     */
    private final double sceneBoundaryRight = 80;
    /**
     * Ground height of the scene. Needed for calculating the position of the
     * characters.Can be different for each level.
     */
    private final double groundHeight = 150;
    /**
     * Maximum jump height allowed for characters. Can be different for each
     * level.
     */
    private final double jumpHeight = 100;

    @Override
    public double getInitialCharacterYPosition() {
        return Constants.SCENE_HEIGHT
                - this.groundHeight
                - Constants.CHARACTER_HEIGHT;
    }

    @Override
    public double getInitialCharacterLeftXPosition() {
        return this.sceneBoundaryLeft + 10;
    }

    @Override
    public double getInitialCharacterRightXPosition() {
        return Constants.SCENE_WIDTH
                - this.sceneBoundaryRight
                - Constants.CHARACTER_WIDTH
                - 10;
    }

    @Override
    public double getSceneBoundaryLeft() {
        return this.sceneBoundaryLeft;
    }

    @Override
    public double getSceneBoundaryRight() {
        return Constants.SCENE_WIDTH
                - this.sceneBoundaryRight
                - Constants.CHARACTER_WIDTH;
    }

    @Override
    public double getJumpHeight() {
        return this.jumpHeight;
    }

    @Override
    public double getGroundHeight() {
        return this.groundHeight;
    }
}
