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
import java.util.Collections;
import java.util.List;

public class Motor {
    // create objects
    private Propellant propellant = new Propellant();
    private Nozzle nozzle = new Nozzle();
    //private Grain[] grainList = new Grain[0];
    private ArrayList<Grain>  grainList;

    private boolean SIUnits = true;
    private String thrustUnits = "N";
    private String pressureUnits = "MPa";
    private String massUnits = "kg";
    private String massFlowUnits = "kg/s";
    private String massFluxUnits = "kg/s/m^2";
    private String lengthUnits = "mm";
    private String areaUnits = "cm^2";
    private String volumeUnits = "cm^3";
    private String impulseUnits = "N s";
    private String velocityUnits = "m/s";
    private final String timeUnits = "s";

    // static doubles
    private double atmPressure;
    private double motorVolume;
    private double cStar;
    private double ISP;
    private double impulse;
    private double maxThrust;
    private double maxChamberPressure;
    private double avgThrust;
    private double burnTime;
    private double initalKn;
    private double maxKn;


    // dynamic doubles
    private double chamberPressure;
    private double exitPressure;
    private double burnArea;
    private double kn;
    private double freeVolume = 0;
    private double volumeLoading;
    private double massFlow;
    private double massFlux;
    private double massEjected;
    private double regRate;
    private double regStep;
    private double regTotal;
    private double forceCoeff;
    private double thrust;
    private double time = 0;
    private int counter = 1;
    private double dt = 1E-3; // default to 1E-3s

    // arrays
    private ArrayList<Double> chamberPressureList = new ArrayList<Double>();
    private ArrayList<Double> exitPressureList = new ArrayList<Double>();
    private ArrayList<Double> burnAreaList = new ArrayList<Double>();
    private ArrayList<Double> knList = new ArrayList<Double>();
    private ArrayList<Double> freeVolumeList = new ArrayList<Double>();
    private ArrayList<Double> volumeLoadingList = new ArrayList<Double>();
    private ArrayList<Double> massFlowList = new ArrayList<Double>();
    private ArrayList<Double> massFluxList = new ArrayList<Double>();
    private ArrayList<Double> massEjectedList = new ArrayList<Double>();
    private ArrayList<Double> regRateList = new ArrayList<Double>();
    private ArrayList<Double> regStepList = new ArrayList<Double>();
    private ArrayList<Double> regTotalList = new ArrayList<Double>();
    private ArrayList<Double> forceCoeffList = new ArrayList<Double>();
    private ArrayList<Double> thrustList = new ArrayList<Double>();
    private ArrayList<Double> timeList = new ArrayList<Double>();

    public Motor(Propellant propellant,Nozzle nozzle, ArrayList<Grain> grainList) {
        this.propellant = propellant;
        this.nozzle = nozzle;
        this.grainList = grainList;
    }

    public void calcRegRate() {
        if (this.counter == 1) {
            this.regRate = this.propellant.getBurnRateCoeff()*Math.pow(this.atmPressure, this.propellant.getBurnRateExp());
        }
        else {
            this.regRate = this.propellant.getBurnRateCoeff()*Math.pow(this.chamberPressure, this.propellant.getBurnRateExp());
        }
        this.regRateList.add(this.regRate);
    }

    public void calcRegStep() {
        this.regStep = this.regRate*this.dt;
        this.regStepList.add(this.regStep);
    }

    public void calcRegTotal() {
        this.regTotal += this.regStep;
        this.regTotalList.add(this.regTotal);
    }

    public void clacBurnArea() {
        double burnArea = 0;
        for (Grain grain: this.grainList) {
            grain.calcBurnArea(this.regTotal);
            burnArea += grain.getBurnArea();
        }
        this.burnArea = burnArea;
        this.burnAreaList.add(this.burnArea);
    }

    public void calcMassFlow() {
        this.massFlow = this.burnArea*this.regRate*this.propellant.getDensity();
        this.massFlowList.add(this.massFlow);
    }

    public void calcMassFlux() {
        double massFlux = 0;
        for(Grain grain: this.grainList) {
            grain.calcPortArea(this.regTotal);
            if ((this.massFlow/grain.getPortArea()) > massFlux) {
                massFlux = this.massFlow/grain.getPortArea();
            }
        }
        this.massFlux = massFlux;
        this.massFluxList.add(this.massFlux);
    }

    public void calcMassEjected() {
        this.massEjected = this.massFlow*this.dt;
        this.massEjectedList.add(massEjected);
    }

    public void clacKn() {
        this.kn = this.burnArea/this.nozzle.getThroatArea();
        this.knList.add(this.kn);
    }

