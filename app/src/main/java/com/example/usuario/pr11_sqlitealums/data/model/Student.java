package com.example.usuario.pr11_sqlitealums.data.model;

import android.content.ContentValues;

import com.example.usuario.pr11_sqlitealums.data.local.DBContract;

/**
 * Created by usuario on 29/01/2018.
 */

public class Student {
    private long id;
    private String name;
    private String phone;
    private String grade;
    private String address;

    public Student() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Student.NAME, name);
        contentValues.put(DBContract.Student.GRADE, grade);
        contentValues.put(DBContract.Student.PHONE, phone);
        contentValues.put(DBContract.Student.ADDRESS, address);
        return contentValues;
    }
}
