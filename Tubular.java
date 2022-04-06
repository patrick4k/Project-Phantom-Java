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

public class Tubular extends Grain{
    private double innerDiameter;

    @Override
    public void calcBurnArea(double regTotal) {
        double burnArea;
        if (regTotal >= (getOuterDiameter()-this.innerDiameter)/2){
            burnArea = 0;
        }
        else {
            double currentDiameter = this.innerDiameter + 2*regTotal;
            double currentLength = getGrainLength() - (2-getInhibitedEnds())*regTotal;
            burnArea = currentLength*currentDiameter*Math.PI + (2-getInhibitedEnds())*(Math.PI/4)*((Math.pow(getOuterDiameter(),2)-(Math.pow(currentDiameter,2))));
        }
        setBurnArea(burnArea);
    }

    @Override
    public void calcPortArea(double regTotal) {
        double portArea;
        if (regTotal >= (getOuterDiameter()-this.innerDiameter)/2) {
            portArea = (Math.PI/4)*getOuterDiameter();
        }
        else {
            double currentDiameter = this.innerDiameter + 2*regTotal;
            portArea = (Math.PI/4)*currentDiameter;
        }
        setPortArea(portArea);
    }

    @Override
    public double getInitialFreeVolume() {
        return (Math.PI/4)* this.innerDiameter*getGrainLength();
    }

    public double getInnerDiameter() {
        return innerDiameter;
    }

    public void setInnerDiameter(double innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

}
