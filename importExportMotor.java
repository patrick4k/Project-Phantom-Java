import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class importExportMotor {
    private Motor motor;

    public static void main(String[] args) {
        test test = new test();
        Motor motor = test.initMotor();
        exportMotor(motor);
    }

    public static void exportMotor(Motor motor) {
        try {
            FileOutputStream saveFile = new FileOutputStream(motor.getMotorName() + ".txt");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(motor);
            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
