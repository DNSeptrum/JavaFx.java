package com.example.AIOM3;

import java.util.Comparator;

public class TeachercomparatorSalary implements Comparator<Teacher> {
    public TeachercomparatorSalary() {
    }

    public int compare(Teacher o1, Teacher o2) {
        if (o1.getWynagrodzenie() > o2.getWynagrodzenie()) {
            return -1;
        } else {
            return o1.getWynagrodzenie() < o2.getWynagrodzenie() ? 1 : 0;
        }
    }
}
