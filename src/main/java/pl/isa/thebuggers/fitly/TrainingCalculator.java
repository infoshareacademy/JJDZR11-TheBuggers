package pl.isa.thebuggers.fitly;

public class TrainingCalculator {
    private double userWeight; // waga użytkownika w kilogramach

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

    public double getDeadliftWeight() {
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
        // Przedziały procentowe dla różnej liczby powtórzeń
        double[] percentageRanges = {1.0, 0.9, 0.85, 0.8, 0.75, 0.7, 0.65, 0.6, 0.55, 0.5, 0.45, 0.4, 0.4, 0.4, 0.4};

        if (reps < 1 || reps > percentageRanges.length) {
            throw new IllegalArgumentException("Invalid number of reps: " + reps);
        }

        return percentageRanges[reps - 1] * userWeight;
    }
}