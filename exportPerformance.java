import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class exportPerformance {
    private Motor motor;
    private ArrayList<String> formattedData;
    private String filename;

    public exportPerformance(Motor motor) throws IOException {
        this.motor = motor;
        extractMotorData();
        exportCsvFile();
    }

    public void extractMotorData() {
        formattedData = new ArrayList<>();
        formattedData.add("Time (" + motor.getTimeUnits() + "), Thrust(" + motor.getThrustUnits() + "), Chamber Pressure(" + motor.getPressureUnits()
                + "), Mass Flow(" + motor.getMassFlowUnits() + "), Mass Ejected (" + motor.getMassUnits() + "), Mass Flux (" + motor.getMassFluxUnits()
                + "), Burn Area (" + motor.getAreaUnits() + "), Kn" + ", Regression Rate (" + motor.getVelocityUnits() + "), Regression (" + motor.getLengthUnits()
                + "), Exit Pressure (" + motor.getPressureUnits() + "), Force Coefficient" + ", Free Volume (" + motor.getVolumeUnits()
                + "), Volume Loading");
        for (int i = 0; i < motor.getCounter(); i++) {
            String timeStr = motor.getTimeList().get(i) + ",";
            String thrustStr = motor.getThrustList().get(i) + ",";
            String chamberPressureStr = motor.getChamberPressureList().get(i) + ",";
            String massFlowStr = motor.getMassFlowList().get(i) + ",";
            String massEjectStr = motor.getMassEjectedList().get(i) + ",";
            String massFluxStr = motor.getMassFluxList().get(i) + ",";
            String burnAreaStr = motor.getBurnAreaList().get(i) + ",";
            String knStr = motor.getKnList().get(i) + ",";
            String regRateStr = motor.getRegRateList().get(i) + ",";
            String regTotalStr = motor.getRegTotalList().get(i) + ",";
            String exitPressureStr = motor.getExitPressureList().get(i) + ",";
            String forceCoeffStr = motor.getForceCoeffList().get(i) + ",";
            String freeVolStr = motor.getFreeVolumeList().get(i) + ",";
            String volLoadStr = motor.getVolumeLoadingList().get(i) + ",";
            formattedData.add(timeStr+thrustStr+chamberPressureStr+massFlowStr+massEjectStr+massFluxStr+burnAreaStr+knStr+
                    regRateStr+regTotalStr+exitPressureStr+forceCoeffStr+freeVolStr+volLoadStr);
        }
    }

    public void exportCsvFile() throws IOException {
        String motorName = motor.getMotorName();
        if (Objects.isNull(motorName)) { // Auto assign name
            motorName = "myMotor";
        }
        int i = 0;
        File file = new File(motorName + ".csv");
        while (file.isFile()) {
            i++;
            file = new File(motorName + "_v" + i + ".csv");
        }

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String formattedDatum : formattedData) {
            bufferedWriter.write(formattedDatum);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

}
