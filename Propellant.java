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
    private double gamma; // input no units
    private double burnRateCoeff; // input mm/s/(MPa^n)
    private double burnRateExp; // input no units
    private double molarMass; // input g/mol

    public void runPropConversion(Boolean fromEngUnits) {
        if (fromEngUnits) {
            this.density = (27679.9)*density; // lbm/in3 to kg/m3
            this.chamberTemp = chamberTemp/1.8; // R to K
            this.burnRateCoeff = burnRateCoeff*(1/39.3701)*Math.pow((1/(6.895E6)),burnRateExp); // in/s/ksi^n to m/s/Pa^n
        }
        else {
            this.burnRateCoeff =  burnRateCoeff*(1E-3)*Math.pow(1E-6,this.burnRateExp); // mm/s/MPa^n to m/s/Pa^n
        }
        this.molarMass = molarMass/1000; // g/mol or lb/lbmol to kg/mol
    }

    public String getDispDensity(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(density/27679.9);
        }
        else {
            return String.valueOf(density);
        }
    }

    public String getDispChamberTemp(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(chamberTemp*1.8);
        }
        else {
            return String.valueOf(chamberTemp);
        }
    }

    public String getDispBurnRateCoeff(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(burnRateCoeff*(39.3701)*Math.pow((6.895E6),burnRateExp));
        }
        else {
            return String.valueOf(burnRateCoeff*(1E3)*Math.pow((1E6),burnRateExp));
        }
    }

    public String getDispMolarMass() {
        return String.valueOf(molarMass*1000);
    }

    public String getDispGamma() {
        return String.valueOf(gamma);
    }

    public String getDispBurnRateExp() {
        return String.valueOf(burnRateExp);
    }

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
        return molarMass;
    }

    public void setMolarMass(double molarMass) {
        this.molarMass = molarMass;
    }

    public double getSpecificGasConst() {
        return 8.314/this.molarMass; // convert to kg*J/K
    }
    
    public double getCstar() {
        return Math.sqrt((this.getSpecificGasConst()*this.getChamberTemp())/(this.getGamma()*Math.pow((2/(this.getGamma()+1)),((this.getGamma()+1)/(this.getGamma()-1)))));
    }

}
