package pl.isa.thebuggers.fitly;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Menu:");
            System.out.println("1. Option 1");
            System.out.println("2. BMI");
            System.out.println("3. Training");
            System.out.println("4. Diet");
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
                case 4:
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
