/*
Name: Patrick Kennedy
Date: 4/20/22

NOTE: I know static methods are not advised, though I took liberty in using them here as a design choice and convenience

dotMotorIO
    - This class handles the importation and exportation of .motor files

Methods:
exportMotor(Motor): void
    - This method takes a motor argument and creates a unique .motor file
    - This file is serialized, hence the implementation of Serializable in all motor related classes
importMotor(String): Motor
    - This method takes a string filename input and outputs the corresponding motor object for said filename
 */
import java.io.*;
import java.util.Objects;

public class dotMotorIO {

    public static void exportMotor(Motor motor, boolean overrideFile) {
        try {
            String motorName = motor.getMotorName();
            if (Objects.isNull(motorName)) { // Auto assign name
                motorName = "myMotor";
            }
            int i = 0;
            File file = new File(motorName + ".motor");
            if (!overrideFile) {
                while (file.isFile()) {
                    i++;
                    file = new File(motorName + "(" + i + ").motor");
                }
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
            open.close();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return motor;
    }
}
