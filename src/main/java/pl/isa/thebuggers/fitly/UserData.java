package pl.isa.thebuggers.fitly;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import com.sun.source.tree.CaseTree;

public class UserData {

    private String name;
    private int age;
    private double weight;
    private int activity;
    private boolean whatGender;



/*----Dane Uzytkownika---- 1.Wzrost 2.Waga 3.Plec 4.Aktywnosc fiz.
----Dane Wyjsciowe------ 1.BMI 2.Dieta Default 3.Trening Default
----Funkcjonalnosci----- 1.Pobieranie danych uzytkownika 2.wyswietlanie bmi
3.wyswietlanie diety default 4.wyswietlanie treningu default
 */

    //user musi wprowadzić dane typu String -scanner w Main
//zrobic exeption dla wprowadzenia poprawnego Stringa
    public String getName() throws IOException {
        this.name = name;
        return name;
    }

    //user musi wprowadzić dane typu int -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niż int
    public int getAge() {
        this.age = age;
        return age;
    }

    //user musi wprowadzić dane typu int -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz double
    public double getWeight() {

        this.weight = weight;
        return weight;

    }

    //utworzyć mozliwość wyboru male/female
    //user musi wprowadzić dane char 'M' lub 'F' -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz char i M/F
    public char getGender() {

        this.whatGender = whatGender;
        return whatGender ? 'M' : 'F';
    }

    //utworzyc skalę wyboru aktywności w zakładanym zakresie np. 1-5
    //user musi wprowadzić dane typu int -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz int
    public int getActivity() {
        this.activity = activity;
        return activity;
    }




}
