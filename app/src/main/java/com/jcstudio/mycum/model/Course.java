package com.jcstudio.mycum.model;

public class Course {
    private long mId;
    private String course_name;
    private double course_uv;
    private double obtain_grade;
    private double calculated_cgrade;
    private long cycle_id;

    public Course() {

    }

    public Course(String course_name, double course_uv, double obtain_grade) {
        this.course_name = course_name;
        this.course_uv = course_uv;
        this.obtain_grade = obtain_grade;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public double getCourse_uv() {
        return course_uv;
    }

    public void setCourse_uv(double course_uv) {
        this.course_uv = course_uv;
    }

    public double getObtain_grade() {
        return obtain_grade;
    }

    public void setObtain_grade(double obtain_grade) {
        this.obtain_grade = obtain_grade;
    }

    public long getCycle_id() {
        return cycle_id;
    }

    public void setCycle_id(long cycle_id) {
        this.cycle_id = cycle_id;
    }

    public double getCalculated_cgrade() {
        return calculated_cgrade;
    }

    public void setCalculated_cgrade(double calculated_cgrade) {
        this.calculated_cgrade = calculated_cgrade;
    }
}


