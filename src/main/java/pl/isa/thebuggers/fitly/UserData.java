package pl.isa.thebuggers.fitly;
public class UserData {
    private String name;
    private int age;
    private double weight;
    private String activityLevel;
    private boolean whatGender;
    private int height;

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

    public boolean getGender() {
        return whatGender;
    }

    public int getHeight() {
        return height;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }
}