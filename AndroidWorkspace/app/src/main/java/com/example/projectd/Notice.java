package com.example.projectd;

public class Notice {
    String day;
    String sub;

    public Notice(String day, String sub){
        this.day = day;
        this.sub = sub;
    }

    public String getDay(){
        return day;
    }

    public void setDay(String day){
        this.day = day;
    }

    public String getSub(){
        return sub;
    }

    public void setSub(String sub){
        this.sub = sub;
    }

}
