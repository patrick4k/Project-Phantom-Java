import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class staticResults {
    private Motor motor;
    private double maxThrust, maxChamberPressure, burnTime, cStar, initalKn, maxKn;

    private double panHeight, paneWidth;
    private Label staticResultsTitle, maxThrustLabel, maxChamberPressureLabel, burnTimeLabel, ispLabel, cStarLabel, initialKnLabel, maxKnLabel;
    private Pane staticResultsPane;


    public staticResults(Motor motor, double paneHeight, double paneWidth) {
        this.motor = motor;
        this.panHeight = paneHeight;
        this.paneWidth = paneWidth;

        evaluateMotor();

        staticResultsTitle = new Label("Thats a lot of power!");
        maxThrustLabel = new Label("Max Thrust = " + roundToSigFigs(maxThrust, 6) + " " + motor.getThrustUnits());
        maxChamberPressureLabel = new Label("Max Chamber Pressure = " + roundToSigFigs(maxChamberPressure,5) + " " + motor.getPressureUnits());
        burnTimeLabel = new Label("Burn Time = " + roundToSigFigs(burnTime,6) + " s");
        ispLabel = new Label("ISP = " + roundToSigFigs(motor.getISP(), 6) + " " + motor.getTimeUnits());
        cStarLabel = new Label("C* = " + roundToSigFigs(cStar, 6) + " " + motor.getVelocityUnits());
        initialKnLabel = new Label("Initial Kn = " + roundToSigFigs(initalKn, 6));
        maxKnLabel = new Label("Max Kn = " + roundToSigFigs(maxKn, 6));

        staticResultsTitle.setFont(new Font("Comic Sans MS", 20));

        staticResultsTitle.setLayoutX(.5*paneWidth - 85);
        staticResultsTitle.setLayoutY(.1*paneHeight);
        maxThrustLabel.setLayoutX(50);
        maxThrustLabel.setLayoutY(125);
        maxChamberPressureLabel.setLayoutX(50);
        maxChamberPressureLabel.setLayoutY(150);
        burnTimeLabel.setLayoutX(50);
        burnTimeLabel.setLayoutY(175);
        ispLabel.setLayoutX(50);
        ispLabel.setLayoutY(200);
        cStarLabel.setLayoutX(50);
        cStarLabel.setLayoutY(225);
        initialKnLabel.setLayoutX(50);
        initialKnLabel.setLayoutY(250);
        maxKnLabel.setLayoutX(50);
        maxKnLabel.setLayoutY(275);


        staticResultsPane = new Pane();
        staticResultsPane.getChildren().addAll(staticResultsTitle, maxThrustLabel, maxChamberPressureLabel, burnTimeLabel, ispLabel, cStarLabel, initialKnLabel, maxKnLabel);
    }

    public void evaluateMotor() {
        maxThrust = Collections.max(motor.getThrustList());
        maxChamberPressure = Collections.max(motor.getChamberPressureList());
        burnTime = Collections.max(motor.getTimeList());
        cStar = motor.getcStar();
        initalKn = motor.getKnList().get(1);
        maxKn = Collections.max(motor.getKnList());
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
