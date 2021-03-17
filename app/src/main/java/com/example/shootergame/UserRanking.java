package com.example.shootergame;

public class UserRanking {
    private int no;
    private String name;
    private String rp;

    public UserRanking(int no, String name, String rp){
        this.no = no;
        this.name = name;
        this.rp = rp;
    }

    public int getNo(){
        return no;
    }
    public void setNo(int no){
        this.no = no;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getRp(){
        return rp;
    }
    public void setRp(String rp){
        this.rp = rp;
    }
}
