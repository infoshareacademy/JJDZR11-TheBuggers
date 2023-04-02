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
            System.out.println("2. Option 2");
            System.out.println("3. Training");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                case 3:
                    System.out.print("Enter your weight in kg: ");
                    double weight = scanner.nextDouble();
                    TrainingCalculator calculator = new TrainingCalculator(weight);
                    TrainingCalculatorPrinter printer = new TrainingCalculatorPrinter(calculator);

                    printer.printTraining();
                    printer.printTrainingWeightBasedOnRM();
                    TrainingFileReader.displayTrainingFile();
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
}
