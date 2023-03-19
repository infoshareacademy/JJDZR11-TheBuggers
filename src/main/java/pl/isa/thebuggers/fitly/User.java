package pl.isa.thebuggers.fitly;

public class User {

    private String name;
    private int age;
    private int weight;

//zrobic warunki dla integerow i Stringow, żeby nie przyjmowały niewłasciwych wartości

    public String userName() {
        this.name = name;
        return name;
    }

    public int userAge(){
        this.age = age;
         return age;
    }

    public int userWeight(){
        this.weight = weight;

        return weight;

    }

}
