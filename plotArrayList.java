/*
Name: Patrick Kennedy
Date: 4/20/22

plotArrayList
    - This class is used to handle the creation of a plot pane

Methods:
plotArrayList(ArrayList<Double>, ArrayList<Double>, double, double, String, String): Constructor
    - This constructor imports 2 arrayList, pane width and height and two string for x and y axis text
    - Most nodes belonging to the plot pane are created and initialized within the constructor
scaleData(): void
    - This method formats the circle array to fit within the pane boundaries
roundToSigFigs(double, int): String
    - This method returns a string form of a double after that double is rounded to a specific number of significant digits
    - This method uses math to chop off a majority of the significant digits, but requires Decimal format to compensate for java math errors
scaleAxis(): void
    - This method formats the axes of the plot to 5 different values
getPlotPane(): Pane
    - Assembles and returns the plot pane
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class plotArrayList {
    private ArrayList<Double> arrX;
    private ArrayList<Double> arrY;
    private double paneHeight;
    private double paneWidth;
    private double maxHeight;
    private double maxWidth;
    private double pointRadius;
    private Circle[] points;
    private Rectangle plotBorderWhiteFill;
    private Rectangle plotBorderBlackEdge;
    private Pane plotPane;
    private Line[] hLines;
    private Line[] vLines;
    private Label[] xAxisLabels;
    private Label[] yAxisLabels;
    private Label xAxisTitle;
    private Label yAxisTitle;
    private Line topBorderLine;

    public plotArrayList(ArrayList<Double> arrX, ArrayList<Double> arrY, double paneHeight, double paneWidth, String xAxisText, String yAxisText) {
        this.arrX = arrX;
        this.arrY = arrY;
        while (arrX.size() > arrY.size()) {
            arrY.add(0.0);
        }
        while (arrY.size() > arrX.size()) {
            arrX.add(0.0);
        }
        this.paneHeight = paneHeight;
        this.paneWidth = paneWidth;
        topBorderLine = new Line(0,45,paneWidth,45);
        maxHeight = 0.5*paneHeight;
        maxWidth = 0.5*paneWidth;
        pointRadius = 1;
        plotBorderWhiteFill = new Rectangle(maxWidth,maxHeight);
        plotBorderWhiteFill.setLayoutX(0.25*paneWidth);
        plotBorderWhiteFill.setLayoutY(0.25*paneHeight);
        plotBorderWhiteFill.setFill(Color.WHITE);
        plotBorderBlackEdge = new Rectangle(maxWidth+5,maxHeight+5);
        plotBorderBlackEdge.setLayoutX(0.25*paneWidth-2.5);
        plotBorderBlackEdge.setLayoutY(0.25*paneHeight-2.5);
        hLines = new Line[3];
        vLines = new Line[3];
        for (int i = 0; i < 3; i++) {
            double yLoc = 0.25*paneHeight + ((double) i+1)*0.5*paneHeight/4;
            double xLoc = 0.25*paneWidth + ((double) i+1)*0.5*paneWidth/4;
            hLines[i] = new Line(0.25*paneWidth,yLoc, 0.75*paneWidth,yLoc);
            vLines[i] = new Line(xLoc,0.25*paneHeight, xLoc,0.75*paneHeight);
        }
        xAxisTitle = new Label(xAxisText);
        yAxisTitle = new Label(yAxisText);
        yAxisTitle.setRotate(-90);

        xAxisTitle.setLayoutX(.5*paneWidth - 5);
        xAxisTitle.setLayoutY(0.85*paneHeight);
        yAxisTitle.setLayoutX(0.05*paneWidth);
        yAxisTitle.setLayoutY(0.5*paneHeight - 5);

        plotPane = new Pane();
    }

    public void scaleData() {
        points = new Circle[arrX.size()];
        double maxX = Collections.max(arrX);
        double maxY = Collections.max(arrY);
        double xFactor = maxWidth/maxX;
        double yFactor = maxHeight/maxY;
        for (int i = 0; i < arrX.size(); i++) {
            points[i] = new Circle(pointRadius);
            points[i].setLayoutX(arrX.get(i) * xFactor + 0.25*paneWidth);
            points[i].setLayoutY(paneHeight - arrY.get(i) * yFactor - 0.25*paneHeight);
        }
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

    public void scaleAxis() {
        xAxisLabels = new Label[5];
        yAxisLabels = new Label[5];
        double maxX = Collections.max(arrX);
        double maxY = Collections.max(arrY);
        for (int i = 0; i < 5; i++) {

            xAxisLabels[i] = new Label(String.valueOf(roundToSigFigs(maxX*((double) i /4), 3)));
            yAxisLabels[i] = new Label(String.valueOf(roundToSigFigs(maxY*((double) i /4), 4)));

            xAxisLabels[i].setLayoutX(0.25*paneWidth - 10+ ((double) i /4)*0.5*paneWidth);
            xAxisLabels[i].setLayoutY(0.75*paneHeight + 30);

            yAxisLabels[i].setLayoutX(0.25*paneWidth - 75);
            yAxisLabels[i].setLayoutY(0.75*paneHeight - 5 - ((double) i /4)*0.5*paneHeight);
        }
    }

    public Pane getPlotPane() {
        scaleData();
        scaleAxis();
        plotPane.getChildren().clear();
        plotPane.getChildren().addAll(topBorderLine, plotBorderBlackEdge, plotBorderWhiteFill, xAxisTitle, yAxisTitle);
        plotPane.getChildren().addAll(xAxisLabels);
        plotPane.getChildren().addAll(yAxisLabels);
        plotPane.getChildren().addAll(hLines);
        plotPane.getChildren().addAll(vLines);
        for (Circle point:points) {
            point.setFill(Color.BLUE);
            plotPane.getChildren().add(point);
        }
        return plotPane;
    }
}
