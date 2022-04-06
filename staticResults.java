import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.awt.*;
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
        maxThrustLabel = new Label("Max Thrust = " + maxThrust + " " + motor.getThrustUnits());
        maxChamberPressureLabel = new Label("Max Chamber Pressure = " + maxChamberPressure + " " + motor.getPressureUnits());
        burnTimeLabel = new Label("Burn Time = " + burnTime + " s");
        ispLabel = new Label("ISP = " + motor.getISP() + " " + motor.getTimeUnits());
        cStarLabel = new Label("C* = " + cStar + " " + motor.getVelocityUnits());
        initialKnLabel = new Label("Initial Kn = " + initalKn);
        maxKnLabel = new Label("Max Kn = " + maxKn);

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

    public Pane getStaticResultsPane() {
        return staticResultsPane;
    }

}
