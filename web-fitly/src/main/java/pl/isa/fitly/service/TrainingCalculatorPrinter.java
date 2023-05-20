package pl.isa.fitly.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingCalculatorPrinter {
    private TrainingCalculator calculator;

    public TrainingCalculatorPrinter(final TrainingCalculator calculator) {
        this.calculator = calculator;
    }

    public void printTraining(Model model) {
        model.addAttribute("benchPressWeight", calculator.getBenchPressWeight());
        model.addAttribute("squatWeight", calculator.getSquatWeight());
        model.addAttribute("deadliftWeight", calculator.getDeadLiftWeight());
        model.addAttribute("dumbbellCurlsWeight", calculator.getDumbbellCurlsWeight());
        model.addAttribute("dumbbellRowsWeight", calculator.getDumbbellRowsWeight());
        model.addAttribute("dumbbellShoulderPressWeight", calculator.getDumbbellShoulderPressWeight());
    }

    public void printTrainingWeightBasedOnRM(Model model) {
        List<String> weights = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            double weight = calculator.getWeightForReps(i);
            String weightString = weight + " kg (" + i + " repetition" + (i > 1 ? "s" : "") + ")";
            weights.add(weightString);
        }
        model.addAttribute("weights", weights);
    }
}