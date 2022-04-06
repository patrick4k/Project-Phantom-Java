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

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        test test = new test();
        Motor testMotor = test.initMotor();
        testMotor.runSim();
        test.printResults(testMotor);
    }

    public void printResults(Motor testMotor) {
        // Print Results
        System.out.println("Mass Flux " + testMotor.getMassFluxList());
        System.out.println("Time: " + testMotor.getTimeList());

        // Unit Test for burn area calculation
        testMotor.printBurnAreaAtRegTotal(.01431125); // <- uncomment this line to run unit test
        // Unit test results
        // regTotal = 3.8506E-6 => burnArea = 0.012040140648811436
        // regTotal = 0.015 => burnArea = 0.0
        // regTotal = 0.01431125 => burnArea = 0.0
    }

    public Motor initMotor() {
        // Propellant
        Propellant testPropellant = new Propellant();
        testPropellant.setBurnRateCoeff(2.47);
        testPropellant.setBurnRateExp(0.49);
        testPropellant.setChamberTemp(2159);
        testPropellant.setDensity(1581.8);
        testPropellant.setGamma(1.2562);
        testPropellant.setMolarMass(21.969);

        // Nozzle
        Nozzle testNozzle = new Nozzle();
        testNozzle.setExitAngle(15);
        testNozzle.setExitDiameter(0.0254);
        testNozzle.setThroatDiameter(0.009525);

        // Grain (Tubular)
        Tubular testGrain1 = new Tubular();
        testGrain1.setGrainLength(0.12065);
        testGrain1.setInnerDiameter(0.015875);
        testGrain1.setOuterDiameter(0.04445);
        testGrain1.setInhibitedEnds(2);

        // Grain (Cross)
        Cross testGrain2 = new Cross();
        testGrain2.setGrainLength(0.12065);
        testGrain2.setWidth(0.04445/10);
        testGrain2.setLength((0.04445/2) - 0.001);
        testGrain2.setInhibitedEnds(2);
        testGrain2.setOuterDiameter(0.04445);

        // Add grains into grain list (2 identical grains in this test motor)
        ArrayList<Grain> testGrainList = new ArrayList<Grain>();
        testGrainList.add(testGrain2);
        testGrainList.add(testGrain2);
        testGrainList.add(testGrain2);
        testGrainList.add(testGrain2);

        Motor testMotor = new Motor(testPropellant, testNozzle, testGrainList);
        //testMotor.setPropellant(testPropellant);
        //testMotor.setNozzle(testNozzle);
        //testMotor.setGrainList(testGrainList);
        //testMotor.setMotorVolume((Math.PI/4)*(0.04445)*(4*0.12065));
        testMotor.setAtmPressure(101325);
        testMotor.setDt(1E-3);
        return testMotor;
    }

    public static Propellant loadPresetPropellant() {
        Propellant testPropellant = new Propellant();
        testPropellant.setBurnRateCoeff(2.47);
        testPropellant.setBurnRateExp(0.49);
        testPropellant.setChamberTemp(2159);
        testPropellant.setDensity(1581.8);
        testPropellant.setGamma(1.2562);
        testPropellant.setMolarMass(21.969);
        return testPropellant;
    }
    public static Nozzle loadPresetNozzle() {
        // Nozzle
        Nozzle testNozzle = new Nozzle();
        testNozzle.setExitAngle(15);
        testNozzle.setExitDiameter(0.0254);
        testNozzle.setThroatDiameter(0.009525);
        return testNozzle;
    }
    public static ArrayList<Grain> loadPresetGrains() {
        // Grain (Tubular)
        Tubular testGrain1 = new Tubular();
        testGrain1.setGrainLength(0.12065);
        testGrain1.setInnerDiameter(0.015875);
        testGrain1.setOuterDiameter(0.04445);
        testGrain1.setInhibitedEnds(2);

        // Grain (Cross)
        Cross testGrain2 = new Cross();
        testGrain2.setGrainLength(0.12065);
        testGrain2.setWidth(0.04445/10);
        testGrain2.setLength((0.04445/2) - 0.001);
        testGrain2.setInhibitedEnds(2);
        testGrain2.setOuterDiameter(0.04445);

        // Add grains into grain list (2 identical grains in this test motor)
        ArrayList<Grain> testGrainList = new ArrayList<Grain>();
        testGrainList.add(testGrain2);
        testGrainList.add(testGrain2);
        testGrainList.add(testGrain2);
        testGrainList.add(testGrain2);

        return testGrainList;
    }

}
