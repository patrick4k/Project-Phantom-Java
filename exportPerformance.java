import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class exportPerformance {
    private Motor motor;
    private String filename;
    private ArrayList<String> formattedData;

    public exportPerformance(Motor motor, String filename) throws IOException {
        this.motor = motor;
        this.filename = filename;
        formatData();
        exportCsvFile();
    }

    public void appendFormattedData(StringBuilder stringBuilder, ArrayList<Double> arrList) {
        for (double arrValue:arrList) {
            stringBuilder.append(arrValue);
            stringBuilder.append(",");
        }
        formattedData.add(stringBuilder.toString());
        stringBuilder.delete(0,stringBuilder.length());
    }

    public static void main(String[] args) throws IOException {
        test test = new test();
        Motor motor = test.initMotor();
        motor.runSim();
        new exportPerformance(motor, "testFilename");
    }

    public void formatData() {
        formattedData = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        // Time
        stringBuilder.append("Time (").append(motor.getTimeUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getTimeList());

        // Thrust
        stringBuilder.append("Thrust (").append(motor.getThrustUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getThrustList());

        // Chamber Pressure
        stringBuilder.append("Chamber Pressure (").append(motor.getPressureUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getChamberPressureList());

        // Mass Flow
        stringBuilder.append("Mass Flow (").append(motor.getMassFlowUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getMassFlowList());

        // Mass Ejected
        stringBuilder.append("Mass Ejected (").append(motor.getMassUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getMassEjectedList());

        // Mass Flux
        stringBuilder.append("Mass Flux (").append(motor.getMassFluxUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getMassFluxList());

        // Burn Area
        stringBuilder.append("Burn Area (").append(motor.getAreaUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getBurnAreaList());

        // Kn
        stringBuilder.append("Kn,");
        appendFormattedData(stringBuilder,motor.getKnList());

        // Regression Rate
        stringBuilder.append("Regression Rate (").append(motor.getVelocityUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getRegRateList());

        // Regression
        stringBuilder.append("Regression (").append(motor.getLengthUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getRegTotalList());

        // Exit Pressure
        stringBuilder.append("Exit Pressure (").append(motor.getPressureUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getExitPressureList());

        // Force Coefficient
        stringBuilder.append("Force Coefficient,");
        appendFormattedData(stringBuilder,motor.getChamberPressureList());

        // Free Volume
        stringBuilder.append("Free Volume (").append(motor.getVolumeUnits()).append("),");
        appendFormattedData(stringBuilder,motor.getFreeVolumeList());

        // Volume Loading
        stringBuilder.append("Volume Loading,");
        appendFormattedData(stringBuilder,motor.getVolumeLoadingList());

    }

    public void exportCsvFile() throws IOException {
        File file = new File(filename + ".csv");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String formattedDatum : formattedData) {
            bufferedWriter.write(formattedDatum);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

}
