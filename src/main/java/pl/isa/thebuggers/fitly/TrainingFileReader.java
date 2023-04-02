package pl.isa.thebuggers.fitly;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrainingFileReader {
    public static void displayTrainingFile() {
        try {
            File file = new File("src/main/resources/Training.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("Sample training");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}