package pl.isa.thebuggers.fitly;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuOption choice;
        do {
            printMenu();
            int choiceValue = scanner.nextInt();
            choice = MenuOption.values()[choiceValue];
            switch (choice) {
                case USER:
                    handleUserOption();
                    break;
                case BMI:
                    handleBmiOption(scanner);
                    break;
                case TRAINING:
                    handleTrainingOption(scanner);
                    break;
                case DIET:
                    handleDietOption();
                    break;
                case EXIT:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (choice != MenuOption.EXIT);
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. User");
        System.out.println("2. BMI");
        System.out.println("3. Training");
        System.out.println("4. Diet");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void handleUserOption() {
        // TODO: implement
    }

    private static void handleBmiOption(Scanner scanner) {
        System.out.print("Enter your weight in kg: ");
        double weightBMI = scanner.nextDouble();
        System.out.print("Enter your height in cm: ");
        double heightBMI = scanner.nextDouble();
        BMI bmi = new BMI(weightBMI, heightBMI);
        System.out.println(String.format("%.2f", bmi.value()));
        System.out.println(bmi.nutritionalStatus());
    }

    private static void handleTrainingOption(Scanner scanner) {
        System.out.print("Enter your weight in kg: ");
        double weight = scanner.nextDouble();
        TrainingCalculator calculator = new TrainingCalculator(weight);
        TrainingCalculatorPrinter printer = new TrainingCalculatorPrinter(calculator);
        printer.printTraining();
        printer.printTrainingWeightBasedOnRM();
        TrainingFileReader.displayTrainingFile();
    }

    private static void handleDietOption() {
        // TODO: implement
    }
    private enum MenuOption {

        DUMP(0), // do wygody
        USER(1),
        BMI(2),
        TRAINING(3),
        DIET(4),
        EXIT(5);

        private final int value;

        MenuOption(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
