/*
importExportMotor.java
By Patrick Kennedy
Date Modified: 4/12/21
This class is used to import and export a motor, this creates a custom file type of ".motor" that stores serialized attributes
of a motor
 */

import java.io.*;
import java.util.Objects;

public class dotMotorIO {

    public static void exportMotor(Motor motor) {
        try {
            String motorName = motor.getMotorName();
            if (Objects.isNull(motorName)) { // Auto assign name
                motorName = "myMotor";
            }
            int i = 0;
            File file = new File(motorName + ".motor");
            while (file.isFile()) {
                i++;
                file = new File(motorName + "(" + i + ").motor");
            }
            FileOutputStream saveFile = new FileOutputStream(file.getName());
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(motor);
            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Motor importMotor(String filename) {
        Motor motor = null;
        try {
            FileInputStream openFile = new FileInputStream(filename);
            ObjectInputStream open = new ObjectInputStream(openFile);
            motor = (Motor) open.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return motor;
    }
}
