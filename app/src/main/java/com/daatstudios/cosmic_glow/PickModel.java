package com.daatstudios.cosmic_glow;

public class PickModel {
    String name,pickl,dropl,time,task,number,ststus;

    public PickModel(String name, String pickl, String dropl, String time, String task, String number,String status) {
        this.name = name;
        this.pickl = pickl;
        this.dropl = dropl;
        this.time = time;
        this.task = task;
        this.number = number;
        this.ststus=status;
    }

    public String getStstus() {
        return ststus;
    }

    public void setStstus(String ststus) {
        this.ststus = ststus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickl() {
        return pickl;
    }

    public void setPickl(String pickl) {
        this.pickl = pickl;
    }

    public String getDropl() {
        return dropl;
    }

    public void setDropl(String dropl) {
        this.dropl = dropl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
