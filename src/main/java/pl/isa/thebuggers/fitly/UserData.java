package pl.isa.thebuggers.fitly;
public class UserData {
    private String name;
    private int age;
    private double weight;
    private int activity;
    private boolean whatGender;

    public UserData(String name, int age, double weight, int activity, boolean whatGender) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.activity = activity;
        this.whatGender = whatGender;
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

    public int getActivity() {
        return activity;
    }

    public char getGender() {
        return whatGender ? 'M' : 'F';
    }
}