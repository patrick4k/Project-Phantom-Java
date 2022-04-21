/*
Test class
By Patrick Kennedy
Date Modified: 4/8/22

Test is a script for testing funcitonality of the motor class, test is later adapted for unit testing requiremtns and implemented as
a quick load feature in the SRMSim Application
 */

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
        testNozzle.setExitDiameter(.0254);
        testNozzle.setThroatDiameter(.009525);

        // Grain (Tubular)
        Tubular testGrain1 = new Tubular();
        testGrain1.setGrainLength(0.12065);
        testGrain1.setInnerDiameter(.015875);
        testGrain1.setOuterDiameter(.04445);
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
        testGrainList.add(testGrain1);
        testGrainList.add(testGrain1);
        testGrainList.add(testGrain1);
        testGrainList.add(testGrain1);

        Motor testMotor = new Motor(testPropellant, testNozzle, testGrainList);
        //testMotor.setPropellant(testPropellant);
        //testMotor.setNozzle(testNozzle);
        //testMotor.setGrainList(testGrainList);
        //testMotor.setMotorVolume((Math.PI/4)*(0.04445)*(4*0.12065));
        testMotor.setAtmPressure(101325);
        testMotor.setDt(1E-3);
        testMotor.setMotorName("myTestMotor");
        return testMotor;
    }

    public static Propellant loadPresetPropellant() {
        Propellant testPropellant = new Propellant();
        testPropellant.setBurnRateExp(0.49);
        testPropellant.setBurnRateCoeff(2.47);
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
    public static ArrayList<Grain> loadPresetCross() {
        // Grain (Cross)
        Cross testGrain = new Cross();
        testGrain.setGrainLength(0.12065);
        testGrain.setWidth(0.04445/10);
        testGrain.setLength((0.04445/2) - 0.001);
        testGrain.setInhibitedEnds(2);
        testGrain.setOuterDiameter(0.04445);

        // Add grains into grain list (2 identical grains in this test motor)
        ArrayList<Grain> testGrainList = new ArrayList<Grain>();
        testGrainList.add(testGrain);
        testGrainList.add(testGrain);
        testGrainList.add(testGrain);
        testGrainList.add(testGrain);

        return testGrainList;
    }
    public static ArrayList<Grain> loadPresetBates() {
        // Grain (Tubular)
        Tubular testGrain = new Tubular();
        testGrain.setGrainLength(0.12065);
        testGrain.setInnerDiameter(0.015875);
        testGrain.setOuterDiameter(0.04445);
        testGrain.setInhibitedEnds(2);

        // Add grains into grain list (2 identical grains in this test motor)
        ArrayList<Grain> testGrainList = new ArrayList<Grain>();
        testGrainList.add(testGrain);
        testGrainList.add(testGrain);
        testGrainList.add(testGrain);
        testGrainList.add(testGrain);

        return testGrainList;
    }
}
