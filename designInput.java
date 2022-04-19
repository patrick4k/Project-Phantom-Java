import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Objects;

public class designInput {
    private Label inputText, units;
    private TextField inputTF;
    private double xLoc, yLoc;

    public designInput(String inputStr, String unitsStr, double xLoc, double yLoc) {
        double TFOffset = 120;
        double unitsOffset = TFOffset + 105;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        inputText = new Label(inputStr);
        inputText.setLayoutX(xLoc);
        inputText.setLayoutY(yLoc);
        inputText.setFont(new Font(14));
        inputTF = new TextField();
        inputTF.setMaxSize(100,10);
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
        return Double.parseDouble(inputTF.getText());
    }

}
