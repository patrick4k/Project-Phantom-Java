// ***************************************************************
// Name: Patrick Kennedy
// Date: 03/25/22
//
// Open test.java to run simulation
//  test.java will:
//      - create a motor object with properties of the test motor within the requirement and testing document
//      - run a simulation of the internal ballistics of the test motor
//      - output the chamber pressure in array form through the console output
//      - conduct a unit test to test requirement 1
//      - this version supports multi grain motors, though is restricted to tubular grain shapes
//
//  Classes and short description:
//  - Motor.java
//      - Where the simulation occurs, the motor class puts all other classes together to form a motor
//  - Propellant.java
//      - Holds properties of the propellant used. Very little calculation within propellant class, main purpose is to feed into the motor class
//  - Nozzle.java
//      - Holds properties of the nozzle. Very little calculation within nozzle class, main purpose is to feed into the motor class
//  - Grain.java
//      - Parent class of all grain types.
//      - Abstract attributes and methods
//  - Tubular.java
//      - Child of grain.
//      - Holds properties of grain geometry and methods to calculate burn area, port area, and initial free volume
//
// ***************************************************************

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
