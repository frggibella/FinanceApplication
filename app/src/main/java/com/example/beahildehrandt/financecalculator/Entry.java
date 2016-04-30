package com.example.beahildehrandt.financecalculator;

import java.io.Serializable;

/**
 * Created by beahildehrandt on 25.10.15.
 */

//Klassenkopf
public class Entry implements Serializable{
    private String name;
    private Double amount;
    private String date;

    //Konstruktor der Klassen
    public Entry(String name, Double amount, String date){
        this.name = name;
        this.amount = amount;
        this.date = date;

    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
