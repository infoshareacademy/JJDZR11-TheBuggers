package pl.isa.fitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.service.TrainingCalculator;
import pl.isa.fitly.service.TrainingCalculatorPrinter;
import pl.isa.fitly.service.TrainingFileReader;


@Controller
public class TrainingController {
    private TrainingCalculator trainingCalculator;
    private TrainingCalculatorPrinter trainingCalculatorPrinter;
    private TrainingFileReader trainingFileReader;
    private boolean showWeights = false;
    private boolean showFileContent = false;

    public TrainingController(TrainingCalculator trainingCalculator, TrainingCalculatorPrinter trainingCalculatorPrinter, TrainingFileReader trainingFileReader) {
        this.trainingCalculator = trainingCalculator;
        this.trainingCalculatorPrinter = trainingCalculatorPrinter;
        this.trainingFileReader = trainingFileReader;
    }

    @GetMapping("/trainings")
    public String showTrainingsPage(Model model) {
        resetFlags();
        model.addAttribute("userData", new UserData());
        model.addAttribute("showWeights", showWeights);
        model.addAttribute("showFileContent", showFileContent);
        return "trainings";
    }

    @PostMapping("/training")
    public String calculateTraining(@RequestParam("weight") double weight, Model model) {
        showWeights = true;
        showFileContent = true;

        trainingCalculator.calculateTrainingWeights(weight);
        trainingCalculatorPrinter.printTraining(model);
        trainingCalculatorPrinter.printTrainingWeightBasedOnRM(model);
        trainingFileReader.run(model);

        model.addAttribute("showWeights", showWeights);
        model.addAttribute("showFileContent", showFileContent);

        return "trainings";
    }

    private void resetFlags() {
        showWeights = false;
        showFileContent = false;
    }
}
