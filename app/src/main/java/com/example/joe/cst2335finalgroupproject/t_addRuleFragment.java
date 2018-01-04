package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Add Rule Fragment for Thermostat Rule Management
 *
 * Author: Joe Ireland - 040 767 052
 */
public class t_addRuleFragment extends Fragment {

    //reference to the Activity that launched this fragment
    Activity callingActivity;

    //references to the objects on the screen
    EditText et;
    View v;

    //references to the rule being edited
    t_ThermostatRule rule;

    //references to the 4 spinners
    Spinner daySpinner;
    Spinner hourSpinner;
    Spinner minuteSpinner;
    Spinner tempSpinner;

    //references to both buttons
    Button discardButton;
    Button saveButton;

    /**
     * Sets the callingActivity reference to the Activity that launched this Fragment.
     *
     * @param activity The Activity from which this Fragment has been launched.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callingActivity = activity;
    }

    /**
     * Populates the View by inflating the layout, setting up an empty rule to add to,
     * and setting the behaviors of the Spinners and Buttons.
     *
     * @param inflater           The LayoutInflater of the Fragment
     * @param container          The View that contains the Fragment
     * @param savedInstanceState The Bundle containing the last-saved state of the Fragment
     * @return The completed view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate the EditRule Layout into View v
        v = inflater.inflate(R.layout.t_add_rule, container, false);

        //set the rule as an empty rule
        rule = new t_ThermostatRule();

        //initialize the spinners (see methods below)
        initializeDaySpinner();
        initializeHourSpinner();
        initializeMinuteSpinner();
        initializeTempSpinner();

        //initialize the buttons (see methods below)
        initializeDiscardButton();
        initializeSaveButton();

        //find the EditText in the view (nothing to set it to at onCreate time,
        //will be used by spinners later on.
        et = v.findViewById(R.id.manual_text_entry);

        //return the completed view
        return v;
    }

    /**
     * Define the behavior of the "Discard" Button.
     */
    public void initializeDiscardButton() {
        discardButton = v.findViewById(R.id.t_discardAddRuleButton);
        switch (callingActivity.getLocalClassName()) {
            case "t_ThermostatProgramActivity": //landscape view - frameLayout in t_ThermostatProgramActivity
                discardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((t_ThermostatProgramActivity) callingActivity).discardMethod();
                        callingActivity.getFragmentManager()
                                .beginTransaction()
                                .remove(t_addRuleFragment.this)
                                .commit();
                    }
                });
                break;
            case "t_DetailView":    //portrait view - frameLayout in t_DetailView
                discardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callingActivity.setResult(t_ThermostatProgramActivity.DISCARD_RESULT_CODE);
                        callingActivity.finish();
                    }
                });
                break;
        }
    }

    /**
     * Define the onClick behavior of the "Save" Button
     */
    public void initializeSaveButton() {
        saveButton = v.findViewById(R.id.t_saveAddRuleButton);

        final Bundle extrasBundle = new Bundle();

        switch (callingActivity.getLocalClassName()) {
            case "t_ThermostatProgramActivity": //landscape view - frameLayout in same activity as list
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        extrasBundle.putString("ruleToAdd", rule.toString());
                        ((t_ThermostatProgramActivity) callingActivity).saveRuleAsNew(extrasBundle);
                        callingActivity.getFragmentManager()
                                .beginTransaction()
                                .remove(t_addRuleFragment.this)
                                .commit();
                    }
                });
                break;
            case "t_DetailView":   //portrait view - frameLayout in t_DetailView
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        extrasBundle.putString("ruleToAdd", rule.toString());
                        Intent resultIntent = new Intent().putExtras(extrasBundle);
                        callingActivity.setResult(t_ThermostatProgramActivity.SAVE_RESULT_CODE, resultIntent);
                        callingActivity.finish();
                    }
                });
                break;
        }
    }

    /**
     * Sets the Values and Behaviors of the Day Spinner.
     */
    public void initializeDaySpinner() {
        //find the day Spinner
        daySpinner = v.findViewById(R.id.day_spinner);

        //define the values
        List<String> days = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.t_daysArray)));

        //insert the values into an arrayAdapter.
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //connect the arrayAdapter to the Spinner
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(dayAdapter.getPosition(rule.getDay()));

        //define the onClick behavior
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

    /**
     * Sets the Values and Behaviors of the Hour Spinner.
     */
    public void initializeHourSpinner() {
        //find the hour spinner
        hourSpinner = v.findViewById(R.id.hour_spinner);

        //define the values
        Integer[] hours_array = new Integer[24];
        for (int i = 0; i < hours_array.length; i++) {
            hours_array[i] = i;
        }
        List<Integer> hours = new ArrayList<>(Arrays.asList(hours_array));

        //insert the values into an arrayAdapter.
        ArrayAdapter<Integer> hourAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //connect the arrayAdapter to the Spinner
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setSelection(hourAdapter.getPosition(rule.getHour()));

        //define the onClick behavior
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

    /**
     * Sets the Values and Behaviors of the Minute Spinner.
     */
    public void initializeMinuteSpinner() {
        //find the minute spinner
        minuteSpinner = v.findViewById(R.id.minute_spinner);

        //define the values
        Integer[] minute_array = new Integer[4];
        for (int i = 0; i < minute_array.length; i++) {
            minute_array[i] = i * 15;
        }
        List<Integer> minutes = new ArrayList<>(Arrays.asList(minute_array));

        //insert the values into an arrayAdapter.
        ArrayAdapter<Integer> minuteAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, minutes);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //connect the arrayAdapter to the Spinner
        minuteSpinner.setAdapter(minuteAdapter);
        minuteSpinner.setSelection(minuteAdapter.getPosition(rule.getMinute()));

        //define the onClick behavior
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

    /**
     * Sets the Values and Behaviors of the Temperature Spinner.
     */
    public void initializeTempSpinner() {
        //find the temp spinner
        tempSpinner = v.findViewById(R.id.temp_spinner);

        //define the values
        Integer[] temp_array = new Integer[26];
        for (int i = 0; i < temp_array.length; i++) {
            temp_array[i] = i + 10;
        }
        List<Integer> temps = new ArrayList<>(Arrays.asList(temp_array));

        //insert the values into an arrayAdapter.
        ArrayAdapter<Integer> tempAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, temps);
        tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //connect the arrayAdapter to the Spinner
        tempSpinner.setAdapter(tempAdapter);
        tempSpinner.setSelection(tempAdapter.getPosition(rule.getTemp()));

        //define the onClick behavior
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
