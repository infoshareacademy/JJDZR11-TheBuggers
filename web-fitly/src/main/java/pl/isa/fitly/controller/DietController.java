package pl.isa.fitly.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

import java.io.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DietController {

    UserRepository userRepository;

    public DietController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @PostMapping("/diets/glutenAndLactoseFree")
    public String glutenAndLactoseFreeDiet(@RequestParam("age") int age,
                                           @RequestParam("height") double height,
                                           @RequestParam("weight") double weight,
                                           @RequestParam("gender") String gender,
                                           @RequestParam("activityLevel") String activityLevel,
                                           Model model, UserData userData) {
        return processDiet(age, height, weight, gender, activityLevel, "glutenAndLactoseFree-diet", model);
    }

    @PostMapping("/diets/pescetarian")
    public String pescetarianDiet(@RequestParam("age") int age,
                                  @RequestParam("height") double height,
                                  @RequestParam("weight") double weight,
                                  @RequestParam("gender") String gender,
                                  @RequestParam("activityLevel") String activityLevel,
                                  Model model, UserData userData) {
        return processDiet(age, height, weight, gender, activityLevel, "pescetarian-diet", model);
    }

    @PostMapping("/diets/standard")
    public String standardDiet(@RequestParam("age") int age,
                               @RequestParam("height") double height,
                               @RequestParam("weight") double weight,
                               @RequestParam("gender") String gender,
                               @RequestParam("activityLevel") String activityLevel,
                               Model model, UserData userData) {
        return processDiet(age, height, weight, gender, activityLevel, "standard-diet", model);
    }

    @PostMapping("/diets/vege")
    public String vegeDiet(@RequestParam("age") int age,
                           @RequestParam("height") double height,
                           @RequestParam("weight") double weight,
                           @RequestParam("gender") String gender,
                           @RequestParam("activityLevel") String activityLevel,
                           Model model, UserData userData) {
        return processDiet(age, height, weight, gender, activityLevel, "vege-diet", model);
    }

    @GetMapping("/diets")
    public String showDietsPage() {
        return "diets";
    }

    @GetMapping("/diets/glutenAndLactoseFree")
    public String showGlutenAndLactoseFreeDietForm(Model model, Principal principal) {
        return dietCurrentUser("glutenAndLactoseFree-diet", model, principal);
    }

    @GetMapping("/diets/pescetarian")
    public String showPescetarianDietForm(Model model, Principal principal) {
        return dietCurrentUser("pescetarian-diet", model, principal);
    }

    @GetMapping("/diets/standard")
    public String showStandardDietForm(Model model, Principal principal) {
        return dietCurrentUser("standard-diet", model, principal);
    }

    @GetMapping("/diets/vege")
    public String showVegeDietForm(Model model, Principal principal) {
        return dietCurrentUser("vege-diet", model, principal);
    }

    private String processDiet(int age, double height, double weight, String gender, String activityLevel, String dietTemplateName, Model model) {
        double bmr;
        if (gender.equals("male")) {
            bmr = calculateBmrForMale(weight, height, age);
        } else if (gender.equals("female")) {
            bmr = calculateBmrForFemale(weight, height, age);
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }

        double activityFactor = getActivityFactor(activityLevel);
        double dailyCalorieNeeds = bmr * activityFactor;

        String selectedFile = selectAndDisplayDietFile(dailyCalorieNeeds, dietTemplateName);

        model.addAttribute("selectedFile", selectedFile);
        model.addAttribute("dailyCalorieNeeds", dailyCalorieNeeds);

        return dietTemplateName;
    }

    private static double calculateBmrForMale(double weight, double height, int age) {
        return (MALE_BMR_CONSTANT + (MALE_BMR_WEIGHT_COEFFICIENT * weight)
                + (MALE_BMR_HEIGHT_COEFFICIENT * height) - (MALE_BMR_AGE_COEFFICIENT * age))
                + BMR_MALE_ADJUSTMENT;
    }

    private static double calculateBmrForFemale(double weight, double height, int age) {
        return (FEMALE_BMR_CONSTANT + (FEMALE_BMR_WEIGHT_COEFFICIENT * weight)
                + (FEMALE_BMR_HEIGHT_COEFFICIENT * height) - (FEMALE_BMR_AGE_COEFFICIENT * age))
                + BMR_FEMALE_ADJUSTMENT;
    }

    private static double getActivityFactor(String activityLevel) {
        Map<String, Double> activityLevelMap = createActivityFactorsMap();
        if (activityLevelMap.containsKey(activityLevel)) {
            return activityLevelMap.get(activityLevel);
        } else {
            throw new IllegalArgumentException("Invalid activity level: " + activityLevel);
        }
    }

    private static Map<String, Double> createActivityFactorsMap() {
        Map<String, Double> activityLevel = new HashMap<>();
        activityLevel.put("Sedentary", 1.2);
        activityLevel.put("Lightly active", 1.375);
        activityLevel.put("Moderately active", 1.55);
        activityLevel.put("Very active", 1.725);
        activityLevel.put("Super active", 1.9);
        return activityLevel;
    }

    private static final String[] DIET_FILES = {
            "1500kcl.txt", "1800kcl.txt", "2000kcl.txt", "2200kcl.txt", "2500kcl.txt",
            "2800kcl.txt", "3000kcl.txt", "3200kcl.txt", "3500kcl.txt", "3800kcl.txt", "4000kcl.txt"
    };

    private static String selectAndDisplayDietFile(double dailyCalorieNeeds, String dietType) {
        String selectedFile = null;
        String folder = dietType;

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

        StringBuilder dietContent = new StringBuilder();
        String filePath = "static/diety/" + folder + "/" + selectedFile;
        try {
            Resource resource = new ClassPathResource(filePath);
            File file = resource.getFile();

            try (BufferedReader dietFileReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = dietFileReader.readLine()) != null) {
                    dietContent.append(line).append("\n");
                }
            } catch (IOException e) {
                System.out.println("Error reading diet file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Cannot find diet file: " + e.getMessage());
        }

        return dietContent.toString();
    }

    private String dietCurrentUser(String dietTemplateName, Model model, Principal principal) {
        if (principal != null) {
            UserData user = userRepository.getUserFromPrincipal(principal);
            model.addAttribute("userData", user);
            return processDiet(user.getAge(),
                    user.getHeight(),
                    user.getWeight(),
                    user.getGender(),
                    user.getActivityLevel(),
                    dietTemplateName, model);
        }
        model.addAttribute("userData", UserData.createUserData());
        return dietTemplateName;
    }

}
