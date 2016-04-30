package com.example.beahildehrandt.financecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EntryViewActivity extends AppCompatActivity {
    public final static String EXTRA_NAME = "com.example.beahildehrandt.financecalculator.EXTRA_NAME";
    public final static String EXTRA_AMOUNT = "com.example.beahildehrandt.financecalculator.EXTRA_AMOUNT";
    public final static String EXTRA_DATE = "com.example.beahildehrandt.financecalculator.EXTRA_DATE";

    public final static String EXTRA_UPDATE = "com.example.beahildehrandt.financecalculator.EXTRA_UPDATE";
    public final static String EXTRA_DELETE = "com.example.beahildehrandt.financecalculator.EXTRA_DELETE";

    private boolean isUpdate = false;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);
     //   if(findViewById(R.id.mainActivity_newEntry).isPressed()){
    //        findViewById(R.id.deleteEntry).setVisibility(Button.INVISIBLE);
      //  }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        position = intent.getStringExtra(MainViewActivity.EXTRA_POSITION);
        if (position != null){
            isUpdate = true;

            //Adaptieren der Überschrift
            TextView textView = (TextView) findViewById(R.id.TextView2);
            textView.setText("Update entry");

            //Vorausfüllen der Textfelder
            EditText amountText = (EditText) findViewById(R.id.entryAmount);
            amountText.setText(intent.getStringExtra(MainViewActivity.EXTRA_AMO));

            EditText nameText = (EditText) findViewById(R.id.entryName);
            nameText.setText(intent.getStringExtra(MainViewActivity.EXTRA_NAM));

            EditText dateText = (EditText) findViewById(R.id.entryDate);
            dateText.setText(intent.getStringExtra(MainViewActivity.EXTRA_DAT));

        }
    }

    public void deleteEntry(View view){
        Intent intent = new Intent(this, MainViewActivity.class);
        intent.putExtra(EXTRA_DELETE, position);
        startActivity(intent);
    }

    public void saveEntry(View view){
        Intent intent = new Intent(this, MainViewActivity.class);

        //Wir holen die Einträge aus den Textfeldern.
        EditText nameText = (EditText) findViewById(R.id.entryName);
        String name = nameText.getText().toString();
        EditText amountText = (EditText) findViewById(R.id.entryAmount);
        String amount = amountText.getText().toString();
        EditText dateText = (EditText) findViewById(R.id.entryDate);
        String date = dateText.getText().toString();

        //wenn die Felder nicht  ausgefüllt sind, passiert nix.
        if(!name.matches("") && !amount.matches("") && !date.matches("")) {
            intent.putExtra(EXTRA_NAME, name);
            intent.putExtra(EXTRA_AMOUNT, amount);
            intent.putExtra(EXTRA_DATE, date);
            if(isUpdate){
                intent.putExtra(EXTRA_UPDATE, position);
            }
            startActivity(intent);
        }
        else{
            TextView tv = (TextView) findViewById(R.id.gap);
            tv.setText("Please fill out all fields!");
        }
    }

}
