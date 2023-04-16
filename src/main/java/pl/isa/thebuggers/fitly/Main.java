package pl.isa.thebuggers.fitly;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuOption choice = null;
        do {
            printMenu();
            int choiceValue = scanner.nextInt();
            if (choiceValue < 0 || choiceValue >= MenuOption.values().length) {
                System.out.println("Invalid option");
                continue;
            }
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
        String[] activityLevels = {Diet.ActivityLevels.FIRST_LVL, Diet.ActivityLevels.SECOND_LVL,
                Diet.ActivityLevels.THIRD_LVL, Diet.ActivityLevels.FOURTH_LVL,
                Diet.ActivityLevels.FIFTH_LVL};

        System.out.println("1. Create a new user");
        System.out.println("2. Load an existing user");
        System.out.println("3. Exit");
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
                System.out.print("Enter your height in cm: ");
                int height = scanner.nextInt();
                int activityLevelOption;
                do {
                    System.out.println("Specify your activity level:");
                    System.out.println("1. Sedentary");
                    System.out.println("2. Lightly active");
                    System.out.println("3. Moderately active");
                    System.out.println("4. Very active");
                    System.out.println("5. Super active");
                    System.out.print("Type your choice: ");
                    activityLevelOption = scanner.nextInt() - 1;
                } while (activityLevelOption < 0 || activityLevelOption >= activityLevels.length);
                boolean isMale;
                do {
                    System.out.print("Enter your gender (M/F): ");
                    char gender = scanner.next().charAt(0);
                    isMale = (gender == 'M' || gender == 'm');
                } while (!(isMale || !isMale));
                userData = new UserData(name, age, weight, activityLevels[activityLevelOption], isMale, height);
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
            case 3:
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
        if (userData != null) {
            System.out.println("Name: " + userData.getName());
            System.out.println("Age: " + userData.getAge());
            System.out.println("Weight: " + userData.getWeight());
            System.out.println("Height: " + userData.getHeight());
            System.out.println("Activity level: " + userData.getActivityLevel());
            System.out.println("Gender: " + userData.getGender());
        }
    }
    private static void handleBmiOption(Scanner scanner) {
        UserService userService = new UserService();
        UserData userData = null;

        System.out.println("1. Use user data from file");
        System.out.println("2. Enter weight and height manually");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                try {
                    userData = userService.getUserData("userData.json");
                    System.out.println("Calculating BMI for user: " + userData.getName());
                    System.out.println("Weight: " + userData.getWeight());
                    System.out.println("Height: " + userData.getHeight());
                    BMI bmi = new BMI(userData.getWeight(), userData.getHeight());
                    System.out.println(String.format("%.2f", bmi.value()));
                    System.out.println(bmi.nutritionalStatus());
                } catch (IOException e) {
                    System.out.println("Error while loading user data.");
                    System.out.println("Please enter your weight and height manually.");
                    handleBmiManually(scanner);
                }
                break;
            case 2:
                handleBmiManually(scanner);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void handleBmiManually(Scanner scanner) {
        System.out.print("Enter your weight in kg: ");
        double weightBMI = scanner.nextDouble();
        System.out.print("Enter your height in cm: ");
        double heightBMI = scanner.nextDouble();
        BMI bmi = new BMI(weightBMI, heightBMI);
        System.out.println(String.format("%.2f", bmi.value()));
        System.out.println(bmi.nutritionalStatus());
    }
    private static void handleTrainingOption(Scanner scanner) {
        System.out.println("1. Use training data from file");
        System.out.println("2. Enter weight manually");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                try {
                    UserData userData = new UserService().getUserData("userData.json");
                    System.out.println("Calculating training weights for user with weight " + userData.getWeight() + " kg");
                    TrainingCalculator calculator = new TrainingCalculator(userData.getWeight());
                    TrainingCalculatorPrinter printer = new TrainingCalculatorPrinter(calculator);
                    printer.printTraining();
                    printer.printTrainingWeightBasedOnRM();
                    TrainingFileReader.displayTrainingFile();
                } catch (IOException e) {
                    System.out.println("Error while loading user data.");
                    System.out.println("Please enter your weight manually.");
                    handleTrainingManually(scanner);
                }
                break;
            case 2:
                handleTrainingManually(scanner);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    private static void handleTrainingManually(Scanner scanner) {
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
        System.out.println("Do you want to use saved user data or enter it manually?");
        System.out.println("1. Use saved data");
        System.out.println("2. Enter data manually");
        System.out.println("3. Exit");
        System.out.print("Type your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                try {
                    UserService userService = new UserService();
                    UserData data = userService.getUserData("userData.json");
                    double dietWeight = data.getWeight();
                    double dietHeight = data.getHeight();
                    int age = data.getAge();
                    String activityLevel = data.getActivityLevel();
                    boolean isMale = data.getGender();
                    double bmr;
                    if (isMale) {
                        bmr = Diet.calculateBmrForMale(dietWeight, dietHeight, age);
                    } else {
                        bmr = Diet.calculateBmrForFemale(dietWeight, dietHeight, age);
                    }
                    double activityFactor = Diet.getActivityFactor(activityLevel);
                    double dailyCalorieNeeds = bmr * activityFactor;
                    String selectedDietFile = Diet.selectAndDisplayDietFile(dailyCalorieNeeds);
                } catch (IOException e) {
                    System.out.println("Failed to read user data from file. Please enter data manually.");
                    handleDietOptionManually();
                }
                break;
            case 2:
                handleDietOptionManually();
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid input. Please enter a number between 1 and 2.");
                break;
        }
    }
    private static void handleDietOptionManually() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are in \"Diet\" section. Type your parameters to receive diet example based on your weight, height, age, and gender.");
        System.out.print("Enter your weight (in kg): ");
        double dietWeight = scanner.nextDouble();
        System.out.print("Enter your height (in cm): ");
        double dietHeight = scanner.nextDouble();
        System.out.print("Enter your age (in years): ");
        int age = scanner.nextInt();

        String gender = null;
        do {
            System.out.print("Enter your gender (M/F): ");
            gender = scanner.next().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                System.out.println("Invalid input. Please enter either 'M' or 'F'.");
            }
        } while (!gender.equals("M") && !gender.equals("F"));

        int activityLevelOption;
        String activityLevel = null;
        do {
            System.out.println("Specify your activity level:");
            System.out.println("1. Sedentary");
            System.out.println("2. Lightly active");
            System.out.println("3. Moderately active");
            System.out.println("4. Very active");
            System.out.println("5. Super active");
            System.out.print("Type your choice: ");
            try {
                activityLevelOption = scanner.nextInt();
                switch (activityLevelOption) {
                    case 1:
                        activityLevel = "Sedentary";
                        break;
                    case 2:
                        activityLevel = "Lightly active";
                        break;
                    case 3:
                        activityLevel = "Moderately active";
                        break;
                    case 4:
                        activityLevel = "Very active";
                        break;
                    case 5:
                        activityLevel = "Super active";
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
        if (gender.equals("M")) {
            bmr = Diet.calculateBmrForMale(dietWeight, dietHeight, age);
        } else {
            bmr = Diet.calculateBmrForFemale(dietWeight, dietHeight, age);
        }

        double activityFactor = Diet.getActivityFactor(activityLevel);
        double dailyCalorieNeeds = bmr * activityFactor;
        String selectedDietFile = Diet.selectAndDisplayDietFile(dailyCalorieNeeds);
    }

    private enum MenuOption {

        DUMP(0),
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
