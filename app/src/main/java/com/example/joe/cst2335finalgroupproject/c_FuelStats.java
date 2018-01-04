package com.example.joe.cst2335finalgroupproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object used to encapsulate the total gas purchase of a previous month and year
 * Parcelable methods generated by Android Parcelable code generator
 * References:
 * Mandel(2011). Help passing an ArrayList of Objects to a new Activity [WebPage] Retrieved from:
 *  https://stackoverflow.com/questions/6681217/help-passing-an-arraylist-of-objects-to-a-new-activity
 * @author Nathan Doef
 * @version 2
 */
public class c_FuelStats implements Parcelable {

    public static final Creator<c_FuelStats> CREATOR = new Creator<c_FuelStats>() {
        @Override
        public c_FuelStats createFromParcel(Parcel source) {
            return new c_FuelStats(source);
        }

        @Override
        public c_FuelStats[] newArray(int size) {
            return new c_FuelStats[size];
        }
    };
    /**
     * Month and year of the statistic (i.e. January 2018)
     */
    private String monthYear;
    /** Total purchases for the month */
    private double totalPurchases;

    /**
     * Constructor
     * @param monthYear
     * @param totalPurchases
     */
    public c_FuelStats(String monthYear, double totalPurchases) {
        this.monthYear = monthYear;
        this.totalPurchases = totalPurchases;
    }

    protected c_FuelStats(Parcel in) {
        this.monthYear = in.readString();
        this.totalPurchases = in.readDouble();
    }

    // ** Getters and setters */
    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public Double getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(Double totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    /*** AUTO-GENERATED PARCEABLE METHODS ***/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.monthYear);
        dest.writeDouble(this.totalPurchases);
    }
}