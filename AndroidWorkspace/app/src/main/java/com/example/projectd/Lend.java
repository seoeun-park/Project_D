package com.example.projectd;

public class Lend {
    String name;
    String lender;

    public Lend(String name, String lender){
        this.name = name;
        this.lender = lender;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLender(){ return lender;  }

    public void getLender(String addr){
        this.lender = addr;
    }

}
