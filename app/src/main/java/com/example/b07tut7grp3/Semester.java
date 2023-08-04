package com.example.b07tut7grp3;

@SuppressWarnings("unused")
/**
 * An enumeration of possible semesters
 * @author Kevin Li
 * @since 0.1
 */
public enum Semester {

    FALL("Fall"),
    WINTER("Winter"),
    SUMMER("Summer");
    private String semester;
    private Semester(String term) {
        this.semester = term;
    }

    @Override
    public String toString(){
        return this.semester;
    }
}
