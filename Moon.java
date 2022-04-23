import java.io.Serializable;

public class Moon extends Grain implements Serializable {
    private double innerDiameter, offset;

    public String getDispInnerDiameter(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(innerDiameter*39.3701);
        }
        else {
            return String.valueOf(innerDiameter*100);
        }
    }

    public String getDispOffset(Boolean toEngUnits) {
        if (toEngUnits) {
            return String.valueOf(offset*39.3701);
        }
        else {
            return String.valueOf(offset*100);
        }
    }

    public void setInnerDiameter(double innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public void runGrainConversion(Boolean fromEngUnits) {
        super.runGrainConversion(fromEngUnits);
        if (fromEngUnits) {
            this.innerDiameter = innerDiameter/39.3701; // in to m
            this.offset = offset/39.3701; // in to m
        }
        else {
            this.innerDiameter = innerDiameter/100; // cm to m
            this.offset = offset/100; // cm to m
        }
    }

    @Override
        public void calcBurnArea(double regTotal) {
        double burnArea = 0;
        setBurnArea(burnArea);
    }

    @Override
    public void calcPortArea(double regTotal) {
        double portArea = 0;
        setPortArea(portArea);
    }

    @Override
    public double getInitialFreeVolume() {
        return (Math.PI/4)* this.innerDiameter*getGrainLength();
    }

}
