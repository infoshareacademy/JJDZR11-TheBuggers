package pl.isa.thebuggers.fitly;

public class TrainingCalculatorPrinter {
    private TrainingCalculator calculator;

    public TrainingCalculatorPrinter(TrainingCalculator calculator) {
        this.calculator = calculator;
    }

    public void printTraining() {
        System.out.printf("Bench press: %.1f kg%n", calculator.getBenchPressWeight());
        System.out.printf("Squat: %.1f kg%n", calculator.getSquatWeight());
        System.out.printf("Deadlift: %.1f kg%n", calculator.getDeadliftWeight());
        System.out.printf("Dumbbell curls: %.1f kg%n", calculator.getDumbbellCurlsWeight());
        System.out.printf("Dumbbell rows: %.1f kg%n", calculator.getDumbbellRowsWeight());
        System.out.printf("Dumbbell shoulder press: %.1f kg%n%n", calculator.getDumbbellShoulderPressWeight());
    }

    public void printTrainingWeightBasedOnRM() {
        System.out.println("Sample exercise weight based on your weight and number of reps:");
        for (int i = 1; i <= 15; i++) {
            double weight = calculator.getWeightForReps(i);
            System.out.printf("Weight for %d reps: %.1f kg%n", i, weight);
        }
        System.out.println("");
    }
}