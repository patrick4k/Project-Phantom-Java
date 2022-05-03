/*
Name: Patrick Kennedy
Date: 4/20/22

designInput
    - This class is a blueprint for all design input when the use creates their motor
    - This class is used for organization and non-redundant code

Methods:
designInput(String,String,double,double): Constructor
    - The constructor handle the creation of the nodes
    - The String inputs is the descriptive text for input type, the second is the units th input would be in
    - The doubles are for the location of the nodes
getNodeArr(): ArrList<Node>
    - Returns an arraylist of nodes that can be added to the home pane as an input
getInputTF(): TextField
    - Returns the text field for each design input, used for motor creation and importation
 */
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Objects;

public class designInput {
    private final Label inputText, units;
    private final TextField inputTF;
    private final double xLoc, yLoc;

    public designInput(String inputStr, String unitsStr, double xLoc, double yLoc) {
        double TFOffset = 120;
        double unitsOffset = TFOffset + 105;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        inputText = new Label(inputStr);
        inputText.setLayoutX(xLoc);
        inputText.setLayoutY(yLoc);
        inputText.setFont(new Font(14));
        inputTF = new TextField("0.0");
        inputTF.setMaxSize(100,25);
        inputTF.setMinSize(100,25);
        inputTF.setLayoutX(xLoc + TFOffset);
        inputTF.setLayoutY(yLoc);

        // Force text input to be double
        inputTF.setOnKeyReleased(event -> {
            try {
                double num = Double.parseDouble(inputTF.getText());
                if (Objects.equals(inputTF.getText(inputTF.getText().length() - 1, inputTF.getText().length()), " ")) {
                    inputTF.deletePreviousChar();
                }
            } catch (Exception e) {
                inputTF.deletePreviousChar();
            } finally {
                try {
                    double num = Double.parseDouble(inputTF.getText());
                } catch (Exception e) {
                    inputTF.clear();
                }
            }
        });

        units = new Label(unitsStr);
        units.setLayoutX(xLoc + unitsOffset);
        units.setLayoutY(yLoc);
    }

    public ArrayList<Node> getNodeArr() {
        ArrayList<Node> returnArr = new ArrayList<>();
        returnArr.add(inputText);
        returnArr.add(inputTF);
        returnArr.add(units);
        return returnArr;
    }

    public TextField getInputTF() {
        return inputTF;
    }

    public void setUnits(String unitsStr) {
        units.setText(unitsStr);
    }

    public double getyLoc() {
        return yLoc;
    }

    public double getValue() {
        double returnValue = 0;
        try {
            returnValue = Double.parseDouble(inputTF.getText());
        } catch (Exception ignored) {
        }
        return returnValue;
    }

}
