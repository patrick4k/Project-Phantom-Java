/*
Name: Patrick Kennedy
Date: 4/20/22

Grain
    - This class holds information on the motors grain and its properties

Attributes:
grainName: String
    - name of grain, used for GUI handling
outerDiameter: double
    - outer diameter of grain
grainLength: double
    - length of grain
inhibitedEnds: double
    - number of inhibited ends for grain
burnArea: double
    - instantaneous burning surface area of grain
portArea: double
    - exposed cross-sectional area of grain

Methods:
runPropConversion(Boolean): void
    - This method converts input values into base SI units which are used for calculations
    - Will convert english units to base SI and non-base SI units to base SI
getDisp_________(Boolean): String
    - Returns display value of attributes, if GUI is in SI units will return SI, if english will return english
 */
import java.io.Serializable;

public class Grain implements Serializable {
    private double outerDiameter;
    private double grainLength;
    private double inhibitedEnds;
    private double burnArea;
    private double portArea;
    private String grainName;

    public void runGrainConversion(Boolean fromEngUnits) {
        if (fromEngUnits) {
            this.grainLength = grainLength/39.3701; // in to m
            this.outerDiameter = outerDiameter/39.3701; // in to m
        }
        else {
            this.grainLength = grainLength/100; // cm to m
            this.outerDiameter = outerDiameter/100; // cm to m
        }
    }

    public String getGrainName() {
        return grainName;
    }

    public void setGrainName(String grainName) {
        this.grainName = grainName;
    }

    public String getDispOuterDiameter(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(outerDiameter*39.3701);
        }
        else {
            return String.valueOf(outerDiameter*100);
        }
    }

    public String getDispGrainLength(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(grainLength*39.3701);
        }
        else {
            return String.valueOf(grainLength*100);
        }
    }

    public String getDispInhibitedEnds() {
        return String.valueOf(inhibitedEnds);
    }

    public void calcBurnArea(double regTotal) {
        // abstract
        System.out.println("This Method is Abstract");
    }

    public double getBurnArea() {
        return this.burnArea;
    }

    public  void calcPortArea(double regTotal)  {
        // Abstract
        System.out.println("This Method is Abstract");
    }

    public double getPortArea() {
        return this.portArea;
    }

    public double getInitialFreeVolume() {
        // Abstract
        System.out.println("This Method is Abstract");
        return 0;
    }

    public double getOuterDiameter() {
        return outerDiameter;
    }

    public void setOuterDiameter(double outerDiameter) {
        this.outerDiameter = outerDiameter;
    }

    public double getGrainLength() {
        return grainLength;
    }

    public void setGrainLength(double grainLength) {
        this.grainLength = grainLength;
    }

    public double getInhibitedEnds() {
        return inhibitedEnds;
    }

    public void setInhibitedEnds(double inhibitedEnds) {
        this.inhibitedEnds = inhibitedEnds;
    }

    public void setBurnArea(double burnArea) {
        this.burnArea = burnArea;
    }

    public void setPortArea(double portArea) {
        this.portArea = portArea;
    }

}
