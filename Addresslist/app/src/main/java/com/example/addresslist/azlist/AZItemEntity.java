package com.example.addresslist.azlist;

public class AZItemEntity<T> {
    private T mValue;
    private String mSortLetters;

    public T getValue() {
        return mValue;
    }

    public void setValue(T value) {
        mValue = value;
    }

    public String getSortLetters() {
        return mSortLetters;
    }

    public void setSortLetters(String sortLetters) {
        mSortLetters = sortLetters;
    }
}
//public class AZItemEntity<T> {
//    private String id, name, tel, email, company;
//    private String mSortLetters;
//
//    public AZItemEntity() {
//        id = "";
//        name = "";
//        tel = "";
//        email = "";
//        company = "";
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTel() {
//        return tel;
//    }
//
//    public void setTel(String tel) {
//        this.tel = tel;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCompany() {
//        return company;
//    }
//
//    public void setCompany(String company) {
//        this.company = company;
//    }
//
//    public String getSortLetters() {
//        return mSortLetters;
//    }
//
//    public void setSortLetters(String sortLetters) {
//        mSortLetters = sortLetters;
//    }
//}