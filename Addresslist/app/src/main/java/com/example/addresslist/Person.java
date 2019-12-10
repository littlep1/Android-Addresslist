package com.example.addresslist;

public class Person {
    private String id, name, tel, email, company;
    private String mSortLetters;

    public Person() {
        id = "";
        name = "";
        tel = "";
        email = "";
        company = "";
        mSortLetters="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSortLetters() {
        return mSortLetters;
    }

    public void setSortLetters(String sortLetters) {
        mSortLetters = sortLetters;
    }
}
