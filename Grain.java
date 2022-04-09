/*
Grain class
By Patrick Kennedy
Date Modified: 4/8/22

The Grain class is an abstract class for separate grains, the Grain class holds basic functionality of setters and getters
and allows adaptability for different grain geometries
 */

public class Grain {
    private double outerDiameter;
    private double grainLength;
    private double inhibitedEnds;
    private double burnArea;
    private double portArea;

    public void calcBurnArea(double regTotal) {
        // abstract
        System.out.println("This Method is Abstract");
    }
    public double getBurnArea() {
        return this.burnArea;
    }

    public  void calcPortArea(double regTotal)  {
        // Abstract
    }

    public double getPortArea() {
        return this.portArea;
    }

    public double getInitialFreeVolume() {
        // Abstract
        System.out.println("Abstract method called");
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
