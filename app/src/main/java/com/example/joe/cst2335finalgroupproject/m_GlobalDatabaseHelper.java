package com.example.joe.cst2335finalgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This Class is used by all Activity in the App to access a shared, persistent database.
 */
public class m_GlobalDatabaseHelper extends SQLiteOpenHelper {

    //Database and Table Names
    static final String DATABASE_NAME = "GlobalDatabase.db";
    static final String C_TABLE_NAME = "C_TABLE_NAME";
    static final String T_TABLE_NAME = "THERMOSTAT_RULES";
    static final String N_TABLE_NAME = "NUTRITION_INFO";
    static final String A_TABLE_NAME = "ACTIVITY_LOG";
    static final String N_DROP_TABLE = "DROP TABLE IF EXISTS " + N_TABLE_NAME;
    //Car Database and Column Names
    //column names
    static final String C_KEY_ID = "_id";
    static final String C_KEY_PRICE = "price";
    static final String C_KEY_LITRES = "litres";
    static final String C_KEY_KILOMETERS = "kilometers";
    static final String C_KEY_DATE = "date";
    //SQL statements - select all, create table
    static final String C_SELECT_ALL_SQL
            = String.format("SELECT * FROM %s ORDER BY %s", C_TABLE_NAME, C_KEY_DATE);
    //Thermostat Database and Column Names
    //column names
    static final String T_RULE_ID = "RULE_ID";
    static final String T_RULE_COL_NAME = "RULE";
    //SQL statements - select all, create table
    static final String T_SELECT_ALL_SQL
            = String.format("SELECT * FROM %s", T_TABLE_NAME);
    //Nutrition Database and Column Names
    //column names
    static final String N_FOOD_ID = "FOOD_ID";
    static final String N_FOOD_ITEM_COL_NAME = "FOOD_NAME";
    static final String N_CALORIES_COL_NAME = "CALORIES";
    static final String N_CARB_COL_NAME = "CARBOHYDRATE";
    static final String N_FAT_COL_NAME = "FAT";
    static final String N_COMMENTS_COL_NAME = "COMMENTS";
    static final String N_FOOD_DATE_COL_NAME = "DATE";
    //SQL statements - select all, create table
    static final String N_SELECT_ALL_SQL
            = String.format("SELECT * FROM %s ORDER BY %s", N_TABLE_NAME, N_FOOD_ID);
    static final String CREATE_NUTRITION_TABLE_SQL
            = "CREATE TABLE " + N_TABLE_NAME + " ( "
            + N_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + N_FOOD_ITEM_COL_NAME + " TEXT, "
            + N_CALORIES_COL_NAME + " INTEGER, "
            + N_CARB_COL_NAME + " INTEGER, "
            + N_FAT_COL_NAME + " INTEGER, "
            + N_COMMENTS_COL_NAME + " TEXT, "
            + N_FOOD_DATE_COL_NAME + " TEXT "
            + " );";
    //Activity Database and Column Names
    //column names
    static final String A_WORKOUT_ID = "A_WORKOUT_ID";
    static final String A_TYPE_COL_NAME = "WORKOUT_TYPE";
    static final String A_DURATION_COL_NAME = "WORKOUT_DURATION";
    static final String A_NOTE_COL_NAME = "WORKOUT_NOTES";
    static final String A_TIME_COL_NAME = "TIMESTAMP";
    //Database Version Number
    private static final int DATABASE_VERSION_NUM = 1;  //WARNING: AVOID ALTERING THIS VALUE UNLESS REQUIRED. THIS WILL CLEAR ALL TABLES FOR ALL ACTIVITIES.
    //Drop Table Statements
    private static final String C_DROP_TABLE = "DROP TABLE IF EXISTS " + C_TABLE_NAME;
    private static final String T_DROP_TABLE = "DROP TABLE IF EXISTS " + T_TABLE_NAME;
    private static final String A_DROP_TABLE = "DROP TABLE IF EXISTS " + A_TABLE_NAME;
    private static final String CREATE_CAR_TABLE_SQL
            = "CREATE TABLE " + C_TABLE_NAME + " ( "
            + C_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_KEY_PRICE + " REAL, "
            + C_KEY_LITRES + " REAL, "
            + C_KEY_KILOMETERS + " REAL, "
            + C_KEY_DATE + " INTEGER"
            + " );";  // can convert to timestamp later
    private static final String CREATE_THERMOSTAT_TABLE_SQL
            = "CREATE TABLE " + T_TABLE_NAME + " ( "
            + T_RULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + T_RULE_COL_NAME + " TEXT "
            + " );";
    //SQL statements - create table
    private static final String CREATE_ACTIVITY_TABLE_SQL
            = "CREATE TABLE " + A_TABLE_NAME + " ( "
            + A_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + A_TYPE_COL_NAME + " INTEGER, "
            + A_DURATION_COL_NAME + " INTEGER, "
            + A_NOTE_COL_NAME + " INTEGER, "
            + A_TIME_COL_NAME + " INTEGER "//consider changing to real
            + " );";

    m_GlobalDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_THERMOSTAT_TABLE_SQL);
        db.execSQL(CREATE_NUTRITION_TABLE_SQL);
        db.execSQL(CREATE_CAR_TABLE_SQL);
        db.execSQL(CREATE_ACTIVITY_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(T_DROP_TABLE);
        db.execSQL(N_DROP_TABLE);
        db.execSQL(C_DROP_TABLE);
        db.execSQL(A_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(T_DROP_TABLE);
        db.execSQL(N_DROP_TABLE);
        db.execSQL(C_DROP_TABLE);
        db.execSQL(A_DROP_TABLE);
        onCreate(db);
    }
}
