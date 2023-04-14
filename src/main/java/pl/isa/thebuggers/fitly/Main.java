package pl.isa.thebuggers.fitly;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Menu:");
            System.out.println("1. Option 1");
            System.out.println("2. BMI");
            System.out.println("3. Training");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.print("Enter your weight in kg: ");
                    double weightBMI = scanner.nextDouble();
                    System.out.print("Enter your height in kg: ");
                    double heightBMI = scanner.nextDouble();
                    BMI bmi = new BMI(weightBMI,heightBMI);
                    System.out.println(String.format("%.2f", bmi.value()));
                    System.out.println(bmi.nutritionalStatus());
                    break;
                case 3:
                    System.out.print("Enter your weight in kg: ");
                    double weight = scanner.nextDouble();
                    Training training = new Training(weight);
                    training.calculateTraining();
                    training.calculateTrainingWeightBasedOnRM();
                    training.displayTrainingFile();
                    break;
                case 0:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (choice != 0);
    }
        private static boolean isMale() {
        return true;
        }
}
