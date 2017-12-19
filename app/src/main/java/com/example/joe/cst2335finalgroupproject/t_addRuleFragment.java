package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joe on 2017-12-14.
 */

public class t_addRuleFragment extends Fragment {

    Activity callingActivity;
    EditText et;
    TextView tv;
    View v;
    t_ThermostatRule rule;

    Spinner daySpinner;
    Spinner hourSpinner;
    Spinner minuteSpinner;
    Spinner tempSpinner;

    Button discardButton;
    Button saveButton;

    public t_addRuleFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callingActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle info = getArguments();

        v = inflater.inflate(R.layout.t_add_rule, container, false);

        rule = new t_ThermostatRule();

        initializeDiscardButton();
        initializeSaveButton();

        initializeDaySpinner();
        initializeHourSpinner();
        initializeMinuteSpinner();
        initializeTempSpinner();


        et = v.findViewById(R.id.manual_text_entry);

        tv = v.findViewById(R.id.t_frameLayoutPlaceholderTextBox);
        if (tv != null) {
            tv.setText("");
        }


        return v;
    }

    public void initializeDiscardButton() {
        discardButton = v.findViewById(R.id.t_discardAddRuleButton);
        switch (callingActivity.getLocalClassName()) {
            case "t_ThermostatProgramActivity": //landscape view - frameLayout in same activity as list
                discardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add confimration dialogalert
                        Log.i("AddActivity", "discarding New Rule");
                        ((t_ThermostatProgramActivity) callingActivity).discardMethod();
                        callingActivity.getFragmentManager()
                                .beginTransaction()
                                .remove(t_addRuleFragment.this)
                                .commit();


                    }
                });
            case "t_DetailView":    //portrait view - frameLayout was loaded into a new activity t_DetailView
                discardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add confimration dialogalert
                        Log.i("AddActivity", "discarding New Rule");
                        callingActivity.setResult(t_ThermostatProgramActivity.DISCARD_RESULT);
                        callingActivity.finish();
                    }
                });
        }
    }


    public void initializeSaveButton() {
        saveButton = v.findViewById(R.id.t_saveAddRuleButton);
        switch (callingActivity.getLocalClassName()) {
            case "t_ThermostatProgramActivity": //landscape view - frameLayout in same activity as list
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("AddActivity", "Saving Rule");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ruleToAdd", rule.toString());
                        //resultIntent.putExtra("listPosition", listPosition);
                        callingActivity.setResult(t_ThermostatProgramActivity.SAVE_RESULT, resultIntent);
                        callingActivity.finish();   //finish closes this empty activity on phones.
                    }
                });
            case "t_DetailView":    //portrait view - frameLayout was loaded into a new activity t_DetailView
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("AddActivity", "Saving Rule");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ruleToAdd", rule.toString());
                        //resultIntent.putExtra("listPosition", listPosition);
                        callingActivity.setResult(t_ThermostatProgramActivity.SAVE_RESULT, resultIntent);
                        callingActivity.getFragmentManager().beginTransaction().
                                remove(t_addRuleFragment.this).commit();
                    }
                });
        }
    }

    public void initializeDaySpinner() {

        daySpinner = v.findViewById(R.id.day_spinner);
        List<String> days = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.t_daysArray)));
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(dayAdapter.getPosition(rule.getDay()));
        daySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rule.setDay(adapterView.getItemAtPosition(i).toString());
                et.setText(rule.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initializeHourSpinner() {

        hourSpinner = v.findViewById(R.id.hour_spinner);
        Integer[] hours_array = new Integer[24];
        for (int i = 0; i < hours_array.length; i++) {
            hours_array[i] = i;
        }
        List<Integer> hours = new ArrayList<>(Arrays.asList(hours_array));
        ArrayAdapter<Integer> hourAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setSelection(hourAdapter.getPosition(rule.getHour()));
        hourSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rule.setHour(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
                et.setText(rule.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initializeMinuteSpinner() {

        minuteSpinner = v.findViewById(R.id.minute_spinner);
        Integer[] minute_array = new Integer[4];
        for (int i = 0; i < minute_array.length; i++) {
            minute_array[i] = i * 15;
        }
        List<Integer> minutes = new ArrayList<>(Arrays.asList(minute_array));
        ArrayAdapter<Integer> minuteAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, minutes);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(minuteAdapter);
        minuteSpinner.setSelection(minuteAdapter.getPosition(rule.getMinute()));
        minuteSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rule.setMinute(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
                et.setText(rule.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void initializeTempSpinner() {
        tempSpinner = v.findViewById(R.id.temp_spinner);
        Integer[] temp_array = new Integer[26];
        for (int i = 0; i < temp_array.length; i++) {
            temp_array[i] = i + 10;
        }
        List<Integer> temps = new ArrayList<>(Arrays.asList(temp_array));
        ArrayAdapter<Integer> tempAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, temps);
        tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempSpinner.setAdapter(tempAdapter);
        tempSpinner.setSelection(tempAdapter.getPosition(rule.getTemp()));
        tempSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rule.setTemp(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
                et.setText(rule.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
