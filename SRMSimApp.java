/*
Name: Patrick kennedy
Date: 4/6/22
SRMSim Application file

This file creates and pieces together the SRMSim Application
In SRMSimApp.java:
 - The main pane for motor setup and design is created
 - The menu bar and features are set up
 - The simulation can be ran

 SRMSimApp.java creates new panes and commits these panes to the borderPane using other classes
 */
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;

public class SRMSimApp extends Application {
    // Motor for Sim
    private Propellant propellant;
    private Nozzle nozzle;
    private ArrayList<Grain> grains;
    private Motor motor;
    private boolean simCompleted = false;

    // Panes
    private final BorderPane borderPane;
    private final Pane homePane;
    private Pane plotPane;
    private Pane staticResultsPane;

    // Menu Bar
    private final MenuItem closeMI, loadPresetPropMI,loadPresetNozMI, loadPresetCrossMI, loadPresetBatesMI, loadAllPreset;
    private final RadioMenuItem engUnitToggle;
    private final Menu fileMenu, optionsMenu, loadPresetGrainSubMenu, helpMenu;
    private final MenuBar mainMenu;

    // Buttons
    private final Button runSimButton;
    private final Button backToMainPaneButton;
    private final Button viewStaticButton;
    private final Button backToPlotButton;

    // Exceptions
    //private final Label propExcepLabel, nozzExceptLabel, grainExcepLabel;
    private Label exceptionLabel;
    private BorderPane exceptionBorder;
    private Stage exceptionStage;
    private Pane exceptionPane;

    // Choice Boxes
    private final ChoiceBox<String> plotSelect;

    // Doubles
    private final double paneHeight;
    private final double paneWidth;

    // Constructor
    public SRMSimApp() {
        /*
        propellant = new Propellant();
        nozzle = new Nozzle();
        grains = new ArrayList<>();
        */
        paneWidth = 750;
        paneHeight = 600;

        // Menu bar setup
        closeMI = new MenuItem("Close Sim");
        loadPresetPropMI = new MenuItem("Load Preset Propellant");
        loadPresetNozMI = new MenuItem("Load Preset Nozzle");
        loadPresetBatesMI = new MenuItem("BATES Config");
        loadPresetCrossMI = new MenuItem("Cross Config");
        loadAllPreset = new MenuItem("Load Random Preset Motor");
        loadPresetGrainSubMenu = new Menu("Load Preset Grain");
        loadPresetGrainSubMenu.getItems().addAll(loadPresetCrossMI, loadPresetBatesMI);
        engUnitToggle = new RadioMenuItem("English Units");
        fileMenu = new Menu("File");
        fileMenu.getItems().addAll(closeMI);
        optionsMenu = new Menu("Options");
        optionsMenu.getItems().addAll(engUnitToggle, loadPresetPropMI, loadPresetNozMI, loadPresetGrainSubMenu, loadAllPreset);
        helpMenu = new Menu("Help");
        mainMenu = new MenuBar(fileMenu, optionsMenu, helpMenu);

        // Define Run Sim button
        runSimButton = new Button("Calculate Motor Performance");
        runSimButton.setLayoutX((56.0/75)*paneWidth);
        runSimButton.setLayoutY(paneHeight-67.5);

        // Exception setup
        exceptionBorder = new BorderPane();
        exceptionStage = new Stage();
        exceptionPane = new Pane();
        exceptionBorder.setCenter(exceptionPane);
        Scene exceptionScene = new Scene(exceptionBorder, 0.35*paneWidth, 0.3*paneHeight);
        exceptionStage.setScene(exceptionScene);
        exceptionStage.setResizable(false);
        exceptionStage.setTitle("ERROR");

        // Button setup
        backToMainPaneButton = new Button("Back");
        backToMainPaneButton.setLayoutX(10.0);
        backToMainPaneButton.setLayoutY(10.0);
        viewStaticButton = new Button("View Static Results");
        viewStaticButton.setLayoutX(0.75*paneWidth - 120);
        viewStaticButton.setLayoutY(0.25*paneHeight - 35);
        backToPlotButton = new Button("Back");
        backToPlotButton.setLayoutX(10);
        backToPlotButton.setLayoutY(10);

        borderPane = new BorderPane();
        homePane = new Pane();
        homePane.getChildren().add(runSimButton);
        plotSelect = new ChoiceBox<>();
        plotSelect.getItems().addAll("Thrust vs Time","Chamber Pressure vs Time","Mass Flow vs Time", "Mass Ejected vs Time", "Mass Flux vs Time", "Burn Area vs Time", "Burn Area vs Regression", "Kn vs Time", "Regression Rate vs Time", "Regression vs Time", "Regression Rate vs Chamber Pressure", "Exit Pressure vs Time", "Force Coefficient vs Time", "Free Volume vs Time", "Volume Loading vs Time");
        plotSelect.setLayoutX(0.25*paneWidth);
        plotSelect.setLayoutY(0.25*paneHeight - 35);
    }

