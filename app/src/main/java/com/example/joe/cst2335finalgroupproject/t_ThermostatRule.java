package com.example.joe.cst2335finalgroupproject;

import android.annotation.SuppressLint;

/**
 * Holds the fields and methods of a given Rule
 *
 * Author: Joe Ireland - 040 767 052
 */
public class t_ThermostatRule {

    //private variables for the content of the rule.
    private String day;
    private int hour;
    private int minute;
    private int temp;

    /**
     * No-Arg Constructor.  Returns an "Empty" Rule.
     */
    t_ThermostatRule() {
        this("Sunday", 0, 0, 15);
    }

    /**
     * Constructor.  Returns a complete Rule with all fields defined by parameters.
     *
     * @param day    Name of Day of Week
     * @param hour   Hour of Day
     * @param minute Minute of Hour
     * @param temp   Temperature to be set
     */
    private t_ThermostatRule(String day, int hour, int minute, int temp) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.temp = temp;
    }

    /**
     * Parses a String representation of a Rule into an actual t_ThermostatRule object.
     *
     * @param rawString The Raw String representation of the Rule
     * @return The Object representation of the Rule.
     */
    static t_ThermostatRule valueOf(String rawString) {
        //Format is "Monday 9:00 Temp -> 16"

        //split rawString at each space
        String[] ruleSplit = rawString.split(" ");

        //The Day is the first token
        String day = ruleSplit[0];

        //The Time is the second token, but the token is 'HH:MM'
        String time = ruleSplit[1];

        //split the 'HH:MM" String token at ':'
        String[] timeSplit = time.split(":");

        //Hour and Minute are the first and second tokens.
        int hour = Integer.valueOf(timeSplit[0]);
        int minute = Integer.valueOf(timeSplit[1]);

        //The Temperature is the last token.  "Temp" and "->" represent
        // the tokens at indexes 2 and 3
        int temp = Integer.valueOf(ruleSplit[4]);

        //return a completely built rule
        return new t_ThermostatRule(day, hour, minute, temp);
    }

    String getDay() {
        return day;
    }

    void setDay(String day) {
        this.day = day;
    }

    int getHour() {
        return hour;
    }

    void setHour(int hour) {
        this.hour = hour;
    }

    int getMinute() {
        return minute;
    }

    void setMinute(int minute) {
        this.minute = minute;
    }

    int getTemp() {
        return temp;
    }

    void setTemp(int temp) {
        this.temp = temp;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return day + " " + String.format("%02d", hour) + ":" + String.format("%02d", minute) + " Temp -> " + temp;
    }
}
