package com.jcstudio.mycum.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jcstudio.mycum.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseOperation {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] mAllColumns = {DBHelper.UID_COURSE,
            DBHelper.COLUMN_CGRADE_COURSE_NAME,
            DBHelper.COLUMN_CGRADE_COURSE_UV,
            DBHelper.COLUMN_CGRADE_COURSE_GRADE,
            DBHelper.COLUMN_CGRADE_CYCLE_ID };
    public CourseOperation(Context context) {
        dbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    /*public void close() {
        dbHelper.close();
        Log.d("Anik", "cgrade db close");
    }*/
    public Course createCourse(String course_name, double course_uv, double grade, Long cycleId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CGRADE_COURSE_NAME, course_name);
        values.put(DBHelper.COLUMN_CGRADE_COURSE_UV, course_uv);
        values.put(DBHelper.COLUMN_CGRADE_COURSE_GRADE, grade);
        values.put(DBHelper.COLUMN_CGRADE_CYCLE_ID, cycleId);
        long insertId = sqLiteDatabase.insert(DBHelper.TABLE_NAME_CGRADE,null,values);
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME_CGRADE, mAllColumns,
                DBHelper.UID_COURSE + " = " + insertId, null, null,
                null, null);
        if(cursor == null){
            Log.d("Anik", "null "+cursor);
        }
        else{
            Log.d("Anik", "not null");
        }
        cursor.moveToFirst();
        Course newCourse = cursorToCourse(cursor);
        cursor.close();
        return newCourse;
    }
    public List<Course> getCoursesByCycleId(Long cycleId){
        List<Course> coursesByCycleId = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME_CGRADE, mAllColumns,
                DBHelper.COLUMN_CGRADE_CYCLE_ID + " = ?",
                new String[] { String.valueOf(cycleId) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course courses = cursorToCourse(cursor);
                coursesByCycleId.add(courses);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return coursesByCycleId;
    }
    public List<Course> getAllCourses(){
        List<Course> allCourse = new ArrayList<>();
        Cursor cursor =
                sqLiteDatabase.query(DBHelper.TABLE_NAME_CGRADE,mAllColumns,null,null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course courses = cursorToCourse(cursor);
                allCourse.add(courses);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return allCourse;
    }
    public void deleteCourse(Course course) {
        long id = course.getmId();
        sqLiteDatabase.delete(DBHelper.TABLE_NAME_CGRADE, DBHelper.UID_COURSE
                + " = " + id, null);
    }

    public void updateCourse(Course course, String mCourseName, double mUv, double mGrade){
        long id = course.getmId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_CGRADE_COURSE_NAME,mCourseName);
        contentValues.put(DBHelper.COLUMN_CGRADE_COURSE_UV,mUv);
        contentValues.put(DBHelper.COLUMN_CGRADE_COURSE_GRADE,mGrade);
        String[] whereArgs = {String.valueOf(id)};
        sqLiteDatabase.update(DBHelper.TABLE_NAME_CGRADE,contentValues, DBHelper.UID_COURSE+" =? ",whereArgs);
    }
    private Course cursorToCourse(Cursor cursor) {
        Course course = new Course();
        course.setmId(cursor.getLong(0));
        course.setCourse_name(cursor.getString(1));
        course.setCourse_uv(cursor.getDouble(2));
        course.setObtain_grade(cursor.getDouble(3));
        course.setCycle_id(cursor.getInt(4));
        return course;
    }
}
