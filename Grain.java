/*
Grain class
By Patrick Kennedy
Date Modified: 4/8/22

The Grain class is an abstract class for separate grains, the Grain class holds basic functionality of setters and getters
and allows adaptability for different grain geometries
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
