/*
Name: Patrick kennedy
Date: 4/20/22

Nozzle
    - This class holds information on the motor nozzle and its properties

Attributes:
throatDiameter: double
    - Diameter across throat cross-section of the nozzle
exitDiameter: double
    - Diameter across exit cross-section of the nozzle
exitAngle: double
    - Angle of divergence from throat to exit

Methods:
runNozzleConversion(Boolean): void
    - This method converts input values into base SI units which are used for calculations
    - Will convert english units to base SI and non-base SI units to base SI
getDisp_________(Boolean): String
    - Returns display value of attributes, if GUI is in SI units will return SI, if english will return english
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
