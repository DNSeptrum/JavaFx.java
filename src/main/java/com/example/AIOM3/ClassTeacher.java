package com.example.AIOM3;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ClassTeacher {
    String nazwa_grupy;
    Double liczba_nauczycieli;
    ArrayList<Teacher> nauczyciele;

    public ClassTeacher(String nazwa_grupy, double liczba_nauczycieli, ArrayList<Teacher> nauczyciele) {
        this.nazwa_grupy = nazwa_grupy;
        this.liczba_nauczycieli = liczba_nauczycieli;
        this.nauczyciele = nauczyciele;
    }

    public String getNazwa_grupy() {
        return nazwa_grupy;
    }

    public double getLiczba_nauczycieli() {
        return liczba_nauczycieli;
    }

    public void setNazwa_grupy(String nazwa_grupy) {
        this.nazwa_grupy = nazwa_grupy;
    }

    public void setLiczba_nauczycieli(double liczba_nauczycieli) {
        this.liczba_nauczycieli = liczba_nauczycieli;
    }

    public DoubleProperty liczba_nauczycieliProperty() {
        return new SimpleDoubleProperty(liczba_nauczycieli);
    }

    void addteacher(Teacher teacher) {
        if ((double)this.nauczyciele.size() < this.liczba_nauczycieli) {
            this.nauczyciele.add(teacher);
        } else {
            System.out.println("brak miejsca\n");
        }

        for(int i = 0; this.nauczyciele.size() > i + 1; ++i) {
            if (teacher == this.nauczyciele.get(i)) {
                System.out.println("podany nauczyciel jest już dodany do grupy");
                this.nauczyciele.remove(i);
            }
        }

    }

    void addSalary(Teacher teacher, double salary) {
        teacher.wynagrodzenie += salary;
    }

    void removeteacher(Teacher teacher) {
        this.nauczyciele.remove(teacher);
    }

    void changeCondition(Teacher teacher, TeacherCondition con) {
        teacher.teacherCondition = con;
    }

    void summary() {

        for (Teacher teacher : this.nauczyciele) {
            teacher.printing();
        }

    }

    void countByCondition(TeacherCondition con) {
        Iterator<Teacher> iter = this.nauczyciele.iterator();
        int zlicz = 0;

        while(iter.hasNext()) {
            if (iter.next().teacherCondition == con) {
                ++zlicz;
            }
        }

        System.out.println("liczba nauczycieli z podanym stanem: " + zlicz);
    }

    void sortBySalary() {
        this.nauczyciele.sort(new TeachercomparatorSalary());
    }

    void sortByName() {
        this.nauczyciele.sort(new Teachercomparator());
    }

    void search(String string) {

        for (Teacher teacher : this.nauczyciele) {
            if (string.compareTo(teacher.nazwisko) == 0) {
                teacher.printing();
            }
        }

    }

    void searchPartial(String string) {
        Iterator<Teacher> iter = this.nauczyciele.iterator();

        for (Teacher teacher : this.nauczyciele) {
            if (teacher.nazwisko.matches(string + "(.*)")) {
                teacher.printing();
            } else if (teacher.imie.matches(string + "(.*)")) {
                teacher.printing();
            }
        }

    }

    void max() {
        new Teacher(null, null, null, 1000, 231.0);
        Teacher teacher1 = Collections.max(this.nauczyciele, new Teachercomparator());
        teacher1.printing();
    }
    void showTeacherNamesAndSurnames(ObservableList<Teacher> teacherList) {
        for (Teacher teacher : teacherList) {
            System.out.println("Imię: " + teacher.getImie() + ", Nazwisko: " + teacher.getNazwisko());
        }
    }
    public StringProperty nazwaProperty() {
        return new SimpleStringProperty(nazwa_grupy);
    }
    public ArrayList<Teacher> getNauczyciele() {
        return nauczyciele;
    }


}
