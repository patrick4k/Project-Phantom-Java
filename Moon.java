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
        double Do = getOuterDiameter();
        double l = getGrainLength() - (2-getInhibitedEnds())*regTotal;
        double d = innerDiameter + 2*regTotal;
        if (0 <= ((Do-d)/2)-offset) {
            burnArea = Math.PI*d*l;
        }
        else if (0 <= ((Do-d)/2)+offset) {
            double yBar = (.5/offset)*(0.25*(Math.pow(Do,2)-Math.pow(d,2))+Math.pow(offset,2));
            double gamma = 0;
            if (yBar >= 0) {
                gamma = Math.PI + 2*Math.asin(2*yBar/Do);
            }
            else {
                gamma = Math.PI - 2*Math.asin(-2*yBar/Do);
            }
            if (yBar-offset > 0) {
                double theta = Math.asin((2/d)*(yBar-offset));
                burnArea = ((Math.PI/2)+theta)*d*l + (2-getInhibitedEnds())*gamma*Math.pow(d,2)/8;
            }
            else {
                double theta = Math.asin((2/d)*(offset-yBar));
                burnArea = ((Math.PI/2)-theta)*d*l;
            }
        }
        setBurnArea(burnArea);
    }

    @Override
    // TODO Something is wrong here // def need to fix
    public void calcPortArea(double regTotal) {
        double portArea = 0;
        double Do = getOuterDiameter();
        double d = innerDiameter + 2*regTotal;
        if (0 <= ((Do-d)/2)-offset) {
            portArea = (Math.PI/4)*(Math.pow(innerDiameter+2*regTotal,2));
        }
        else if (0 <= ((Do-d)/2)+offset) {
            double yBar = (.5/offset)*(0.25*(Math.pow(Do,2)-Math.pow(d,2))+Math.pow(offset,2));
            if (yBar-offset > 0) {
                double theta = Math.asin((2/d)*(yBar-offset));
                portArea = (Math.PI+2*theta)*Math.pow(d,2)/8;
                portArea += (4/(d*d))*Math.sin((Math.PI/2)-theta)*Math.cos((Math.PI/2)-theta);
                portArea += (4.0/3.0)*(1/d)*Math.sin((Math.PI/2)-theta)*(d+2*offset-Do);
            }
            else {
                double theta = Math.asin((2/d)*(offset-yBar));
                portArea = (Math.PI-2*theta)*Math.pow(d,2)/8;
                portArea += (4/(d*d))*Math.sin((Math.PI/2)+theta)*Math.cos((Math.PI/2)+theta);
                portArea += (4.0/3.0)*(1/d)*Math.sin((Math.PI/2)+theta)*(d+2*offset-Do);
            }
        }
        System.out.println(portArea);
        setPortArea(portArea);
    }

    @Override
    public double getInitialFreeVolume() {
        return (Math.PI/4)* this.innerDiameter*getGrainLength();
    }

}