    public void calcFreeVolume() {
        if (counter == 1) {
            for (Grain grain: this.grainList) {
                this.freeVolume += grain.getInitialFreeVolume();
            }
        }
        else {
            this.freeVolume += this.burnArea*this.regStep;
        }
        this.freeVolumeList.add(this.freeVolume);
    }

    public void calcChamberPressure() {
        if (counter == 1) {
            this.chamberPressure = 101325;
        }
        else {
            double cStar = this.propellant.getCstar();
            this.chamberPressure = Math.pow(this.kn*this.propellant.getDensity()*this.propellant.getBurnRateCoeff()*cStar,1/(1-this.propellant.getBurnRateExp()));
        }
        this.chamberPressureList.add(this.chamberPressure);
    }

    public double exitPressureFunc(double exitPressureCalc) {
        double At = (Math.PI/4)*Math.pow(this.nozzle.throatDiameter,2);
        double Ae = (Math.PI/4)*Math.pow(this.nozzle.exitDiameter,2);
        double k = this.propellant.getGamma();
        double Pec = exitPressureCalc/this.chamberPressure;
        return (At/Ae - Math.pow((k+1)/2,1/(k-1))*Math.pow(Pec,1/k)*Math.sqrt(((k+1)/(k-1))*(1-Math.pow(Pec,(k-1)/k))));
    }

    public void calcExitPressure() {
        // Uses bisection method to calculate the exit pressure through finding the root a divergance equation (exitPressureFunc)
        double a = 0;
        double b = 0.99*this.chamberPressure;
        double c = a;
        if (exitPressureFunc(a) * exitPressureFunc(b) >= 0) {
            System.out.println("ERROR: Left or right bound incorrectly assumed");
        }
        while ((b-a) >= 1E-12) {
            c = (a+b)/2;
            if (exitPressureFunc(c) == 0.0)
                break;
            else if (exitPressureFunc(c)*exitPressureFunc(a) < 0)
                b = c;
            else
                a = c;
        }
        this.exitPressure = c;
        this.exitPressureList.add(this.exitPressure);
    }

    public void calcForceCoefficient() {
        double forceCoefficientIdeal = Math.sqrt(((2*(Math.pow(this.propellant.getGamma(),2))/(this.propellant.getGamma()-1))*(Math.pow((2/(this.propellant.getGamma()+1)),((this.propellant.getGamma()+1)/(this.propellant.getGamma()-1)))))*(1 - Math.pow((this.exitPressure/this.chamberPressure),((this.propellant.getGamma()-1)/this.propellant.getGamma())))) + ((this.exitPressure - this.atmPressure)*Math.pow(this.nozzle.getExitDiameter(),2))/(this.chamberPressure*Math.pow(this.nozzle.getThroatDiameter(),2));
        double throatLoss = 0.99;
        double skinLoss = 0.99;
        double divLoss = (0.5)*(1+Math.cos(this.nozzle.getExitAngle()));
        double nozzleLength = ((this.nozzle.getExitDiameter()-this.nozzle.getThroatDiameter())/2)*Math.cos(this.nozzle.getExitAngle());
        double throatLD = nozzleLength / this.nozzle.getExitDiameter();
        double efficiency = 0.97 - (0.0333*throatLD);
        this.forceCoeff = divLoss*throatLoss*efficiency*(skinLoss*forceCoefficientIdeal+(1-skinLoss));
        if (this.forceCoeff < 0) {
            this.forceCoeff = 0;
        }
        this.forceCoeffList.add(this.forceCoeff);
    }

    public void calcThrust() {
        this.thrust = this.forceCoeff*this.chamberPressure*this.nozzle.getThroatArea();
        this.thrustList.add(this.thrust);
    }

    public void calcVolumeLoading() {
        this.motorVolume = Collections.max(freeVolumeList);
        for (Double freeVolume : this.freeVolumeList) {
            this.volumeLoading = 100 * (1 - (freeVolume / this.motorVolume));
            this.volumeLoadingList.add(this.volumeLoading);
        }
    }

    public void runSim() {
        //this.calcMotorVolume();
        // while loop opens
        while ((this.burnArea > 0) || (counter == 1)) {
            this.calcRegRate();
            this.calcRegStep();
            this.calcRegTotal();
            this.clacBurnArea();
            if (this.burnArea > 0) {
                this.calcMassFlow();
                this.calcMassFlux();
                this.calcMassEjected();
                this.clacKn();
                this.calcFreeVolume();
                this.calcChamberPressure();
                this.calcExitPressure();
                this.calcForceCoefficient();
                this.calcThrust();
                this.counter += 1;
                this.time += this.dt;
                this.timeList.add(this.time);
            }
            else {
                popList();
            }
        }
        this.calcVolumeLoading();
        this.evaluateMotor();
        this.shiftSIUnits();
    }

