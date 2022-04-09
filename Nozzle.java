/*
Nozzle class
By Patrick Kennedy
Bate Modified: 4/8/22

The nozzle class hold values of the nozzle being simulated, no calculations are performed within the nozzle class
 */

public class Nozzle {
    double throatDiameter;
    double exitDiameter;
    double exitAngle;

    public double getThroatDiameter() {
        return throatDiameter;
    }

    public void setThroatDiameter(double throatDiameter) {
        this.throatDiameter = throatDiameter;
    }

    public double getExitDiameter() {
        return exitDiameter;
    }

    public void setExitDiameter(double exitDiameter) {
        this.exitDiameter = exitDiameter;
    }

    public double getExitAngle() {
        return exitAngle*Math.PI/180; // convert to rad
    }

    public void setExitAngle(double exitAngle) {
        this.exitAngle = exitAngle;
    }

    public double getThroatArea() {
        return (Math.PI/4)*Math.pow(this.throatDiameter,2);
    }
}
