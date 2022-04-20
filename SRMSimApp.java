/*
Name: Patrick Kennedy
Date: 4/20/22
SRMSim Application

Methods:
formatImage(ImageView): void
    - Formats an image to ensure all grain cross-section image files are processed the same and appear in identical location
setInputUnits(): void
    - Changes the units of the input parameters when necessary
assesGrainSelect(): void
    - changes input parameters for individual grains when a different grain type is selected
addGrainToList(): void
    - Adds inputted grain to list of grains in grainListBox
    - Error checks inputted grain for impossible geometries
assesPlotSelect(): void
    - Asses choice box for the plot display
    - Connects the plotArrayList class with motor attributes
updatePlotPane(ArrayList<Double>,ArrayList<Double>,String,String,String): void
    - Updates plot pane for an inputted display
    - configurable with plot name, units and arraylist of different lengths
assesStaticResults(): void
    - Uses staticResults class to create a pane to view the static results of the motor
changeMenuBar(boolean): void
    - Updates the menu bar when coming from home pane (true) and to the home pane (false)
showExceptionStage(String): void
    - Shows exceptionStage with custom message through input
showSelectedGrain(): void
    - Updates input parameters when a grain is selected with grainListBox
setImportedNozzle(Nozzle): void
    - Updates input parameters to match that of the imported nozzle
setImportedPropellant(Propellant): void
    - Updates input parameters to match that of the imported propellant
setImportedGrain(ArrayList<Grain>): void
    - Updates input parameters to match that of the imported grain list
initializeNozzle(): void
    - Creates new Nozzle using input parameters under nozzle design
initializePropellant(): void
    - Creates new Propellant using input parameters under propellant design
initializeGrains(): void
    - Creates new ArrayList<Grain> using grainListBox
validMotor(Motor): boolean
    - Returns boolean if a motor is valid and geometrically possible
    - Builds string message for exception pop up if there are errors in nozzle, propellant or grain design
lambdaFunction(): void
    - holds all lambda functions for most applicable nodes in application
 */
