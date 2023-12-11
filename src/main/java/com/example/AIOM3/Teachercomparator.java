package com.example.AIOM3;

import java.util.Comparator;

public class Teachercomparator implements Comparator<Teacher> {
    public Teachercomparator() {
    }

    public int compare(Teacher o1, Teacher o2) {
        return o1.getImie().compareTo(o2.getImie());
    }
}
