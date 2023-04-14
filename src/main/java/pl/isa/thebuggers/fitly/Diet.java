//  TODO: COPY -> PASTE to Case 1 instead of line 20
//          IMPLEMENTATION DIET CLASS IN MAIN.java COMMENTED BELOW

//        System.out.println("You are in \"Diet\" section. Type your parameters to receive diet example based on your weight, height and age.");
//        System.out.print("Enter your weight (in kg): ");
//        double dietWeight = scanner.nextDouble();
//        System.out.print("Enter your height (in cm): ");
//        double dietHeight = scanner.nextDouble();
//        System.out.print("Enter your age (in years): ");
//        int age = scanner.nextInt();
//
//        int activityLevelOption;
//        String activityLevel = null;
//        do {
//            System.out.println("Specify your activity level:");
//            System.out.println("1. Couch potato mode");
//            System.out.println("2. Couch potato with occasional dance breaks");
//            System.out.println("3. Parkour panda mode");
//            System.out.println("4. Marathoner chasing ice cream truck");
//            System.out.println("5. Superhero or ninja warrior level");
//            System.out.print("Type your choice: ");
//            try {
//                activityLevelOption = scanner.nextInt();
//                switch (activityLevelOption) {
//                    case 1:
//                        activityLevel = "Couch potato mode";
//                        break;
//                    case 2:
//                        activityLevel = "Couch potato with occasional dance breaks";
//                        break;
//                    case 3:
//                        activityLevel = "Parkour panda mode";
//                        break;
//                    case 4:
//                        activityLevel = "Marathoner chasing ice cream truck";
//                        break;
//                    case 5:
//                        activityLevel = "Superhero or ninja warrior level";
//                        break;
//                    default:
//                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
//                        activityLevel = "Invalid";
//                        break;
//                }
//            } catch (IllegalArgumentException e) {
//                activityLevelOption = 0;
//            }
//        } while (activityLevelOption < 1 || activityLevelOption > 5);
//
//        System.out.println("Selected activity level: " + activityLevel);
//
//        double bmr;
//        if (isMale()) {
//            bmr = Diet.calculateBmrForMale(dietWeight, dietHeight, age);
//        } else {
//            bmr = Diet.calculateBmrForFemale(dietWeight, dietHeight, age);
//        }
//
//        double activityFactor = Diet.getActivityFactor(activityLevel);
//        double dailyCalorieNeeds = bmr * activityFactor;
//        String selectedDietFile = Diet.selectAndDisplayDietFile(dailyCalorieNeeds);


//      TODO:#2 add this to Main in Main.java for Case 1 to work correctly
//    private static boolean isMale() {
//        return true;
//    }

package pl.isa.thebuggers.fitly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Diet {

    private static final double maleBmrConstant = 88.362;
    private static final double maleBmrWeightCoefficient = 13.397;
    private static final double maleBmrHeightCoefficient = 4.799;
    private static final double maleBmrAgeCoefficient = 5.677;
    private static final double femaleBmrConstant = 447.593;
    private static final double femaleBmrWeightCoefficient = 9.247;
    private static final double femaleBmrHeightCoefficient = 3.098;
    private static final double femaleBmrAgeCoefficient = 4.330;
    private static final double bmrMaleAdjustment = 5;
    private static final double bmrFemaleAdjustment = -161;

    public class ActivityLevels {
        public static final String FIRST_LVL = "Couch potato mode";
        public static final String SECOND_LVL = "Couch potato with occasional dance breaks";
        public static final String THIRD_LVL = "Parkour panda mode";
        public static final String FOURTH_LVL = "Marathoner chasing ice cream truck";
        public static final String FIFTH_LVL = "Superhero or ninja warrior level";
    }

    private static Map<String, Double> createActivityFactorsMap() {
        Map<String, Double> activityLevel = new HashMap<>();
        activityLevel.put(ActivityLevels.FIRST_LVL, 1.2);
        activityLevel.put(ActivityLevels.SECOND_LVL, 1.375);
        activityLevel.put(ActivityLevels.THIRD_LVL, 1.55);
        activityLevel.put(ActivityLevels.FOURTH_LVL, 1.725);
        activityLevel.put(ActivityLevels.FIFTH_LVL, 1.9);
        return activityLevel;
    }

    static double calculateBmrForMale(double weight, double height, int age) {
        return (maleBmrConstant + (maleBmrWeightCoefficient * weight)
                + (maleBmrHeightCoefficient * height) - (maleBmrAgeCoefficient * age))
                + bmrMaleAdjustment;
    }

    static double calculateBmrForFemale(double weight, double height, int age) {
        return (femaleBmrConstant + (femaleBmrWeightCoefficient * weight)
                + (femaleBmrHeightCoefficient * height) - (femaleBmrAgeCoefficient * age))
                + bmrFemaleAdjustment;
    }

    static double getActivityFactor(String activityLevel) {
        Map<String, Double> activityLevelMap = createActivityFactorsMap();
        if (activityLevelMap.containsKey(activityLevel)) {
            return activityLevelMap.get(activityLevel);
        } else {
            throw new IllegalArgumentException("Invalid activity level: " + activityLevel);
        }
    }

    static String selectAndDisplayDietFile(double dailyCalorieNeeds) {
        String selectedFile = null;
        String[] dietFiles = {
                "1500kcl.txt", "1800kcl.txt", "2000kcl.txt", "2200kcl.txt", "2500kcl.txt",
                "2800kcl.txt", "3000kcl.txt", "3200kcl.txt", "3500kcl.txt", "3800kcl.txt", "4000kcl.txt"
        };
        for (String file : dietFiles) {
            String[] parts = file.split("kcl");
            int calorieLimit = Integer.parseInt(parts[0]);
            if (dailyCalorieNeeds <= calorieLimit) {
                selectedFile = file;
                break;
            }
        }
        if (selectedFile == null) {
            selectedFile = dietFiles[dietFiles.length - 1];
        }

        String nazwaPliku = "./diety/" + selectedFile;

        try (BufferedReader dietFileReader = new BufferedReader(new FileReader(nazwaPliku))) {
            String linia;
            String kclLevel = selectedFile.substring(0, selectedFile.indexOf('k'));
            System.out.println("Your diet should hit around: " + kclLevel + " KCL. Here's your menu example:");
            while ((linia = dietFileReader.readLine()) != null) {
                System.out.println(linia);
            }
        } catch (IOException e) {
            System.out.println("Cannot find file: " + e.getMessage());
        }
        return selectedFile;
    }


}