package com.example.beahildehrandt.financecalculator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Sortierklasse
 *
 * sortiert alle Einträge nach Datum, jedesmal, wenn refreshList aufgerufen wird
 */
class MyListSort implements Comparator<Entry> {
    @Override
    public int compare(Entry e1, Entry e2) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = format.parse(e1.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            d2 = format.parse(e2.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d1.compareTo(d2) < 0){
            return 1;
        }
        if(d1.compareTo(d2) > 0){
            return -1;
        }else {
            return 0;
        }
    }
}