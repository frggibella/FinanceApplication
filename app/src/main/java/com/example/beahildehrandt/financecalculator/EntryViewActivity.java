package com.example.beahildehrandt.financecalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

/**
 * EntryViewActivity-Klasse
 *
 * Verwaltungs-Klasse von neuen, zu ändernden bzw zu löschenden Einträgen
 */

public class EntryViewActivity extends AppCompatActivity {
    public final static String EXTRA_NAME = "com.example.beahildehrandt.financecalculator.EXTRA_NAME";
    public final static String EXTRA_AMOUNT = "com.example.beahildehrandt.financecalculator.EXTRA_AMOUNT";
    public final static String EXTRA_DATE = "com.example.beahildehrandt.financecalculator.EXTRA_DATE";

    public final static String EXTRA_UPDATE = "com.example.beahildehrandt.financecalculator.EXTRA_UPDATE";
    public final static String EXTRA_DELETE = "com.example.beahildehrandt.financecalculator.EXTRA_DELETE";

    private boolean isUpdate = false;
    private String position;
    private static String dates;
    private static Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UI-Zuordnung:
        setContentView(R.layout.activity_entry_view);
        Intent intent = getIntent();
        //Übergeben des Eintrags aus der MainViewActivity
        position = intent.getStringExtra(MainViewActivity.EXTRA_POSITION);

        dateButton = (Button) findViewById(R.id.entryDate);

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

            dateButton.setText(intent.getStringExtra(MainViewActivity.EXTRA_DAT));
        }
    }

    public void deleteEntry(View view){
        Intent intent = new Intent(this, MainViewActivity.class);
        intent.putExtra(EXTRA_DELETE, position);
        startActivity(intent);
    }

    public void saveEntry(View view){
        Intent intent = new Intent(this, MainViewActivity.class);

        //Holen der Einträge aus den Textfeldern
        EditText nameText = (EditText) findViewById(R.id.entryName);
        String name = nameText.getText().toString();
        EditText amountText = (EditText) findViewById(R.id.entryAmount);
        String amount = amountText.getText().toString();

        //wenn die Felder nicht  ausgefüllt sind, passiert nichts
        if(!name.matches("") && !amount.matches("") && !dates.matches("")) {
            intent.putExtra(EXTRA_NAME, name);
            intent.putExtra(EXTRA_AMOUNT, amount);
            intent.putExtra(EXTRA_DATE, dates);
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current dates as the default dates in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month = month + 1;
            dates = day + "." + month + "." + year;
            dateButton.setText(dates);

        }
    }

}