    public void evaluateMotor() {
        this.maxThrust = Collections.max(this.getThrustList());
        this.maxChamberPressure = Collections.max(this.getChamberPressureList());
        this.ISP = average(this.thrustList) / (9.81 * average(this.massFlowList));
        this.impulse = average(this.thrustList) * Collections.max(this.timeList);
        this.burnTime = Collections.max(this.getTimeList());
        this.cStar = this.getcStar();
        this.initalKn = this.getKnList().get(1);
        this.maxKn = Collections.max(this.getKnList());
    }

    public void printBurnAreaAtRegTotal(double regTotal) {
        double burnArea = 0;
        for (Grain grain: this.grainList) {
            grain.calcBurnArea(regTotal);
            burnArea += grain.getBurnArea();
        }
        System.out.println("Burn Area @ regTotal = " + regTotal + ": " + burnArea);
    }

    public double average(ArrayList<Double> ArrList) {
        double sum = 0;
        double count = 0;
        for (Double arrValue : ArrList) {
            sum += arrValue;
            count++;
        }
        return sum / count;
    }

    public void popList() { // clears indexing issues if propellant is fully burned
        this.regRateList.remove(this.regRateList.size()-1);
        this.regStepList.remove(this.regStepList.size()-1);
        this.regTotalList.remove(this.regTotalList.size()-1);
        this.burnAreaList.remove(this.burnAreaList.size()-1);
        counter -= 1;
    }

    public void shiftSIUnits() {
        for (int i = 0; i < counter; i++) { // convert each list to correct units
            regTotalList.set(i, 1000*regTotalList.get(i)); // m to mm
            burnAreaList.set(i,  (10000 * burnAreaList.get(i))); // m^2 to cm^2
            chamberPressureList.set(i, (1E-6)*chamberPressureList.get(i)); // Pa to MPa
            exitPressureList.set(i, (1E-6)*exitPressureList.get(i)); // Pa to MPa
            freeVolumeList.set(i,Math.pow(100,3)*freeVolumeList.get(i)); // m^3 to cm^3
        }
        this.cStar = propellant.getCstar();
    }

    public void convertResult(boolean toEng) {
        if ((toEng) && (isSI())) { // SI to English Units
            for (int i = 0; i < counter; i++) { // convert each list to correct units
                burnAreaList.set(i,   Math.pow(2.54,-2)*burnAreaList.get(i)); // cm^2 to in^2
                chamberPressureList.set(i, (1E6)*(1/6894.76)*chamberPressureList.get(i)); // MPa to psi
                exitPressureList.set(i, (1E6)*(1/6894.76)*exitPressureList.get(i)); // MPa to psi
                freeVolumeList.set(i,Math.pow(2.54,-3)*freeVolumeList.get(i)); // cm^3 to in^3
                thrustList.set(i, (1/4.44822)*thrustList.get(i)); // N to lbf
                massEjectedList.set(i, 2.20462*massEjectedList.get(i)); // kg to lbm
                massFlowList.set(i, 2.20462*massFlowList.get(i)); // kg/s to lbm/s
                massFluxList.set(i,  2.20462*(1/10.7639)*massFluxList.get(i)); // kg/s/m^2 to lbm/s/ft^2
                regRateList.set(i, 39.3701*regRateList.get(i)); // m/s to in/s
                regTotalList.set(i, (1/25.4)*regTotalList.get(i)); // mm to in
            }
            this.maxThrust = (1/4.44822)*maxThrust; // N to lbf
            this.maxChamberPressure = (1E6)*(1/6894.76)*maxChamberPressure; // MPa to psi
            this.impulse = (1/4.44822)*impulse; // N s to lbf s
            this.cStar = propellant.getCstar() * 3.28084 * 12; // m/s to in/s

            this.lengthUnits = "in";
            this.areaUnits = "in^2";
            this.volumeUnits = "in^3";
            this.pressureUnits = "psi";
            this.thrustUnits = "lbf";
            this.massUnits = "lbm";
            this.massFlowUnits = "lbm/s";
            this.massFluxUnits = "lbm/s/ft^2";
            this.impulseUnits = "lbf s";
            this.velocityUnits = "in/s";
            this.SIUnits = false;
        }
        else if ((!toEng) && (!isSI())) { // English to SI
            for (int i = 0; i < counter; i++) { // convert each list to correct units
                burnAreaList.set(i,  Math.pow(2.54,2)*burnAreaList.get(i)); // in^2 to cm^2
                chamberPressureList.set(i, (1E-6)*(6894.76)*chamberPressureList.get(i)); // psi to MPa
                exitPressureList.set(i, (1E-6)*(6894.76)*exitPressureList.get(i)); // psi to MPa
                freeVolumeList.set(i,Math.pow(2.54,3)*freeVolumeList.get(i)); // in^3 to cm^3
                thrustList.set(i, (4.44822)*thrustList.get(i)); // lbf to N
                massEjectedList.set(i, (1/2.20462)*massEjectedList.get(i)); // lbm to kg
                massFlowList.set(i, (1/2.20462)*massFlowList.get(i)); // lbm/s to kg/s
                massFluxList.set(i,  (1/2.20462)*(10.7639)*massFluxList.get(i)); // lbm/s/ft^2 to kg/s/m^2
                regRateList.set(i, (1/(39.3701))*regRateList.get(i)); // in/s to m/s
                regTotalList.set(i, (25.4)*regTotalList.get(i)); // in to mm
            }
            this.maxThrust = (4.44822)*maxThrust; // lbf to N
            this.maxChamberPressure = (1E-6)*(6894.76)*maxChamberPressure; // psi to MPa
            this.impulse = (4.44822)*impulse; // lbf s to N s
            this.cStar = propellant.getCstar() / (3.28084*12); // in/s to m/s

            this.thrustUnits = "N";
            this.pressureUnits = "MPa";
            this.massUnits = "kg";
            this.massFlowUnits = "kg/s";
            this.massFluxUnits = "kg/s/m^2";
            this.lengthUnits = "mm";
            this.areaUnits = "cm^2";
            this.volumeUnits = "cm^3";
            this.impulseUnits = "N s";
            this.velocityUnits = "m/s";
            this.SIUnits = true;
        }
    }

