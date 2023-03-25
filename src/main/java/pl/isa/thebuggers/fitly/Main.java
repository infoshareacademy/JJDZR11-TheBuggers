package pl.isa.thebuggers.fitly;

public class Main {
    public static void main(String[] args) {
        double valueBMI;
        valueBMI = BMI.value(90, 192);
        System.out.println(String.format("%.2f", valueBMI));
        System.out.println(BMI.nutritionalStatus(valueBMI));

        BMI bmi = new BMI();
        bmi.setWeight(88);
        bmi.setHeight(193);
        System.out.println(String.format("%.2f", bmi.value()));
        System.out.println(bmi.nutritionalStatus());



    }
}
