package pl.isa.thebuggers.fitly;

import com.sun.source.tree.CaseTree;

public class UserData {

    private String name;
    private int age;
    private int weight;
    private char gender;
    private int activity;

/*----Dane Uzytkownika---- 1.Wzrost 2.Waga 3.Plec 4.Aktywnosc fiz.
----Dane Wyjsciowe------ 1.BMI 2.Dieta Default 3.Trening Default
----Funkcjonalnosci----- 1.Pobieranie danych uzytkownika 2.wyswietlanie bmi
3.wyswietlanie diety default 4.wyswietlanie treningu default
 */

//zrobic warunki dla integerow i Stringow, żeby nie przyjmowały niewłasciwych wartości

    public String userName() {

        return name;
    }

    public int userAge(){

         return age;
    }

    public int userWeight(){


        return weight;

    }
//utworzyć mozliwość wyboru male/female
    public boolean userGender() {

           return true;
    }
//utworzyc skalę wyboru aktywności w zakładanym zakresie np. 1-5
    public int userActivity() {

        return activity;
    }
}
