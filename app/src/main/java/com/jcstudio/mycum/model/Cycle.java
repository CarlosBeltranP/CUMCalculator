package com.jcstudio.mycum.model;

public class Cycle {

    private long mId;
    private String cycle_name;
    private double total_grade;
    private int total_course;
    private double total_uv;


    public Cycle() {

    }
    public Cycle(long mId, String cycle_name, double total_grade, int total_course, double total_uv) {
        this.mId = mId;
        this.cycle_name = cycle_name;
        this.total_grade = total_grade;
        this.total_course = total_course;
        this.total_uv = total_uv;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getCycle_name() {
        return cycle_name;
    }

    public void setCycle_name(String cycle_name) {
        this.cycle_name = cycle_name;
    }

    public double getTotal_grade() {
        return total_grade;
    }

    public void setTotal_grade(double total_grade) {
        this.total_grade = total_grade;
    }

    public int getTotal_course() {
        return total_course;
    }

    public void setTotal_course(int total_course) {
        this.total_course = total_course;
    }

    public double getTotal_uv() {
        return total_uv;
    }

    public void setTotal_uv(double total_uv) {
        this.total_uv = total_uv;
    }
}

