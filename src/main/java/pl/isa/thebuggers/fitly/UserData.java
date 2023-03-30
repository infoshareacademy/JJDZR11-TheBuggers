package pl.isa.thebuggers.fitly;
public class UserData {
    private String name;
    private int age;
    private double weight;
    private int activity;
    private boolean whatGender;
    public String getName() {
                return name;
    }
    public int getAge() {
                return age;
    }
    public double getWeight() {
                return weight;
    }
    public char getGender() {
              return whatGender ? 'M' : 'F';
    }
    public int getActivity() {
               return activity;
    }
}
