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

public class Cross extends Grain {
    private double width;
    private double length;

    @Override
    public void calcBurnArea(double regTotal) {
        double burnArea;
        double w = width + 2*regTotal;
        double l;
        double y;
        double Do = getOuterDiameter();
        if (w >= (2.0/3.0)*Do) {
            burnArea = 0;
        }
        else {
            y = (Do - Math.sqrt(Do*Do - w*w))/2;
            l = length - regTotal - y;
            double currentLength = getGrainLength() - (2-getInhibitedEnds())*regTotal;
            burnArea = 8*l*currentLength + (2-getInhibitedEnds())*2*l*l;
        }
        setBurnArea(burnArea);
    }

    @Override
    public void calcPortArea(double regTotal) {
        double portArea;
        double w = width + 2*regTotal;
        double l;
        double y;
        double Do = getOuterDiameter();
        if (w >= (2.0/3.0)*Do) {
            portArea = Math.PI*Do*Do/4;
        }
        else {
            y = (Do - Math.sqrt(Do*Do - w*w))/2;
            l = length - regTotal - y;
            portArea = 4*l*w + w*w;
        }
        setPortArea(portArea);
    }

    @Override
    public double getInitialFreeVolume() {
        return ((4*length*width + width*width)*getGrainLength());
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
