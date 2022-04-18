/*
Propellant class
By Patrick Kennedy
Bate Modified: 4/8/22

The propellant class hold values of the propellant being simulated, no calculations are performed within the propellant class
with exception to cStar
 */

import java.io.Serializable;

public class Propellant implements Serializable {
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
        return burnRateCoeff;
    }
/* TODO Fix converrsion and complete setter */
    public void setBurnRateCoeff(double burnRateCoeff, boolean isEngUnits) {
        if (isEngUnits) {
            this.burnRateCoeff = burnRateCoeff*(1E-3)*Math.pow(1E-6,this.burnRateExp); // in/s/(ksi^n) to m/s/(Pa^n)
        }
        else {
            this.burnRateCoeff = burnRateCoeff*(1E-3)*Math.pow(1E-6,this.burnRateExp); // mm/s/(MPa^n) to m/s/(Pa^n)
        }

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
