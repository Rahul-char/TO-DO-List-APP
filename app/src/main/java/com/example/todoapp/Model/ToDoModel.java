package com.example.todoapp.Model;

import com.example.todoapp.Date_and_time;

import java.util.Date;

public class ToDoModel {

    private String task;

    public ToDoModel() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    private String dat;
    private String des;
    private int id , status;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    private String s;

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public int radio;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
