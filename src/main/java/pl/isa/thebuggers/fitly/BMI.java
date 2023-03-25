package pl.isa.thebuggers.fitly;

public class BMI {
    private double weight;
    private double height;

    /**
     * @param weight user's weight in kilograms
     * @param height user's height in centimeters
     * @return user's BMI value
     */

    static double value(double weight, double height) {
        // height converted from centimeters to meters (/100)
        return (weight / Math.pow(height / 100, 2));
    }

    public double value() {
        // height converted from centimeters to meters (/100)
        return (this.weight / Math.pow(this.height / 100, 2));
    }

    /**
     * @param bmi user's BMI value
     * @return nutritional status based on BMI
     */
    static String nutritionalStatus(double bmi) {
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

    public String nutritionalStatus() {
        return nutritionalStatus(value());
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
