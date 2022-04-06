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

public class Grain {
    private double outerDiameter;
    private double grainLength;
    private double inhibitedEnds;
    private double burnArea;
    private double portArea;

    public void calcBurnArea(double regTotal) {
        // abstract
        System.out.println("This Method is Abstract");
    }
    public double getBurnArea() {
        return this.burnArea;
    }

    public  void calcPortArea(double regTotal)  {
        // Abstract
    }

    public double getPortArea() {
        return this.portArea;
    }

    public double getInitialFreeVolume() {
        // Abstract
        System.out.println("Abstract method called");
        return 0;
    }

    public double getOuterDiameter() {
        return outerDiameter;
    }

    public void setOuterDiameter(double outerDiameter) {
        this.outerDiameter = outerDiameter;
    }

    public double getGrainLength() {
        return grainLength;
    }

    public void setGrainLength(double grainLength) {
        this.grainLength = grainLength;
    }

    public double getInhibitedEnds() {
        return inhibitedEnds;
    }

    public void setInhibitedEnds(double inhibitedEnds) {
        this.inhibitedEnds = inhibitedEnds;
    }

    public void setBurnArea(double burnArea) {
        this.burnArea = burnArea;
    }

    public void setPortArea(double portArea) {
        this.portArea = portArea;
    }

}
