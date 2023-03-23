package pl.isa.thebuggers.fitly;

import com.sun.source.tree.CaseTree;

public class UserData {

    private String name;
    private int age;
    private int weight;
    private char gender;
    private int activity;
    char male = 'M';
    char female = 'F';


/*----Dane Uzytkownika---- 1.Wzrost 2.Waga 3.Plec 4.Aktywnosc fiz.
----Dane Wyjsciowe------ 1.BMI 2.Dieta Default 3.Trening Default
----Funkcjonalnosci----- 1.Pobieranie danych uzytkownika 2.wyswietlanie bmi
3.wyswietlanie diety default 4.wyswietlanie treningu default
 */

    //user musi wprowadzić dane typu String -scanner w Main
//zrobic exeption dla wprowadzenia integera
    public String userName() {

        return name;
    }
    //user musi wprowadzić dane typu int -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz int
    public int userAge() {

        return age;
    }

    //user musi wprowadzić dane typu int -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz int
    public int userWeight() {


        return weight;

    }

    //utworzyć mozliwość wyboru male/female
    //user musi wprowadzić dane char 'M' lub 'F' -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz char i M/F
    public boolean userGender(char userChar) {
        if ('F' == female) {
            return true;
        } else ('M' = male) {
            return false;



    }

    //utworzyc skalę wyboru aktywności w zakładanym zakresie np. 1-5
    //user musi wprowadzić dane typu int -scanner w Main
//zrobic exeption dla niepoprawnego wprowadzenia innego typu danych niz int
    public int userActivity() {

        return activity;
    }
}