    // Setters
    public void setAtmPressure(double atmPressure) {
        this.atmPressure = atmPressure;
    }
    public void setDt(double dt) {
        this.dt = dt;
    }
    public void setSIUnits(boolean b) {
        this.SIUnits = b;
    }

    // Getters
    public Propellant getPropellant() {
        return propellant;
    }
    public double getcStar() {
        return cStar;
    }
    public double getISP() {
        return ISP;
    }
    public int getCounter() {
        return this.counter;
    }
        // Array List
    public ArrayList<Double> getChamberPressureList() {
        return chamberPressureList;
    }
    public ArrayList<Double> getExitPressureList() {
        return exitPressureList;
    }
    public ArrayList<Double> getBurnAreaList() {
        return burnAreaList;
    }
    public ArrayList<Double> getKnList() {
        return knList;
    }
    public ArrayList<Double> getFreeVolumeList() {
        return freeVolumeList;
    }
    public ArrayList<Double> getVolumeLoadingList() {
        return volumeLoadingList;
    }
    public ArrayList<Double> getMassFlowList() {
        return massFlowList;
    }
    public ArrayList<Double> getMassFluxList() {
        return massFluxList;
    }
    public ArrayList<Double> getMassEjectedList() {
        return massEjectedList;
    }
    public ArrayList<Double> getRegRateList() {
        return regRateList;
    }
    public ArrayList<Double> getRegStepList() {
        return regStepList;
    }
    public ArrayList<Double> getRegTotalList() {
        return regTotalList;
    }
    public ArrayList<Double> getForceCoeffList() {
        return forceCoeffList;
    }
    public ArrayList<Double> getThrustList() {
        return thrustList;
    }
    public ArrayList<Double> getTimeList() {
        return timeList;
    }
        // Static results
    public double getImpulse() {
        return impulse;
    }
    public double getMaxThrust() {
        return maxThrust;
    }
    public double getMaxChamberPressure() {
        return maxChamberPressure;
    }
    public double getAvgThrust() {
        return avgThrust;
    }
    public double getBurnTime() {
        return burnTime;
    }
    public double getInitalKn() {
        return initalKn;
    }
    public double getMaxKn() {
        return maxKn;
    }
        // Units
    public boolean isSI() {
        return SIUnits;
    }
    public String getThrustUnits() {
        return thrustUnits;
    }
    public String getPressureUnits() {
        return pressureUnits;
    }
    public String getMassUnits() {
        return massUnits;
    }
    public String getMassFlowUnits() {
        return massFlowUnits;
    }
    public String getMassFluxUnits() {
        return massFluxUnits;
    }
    public String getLengthUnits() {
        return lengthUnits;
    }
    public String getAreaUnits() {
        return areaUnits;
    }
    public String getVolumeUnits() {
        return volumeUnits;
    }
    public String getImpulseUnits() {
        return impulseUnits;
    }
    public String getVelocityUnits() {
        return velocityUnits;
    }
    public String getTimeUnits() {
        return timeUnits;
    }
}
