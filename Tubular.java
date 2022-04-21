/*
Name: Patrick Kennedy
Date: 4/20/22

Tubular
    - This class is an extension of the grain class, tubular is a grain shape

Attributes:
innerDiameter: double
    - inner diameter of tubular grain

Methods:
runPropConversion(Boolean): void
    - This method converts input values into base SI units which are used for calculations
    - Will convert english units to base SI and non-base SI units to base SI
getDisp_________(Boolean): String
    - Returns display value of attributes, if GUI is in SI units will return SI, if english will return english
calcBurnArea(double): void
    - Calculates and updates the instantaneous burn area of the grain as a function of total regression
calcPortArea(double): void
    - Calculates and updates the instantaneous port area of the grain as a function of total regression
 */
import java.io.Serializable;

public class Tubular extends Grain implements Serializable {
    private double innerDiameter;

    public String getDispInnerDiameter(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(innerDiameter*39.3701);
        }
        else {
            return String.valueOf(innerDiameter*100);
        }
    }

    public double getInnerDiameter() {
        return innerDiameter;
    }

    public void setInnerDiameter(double innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    @Override
    public void runGrainConversion(Boolean fromEngUnits) {
        super.runGrainConversion(fromEngUnits);
        if (fromEngUnits) {
            this.innerDiameter = innerDiameter/39.3701; // in to m
        }
        else {
            this.innerDiameter = innerDiameter/100; // cm to m
        }
    }

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



}
