import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class designInput {
    private Label inputText, units;
    private TextField inputTF;

    public designInput(String inputStr, String unitsStr, double xLoc, double yLoc) {
        double TFOffset = 120;
        double unitsOffset = TFOffset + 105;
        inputText = new Label(inputStr);
        inputText.setLayoutX(xLoc);
        inputText.setLayoutY(yLoc);
        inputText.setFont(new Font(14));
        inputTF = new TextField();
        inputTF.setMaxSize(100,10);
        inputTF.setLayoutX(xLoc + TFOffset);
        inputTF.setLayoutY(yLoc);
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

}
