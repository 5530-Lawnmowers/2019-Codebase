package frc.robot.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import edu.wpi.first.wpilibj.Filesystem;

public class CSVHelper {

    //Column 1: Left Velocity, Column 2: Left Distance, Column 3: Right Velocity, Column 4: Right Distance
    public List<List<String>> data;

    public CSVHelper(String fileName) {

        File file = new File(Filesystem.getDeployDirectory(), fileName);


        data = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                data.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getLength() {
        return data.size();
    }

    public double getLeftVelocity(int index) {
        return Double.valueOf(data.get(index).get(0));
    }

    public double getLeftDistance(int index) {
        return Double.valueOf(data.get(index).get(1));
    }
    
    public double getRightVelocity(int index) {
        return Double.valueOf(data.get(index).get(2));
    }

    public double getRightDistance(int index) {
        return Double.valueOf(data.get(index).get(3));
    }

}
