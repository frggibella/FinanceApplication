package com.example.beahildehrandt.financecalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


public class MainViewActivity extends AppCompatActivity {

    public final static String EXTRA_POSITION = "com.example.beahildehrandt.financecalculator.EXTRA_POSITION";
    public final static String EXTRA_NAM = "com.example.beahildehrandt.financecalculator.EXTRA_NAM";
    public final static String EXTRA_AMO = "com.example.beahildehrandt.financecalculator.EXTRA_AMO";
    public final static String EXTRA_DAT = "com.example.beahildehrandt.financecalculator.EXTRA_DAT";

    private ArrayList<String> entryListString = new ArrayList<String>();
    private ArrayList<Double> entryListDouble = new ArrayList<Double>();
    private ArrayList<String> entryListDate = new ArrayList<String>();
    private ArrayList<Entry> entryList = new ArrayList<Entry>();
 /*   private void getTitleList(){
        Entry tempEntry = new Entry;
        ArrayList titleList = new ArrayList<String>;
        for ( int i = 0, i < entryList.size(), i++){
            String temp = entryList.getName(i);

        }
    };

    entryList.getAmountList();
    entryList.getDateList();
    */

    private ListView listView;
    public MyCustomBaseAdapter myCustomBaseAdapter;
    private TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Auslesen des Intents von EntryActivity
        Intent intent = getIntent();
        String name = intent.getStringExtra(EntryViewActivity.EXTRA_NAME);
        String amount = intent.getStringExtra(EntryViewActivity.EXTRA_AMOUNT);
        String date = intent.getStringExtra(EntryViewActivity.EXTRA_DATE);

        String update = intent.getStringExtra(EntryViewActivity.EXTRA_UPDATE);
        String delete = intent.getStringExtra(EntryViewActivity.EXTRA_DELETE);

        //UI-Element aus xml
        setContentView(R.layout.activity_main_view);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //Aufruf von persistierten Dateien
        tinydb = new TinyDB(this);
        entryListString = tinydb.getListString("ListString");
        entryListDouble = tinydb.getListDouble("ListDouble");
        entryListDate = tinydb.getListString("ListDate");

        listView = (ListView) findViewById(R.id.listView);
        myCustomBaseAdapter = new MyCustomBaseAdapter(this, entryList);
        listView.setAdapter(myCustomBaseAdapter);
        refreshList();

        //OnClicklistener für ListView öffnet neue Activity
        // EntryViewActivity wird geöffnet und Position aus der ListView wird übergeben.

        AdapterView.OnItemClickListener av = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                Intent intent = new Intent(parent.getContext(), EntryViewActivity.class);
                intent.putExtra(EXTRA_POSITION, Integer.toString(position));

                String nam = entryListString.get(position);
                intent.putExtra(EXTRA_NAM, nam);

                Double amo = entryListDouble.get(position);
                intent.putExtra(EXTRA_AMO, Double.toString(amo));

                String dat = entryListDate.get(position);
                intent.putExtra(EXTRA_DAT, dat);

                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(av);

        //Anlegen eines neuen Eintrages, wenn er aus EntryActivity kommt
        if((name != null && amount != null && date != null) && update == null){
            //String to Float
            Double amountDouble = Double.parseDouble(amount);
            Entry newEntry = new Entry(name, amountDouble, date);
            entryListString.add(name);
            entryListDouble.add(amountDouble);
            entryListDate.add(date);
            refreshList();

        }

        //Löschen eines Eintrags
        if(delete != null){
            Integer pos = Integer.parseInt(delete);
            entryListDouble.remove(entryListDouble.get(pos));
            entryListString.remove(entryListString.get(pos));
            entryListDate.remove(entryListDate.get(pos));
            tinydb.putListString("ListString", entryListString);
            tinydb.putListDouble("ListDouble", entryListDouble);
            tinydb.putListString("ListDate", entryListString);
            refreshList();
        }

        //Eintragung,falls Datensatz geändert wurde
        if(update != null && delete == null){
            Integer pos = Integer.parseInt(update);
            entryListDouble.set(pos, Double.parseDouble(amount));
            entryListString.set(pos, name);
            entryListDate.set(pos, date);

            tinydb.putListString("ListString", entryListString);
            tinydb.putListDouble("ListDouble", entryListDouble);
            tinydb.putListString("ListDate", entryListDate);
            refreshList();
        }

        //Überschrift mit Gesamtbetrag
        refreshHeadline();

        //Persistierung von Daten
        tinydb.putListString("ListString", entryListString);
        tinydb.putListDouble("ListDouble", entryListDouble);
        tinydb.putListString("ListDate", entryListDate);
    }

    private void refreshList(){
        Entry tempEntry;
        entryList = new ArrayList<Entry>();
        for(int i = 0; i < entryListDate.size(); i++){
            tempEntry = new Entry(
                    entryListString.get(i),
                    entryListDouble.get(i),
                    entryListDate.get(i)
            );
            entryList.add(tempEntry);
        }

        //SORTIERALGORITHMUS für Einträge
        //Arraylist "entryList" vom selbst kreierten Datentyp "Entry" wird in der compare-Methode der Comparator-Klasse "MyListSort" sortiert.
        Collections.sort(entryList, new MyListSort());

        /*
        for(int i = 0; i < entryList.size(); i++){

                    entryListString[i] = entryList.get(i,name)[i],
                    entryListDouble.set(i),
                    entryListDate.set(i)

        }
        */
        myCustomBaseAdapter.clear();
        myCustomBaseAdapter.addAll(entryList);
        myCustomBaseAdapter.notifyDataSetChanged();
    }

    //Methode zur Aktualisierung des Gesamtbetrages
    private void refreshHeadline(){
        Double sum = 0.00;
        for (Double amou : entryListDouble) {
            sum += amou;
        }
        //runden der Gesamtsumme:
        DecimalFormat df = new DecimalFormat("#.##");
        sum = Double.valueOf(df.format(sum));

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Overall: " + sum + " €");
    }




    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main_view, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */
    //Buttonlistener für newEntry Button
    public void newEntry(View view){
        Intent intent = new Intent(this, EntryViewActivity.class);
        startActivity(intent);
    }
    public void deleteAll(View view){
        tinydb.clear();

        entryListDouble.clear();
        refreshHeadline();

        myCustomBaseAdapter.clear();
        myCustomBaseAdapter.notifyDataSetChanged();
    }
}


