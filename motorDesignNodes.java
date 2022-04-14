import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class motorDesignNodes {
    private final ArrayList<Label> nozzLabels = new ArrayList<>();
    private final ArrayList<Label> propLabels = new ArrayList<>();
    private final ArrayList<TextField> nozzInputs = new ArrayList<>();
    private final ArrayList<TextField> propInputs = new ArrayList<>();
    String lengthUnits, burnRateCoeffUnits, tempUnits, molarMassUnits, densityUnits, angleUnits = "deg";

    public motorDesignNodes(Boolean toEng) {
        // set up units
        if (toEng) {
            lengthUnits = "(in)";
            burnRateCoeffUnits = "(in/s/ksi)";
            tempUnits = "(R)";
            molarMassUnits = "(lbm/lbmol)";
            densityUnits = "(lbm/in3)";
        }
        else {
            lengthUnits = "(cm)";
            burnRateCoeffUnits = "(mm/s/MPa)";
            tempUnits = "(K)";
            molarMassUnits = "(g/mol)";
            densityUnits = "(kg/m3)";
            angleUnits = "(deg)";
        }

        // Start Nozzle Sec
        Label nozzTitle = new Label("Nozzle Design:");
        nozzTitle.setFont(new Font(14));
        Label nozzThroatDiameterLabel = new Label("Throat Diameter:");
        Label nozzExiDiameterLabel = new Label("Exit Diameter:");
        Label nozzExitAngleLabel = new Label("Exit Angle:");

        nozzTitle.setLayoutX(15);
        nozzThroatDiameterLabel.setLayoutX(15);
        nozzExiDiameterLabel.setLayoutX(15);
        nozzExitAngleLabel.setLayoutX(15);

        nozzTitle.setLayoutY(55);
        nozzThroatDiameterLabel.setLayoutY(55 + 25);
        nozzExiDiameterLabel.setLayoutX(55 + 2*25);
        nozzExitAngleLabel.setLayoutX(55 + 3*25);

        nozzLabels.add(nozzTitle);
        nozzLabels.add(nozzThroatDiameterLabel);
        nozzLabels.add(nozzExiDiameterLabel);
        nozzLabels.add(nozzExitAngleLabel);

        TextField textInput1 = new TextField();
        textInput1.setLayoutX(15 + 100);
        textInput1.setLayoutY(77.5 + 25);
        textInput1.setMaxSize(100,10);
        TextField textInput2 = new TextField();
        textInput2 = textInput1;
        textInput2.setLayoutY(textInput1.getLayoutY()+25);
        TextField textInput3 = new TextField();
        textInput3 = textInput1;
        textInput3.setLayoutY(textInput1.getLayoutY()+50);
        nozzInputs.add(textInput1);
        nozzInputs.add(textInput2);
        nozzInputs.add(textInput3);

        Label nozzThroatDiameterUnitsLabel = new Label(lengthUnits);
        nozzThroatDiameterUnitsLabel.setLayoutX(15 + + 100 + 100 + 15);
        nozzThroatDiameterUnitsLabel.setLayoutY(55 + 25);
        nozzLabels.add(nozzThroatDiameterUnitsLabel);

        // End Nozzle Section

        // Start Propellant Section
        Label propTitle = new Label("Propellant Design");
        propTitle.setFont(new Font(14));
        Label propDensityLabel = new Label("Density:");
        Label propChamberTempLabel = new Label("Chamber Temp:");
        Label propGammaLabel = new Label("Spec. Heat Ratio:");
        Label propBurnRateCoeffLabel = new Label("Burn Rate Coeff:");
        Label propBurnRateExpLabel = new Label("Burn Rate Exp:");
        Label propMolarMassLabel = new Label("Molar Mass:");
        propLabels.add(propTitle);
        propLabels.add(propDensityLabel);
        propLabels.add(propChamberTempLabel);
        propLabels.add(propGammaLabel);
        propLabels.add(propBurnRateCoeffLabel);
        propLabels.add(propBurnRateExpLabel);
        propLabels.add(propMolarMassLabel);

        for (int i = 0; i < propLabels.size(); i ++) {
            propLabels.get(i).setLayoutX(400);
            propLabels.get(i).setLayoutY(55 + 25*i);
            if (i > 0) {
                propInputs.add(new TextField());
            }
        }
        for (int i = 0; i < propInputs.size(); i++) {
            propInputs.get(i).setLayoutX(propLabels.get(i).getLayoutX() + 140);
            propInputs.get(i).setLayoutY(77.5 + 25*i);
            propInputs.get(i).setMaxSize(100,10);
        }
    }

    public ArrayList<Label> getNozzLabels() {
        return nozzLabels;
    }

    public ArrayList<TextField> getNozzInputs() {
        return nozzInputs;
    }

    public ArrayList<Label> getPropLabels() {
        return propLabels;
    }

    public ArrayList<TextField> getPropInputs() {
        return propInputs;
    }
}
