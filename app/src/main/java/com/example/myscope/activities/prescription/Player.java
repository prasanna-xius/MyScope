package com.example.myscope.activities.prescription;

public class Player {
    private String name,position,drugname,brandname;
    private int id;

    public Player(String name, String position,String drugname,String brandname, int id) {
        this.name = name;
        this.position = position;
        this.drugname = drugname;
        this.brandname = brandname;
        this.id = id;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }



    public String getDrugname() {
        return drugname;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
