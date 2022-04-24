/*
Name: Patrick Kennedy
Date: 4/20/22

staticResults
    - This class creates a pane of static results from a motor

Methods:
staticResults(Motor, double, double): Constructor
    - The constructor takes in motor import, pane height, and pane width parameters and assembles the static results pane
roundToSigFigs(double, int): String
    - This method returns a string form of a double after that double is rounded to a specific number of significant digits
    - This method uses math to chop off a majority of the significant digits, but requires Decimal format to compensate for java math errors
 */
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class staticResults {
    private final Motor motor;

    private final double panHeight, paneWidth;
    private final ArrayList<Label> staticResultLabels;
    private final Label staticResultsTitle;
    private final Line topBorderLine;

    private final Pane staticResultsPane;

    public staticResults(Motor motor, double paneHeight, double paneWidth) {
        this.motor = motor;
        this.panHeight = paneHeight;
        this.paneWidth = paneWidth;
        topBorderLine = new Line(0,45,paneWidth,45);

        staticResultsTitle = new Label(motor.getMotorName() + " Static Results");
        staticResultsTitle.setFont(Font.font(null, FontWeight.BOLD, 16));
        staticResultsTitle.setLayoutX(60);
        staticResultsTitle.setLayoutY(10);

        // Create new Labels
        staticResultLabels = new ArrayList<>();
        staticResultLabels.add(new Label("Max Thrust = " + roundToSigFigs(motor.getMaxThrust(), 6) + " " + motor.getThrustUnits()));
        staticResultLabels.add(new Label("Average Thrust = " + roundToSigFigs(motor.getAvgThrust(),6) + " " + motor.getThrustUnits()));
        staticResultLabels.add(new Label("Max Chamber Pressure = " + roundToSigFigs(motor.getMaxChamberPressure(),5) + " " + motor.getPressureUnits()));
        staticResultLabels.add(new Label("Burn Time = " + roundToSigFigs(motor.getBurnTime(),6) + " " + motor.getTimeUnits()));
        staticResultLabels.add(new Label("Impulse = " + roundToSigFigs(motor.getImpulse(),6) + " " + motor.getImpulseUnits()));
        staticResultLabels.add(new Label("ISP = " + roundToSigFigs(motor.getISP(), 6) + " " + motor.getTimeUnits()));
        staticResultLabels.add(new Label("C* = " + roundToSigFigs(motor.getcStar(), 6) + " " + motor.getVelocityUnits()));
        staticResultLabels.add(new Label("Initial Kn = " + roundToSigFigs(motor.getInitalKn(), 6)));
        staticResultLabels.add(new Label("Max Kn = " + roundToSigFigs(motor.getMaxKn(), 6)));

        // Setup font and position for each label
        double i = 0;
        for (Label label:staticResultLabels) {
            label.setLayoutX(50);
            label.setLayoutY(125 + i);
            label.setFont(Font.font(null,14));
            i+= 40;
        }

        staticResultsPane = new Pane();
        staticResultsPane.getChildren().addAll(topBorderLine, staticResultsTitle);
        staticResultsPane.getChildren().addAll(staticResultLabels);
    }

    public String roundToSigFigs(double value, int sigFigs) {
        double numPlace = 1;
        double newValue = 0;
        StringBuilder deciPattern = new StringBuilder("");
        if (value == 1) {
            deciPattern.append("0.0");
            newValue =  1;
        }
        else if (value == 0){
            deciPattern.append("0.0");
            newValue =  0.0;
        }
        else if (value > 1) {
            while (!(((value/numPlace) > 1) && ((value/numPlace) < 10))) {
                numPlace*=10;
            }
            for (int i = 0; i < (Math.log10(numPlace)); i++) {
                deciPattern.append("0");
            }
            if (sigFigs > 1 + (Math.log10(numPlace))) {
                deciPattern.append(".");
                for (int i = 0; i < sigFigs - 1 - (Math.log10(numPlace)); i++) {
                    deciPattern.append("0");
                }
            }
            newValue =  Math.pow(10,-sigFigs+1)*numPlace*Math.round((value/numPlace)*Math.pow(10,sigFigs-1));
        }
        else {
            deciPattern.append("0.");
            while (!(((value/numPlace) > 1) && ((value/numPlace) < 10))) {
                numPlace/=10;
            }
            for (int i = 0; i < (sigFigs - 1 + Math.log10(1/numPlace)); i++) {
                deciPattern.append("0");
            }
            newValue = (Math.pow(10,-sigFigs+1)*numPlace*Math.round((value/numPlace)*Math.pow(10,sigFigs-1)));
        }
        DecimalFormat decimalFormat = new DecimalFormat(deciPattern.toString());
        return decimalFormat.format(newValue);
    }

    public Pane getStaticResultsPane() {
        return staticResultsPane;
    }

}
