package pl.isa.fitly.model;


import org.springframework.stereotype.Component;

@Component
public class UserData {

    private String email;
    private String password;
    private String name;
    private int age;
    private double weight;
    private String activityLevel;
    private boolean whatGender;
    private int height;

    public static UserData createUserData() {
        return new UserData();
    }

    public UserData() {
    }

    public UserData(double weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    public UserData(String name, int age, double weight, String activityLevel, boolean whatGender, int height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.whatGender = whatGender;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public boolean getWhatGender() {
        return whatGender;
    }

    public void setWhatGender(boolean whatGender) {
        this.whatGender = whatGender;
    }

    public int getHeight() {
        return height;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static double bmiValue(double weight, int height) {
        // height converted from centimeters to meters (/100)
        return (weight / Math.pow((double) height / 100, 2));
    }

    public double bmiValue() {
        return bmiValue(this.weight, this.height);
    }

    public String nutritionalStatus() {
        return nutritionalStatus(bmiValue());
    }

    public static String nutritionalStatus(double bmi) {
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

    public boolean emptyUser(){
        return (this.email==null || this.password==null || this.weight ==0 ||this.height==0 || this.age==0 );
    }

    @Override
    public String toString() {
        return "UserData{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", activityLevel='" + activityLevel + '\'' +
                ", whatGender=" + whatGender +
                ", height=" + height +
                '}';
    }

}
