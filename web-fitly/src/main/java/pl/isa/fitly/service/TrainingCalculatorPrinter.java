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
        model.addAttribute("benchPressWeight", formatWeight(calculator.getBenchPressWeight()));
        model.addAttribute("squatWeight", formatWeight(calculator.getSquatWeight()));
        model.addAttribute("deadliftWeight", formatWeight(calculator.getDeadLiftWeight()));
        model.addAttribute("dumbbellCurlsWeight", formatWeight(calculator.getDumbbellCurlsWeight()));
        model.addAttribute("dumbbellRowsWeight", formatWeight(calculator.getDumbbellRowsWeight()));
        model.addAttribute("dumbbellShoulderPressWeight", formatWeight(calculator.getDumbbellShoulderPressWeight()));
    }

    public void printTrainingWeightBasedOnRM(Model model) {
        List<String> weights = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            double weight = calculator.getWeightForReps(i);
            String weightString = formatWeight(weight) + " kg (" + i + " repetition" + (i > 1 ? "s" : "") + ")";
            weights.add(weightString);
        }
        model.addAttribute("weights", weights);
    }

    private double formatWeight(double weight) {
        return Math.round(weight * 2) / 2.0;
    }
}