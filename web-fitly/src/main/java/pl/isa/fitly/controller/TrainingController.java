package pl.isa.fitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;
import pl.isa.fitly.service.TrainingCalculator;
import pl.isa.fitly.service.TrainingCalculatorPrinter;
import pl.isa.fitly.service.TrainingFileReader;

import java.security.Principal;


@Controller
public class TrainingController {
    private TrainingCalculator trainingCalculator;
    private TrainingCalculatorPrinter trainingCalculatorPrinter;
    private TrainingFileReader trainingFileReader;
    UserRepository userRepository;

    public TrainingController(TrainingCalculator trainingCalculator, TrainingCalculatorPrinter trainingCalculatorPrinter, TrainingFileReader trainingFileReader, UserRepository userRepository) {
        this.trainingCalculator = trainingCalculator;
        this.trainingCalculatorPrinter = trainingCalculatorPrinter;
        this.trainingFileReader = trainingFileReader;
        this.userRepository=userRepository;
    }

    @GetMapping("/trainings")
    public String showTrainingsPage(Model model, UserData userData, Principal principal) {
        if (principal != null) {
            userData=userRepository.getUserFromPrincipal(principal);
            trainingCalculator.calculateTrainingWeights(userData.getWeight());
            trainingCalculatorPrinter.printTraining(model);
            trainingCalculatorPrinter.printTrainingWeightBasedOnRM(model);
            trainingFileReader.run(model);
            model.addAttribute("Weight", userData.getWeight());
            model.addAttribute("showFlag", true);
        }else {
            userData=UserData.createUserData();
            model.addAttribute("showFlag", false);
        }
        model.addAttribute("userData", userData);

        return "trainings";
    }

    @PostMapping("/training")
    public String calculateTraining(@RequestParam("weight") double weight, UserData userData, Model model) {

        trainingCalculator.calculateTrainingWeights(weight);
        trainingCalculatorPrinter.printTraining(model);
        trainingCalculatorPrinter.printTrainingWeightBasedOnRM(model);
        trainingFileReader.run(model);
        model.addAttribute("userData", new UserData());
        model.addAttribute("showFlag", true);

        return "trainings";
    }

}
