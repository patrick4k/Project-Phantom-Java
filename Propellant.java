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

public class Propellant {
    private double density; // input kg/m3
    private double chamberTemp; // input K
    private double gamma; // input null
    private double burnRateCoeff; // input mm/s/(MPa^n)
    private double burnRateExp; // input null
    private double molarMass; // input g/mol

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getChamberTemp() {
        return chamberTemp;
    }

    public void setChamberTemp(double chamberTemp) {
        this.chamberTemp = chamberTemp;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getBurnRateCoeff() {
        return burnRateCoeff*(1E-3)*Math.pow(1E-6,this.burnRateExp); // convert to m/s/(Pa^n)
    }

    public void setBurnRateCoeff(double burnRateCoeff) {
        this.burnRateCoeff = burnRateCoeff;
    }

    public double getBurnRateExp() {
        return burnRateExp;
    }

    public void setBurnRateExp(double burnRateExp) {
        this.burnRateExp = burnRateExp;
    }

    public double getMolarMass() {
        return molarMass/1000; // convert to kg/mol
    }

    public void setMolarMass(double molarMass) {
        this.molarMass = molarMass;
    }

    public double getSpecificGasConst() {
        return 8314/this.molarMass; // convert to kg*J/K
    }
    
    public double getCstar() {
        return Math.sqrt((this.getSpecificGasConst()*this.getChamberTemp())/(this.getGamma()*Math.pow((2/(this.getGamma()+1)),((this.getGamma()+1)/(this.getGamma()-1)))));
    }

}
