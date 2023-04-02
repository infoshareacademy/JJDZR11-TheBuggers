package pl.isa.thebuggers.fitly;

public class BMI {
    private double weight;
    private double height;

    public BMI(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public double value() {
        // height converted from centimeters to meters (/100)
        return (this.weight / Math.pow(this.height / 100, 2));
    }

    public String nutritionalStatus() {
        double bmi = value();
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal weight";
        } else if (bmi < 29.9) {
            return "Pre-obesity";
        } else if (bmi < 34.9) {
            return "Obesity class I";
        } else if (bmi < 39.9) {
            return "Obesity class II";
        } else {
            return "Obesity class III";
        }
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
