package com.example.projectd;

public class Rent {
    String name;
    String renter;

    public Rent(String name, String renter){
        this.name = name;
        this.renter = renter;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRenter(){ return renter;  }

    public void getRenter(String renter){
        this.renter = renter;
    }

}
