/*
Tubular class
By Patrick Kennedy
Date Modified: 4/8/22

Tubular is an extension of the Grain class, the Tubular grain overrides various calculation to adhere to the tubular geometry
 */

import java.io.Serializable;

public class Tubular extends Grain implements Serializable {
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
