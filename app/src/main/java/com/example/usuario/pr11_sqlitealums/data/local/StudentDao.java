package com.example.usuario.pr11_sqlitealums.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.usuario.pr11_sqlitealums.data.model.Student;

import java.util.ArrayList;

/**
 * Created by usuario on 29/01/2018.
 */

public class StudentDao {
    private static StudentDao sInstance;

    private final DBHelper dbHelper;
    private final ContentResolver contentResolver;

    private StudentDao(Context context, DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        contentResolver = context.getContentResolver();
    }

    public static synchronized StudentDao getInstance(Context context, DBHelper dbHelper) {
        // Application context to avoid memory leaks.
        if (sInstance == null) {
            sInstance = new StudentDao(context.getApplicationContext(), dbHelper);
        }
        return sInstance;
    }

    public SQLiteDatabase openWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        dbHelper.close();
    }

    public long createStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues contentValues = student.toContentValues();
        long id = bd.insert(DBContract.Student.TABLE_NAME, null, contentValues);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(Uri.parse(DBContract.Student.NOTIFICATION_URI), null);
        return id;
    }

    // Return true in case of success or false otherwise.
    public boolean deleteStudent(long id) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        long deleted = bd.delete(DBContract.Student.TABLE_NAME,
                DBContract.Student._ID + " = " + id, null);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(
                Uri.parse(DBContract.Student.NOTIFICATION_URI), null);
        return deleted == 1;
    }

    // Return true in case of success or false otherwise.
    public boolean updateStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues valores = student.toContentValues();
        long updated = bd.update(DBContract.Student.TABLE_NAME, valores,
                DBContract.Student._ID + " = " + student.getId(), null);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(Uri.parse(DBContract.Student.NOTIFICATION_URI), null);
        return updated == 1;
    }

    // Return student or null if it doesn't exist.
    public Student getStudent(long id) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        Cursor cursor = bd.query(true, DBContract.Student.TABLE_NAME, DBContract.Student.ALL_FIELDS,
                DBContract.Student._ID + " = " + id, null, null, null, null, null);
        Student student = null;
        if (cursor != null) {
            cursor.moveToFirst();
            student = mapStudentFromCursor(cursor);
        }
        dbHelper.close();
        return student;
    }

    // Return all students query cursor, ordered alphabetically.
    public Cursor queryStudents() {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        Log.d(StudentDao.class.getSimpleName(), "Querying students");
        return bd.query(DBContract.Student.TABLE_NAME, DBContract.Student.ALL_FIELDS, null, null,
                null, null, DBContract.Student.NAME);
        // DON'T CLOSE DATABASE SO WE CAN OPERATE WITH THE CURSOR.
    }

    // Return all students, ordered alphabetically.
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        Cursor cursor = this.queryStudents();
        if (cursor != null) {
            students = mapStudentsFromCursor(cursor);
            cursor.close();
        }
        dbHelper.close();
        return students;
    }

    public static ArrayList<Student> mapStudentsFromCursor(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            students.add(mapStudentFromCursor(cursor));
            cursor.moveToNext();
        }
        return students;
    }

    @SuppressWarnings("WeakerAccess")
    public static Student mapStudentFromCursor(Cursor cursor) {
        Student student = new Student();
        student.setId(
                cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Student._ID)));
        student.setName(cursor.getString(
                cursor.getColumnIndexOrThrow(DBContract.Student.NAME)));
        student.setGrade(cursor.getString(
                cursor.getColumnIndexOrThrow(DBContract.Student.GRADE)));
        student.setPhone(cursor.getString(
                cursor.getColumnIndexOrThrow(DBContract.Student.PHONE)));
        student.setAddress(cursor.getString(
                cursor.getColumnIndexOrThrow(DBContract.Student.ADDRESS)));
        return student;
    }


}
