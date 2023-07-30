package pl.isa.fitly.model;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserData {

    private String email;
    private String password;
    private String name;
    private int age;
    private double weight;
    private String activityLevel;
    private String gender;
    private int height;
    private String role;

    private List<String> roomIds;



    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    public static UserData createUserData() {
        return new UserData();
    }

    public UserData() {
    }

    public UserData(double weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    public UserData(String name, int age, double weight, String activityLevel, String gender, int height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.gender = gender;
        this.height = height;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getGender() {
        return gender;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean emptyUser() {
        return (this.email == null || this.password == null || this.weight == 0 || this.height == 0 || this.age == 0);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", activityLevel='" + activityLevel + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(email, userData.email) && Objects.equals(role, userData.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, role);
    }
}
