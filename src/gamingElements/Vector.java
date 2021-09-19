package gamingElements;

public class Vector {
    double displacementX;
    double displacementY;
    Vector (double x, double y) {
        this.displacementX = x;
        this.displacementY = y;
    }
    double getModule() {
        return Math.sqrt(this.displacementX * this.displacementX + this.displacementY * this.displacementY);
    }

    /* This method takes the degree angle and the expected length, and returns a vector */
    static Vector toVector(double angle, double length) {
        double radianAngle = Math.toRadians(angle);
        double x = length * Math.sin(radianAngle);
        double y = length * Math.cos(radianAngle);
        return new Vector(x, y);
    }
}
