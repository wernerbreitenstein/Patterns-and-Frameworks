package puf.frisbee.frontend.model;

public class FrisbeeParameter {
    private double frisbeeSpeedX;
    private double frisbeeSpeedY;

    public FrisbeeParameter(double frisbeeSpeedX, double frisbeeSpeedY) {
        this.frisbeeSpeedX = frisbeeSpeedX;
        this.frisbeeSpeedY = frisbeeSpeedY;
    }

    /**
     * Gets the frisbee speed x value.
     * @return the frisbee speed x value
     */
    public double getFrisbeeSpeedX() {
        return frisbeeSpeedX;
    }

    /**
     * Sets the frisbee speed x value.
     * @param frisbeeSpeedX the frisbee speed x value
     */
    public void setFrisbeeSpeedX(double frisbeeSpeedX) {
        this.frisbeeSpeedX = frisbeeSpeedX;
    }

    /**
     * Gets the frisbee speed y value.
     * @return the frisbee speed y value
     */
    public double getFrisbeeSpeedY() {
        return frisbeeSpeedY;
    }

    /**
     * Sets the frisbee speed y value.
     * @param frisbeeSpeedY the frisbee speed y value
     */
    public void setFrisbeeSpeedY(double frisbeeSpeedY) {
        this.frisbeeSpeedY = frisbeeSpeedY;
    }

    @Override
    public String toString() {
        return this.frisbeeSpeedX + "-" + this.frisbeeSpeedY;
    }

    /**
     * Takes a string and converts it to a FrisbeeParameterObject if possible
     * @param frisbeeParameter String that should be converted to a frisbee parameter object
     * @return FrisbeeParameter object if string could be converted
     * @throws IllegalArgumentException if string can not be converted
     */
    public static FrisbeeParameter stringToObject(String frisbeeParameter) throws IllegalArgumentException {
        String[] parameter = frisbeeParameter.split("-");
        if(parameter.length != 2) {
            throw new IllegalArgumentException("This string can not be converted to a frisbee parameter object.");
        }

        return new FrisbeeParameter(Double.parseDouble(parameter[0]), Double.parseDouble(parameter[1]));
    }
}
