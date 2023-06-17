package pl.isa.fitly.service;

import org.springframework.stereotype.Component;

@Component
public class TrainingCalculator {
    private double userWeight;

    public TrainingCalculator() {
    }

    public TrainingCalculator(double weight) {
        this.userWeight = weight;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }

    public double getBenchPressWeight() {
        return 0.75 * userWeight;
    }

    public double getSquatWeight() {
        return 1.25 * userWeight;
    }

    public double getDeadLiftWeight() {
        return 1.5 * userWeight;
    }

    public double getDumbbellCurlsWeight() {
        return 0.25 * userWeight;
    }

    public double getDumbbellRowsWeight() {
        return 0.5 * userWeight;
    }

    public double getDumbbellShoulderPressWeight() {
        return 0.4 * userWeight;
    }

    public double getWeightForReps(int reps) {
        if (reps < 1) {
            throw new IllegalArgumentException("Invalid number of reps: " + reps);
        }

        double weight = userWeight * (1 - (reps * 0.05));

        if (weight <= 0.5) {
            return 0.5;
        } else {
            return Math.ceil(weight);
        }
    }

    public void calculateTrainingWeights(double weight) {
        this.userWeight = weight;
    }
}