package com.example.beahildehrandt.financecalculator;

/**
 * Enttry-Klasse
 *
 * jeder Eintrag, der erstellt wird, besteht aus Namen des Eintrags, Betrag (in Euro) und einem Datum.
 */

//Klassenkopf
public class Entry{
    private String name;
    private Double amount;
    private String date;

    //Konstruktor der Klassen
    public Entry(String name, Double amount, String date){
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    //get- und set- Methoden
    public Double getAmount() {
        return amount;
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
}
