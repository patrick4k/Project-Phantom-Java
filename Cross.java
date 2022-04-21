/*
Name: Patrick Kennedy
Date: 4/20/22

Cross
    - This class is an extension of the grain class, a cross is a grain shape

Attributes:
width: double
    - width of each slit in the cross geometry
length: double
    - length of each slit in the cross geometry

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

public class Cross extends Grain implements Serializable {
    private double width;
    private double length;

    public String getDispSlitWidth(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(width*39.3701);
        }
        else {
            return String.valueOf(width*100);
        }
    }

    public String getDispSlitLength(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(length*39.3701);
        }
        else {
            return String.valueOf(length*100);
        }
    }

    @Override
    public void runGrainConversion(Boolean fromEngUnits) {
        super.runGrainConversion(fromEngUnits);
        if (fromEngUnits) {
            this.width = width/39.3701; // in to m
            this.length = length/39.3701; // in to m
        }
        else {
            this.width = width/100; // cm to m
            this.length = length/100; // cm to m
        }
    }

    @Override
    public void calcBurnArea(double regTotal) {
        double burnArea;
        double w = width + 2*regTotal;
        double l;
        double y;
        double Do = getOuterDiameter();
        if (w >= (2.0/3.0)*Do) {
            burnArea = 0;
        }
        else {
            y = (Do - Math.sqrt(Do*Do - w*w))/2;
            l = length - regTotal - y;
            double currentLength = getGrainLength() - (2-getInhibitedEnds())*regTotal;
            burnArea = 8*l*currentLength + (2-getInhibitedEnds())*2*l*l;
        }
        setBurnArea(burnArea);
    }

    @Override
    public void calcPortArea(double regTotal) {
        double portArea;
        double w = width + 2*regTotal;
        double l;
        double y;
        double Do = getOuterDiameter();
        if (w >= (2.0/3.0)*Do) {
            portArea = Math.PI*Do*Do/4;
        }
        else {
            y = (Do - Math.sqrt(Do*Do - w*w))/2;
            l = length - regTotal - y;
            portArea = 4*l*w + w*w;
        }
        setPortArea(portArea);
    }

    @Override
    public double getInitialFreeVolume() {
        return ((4*length*width + width*width)*getGrainLength());
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