    public void assesPlotSelect() {
        ArrayList<Double> xArr = new ArrayList<>();
        ArrayList<Double> yArr= new ArrayList<>();
        String plotSelected = plotSelect.getValue();
        String xAxisTitle = null;
        String yAxisTitle = null;
        if (Objects.equals(plotSelected, "Thrust vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getThrustList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getThrustUnits();
        }
        else if (Objects.equals(plotSelected, "Chamber Pressure vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getChamberPressureList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getPressureUnits();
        }
        else if (Objects.equals(plotSelected, "Mass Flow vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getMassFlowList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getMassFlowUnits();
        }
        else if (Objects.equals(plotSelected, "Mass Ejected vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getMassEjectedList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getMassUnits();
        }
        else if (Objects.equals(plotSelected, "Mass Flux vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getMassFluxList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getMassFluxUnits();
        }
        else if (Objects.equals(plotSelected, "Burn Area vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getBurnAreaList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getAreaUnits();
        }
        else if (Objects.equals(plotSelected, "Burn Area vs Regression")) {
            xArr = motor.getRegTotalList();
            yArr = motor.getBurnAreaList();
            xAxisTitle = motor.getLengthUnits();
            yAxisTitle = motor.getAreaUnits();
        }
        else if (Objects.equals(plotSelected, "Regression Rate vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getRegRateList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getVelocityUnits();
        }
        else if (Objects.equals(plotSelected, "Regression vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getRegTotalList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getLengthUnits();
        }
        else if (Objects.equals(plotSelected, "Regression Rate vs Chamber Pressure")) {
            xArr = motor.getChamberPressureList();
            yArr = motor.getRegRateList();
            xAxisTitle = motor.getPressureUnits();
            yAxisTitle = motor.getVelocityUnits();
        }
        else if (Objects.equals(plotSelected, "Exit Pressure vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getExitPressureList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getPressureUnits();
        }
        else if (Objects.equals(plotSelected, "Force Coefficient vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getForceCoeffList();
            xAxisTitle = motor.getTimeUnits();
        }
        else if (Objects.equals(plotSelected, "Kn vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getKnList();
            xAxisTitle = motor.getTimeUnits();
        }
        else if (Objects.equals(plotSelected, "Free Volume vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getFreeVolumeList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = motor.getVolumeUnits();
        }
        else if (Objects.equals(plotSelected, "Volume Loading vs Time")) {
            xArr = motor.getTimeList();
            yArr = motor.getVolumeLoadingList();
            xAxisTitle = motor.getTimeUnits();
            yAxisTitle = "%";
        }
        updatePlotPane(xArr,yArr,plotSelected, xAxisTitle, yAxisTitle);
    }

    public void updatePlotPane(ArrayList<Double> xArr, ArrayList<Double> yArr, String plotTitleText, String xAxisTitle, String yAxisTitle) {
        if (xArr.size() == yArr.size()) {
            plotArrayList plot = new plotArrayList(xArr,yArr,paneHeight,paneWidth, xAxisTitle, yAxisTitle);
            plotSelect.setValue(plotTitleText);
            plotPane = plot.getPlotPane();
            plotPane.getChildren().addAll(backToMainPaneButton, plotSelect, viewStaticButton);
            borderPane.setCenter(plotPane);
        }
        else {
            System.out.println("ERR NO SIZE MATCH");
        }
    }

    public void assesStaticResults() {
        staticResults staticResults = new staticResults(motor, paneHeight, paneWidth);
        staticResultsPane = staticResults.getStaticResultsPane();
        staticResultsPane.getChildren().add(backToPlotButton);
        borderPane.setCenter(staticResultsPane);
    }

    public void changeMenuBar(boolean fromHomePane) {
        if (fromHomePane) {
            /* TODO Add file I/O here */
            optionsMenu.getItems().removeAll(loadPresetPropMI, loadPresetNozMI, loadPresetGrainSubMenu, loadAllPreset);
            optionsMenu.fire();
        }
        else if (!(optionsMenu.getItems().contains(loadPresetPropMI) && optionsMenu.getItems().contains(loadPresetNozMI) && optionsMenu.getItems().contains(loadPresetGrainSubMenu) && optionsMenu.getItems().contains(loadAllPreset))) { // to home pane
            optionsMenu.getItems().addAll(loadPresetPropMI, loadPresetNozMI, loadPresetGrainSubMenu, loadAllPreset);
        }
    }

    public void lambdaFunctions() {
        // Close Application
        closeMI.setOnAction(event -> {
            System.exit(0);
        });

        // Run sim with inputted motor
        /* TODO Add error handling */
        runSimButton.setOnMouseClicked(event -> {
            //initMotor();
            this.motor = new Motor(propellant,nozzle,grains);
            try {
                this.motor.runSim();
                changeMenuBar(true);
                simCompleted = true;
                exceptionStage.close();
                if (engUnitToggle.isSelected()) {
                    motor.convertResult(true);
                }
                updatePlotPane(motor.getTimeList(),motor.getThrustList(), "Thrust vs Time", motor.getTimeUnits(), motor.getThrustUnits());
            } catch (Exception e) {
                System.out.println("Exception");
                StringBuilder errorStringBuilder = new StringBuilder("Sim could not run with current config\n\n");
                if (Objects.isNull(propellant)) {
                    errorStringBuilder.append("Propellant Necessary for Simulation\n\n");
                }
                if (Objects.isNull(nozzle)) {
                    errorStringBuilder.append("Nozzle Necessary for Simulation\n\n");
                }
                if (Objects.isNull(grains)) {
                    errorStringBuilder.append("Grains Necessary for Simulation\n\n");
                }
                // Exception Initialize
                exceptionLabel = new Label(errorStringBuilder.toString());
                exceptionLabel.setFont(new Font(14));
                exceptionStage.close();
                exceptionPane.getChildren().clear();
                exceptionPane.getChildren().add(exceptionLabel);
                exceptionStage.setAlwaysOnTop(true);
                exceptionStage.show();
            }
        });

        // Exit Plot screen and reset pane to homePane
        backToMainPaneButton.setOnMouseClicked(event -> {
            simCompleted = false;
            borderPane.setCenter(homePane);
            changeMenuBar(false);
        });

        // Update plotPane when new plot is selected
        plotSelect.setOnAction(event -> {
            assesPlotSelect();
        });

        // Change units post simulation
        engUnitToggle.setOnAction(event -> {
            if ((simCompleted) && (borderPane.getCenter() == plotPane)) {
                motor.convertResult(engUnitToggle.isSelected());
                assesPlotSelect();
            }
            else if ((simCompleted) && (borderPane.getCenter() == staticResultsPane)) {
                motor.convertResult(engUnitToggle.isSelected());
                assesStaticResults();
            }
        });

        // Update and view 
        viewStaticButton.setOnAction(event -> {
            assesStaticResults();
        });

        // Exit Static results, return to plot pane
        backToPlotButton.setOnAction(event -> {
            assesPlotSelect();
            borderPane.setCenter(plotPane);
        });

        // Load Preset Propellant
        loadPresetPropMI.setOnAction(event -> {
            this.propellant = test.loadPresetPropellant();
        });

        // Load Preset Nozzle
        loadPresetNozMI.setOnAction(event -> {
            this.nozzle = test.loadPresetNozzle();
        });
        // Load Preset for cross grain
        loadPresetCrossMI.setOnAction(event -> {
            this.grains = test.loadPresetCross();
        });
        // Load Preset for BATES grain
        loadPresetBatesMI.setOnAction((event -> {
            this.grains = test.loadPresetBates();
        }));

        // Load All Presets
        loadAllPreset.setOnAction((event -> {
            this.propellant = test.loadPresetPropellant();
            this.nozzle = test.loadPresetNozzle();
            if (Math.random() > 0.5) {
                this.grains = test.loadPresetBates();
            }
            else {
                this.grains = test.loadPresetCross();
            }
        }));

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        lambdaFunctions();

        // SETUP
        Scene scene = new Scene(borderPane, paneWidth, paneHeight);
        borderPane.setCenter(homePane);
        borderPane.setTop(mainMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SRM Sim");
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
