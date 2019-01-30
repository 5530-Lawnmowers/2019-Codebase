import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CsvParser {

    public static void main(String[] args) {
        String fileName= "Output.csv";
        File file= new File(fileName);

        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int lineNo = 1;
        for(List<String> line: lines) {
            int columnNo = 1;
            for (String value: line) {
                System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                columnNo++;
            }
            lineNo++;
        }
    }

}