/*
Name: Patrick Kennedy
Date: 4/20/22

Propellant
    - This class holds information on the motors propellant and its properties

Attributes:
density: double
    - Density of propellant
chamberTemp: double
    - Burning temperature of propellant
gamma: double
    - specific heat ratio of propellant
burnRateCoeff: double
    - burn rate coefficient for propellant, unit conversion is highly dependent on burnRateExp
burnRateExp: double
    - burn rate exponent for propellant
molarMass: double
    - molar mass of propellant

Methods:
runPropConversion(Boolean): void
    - This method converts input values into base SI units which are used for calculations
    - Will convert english units to base SI and non-base SI units to base SI
getDisp_________(Boolean): String
    - Returns display value of attributes, if GUI is in SI units will return SI, if english will return english
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
            this.density = (515.379)*density; // lbm/in3 to kg/m3
            this.chamberTemp = chamberTemp/1.8; // R to K
            this.burnRateCoeff = burnRateCoeff*(1/39.3701)*Math.pow((1/(6894.76)),burnRateExp); // in/s/psi^n to m/s/Pa^n
        }
        else {
            this.burnRateCoeff =  burnRateCoeff*(1E-3)*Math.pow(1E-6,this.burnRateExp); // mm/s/MPa^n to m/s/Pa^n
        }
        this.molarMass = molarMass/1000; // g/mol or lb/lbmol to kg/mol
    }

    public String getDispDensity(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(density/515.379);
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
            return String.valueOf(burnRateCoeff*(39.3701)*Math.pow((6894.76),burnRateExp));
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
        return 8.3145/this.molarMass;
    }
    
    public double getCstar() {
        return Math.sqrt((this.getSpecificGasConst()*this.getChamberTemp())/(this.getGamma()*Math.pow((2/(this.getGamma()+1)),((this.getGamma()+1)/(this.getGamma()-1)))));
    }

}
