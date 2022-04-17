/*
Name: Patrick kennedy
Date: 4/8/22
SRMSim Application file

This file creates and pieces together the SRMSim Application
In SRMSimApp.java:
 - The main pane for motor setup and design is created
 - The menu bar and features are set up
 - The simulation can be run

 SRMSimApp.java creates new panes and commits these panes to the borderPane using other classes
 */
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sun.awt.image.PNGImageDecoder;

import java.io.IOException;
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
    private Pane plotPane, staticResultsPane;

    // Menu Bar
    private final MenuItem closeMI, loadPresetPropMI,loadPresetNozMI, loadPresetCrossMI, loadPresetBatesMI, loadAllPreset,
            exportPerformanceMI, exportMotorMI, importMotorMI, helpMI;
    private final RadioMenuItem engUnitToggle;
    private final Menu fileMenu, optionsMenu, loadPresetGrainSubMenu, helpMenu;
    private final MenuBar mainMenu;

    // Motor Input
    private TextField motorNameInput;
    private ArrayList<Label> nozzLabels = new ArrayList<>();
    private ArrayList<Label> propLabels = new ArrayList<>();
    private ArrayList<TextField> nozzInputs = new ArrayList<>();
    private ArrayList<TextField> propInputs = new ArrayList<>();
    private final designInput throatDiameterInput, exitDiameterInput, exitAngleInput;
    private final designInput densityInput, chamberTempInput, gammaInput, burnRateCoeffInput, burnRateExpInput, molarMassInput;
    private final designInput inhibitedEndsInput, grainLengthInput, outerDiameterInput;
    private final designInput innerDiameterInput;

    // Buttons
    private final Button runSimButton;
    private final Button backToMainPaneButton;
    private final Button viewStaticButton;
    private final Button backToPlotButton;

    // Help
    private Label helpLabel;
    private BorderPane helpBorder;
    private Stage helpStage;
    private Pane helpPane;

    // Exceptions
    private Label exceptionLabel;
    private BorderPane exceptionBorder;
    private Stage exceptionStage;
    private Pane exceptionPane;

    // Addons
    private final ChoiceBox<String> plotSelect;
    private final ChoiceBox<ArrayList<designInput>> grainChoiceBox;
    private Label plotHeaderLabel;
    private Line topBorderLine;

    // Doubles
    private final double paneHeight;
    private final double paneWidth;

    /* TODO Delete once motor input is complete */
    String motorName;

    // Constructor
    public SRMSimApp() {
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
        exportPerformanceMI = new MenuItem("Export .CSV");
        exportMotorMI = new MenuItem("Export .motor");
        importMotorMI = new MenuItem("Import .motor");
        helpMI = new MenuItem("Show Help");
        engUnitToggle = new RadioMenuItem("English Units");
        fileMenu = new Menu("File");
        fileMenu.getItems().addAll(importMotorMI, closeMI);
        optionsMenu = new Menu("Options");
        optionsMenu.getItems().addAll(engUnitToggle, loadPresetPropMI, loadPresetNozMI, loadPresetGrainSubMenu, loadAllPreset);
        helpMenu = new Menu("Help");
        helpMenu.getItems().add(helpMI);
        mainMenu = new MenuBar(fileMenu, optionsMenu, helpMenu);

        // Motor Input setup
        /* TODO Add motor input setup */
        Label motorNameInputLabel = new Label("Motor Name:");
        motorNameInputLabel.setFont(new Font(14));
        motorNameInputLabel.setLayoutX(10);
        motorNameInputLabel.setLayoutY(10);
        motorNameInput = new TextField();
        motorNameInput.setLayoutX(100);
        motorNameInput.setLayoutY(10);
        Label nozzLabel = new Label("Nozzle Design");
        nozzLabel.setFont(new Font(15));
        nozzLabel.setLayoutX(15);
        nozzLabel.setLayoutY(75);
        throatDiameterInput = new designInput("Throat Diameter:", "",nozzLabel.getLayoutX(), nozzLabel.getLayoutY() + 25);
        exitDiameterInput = new designInput("Exit Diameter:", "",nozzLabel.getLayoutX(), throatDiameterInput.getyLoc()+25);
        exitAngleInput = new designInput("Exit Angle:", "",nozzLabel.getLayoutX(), exitDiameterInput.getyLoc()+25);

        Label propLabel = new Label("Propellant Design");
        propLabel.setFont(new Font(15));
        propLabel.setLayoutX(15);
        propLabel.setLayoutY(exitAngleInput.getyLoc() + 50);
        densityInput = new designInput("Density","",propLabel.getLayoutX(),propLabel.getLayoutY()+25);
        chamberTempInput = new designInput("Chamber Temp", "",propLabel.getLayoutX(),densityInput.getyLoc()+25);
        gammaInput = new designInput("Spec Heat Ratio","",propLabel.getLayoutX(), chamberTempInput.getyLoc()+25);
        burnRateCoeffInput = new designInput("Burn Rate Coeff","",propLabel.getLayoutX(),gammaInput.getyLoc()+25);
        burnRateExpInput = new designInput("Burn Rate Exp","",propLabel.getLayoutX(),burnRateCoeffInput.getyLoc()+25);
        molarMassInput = new designInput("Molar Mass","",propLabel.getLayoutX(),burnRateExpInput.getyLoc()+25);

        Label grainLabel = new Label("Grain Design");
        grainLabel.setFont(new Font(15));
        grainLabel.setLayoutX(400);
        grainLabel.setLayoutY(nozzLabel.getLayoutY());
        grainChoiceBox = new ChoiceBox<>();
        grainChoiceBox.setLayoutX(grainLabel.getLayoutX());
        grainChoiceBox.setLayoutY(grainLabel.getLayoutY()+25);
        inhibitedEndsInput = new designInput("Inhibited Ends","0-2",grainLabel.getLayoutX(),grainChoiceBox.getLayoutY()+25);
        grainLengthInput = new designInput("Length","",grainLabel.getLayoutX(), inhibitedEndsInput.getyLoc()+25);
        outerDiameterInput = new designInput("Outer Diameter","",grainLabel.getLayoutX(), grainLengthInput.getyLoc()+25);
        ArrayList<designInput> batesInputArr = new ArrayList<>();
        innerDiameterInput = new designInput("Inner Diameter","",grainLabel.getLayoutX(), outerDiameterInput.getyLoc()+25);
        batesInputArr.add(inhibitedEndsInput);
        batesInputArr.add(grainLengthInput);
        batesInputArr.add(outerDiameterInput);
        batesInputArr.add(innerDiameterInput);

        grainChoiceBox.getItems().add(batesInputArr);
        /* TODO Add grain input system
        *   maybe add arrayList of designInput objects for each grain design? */


        setInputUnits();


        // Define Run Sim button
        runSimButton = new Button("Calculate Motor Performance");
        runSimButton.setLayoutX((56.0/75)*paneWidth);
        runSimButton.setLayoutY(paneHeight-67.5);

        // Help setup
        helpLabel = new Label("\nPropellant is a vital component in SRM design. Common propellants \n" +
                "is dextrose and potassium nitrate, and HTPB and AP. For aid in numeric calculations \n" +
                "of propellant, PROPEP3 is a great resource.\n\n" +
                "The nozzle design is another important attribute to a motor, when designing the nozzle \n" +
                "it is important to note that a smaller nozzle throat tends to lead to higher thrust but \n" +
                "greatly increases the chamber pressure, therefore your chamber design and nozzle \n" +
                "design should correspond with each other. The exit angle for a nozzle is usually \n" +
                "about 15 deg and can effect divergence loss.\n\n" +
                "The grain geometry and configuration is where the thrust curve is most effected. Since \n" +
                "thrust is proportional to burn area the grain geometry has a direct influence to the \n" +
                "rate of change of the thrust and even the duration.");
        helpLabel.setFont(new Font(14));
        helpBorder = new BorderPane();
        helpStage = new Stage();
        helpPane = new Pane();
        helpPane.getChildren().add(helpLabel);
        helpBorder.setCenter(helpPane);
        Scene helpScene = new Scene(helpBorder);
        helpStage.setScene(helpScene);
        helpStage.setResizable(false);
        helpStage.setTitle("Help");
        helpStage.setAlwaysOnTop(true);

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

        // General Setup
        topBorderLine = new Line(0,45,paneWidth,45);
        borderPane = new BorderPane();
        homePane = new Pane();
        /* TODO Add all nodes to home pane */
        homePane.getChildren().addAll(runSimButton, topBorderLine, motorNameInput, motorNameInputLabel, nozzLabel, propLabel, grainLabel);
        homePane.getChildren().addAll(throatDiameterInput.getNodeArr());
        homePane.getChildren().addAll(exitDiameterInput.getNodeArr());
        homePane.getChildren().addAll(exitAngleInput.getNodeArr());
        homePane.getChildren().addAll(densityInput.getNodeArr());
        homePane.getChildren().addAll(chamberTempInput.getNodeArr());
        homePane.getChildren().addAll(gammaInput.getNodeArr());
        homePane.getChildren().addAll(burnRateCoeffInput.getNodeArr());
        homePane.getChildren().addAll(burnRateExpInput.getNodeArr());
        homePane.getChildren().addAll(molarMassInput.getNodeArr());
        homePane.getChildren().add(grainChoiceBox);
        for (designInput input:batesInputArr) {
            homePane.getChildren().addAll(input.getNodeArr());
        }


        plotSelect = new ChoiceBox<>();
        plotSelect.getItems().addAll("Thrust vs Time","Chamber Pressure vs Time","Mass Flow vs Time", "Mass Ejected vs Time",
                "Mass Flux vs Time", "Burn Area vs Time", "Burn Area vs Regression", "Kn vs Time", "Regression Rate vs Time", "Regression vs Time",
                "Regression Rate vs Chamber Pressure", "Exit Pressure vs Time", "Force Coefficient vs Time", "Free Volume vs Time", "Volume Loading vs Time");
        plotSelect.setLayoutX(0.25*paneWidth);
        plotSelect.setLayoutY(0.25*paneHeight - 35);
    }

    public void setInputUnits() {
        if (engUnitToggle.isSelected()) {
            throatDiameterInput.setUnits("in");
            exitDiameterInput.setUnits("in");

            densityInput.setUnits("lbm/in3");
            chamberTempInput.setUnits("R");
            burnRateCoeffInput.setUnits("in/s/ksi");
            molarMassInput.setUnits("lbm/lbmol");
        }
        else {
            throatDiameterInput.setUnits("cm");
            exitDiameterInput.setUnits("cm");

            densityInput.setUnits("kg/m3");
            chamberTempInput.setUnits("K");
            burnRateCoeffInput.setUnits("mm/s/MPa");
            molarMassInput.setUnits("g/mol");
        }
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
            plotHeaderLabel = new Label(motor.getMotorName() + " Performance");
            plotHeaderLabel.setFont(Font.font(null,FontWeight.BOLD,16));
            plotHeaderLabel.setLayoutX(60);
            plotHeaderLabel.setLayoutY(10);
            plotPane.getChildren().addAll(backToMainPaneButton, plotSelect, viewStaticButton, plotHeaderLabel);
            borderPane.setCenter(plotPane);
        }
        else {
            System.out.println("ERR NO SIZE MATCH");
        }
    }

    public void assesStaticResults() {
        staticResults staticResults = new staticResults(motor, paneHeight, paneWidth);
        staticResultsPane = staticResults.getStaticResultsPane();
        staticResultsPane.getChildren().addAll(backToPlotButton);
    }

    public void changeMenuBar(boolean fromHomePane) {
        if (fromHomePane) {
            fileMenu.getItems().clear();
            fileMenu.getItems().addAll(exportPerformanceMI, exportMotorMI, closeMI);
            optionsMenu.getItems().removeAll(loadPresetPropMI, loadPresetNozMI, loadPresetGrainSubMenu, loadAllPreset);
        }
        else {
            fileMenu.getItems().clear();
            fileMenu.getItems().addAll(importMotorMI, closeMI);
            optionsMenu.getItems().addAll(loadPresetPropMI, loadPresetNozMI, loadPresetGrainSubMenu, loadAllPreset);
        }
    }

    public void lambdaFunctions() {
        /* TODO Add lambda for import export of .motor files */
        // Close Application
        closeMI.setOnAction(event -> {
            System.exit(0);
        });

        // Run sim with inputted motor
        runSimButton.setOnMouseClicked(event -> {
            /*TODO asses input text fields and assign to new Prop Nozz and grainList */
            this.motor = new Motor(propellant,nozzle,grains);
            if (!motorNameInput.getText().isEmpty()) {
                this.motor.setMotorName(motorNameInput.getText());
            }
            else {
                this.motor.setMotorName("myMotor");
            }
            try {
                this.motor.runSim();
                changeMenuBar(true);
                simCompleted = true;
                exceptionStage.close();
                helpStage.close();
                if (engUnitToggle.isSelected()) {
                    motor.convertResult(true);
                }
                updatePlotPane(motor.getTimeList(),motor.getThrustList(), "Thrust vs Time", motor.getTimeUnits(), motor.getThrustUnits());
            } catch (Exception e) {
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

        // Export Results as CSV file
        exportPerformanceMI.setOnAction(event -> {
            try {
                new exportPerformance(motor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Exit Plot screen and reset pane to homePane
        backToMainPaneButton.setOnMouseClicked(event -> {
            simCompleted = false;
            borderPane.setCenter(homePane);
            setInputUnits();
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
                borderPane.setCenter(staticResultsPane);
            }
            else if (borderPane.getCenter() == homePane) {
                setInputUnits();
            }
        });
        // Import .motor file
        importMotorMI.setOnAction(event -> {
            String filename = "myMotor.motor"; // <-- add input for this step / text-field maybe?
            Motor motorImport = dotMotorIO.importMotor(filename);
            /* TODO Set all text fields to corresponding values
            *   remember to convert accordingly to eng toggle */
            try {
                engUnitToggle.setSelected(!motorImport.isSI());
                setInputUnits();
                propellant = motorImport.getPropellant();
                nozzle = motorImport.getNozzle();
                grains = motorImport.getGrainList();
                motorNameInput.setText(motorImport.getMotorName());
                throatDiameterInput.getInputTF().setText(String.valueOf(motorImport.getNozzle().getThroatDiameter())); // <-- Do this for every input
            } catch (Exception ignored) {
            }
        });
        // Export .motor file
        exportMotorMI.setOnAction(event -> {
            dotMotorIO.exportMotor(motor);
        });
        // Update and view 
        viewStaticButton.setOnAction(event -> {
            assesStaticResults();
            borderPane.setCenter(staticResultsPane);
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

        // Help menu item
        helpMI.setOnAction(event -> {
            helpStage.close();
            helpStage.show();
        });
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
