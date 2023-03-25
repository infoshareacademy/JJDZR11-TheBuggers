package pl.isa.thebuggers.fitly;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Training {
    private double userWeight; // waga użytkownika w kilogramach

    public Training(double weight) {
        this.userWeight = weight;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }

    public void calculateTraining() {
        // Wyliczenie wagi ciężaru do ćwiczeń
        double benchPress = 0.75 * userWeight;
        double squat = 1.25 * userWeight;
        double deadlift = 1.5 * userWeight;
        double dumbbellCurls = 0.25 * userWeight;
        double dumbbellRows = 0.5 * userWeight;
        double dumbbellShoulderPress = 0.4 * userWeight;

        System.out.println("Sample exercise weight based on your weight");
        System.out.println("Bench press: " + benchPress + " kg");
        System.out.println("Squat: " + squat + " kg");
        System.out.println("Deadlift: " + deadlift + " kg");
        System.out.println("Dumbbell curls: " + dumbbellCurls + " kg");
        System.out.println("Dumbbell rows: " + dumbbellRows + " kg");
        System.out.println("Dumbbell shoulder press: " + dumbbellShoulderPress + " kg");
        System.out.println("");

    }

    public void calculateTrainingWeightBasedOnRM() {
        // Lista liczby powtórzeń
        int[] reps = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        // Przedziały procentowe dla różnej liczby powtórzeń
        double[] percentageRanges = {1.0, 0.9, 0.85, 0.8, 0.75, 0.7, 0.65, 0.6, 0.55, 0.5, 0.45, 0.4, 0.4, 0.4, 0.4};

        System.out.println("Sample exercise weight based on your weight and number of reps:");
        for (int i = 0; i < reps.length; i++) {
            double weight = percentageRanges[i] * userWeight;
            System.out.println("Weight for " + reps[i] + " reps: " + weight + " kg");
        }
        System.out.println("");
    }

    // wczytywanie pliku txt z treningiem
    public static void displayTrainingFile() {
        try {
            File file = new File("Training.txt");
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