import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import sun.awt.image.PNGImageDecoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class SRMSimApp extends Application {
    // Motor for Sim
    private Propellant propellant;
    private Nozzle nozzle;
    private ArrayList<Grain> grains;
    private int batesCounter, crossCounter;
    private Motor motor;
    private boolean simCompleted = false;

    // Panes
    private final BorderPane borderPane;
    private final Pane homePane;
    private Pane plotPane, staticResultsPane;

    // Menu Bar
    private final MenuItem closeMI, exportPerformanceMI, importMotorMI, exportMotorMI, helpMI;
    private final RadioMenuItem engUnitToggle;
    private final Menu fileMenu, optionsMenu, helpMenu;
    private final MenuBar mainMenu;

    // Motor Input
    // TODO add a update button for grain list
    private final TextField motorNameInput;
    private final designInput throatDiameterInput, exitDiameterInput, exitAngleInput;
    private final designInput densityInput, chamberTempInput, gammaInput, burnRateCoeffInput, burnRateExpInput, molarMassInput;
    private final designInput inhibitedEndsInput, grainLengthInput, outerDiameterInput;
    private final designInput innerDiameterInput;
    private final designInput slitWidthInput, slitLengthInput;
    private final ArrayList<designInput> batesInputArr, crossInputArr;
    private Button addGrain, removeGrain;
    private ChoiceBox<Grain> grainListBox;
    private Label grainListLabel;
    private ImageView tubularImage, crossImage;

    // dotMotor import
    private Label importLabel;
    private BorderPane importBorder;
    private Stage importStage;
    private Pane importPane;
    private Button importButton;
    private TextField importTF;

    // Buttons
    private final Button runSimButton;
    private final Button backToMainPaneButton;
    private final Button viewStaticButton;
    private final Button backToPlotButton;

    // Help
    private final Label helpLabel;
    private final BorderPane helpBorder;
    private final Stage helpStage;
    private final Pane helpPane;

    // Exceptions
    private Label exceptionLabel;
    private BorderPane exceptionBorder;
    private Stage exceptionStage;
    private Pane exceptionPane;
    private StringBuilder errorStringBuilder;

    // Addons
    private final ChoiceBox<String> plotSelect;
    private final ChoiceBox<String> grainChoiceBox;
    private Label plotHeaderLabel;
    private Line topBorderLine;

    // Doubles
    private final double paneHeight;
    private final double paneWidth;

    // Constructor
    public SRMSimApp() throws FileNotFoundException {

        paneWidth = 750;
        paneHeight = 600;

        // Menu bar setup
        closeMI = new MenuItem("Close Sim");
        exportPerformanceMI = new MenuItem("Export .CSV");
        exportMotorMI = new MenuItem("Export .motor");
        importMotorMI = new MenuItem("Import .motor");
        helpMI = new MenuItem("Show Help");
        engUnitToggle = new RadioMenuItem("English Units");
        fileMenu = new Menu("File");
        fileMenu.getItems().addAll(importMotorMI, closeMI);
        optionsMenu = new Menu("Options");
        optionsMenu.getItems().add(engUnitToggle);
        helpMenu = new Menu("Help");
        helpMenu.getItems().add(helpMI);
        mainMenu = new MenuBar(fileMenu, optionsMenu, helpMenu);


        // Motor Input setup
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
        throatDiameterInput = new designInput("Throat Diameter", "",nozzLabel.getLayoutX(), nozzLabel.getLayoutY() + 30);
        exitDiameterInput = new designInput("Exit Diameter", "",nozzLabel.getLayoutX(), throatDiameterInput.getyLoc()+25);
        exitAngleInput = new designInput("Exit Angle", "deg",nozzLabel.getLayoutX(), exitDiameterInput.getyLoc()+25);

        Label propLabel = new Label("Propellant Design");
        propLabel.setFont(new Font(15));
        propLabel.setLayoutX(15);
        propLabel.setLayoutY(exitAngleInput.getyLoc() + 50);
        densityInput = new designInput("Density","",propLabel.getLayoutX(),propLabel.getLayoutY()+30);
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
        grainChoiceBox.getItems().addAll("Tubular","Cross");
        grainChoiceBox.setLayoutX(grainLabel.getLayoutX() + 120);
        grainChoiceBox.setLayoutY(grainLabel.getLayoutY());
        grainChoiceBox.setValue("Tubular");
        inhibitedEndsInput = new designInput("Inhibited Ends","0-2",grainLabel.getLayoutX(),grainChoiceBox.getLayoutY()+30);
        inhibitedEndsInput.getInputTF().setOnKeyReleased(event -> {
            try {
                int num = Integer.parseInt(inhibitedEndsInput.getInputTF().getText());
                if ((num < 0) || (num > 2)) {
                    inhibitedEndsInput.getInputTF().deletePreviousChar();
                }
            } catch (Exception e) {
                inhibitedEndsInput.getInputTF().deletePreviousChar();
            } finally {
                try {
                    int num = Integer.parseInt(inhibitedEndsInput.getInputTF().getText());
                    if ((num < 0) || (num > 2)) {
                        inhibitedEndsInput.getInputTF().clear();
                    }
                } catch (Exception e) {
                    inhibitedEndsInput.getInputTF().clear();
                }
            }

        });
        grainLengthInput = new designInput("Length","",grainLabel.getLayoutX(), inhibitedEndsInput.getyLoc()+25);
        outerDiameterInput = new designInput("Outer Diameter","",grainLabel.getLayoutX(), grainLengthInput.getyLoc()+25);
        batesInputArr = new ArrayList<>();
        innerDiameterInput = new designInput("Inner Diameter","",grainLabel.getLayoutX(), outerDiameterInput.getyLoc()+25);
        batesInputArr.add(inhibitedEndsInput);
        batesInputArr.add(grainLengthInput);
        batesInputArr.add(outerDiameterInput);
        batesInputArr.add(innerDiameterInput);
        crossInputArr = new ArrayList<>();
        slitLengthInput = new designInput("Slit Length","",grainLabel.getLayoutX(), outerDiameterInput.getyLoc()+25);
        slitWidthInput = new designInput("Slit Width","",grainLabel.getLayoutX(), slitLengthInput.getyLoc()+25);
        crossInputArr.add(inhibitedEndsInput);
        crossInputArr.add(grainLengthInput);
        crossInputArr.add(outerDiameterInput);
        crossInputArr.add(slitLengthInput);
        crossInputArr.add(slitWidthInput);

        grainListLabel = new Label("Current Grain Configuration");
        grainListLabel.setLayoutX(grainLabel.getLayoutX());
        grainListLabel.setLayoutY(slitWidthInput.getyLoc()+50);
        grainListLabel.setFont(new Font(15));
        grainListBox = new ChoiceBox<>();
        grainListBox.setLayoutX(grainLabel.getLayoutX());
        grainListBox.setLayoutY(grainListLabel.getLayoutY()+25);
        grainListBox.setMaxWidth(100);
        grainListBox.setMinWidth(100);
        grainListBox.setConverter(new StringConverter<Grain>() {
            @Override
            public String toString(Grain grain) {
                return grain.getGrainName();
            }
            @Override
            public Grain fromString(String grainName) {
                Grain returnGrain = new Grain();
                for (Grain grain:grainListBox.getItems()) {
                    if (Objects.equals(grain.getGrainName(),grainName)) {
                        returnGrain = grain;
                    }
                }
                return returnGrain;
            }
        });
        Image tubularImageFile = new Image(new FileInputStream("tubular.jpeg"));
        tubularImage = new ImageView(tubularImageFile);
        formatImage(tubularImage);
        Image crossImageFile = new Image(new FileInputStream("cross.jpeg"));
        crossImage = new ImageView(crossImageFile);
        formatImage(crossImage);
        Line[] imageBorder = new Line[4];
        imageBorder[0] = new Line(tubularImage.getLayoutX(),tubularImage.getLayoutY(),
                tubularImage.getLayoutX()+tubularImage.getFitWidth(),tubularImage.getLayoutY());
        imageBorder[1] = new Line(tubularImage.getLayoutX(),tubularImage.getLayoutY(),
                tubularImage.getLayoutX(),tubularImage.getLayoutY()+tubularImage.getFitHeight());
        imageBorder[2] = new Line(tubularImage.getLayoutX()+tubularImage.getFitWidth(),
                tubularImage.getLayoutY(),tubularImage.getLayoutX()+tubularImage.getFitWidth(),tubularImage.getLayoutY()+tubularImage.getFitHeight());
        imageBorder[3] = new Line(tubularImage.getLayoutX(),tubularImage.getLayoutY()+tubularImage.getFitHeight(),
                tubularImage.getLayoutX()+tubularImage.getFitWidth(),tubularImage.getLayoutY()+tubularImage.getFitHeight());

        addGrain = new Button("Add");
        addGrain.setLayoutX(grainChoiceBox.getLayoutX()+90);
        addGrain.setLayoutY(grainChoiceBox.getLayoutY());
        removeGrain = new Button("Remove");
        removeGrain.setLayoutX(grainListBox.getLayoutX()+120);
        removeGrain.setLayoutY(grainListBox.getLayoutY());

        setInputUnits();

        // dotMotor import setup
        importTF = new TextField("presetMotor");
        importTF.setLayoutX(0.2*paneWidth-125);
        importTF.setLayoutY(0.1*paneHeight);
        importTF.setMaxSize(200,25);
        importTF.setMinSize(200,25);
        importLabel = new Label(".motor");
        importLabel.setMaxSize(100,30);
        importLabel.setMinSize(100,30);
        importLabel.setLayoutX(importTF.getLayoutX()+210);
        importLabel.setLayoutY(importTF.getLayoutY());
        importButton = new Button("Import");
        importButton.setMaxWidth(60);
        importButton.setMinWidth(60);
        importButton.setLayoutX(.2*paneWidth-30);
        importButton.setLayoutY(.3*paneHeight-65);
        importBorder = new BorderPane();
        importStage = new Stage();
        importPane = new Pane();
        importPane.getChildren().addAll(importTF,importLabel, importButton);
        importBorder.setCenter(importPane);
        Scene importScene = new Scene(importBorder, 0.4*paneWidth, 0.3*paneHeight);
        importStage = new Stage();
        importStage.setScene(importScene);
        importStage.setResizable(false);
        importStage.setTitle("Import Motor");

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
        exceptionLabel = new Label();
        exceptionLabel.setFont(new Font(14));
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
        homePane.getChildren().addAll(grainChoiceBox,addGrain,removeGrain, grainListBox, grainListLabel, tubularImage);
        homePane.getChildren().addAll(imageBorder);
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

    public void formatImage(ImageView image) {
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.setPreserveRatio(true);
        image.setLayoutX(grainListBox.getLayoutX() + 210);
        image.setLayoutY(grainListLabel.getLayoutY());
    }

    public void setInputUnits() {
        if (engUnitToggle.isSelected()) {
            throatDiameterInput.setUnits("in");
            exitDiameterInput.setUnits("in");

            densityInput.setUnits("lbm/in3");
            chamberTempInput.setUnits("R");
            burnRateCoeffInput.setUnits("in/s/psi");
            molarMassInput.setUnits("lbm/lbmol");

            grainLengthInput.setUnits("in");
            outerDiameterInput.setUnits("in");
            innerDiameterInput.setUnits("in");
            slitWidthInput.setUnits("in");
            slitLengthInput.setUnits("in");
        }
        else {
            throatDiameterInput.setUnits("cm");
            exitDiameterInput.setUnits("cm");

            densityInput.setUnits("kg/m3");
            chamberTempInput.setUnits("K");
            burnRateCoeffInput.setUnits("mm/s/MPa");
            molarMassInput.setUnits("g/mol");

            grainLengthInput.setUnits("cm");
            outerDiameterInput.setUnits("cm");
            innerDiameterInput.setUnits("cm");
            slitWidthInput.setUnits("cm");
            slitLengthInput.setUnits("cm");
        }
    }

    public void assesGrainSelect() {
        homePane.getChildren().removeAll(innerDiameterInput.getNodeArr());
        homePane.getChildren().removeAll(slitLengthInput.getNodeArr());
        homePane.getChildren().removeAll(slitWidthInput.getNodeArr());
        homePane.getChildren().removeAll(tubularImage,crossImage);
        if (Objects.equals(grainChoiceBox.getValue(),"Tubular")) {
            homePane.getChildren().addAll(innerDiameterInput.getNodeArr());
            homePane.getChildren().add(tubularImage);
        }
        else if (Objects.equals(grainChoiceBox.getValue(),"Cross")) {
            homePane.getChildren().addAll(slitLengthInput.getNodeArr());
            homePane.getChildren().addAll(slitWidthInput.getNodeArr());
            homePane.getChildren().add(crossImage);
        }
    }

    public void addGrainToList() {
        if (Objects.equals(grainChoiceBox.getValue(),"Tubular")) {
            Tubular newTubular = new Tubular();
            try {

                if (outerDiameterInput.getValue() <= innerDiameterInput.getValue()) {
                    showExceptionStage("Invalid Tubular Geometry\n\nInner diameter must be smaller than outer diameter");
                }
                else if ((outerDiameterInput.getValue() == 0) || (innerDiameterInput.getValue() == 0) || (grainLengthInput.getValue() == 0)) {
                    showExceptionStage("Invalid Tubular Geometry\n\nLengths cannot be zero");
                }
                else {
                    newTubular.setInhibitedEnds(inhibitedEndsInput.getValue());
                    newTubular.setOuterDiameter(outerDiameterInput.getValue());
                    newTubular.setGrainLength(grainLengthInput.getValue());
                    newTubular.setInnerDiameter(innerDiameterInput.getValue());
                    newTubular.runGrainConversion(engUnitToggle.isSelected());
                    batesCounter++;
                    newTubular.setGrainName("BATES " + batesCounter);
                    grainListBox.getItems().add(newTubular);
                    grainListBox.setValue(newTubular);
                }
            } catch (Exception e) {
                showExceptionStage("Invalid Tubular Geometry");
            }

        }
        else if (Objects.equals(grainChoiceBox.getValue(),"Cross")) {
            Cross newCross = new Cross();
            try{
                newCross.setInhibitedEnds(inhibitedEndsInput.getValue());
                newCross.setOuterDiameter(outerDiameterInput.getValue());
                newCross.setGrainLength(grainLengthInput.getValue());
                newCross.setWidth(slitWidthInput.getValue());
                newCross.setLength(slitLengthInput.getValue());
                newCross.runGrainConversion(engUnitToggle.isSelected());
                if ((slitLengthInput.getValue() >= outerDiameterInput.getValue()/2) || (slitWidthInput.getValue() >= outerDiameterInput.getValue())) {
                    showExceptionStage("Invalid Cross Geometry\n\nSlits cannot be exceed bounds of motor");
                }
                else if ((outerDiameterInput.getValue() == 0) || (slitWidthInput.getValue() == 0) || (slitLengthInput.getValue() == 0)|| (grainLengthInput.getValue() == 0)) {
                    showExceptionStage("Invalid Cross Geometry\n\nLengths cannot be zero");
                }
                else {
                    crossCounter++;
                    newCross.setGrainName("Cross " + crossCounter);
                    grainListBox.getItems().add(newCross);
                    grainListBox.setValue(newCross);
                }
            } catch (Exception e) {
                showExceptionStage("Invalid Cross Geometry");
            }
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

    public void assesStaticResults() {
        staticResults staticResults = new staticResults(motor, paneHeight, paneWidth);
        staticResultsPane = staticResults.getStaticResultsPane();
        staticResultsPane.getChildren().addAll(backToPlotButton);
    }

    public void changeMenuBar(boolean fromHomePane) {
        if (fromHomePane) {
            fileMenu.getItems().clear();
            fileMenu.getItems().addAll(exportPerformanceMI, exportMotorMI, closeMI);
        }
        else {
            fileMenu.getItems().clear();
            fileMenu.getItems().addAll(importMotorMI, closeMI);
        }
    }

    public void showExceptionStage(String message) {
        exceptionLabel.setText(message);
        exceptionStage.close();
        exceptionPane.getChildren().clear();
        exceptionPane.getChildren().add(exceptionLabel);
        exceptionStage.setAlwaysOnTop(true);
        exceptionStage.show();
    }

    public void showSelectedGrain() {
        try {
            inhibitedEndsInput.getInputTF().setText(grainListBox.getValue().getDispInhibitedEnds());
            grainLengthInput.getInputTF().setText(grainListBox.getValue().getDispGrainLength(engUnitToggle.isSelected()));
            outerDiameterInput.getInputTF().setText(grainListBox.getValue().getDispOuterDiameter(engUnitToggle.isSelected()));
            if (grainListBox.getValue() instanceof Tubular) {
                grainChoiceBox.setValue("Tubular");
                assesGrainSelect();
                innerDiameterInput.getInputTF().setText(((Tubular) grainListBox.getValue()).getDispInnerDiameter(engUnitToggle.isSelected()));
            }
            else if (grainListBox.getValue() instanceof Cross) {
                grainChoiceBox.setValue("Cross");
                assesGrainSelect();
                slitLengthInput.getInputTF().setText(((Cross) grainListBox.getValue()).getDispSlitLength(engUnitToggle.isSelected()));
                slitWidthInput.getInputTF().setText(((Cross) grainListBox.getValue()).getDispSlitWidth(engUnitToggle.isSelected()));
            }
        } catch (NullPointerException ignored) {
        }
    }


    public void setImportedNozzle(Nozzle nozzle) {
        throatDiameterInput.getInputTF().setText(nozzle.getDispThroatDiameter(engUnitToggle.isSelected()));
        exitDiameterInput.getInputTF().setText(nozzle.getDispExitDiameter(engUnitToggle.isSelected()));
        exitAngleInput.getInputTF().setText(nozzle.getDispExitAngle());
    }

    public void setImportedPropellant(Propellant propellant) {
        densityInput.getInputTF().setText(propellant.getDispDensity(engUnitToggle.isSelected()));
        chamberTempInput.getInputTF().setText(propellant.getDispChamberTemp(engUnitToggle.isSelected()));
        gammaInput.getInputTF().setText(propellant.getDispGamma());
        burnRateCoeffInput.getInputTF().setText(propellant.getDispBurnRateCoeff(engUnitToggle.isSelected()));
        burnRateExpInput.getInputTF().setText(propellant.getDispBurnRateExp());
        molarMassInput.getInputTF().setText(propellant.getDispMolarMass());
    }

    public void setImportedGrains(ArrayList<Grain> grains) {
        grainListBox.getItems().clear();
        grainListBox.getItems().addAll(grains);
        grainListBox.setValue(grainListBox.getItems().get(0));
        showSelectedGrain();
    }

    public void initializeNozzle() {
        nozzle = new Nozzle();
        try {
            nozzle.setThroatDiameter(throatDiameterInput.getValue());
            nozzle.setExitDiameter(exitDiameterInput.getValue());
            nozzle.setExitAngle(exitAngleInput.getValue());
            nozzle.runNozzleConversion(engUnitToggle.isSelected());
        } catch (Exception e) {
        }
    }

    public void initializePropellant() {
        propellant = new Propellant();
        try {
            propellant.setDensity(densityInput.getValue());
            propellant.setGamma(gammaInput.getValue());
            propellant.setMolarMass(molarMassInput.getValue());
            propellant.setChamberTemp(chamberTempInput.getValue());
            propellant.setBurnRateExp(burnRateExpInput.getValue());
            propellant.setBurnRateCoeff(burnRateCoeffInput.getValue());
            propellant.runPropConversion(engUnitToggle.isSelected());
        } catch (Exception ignored) {
        }
    }

    public void initializeGrains() {
        grains = new ArrayList<>();
        grains.addAll(grainListBox.getItems());
    }

    public boolean validMotor(Motor motor) {
        boolean propValid = false;
        boolean nozzValid = false;
        boolean grainsValid = false;

        Propellant prop = motor.getPropellant();
        Nozzle nozz = motor.getNozzle();
        ArrayList<Grain> grains = motor.getGrainList();
        if ((prop.getGamma() > 0) && (prop.getDensity() > 0) && (prop.getBurnRateExp() > 0) && (prop.getBurnRateCoeff() > 0) &&
                (prop.getChamberTemp() > 0) & (prop.getMolarMass() > 0)) {
            propValid = true;
        }
        else {
            errorStringBuilder.append("Invalid Propellant Composite\n\n");
        }
        if ((nozz.getExitDiameter() > 0) && (nozz.getThroatDiameter() > 0) && (nozz.getExitAngle() > 0) && (nozz.getExitAngle() < 90)) {
            nozzValid = true;
        }
        else {
            errorStringBuilder.append("Invalid Nozzle Geometry\n\n");
        }
        if (grains.size() > 0) {
            grainsValid = true;
        }
        else {
            errorStringBuilder.append("Invalid Grain Configuration\n\n");
        }
        return propValid && nozzValid && grainsValid;
    }

    public void lambdaFunctions() {
        // Close Application
        closeMI.setOnAction(event -> {
            System.exit(0);
        });

        grainChoiceBox.setOnAction(event -> {
            assesGrainSelect();
        });

        runSimButton.setOnAction(event -> {
            errorStringBuilder = new StringBuilder();
            initializePropellant();
            initializeNozzle();
            initializeGrains();
            motor = new Motor(propellant,nozzle,grains);
            if (motorNameInput.getText().isEmpty()) {
                this.motor.setMotorName("myMotor");
            }
            else {
                this.motor.setMotorName(motorNameInput.getText());
            }
            if (validMotor(motor)) {
                try {
                    motor.runSim();
                    changeMenuBar(true);
                    simCompleted = true;
                    exceptionStage.close();
                    helpStage.close();
                    motor.convertResult(engUnitToggle.isSelected());
                    updatePlotPane(motor.getTimeList(),motor.getThrustList(), "Thrust vs Time", motor.getTimeUnits(), motor.getThrustUnits());
                } catch (Exception e) {
                    showExceptionStage("Sim could not be completed");
                }
            }
            else {
                showExceptionStage(errorStringBuilder.toString());
            }
        });

        // Add grain to grain list
        addGrain.setOnAction(event -> {
            addGrainToList();
        });

        removeGrain.setOnAction(event -> {
            grainListBox.getItems().remove(grainListBox.getValue());
        });

        grainListBox.setOnAction(event -> {
            showSelectedGrain();
        });

        // Export Results as CSV file
        exportPerformanceMI.setOnAction(event -> {
            try {
                new exportPerformance(motor);
            } catch (IOException e) {
                showExceptionStage("CSV file could not be exported");
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
        // TODO Add conversion for text fields when eng unit is toggled
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
            importStage.close();
            importStage.show();
        });
        importButton.setOnAction(event -> {
            String filename = importTF.getText() + ".motor";
            Motor motorImport = dotMotorIO.importMotor(filename);
            try {
                engUnitToggle.setSelected(!motorImport.isSI());
                setInputUnits();
                setImportedNozzle(motorImport.getNozzle());
                setImportedPropellant(motorImport.getPropellant());
                setImportedGrains(motorImport.getGrainList());
                motorNameInput.setText(motorImport.getMotorName());
            } catch (Exception e) {
                showExceptionStage("File not found");
            } finally {
                importStage.close();
            }
        });
        // Export .motor file
        exportMotorMI.setOnAction(event -> {
            try {
                dotMotorIO.exportMotor(motor);
            } catch (Exception e) {
                showExceptionStage("Could not export .motor file");
            }
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
