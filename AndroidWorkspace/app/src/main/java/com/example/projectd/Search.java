package com.example.projectd;

import com.example.projectd.Dto.MdDTO;



public class Search  {
    String title;
    String price;

    public Search(String title, String price){
        this.title = title;
        this.price = price;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
