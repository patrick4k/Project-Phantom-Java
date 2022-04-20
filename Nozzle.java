/*
Nozzle class
By Patrick Kennedy
Bate Modified: 4/8/22

The nozzle class hold values of the nozzle being simulated, no calculations are performed within the nozzle class
 */

import java.io.Serializable;

public class Nozzle implements Serializable {
    double throatDiameter;
    double exitDiameter;
    double exitAngle;

    public void runNozzleConversion(Boolean fromEngUnits) {
        if (fromEngUnits) {
            this.throatDiameter = throatDiameter/39.3701; // in to m
            this.exitDiameter = exitDiameter/39.3701; // in to m
        }
        else {
            this.throatDiameter = throatDiameter/100; // cm to m
            this.exitDiameter = exitDiameter/100; // cm to m
        }
        this.exitAngle = exitAngle*Math.PI/180; // deg to rad
    }

    public String getDispThroatDiameter(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(throatDiameter*39.3701); // m to in
        }
        else {
            return String.valueOf(throatDiameter*100); // m to cm
        }
    }

    public String getDispExitDiameter(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(exitDiameter*39.3701); // m to in
        }
        else {
            return String.valueOf(exitDiameter*100); // m to cm
        }
    }

    public String getDispExitAngle() {
        return String.valueOf(exitAngle*180/Math.PI);
    }

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
        return exitAngle;
    }

    public void setExitAngle(double exitAngle) {
        this.exitAngle = exitAngle;
    }

    public double getThroatArea() {
        return (Math.PI/4)*Math.pow(this.throatDiameter,2);
    }

}
