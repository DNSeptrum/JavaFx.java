package com.example.AIOM3;

import javafx.beans.property.*;

public class Teacher implements Comparable<Teacher> {
    String imie;
    String nazwisko;
    TeacherCondition teacherCondition;
    int rok_urodzenia;
    double wynagrodzenie;

    public Teacher(String imie, String nazwisko, TeacherCondition teacherCondition, int rok_urodzenia, double wynagrodzenie) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.teacherCondition = teacherCondition;
        this.rok_urodzenia = rok_urodzenia;
        this.wynagrodzenie = wynagrodzenie;
    }

    public String getImie() {
        return this.imie;
    }

    public String getNazwisko() {

        return nazwisko;
    }

    public int getRok_urodzenia() {
        return rok_urodzenia;
    }

    public TeacherCondition getTeacherCondition() {
        return teacherCondition;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setTeacherCondition(TeacherCondition teacherCondition) {
        this.teacherCondition = teacherCondition;
    }

    public void setRok_urodzenia(int rok_urodzenia) {
        this.rok_urodzenia = rok_urodzenia;
    }

    public void setWynagrodzenie(double wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    public StringProperty imieProperty() {
        return new SimpleStringProperty(imie);
    }
    public StringProperty nazwiskoProperty() {
        return new SimpleStringProperty(nazwisko);
    }

    @Override
    public String toString() {
        return imie;
    }


    public ObjectProperty teacherConditionProperty() {
        return new SimpleObjectProperty(teacherCondition);
    }
    public IntegerProperty rok_urodzeniaProperty() {
        return new SimpleIntegerProperty( rok_urodzenia);
    }
    public DoubleProperty wynagrodzenieProperty() {
        return new SimpleDoubleProperty( wynagrodzenie);
    }
    public double getWynagrodzenie() {
        return this.wynagrodzenie;
    }

    void printing() {
        System.out.println("imie: " + this.imie);
        System.out.println("nazwisko: " + this.nazwisko);
        System.out.println("kondycja: " + this.teacherCondition);
        System.out.println("rok urodzenia: " + this.rok_urodzenia);
        System.out.println("wynagrodzenie: " + this.wynagrodzenie);
    }

    public int compareTo(Teacher o1) {
        return 0;
    }
}
