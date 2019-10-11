package com.jcstudio.mycum.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jcstudio.mycum.model.Course;
import com.jcstudio.mycum.model.Cycle;

import java.util.ArrayList;
import java.util.List;

public class CycleOperation {

    private Context mContext;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] mAllColumns = {DBHelper.UID_CYCLE,
            DBHelper.COLUMN_CYCLE_NAME,
            DBHelper.COLUMN_CYCLE_GRADE,
            DBHelper.COLUMN_CYCLE_TOTAL_COURSE,
            DBHelper.COLUMN_CYCLE_TOTAL_UV };

    public CycleOperation(Context context) {
        mContext = context;
        dbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
        Log.d("Anik", "cycle db close");
    }
    public Cycle createCycle(String cycle_name, double grade, int total_course, double total_uv) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CYCLE_NAME, cycle_name);
        values.put(DBHelper.COLUMN_CYCLE_GRADE, grade);
        values.put(DBHelper.COLUMN_CYCLE_TOTAL_COURSE, total_course);
        values.put(DBHelper.COLUMN_CYCLE_TOTAL_UV, total_uv);
        long insertId = sqLiteDatabase.insert(DBHelper.TABLE_NAME_CYCLE,null,values);
        Log.d("Anik", "invoked"+insertId);
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME_CYCLE, mAllColumns,
                DBHelper.UID_CYCLE + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Cycle newCycle = cursorToSmester(cursor);
        cursor.close();
        return newCycle;
    }

    public List<Cycle> getAllCycle(){
        List<Cycle> allCycle = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME_CYCLE, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Cycle cycle = cursorToSmester(cursor);
                allCycle.add(cycle);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return allCycle;
    }

    public void deleteCycle(Cycle cycle) {
        long id = cycle.getmId();
        CourseOperation gradecOperation = new CourseOperation(mContext);
        List<Course> listCourses = gradecOperation.getCoursesByCycleId(id);
        if (listCourses != null && !listCourses.isEmpty()) {
            for (Course e : listCourses) {
                gradecOperation.deleteCourse(e);
            }
        }
        String[] args={String.valueOf(id)};
        sqLiteDatabase.delete(DBHelper.TABLE_NAME_CYCLE, DBHelper.UID_CYCLE
                + " = ?", args);
    }

    public void updateCycle(long cycleId, double grade, double total_uv, int total_course){
        long id = cycleId;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_CYCLE_GRADE,grade);
        contentValues.put(DBHelper.COLUMN_CYCLE_TOTAL_UV,total_uv);
        contentValues.put(DBHelper.COLUMN_CYCLE_TOTAL_COURSE,total_course);
        String[] whereArgs = {String.valueOf(id)};
        sqLiteDatabase.update(DBHelper.TABLE_NAME_CYCLE,contentValues, DBHelper.UID_CYCLE+" =? ",whereArgs);
    }

    private Cycle cursorToSmester(Cursor cursor) {
        Cycle cycle = new Cycle();
        cycle.setmId(cursor.getLong(0));
        cycle.setCycle_name(cursor.getString(1));
        cycle.setTotal_grade(cursor.getDouble(2));
        cycle.setTotal_course(cursor.getInt(3));
        cycle.setTotal_uv(cursor.getDouble(4));
        return cycle;
    }
}

