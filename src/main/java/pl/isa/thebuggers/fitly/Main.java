package pl.isa.thebuggers.fitly;


public class Main {
    public static void main(String[] args) {
        BMI bmi = new BMI(88, 193);
        bmi.setWeight(57);
        bmi.setHeight(169);
        System.out.println(String.format("%.2f", bmi.value()));
        System.out.println(bmi.nutritionalStatus());

    }
}
