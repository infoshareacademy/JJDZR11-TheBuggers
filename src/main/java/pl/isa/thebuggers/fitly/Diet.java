package pl.isa.thebuggers.fitly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Diet {
    private static final double MALE_BMR_CONSTANT = 88.362;
    private static final double MALE_BMR_WEIGHT_COEFFICIENT = 13.397;
    private static final double MALE_BMR_HEIGHT_COEFFICIENT = 4.799;
    private static final double MALE_BMR_AGE_COEFFICIENT = 5.677;
    private static final double FEMALE_BMR_CONSTANT = 447.593;
    private static final double FEMALE_BMR_WEIGHT_COEFFICIENT = 9.247;
    private static final double FEMALE_BMR_HEIGHT_COEFFICIENT = 3.098;
    private static final double FEMALE_BMR_AGE_COEFFICIENT = 4.330;
    private static final double BMR_MALE_ADJUSTMENT = 5;
    private static final double BMR_FEMALE_ADJUSTMENT = -161;


    public static class ActivityLevels {
        public static final String FIRST_LVL = "Sedentary";
        public static final String SECOND_LVL = "Lightly active";
        public static final String THIRD_LVL = "Moderately active";
        public static final String FOURTH_LVL = "Very active";
        public static final String FIFTH_LVL = "Super active";
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
        return (MALE_BMR_CONSTANT + (MALE_BMR_WEIGHT_COEFFICIENT * weight)
                + (MALE_BMR_HEIGHT_COEFFICIENT * height) - (MALE_BMR_AGE_COEFFICIENT * age))
                + BMR_MALE_ADJUSTMENT;
    }

    static double calculateBmrForFemale(double weight, double height, int age) {
        return (FEMALE_BMR_CONSTANT + (FEMALE_BMR_WEIGHT_COEFFICIENT * weight)
                + (FEMALE_BMR_HEIGHT_COEFFICIENT * height) - (FEMALE_BMR_AGE_COEFFICIENT * age))
                + BMR_FEMALE_ADJUSTMENT;
    }

    static double getActivityFactor(String activityLevel) {
        Map<String, Double> activityLevelMap = createActivityFactorsMap();
        if (activityLevelMap.containsKey(activityLevel)) {
            return activityLevelMap.get(activityLevel);
        } else {
            throw new IllegalArgumentException("Invalid activity level: " + activityLevel);
        }
    }

    private static final String[] DIET_FILES = {
            "1500kcl.txt", "1800kcl.txt", "2000kcl.txt", "2200kcl.txt", "2500kcl.txt",
            "2800kcl.txt", "3000kcl.txt", "3200kcl.txt", "3500kcl.txt", "3800kcl.txt", "4000kcl.txt"
    };

    static String selectAndDisplayDietFile(double dailyCalorieNeeds) {
        String selectedFile = null;
        for (String file : DIET_FILES) {
            String[] parts = file.split("kcl");
            int calorieLimit = Integer.parseInt(parts[0]);
            if (dailyCalorieNeeds <= calorieLimit) {
                selectedFile = file;
                break;
            }
        }
        if (selectedFile == null) {
            selectedFile = DIET_FILES[DIET_FILES.length - 1];
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
