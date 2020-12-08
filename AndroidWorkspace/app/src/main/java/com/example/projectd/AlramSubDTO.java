package com.example.projectd;

public class AlramSubDTO {
    String content;
    String md_rental_term;

    public AlramSubDTO(String content, String md_rental_term) {
        this.content = content;
        this.md_rental_term = md_rental_term;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMd_rental_term() {
        return md_rental_term;
    }

    public void setMd_rental_term(String md_rental_term) {
        this.md_rental_term = md_rental_term;
    }
}

