package pl.isa.thebuggers.fitly;

import java.io.IOException;
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
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("1. Create a new user");
        System.out.println("2. Load an existing user");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        UserData userData = null;
        switch (choice) {
            case 1:
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();
                System.out.print("Enter your age: ");
                int age = scanner.nextInt();
                System.out.print("Enter your weight in kg: ");
                double weight = scanner.nextDouble();
                int activityLevelOption;
                do {
                    System.out.println("Specify your activity level:");
                    System.out.println("1. Sedentary");
                    System.out.println("2. Lightly active");
                    System.out.println("3. Moderately active");
                    System.out.println("4. Very active");
                    System.out.println("5. Super active");
                    System.out.print("Type your choice: ");
                    activityLevelOption = scanner.nextInt();
                } while (activityLevelOption < 1 || activityLevelOption > 5);

                boolean isMale;
                do {
                    System.out.print("Enter your gender (M/F): ");
                    char gender = scanner.next().charAt(0);
                    isMale = (gender == 'M' || gender == 'm');
                } while (!(isMale || !isMale));
                userData = new UserData(name, age, weight, activityLevelOption, isMale);
                try {
                    userService.saveUserData(userData, "userData.json");
                    System.out.println("User data saved successfully.");
                } catch (IOException e) {
                    System.out.println("Error while saving user data.");
                }
                break;
            case 2:
                try {
                    userData = userService.getUserData("userData.json");
                    System.out.println("User data loaded successfully.");
                } catch (IOException e) {
                    System.out.println("Error while loading user data.");
                }
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
        if (userData != null) {
            System.out.println("Name: " + userData.getName());
            System.out.println("Age: " + userData.getAge());
            System.out.println("Weight: " + userData.getWeight());
            System.out.println("Activity level: " + userData.getActivity());
            System.out.println("Gender: " + userData.getGender());
        }
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are in \"Diet\" section. Type your parameters to receive diet example based on your weight, height and age.");
        System.out.print("Enter your weight (in kg): ");
        double dietWeight = scanner.nextDouble();
        System.out.print("Enter your height (in cm): ");
        double dietHeight = scanner.nextDouble();
        System.out.print("Enter your age (in years): ");
        int age = scanner.nextInt();

        int activityLevelOption;
        String activityLevel = null;
        do {
            System.out.println("Specify your activity level:");
            System.out.println("1. Couch potato mode");
            System.out.println("2. Couch potato with occasional dance breaks");
            System.out.println("3. Parkour panda mode");
            System.out.println("4. Marathoner chasing ice cream truck");
            System.out.println("5. Superhero or ninja warrior level");
            System.out.print("Type your choice: ");
            try {
                activityLevelOption = scanner.nextInt();
                switch (activityLevelOption) {
                    case 1:
                        activityLevel = "Couch potato mode";
                        break;
                    case 2:
                        activityLevel = "Couch potato with occasional dance breaks";
                        break;
                    case 3:
                        activityLevel = "Parkour panda mode";
                        break;
                    case 4:
                        activityLevel = "Marathoner chasing ice cream truck";
                        break;
                    case 5:
                        activityLevel = "Superhero or ninja warrior level";
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        activityLevel = "Invalid";
                        break;
                }
            } catch (IllegalArgumentException e) {
                activityLevelOption = 0;
            }
        } while (activityLevelOption < 1 || activityLevelOption > 5);

        System.out.println("Selected activity level: " + activityLevel);

        double bmr;
        if (isMale()) {
            bmr = Diet.calculateBmrForMale(dietWeight, dietHeight, age);
        } else {
            bmr = Diet.calculateBmrForFemale(dietWeight, dietHeight, age);
        }

        double activityFactor = Diet.getActivityFactor(activityLevel);
        double dailyCalorieNeeds = bmr * activityFactor;
        String selectedDietFile = Diet.selectAndDisplayDietFile(dailyCalorieNeeds);

    }

    private static boolean isMale() {
        return true;
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
