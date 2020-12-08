package com.example.projectd.Dto;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

public class ChatDto {

    private String name;
    private String addr;
    private String msg;
    private String photoUrl;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddr(){
        return addr;
    }

    public void getAddr(String addr){
        this.addr = addr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
