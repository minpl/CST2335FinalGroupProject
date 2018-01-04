package com.example.joe.cst2335finalgroupproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Main Activity for Thermostat Rule Management Activity
 *
 * Author: Joe Ireland - 040 767 052
 */
public class t_ThermostatProgramActivity extends AppCompatActivity {

    //define static FragmentActivity Request Codes
    public static final int ADD_REQUEST_CODE = 10;
    public static final int EDIT_REQUEST_CODE = 20;

    //define static FragmentActivity Result Codes
    public static final int SAVE_AS_NEW_RESULT_CODE = 30;
    public static final int SAVE_RESULT_CODE = 40;
    public static final int DISCARD_RESULT_CODE = 50;

    //local ArrayList to handle the rules_list being read from the database and displayed on screen
    final ArrayList<String> rules_list = new ArrayList<>();

    //database references
    m_GlobalDatabaseHelper tdh;
    SQLiteDatabase db;
    Cursor c;

    //create handle to asyncTask object (database read)
    asyncRead aRead;

    //create handles to the objects on the screen
    ListView lv;
    Button addButton;
    TextView progressBarTextView;
    ProgressBar progressBar;
    RuleAdapter rulesAdapter;

    //boolean that reflects if the frame layout is loaded in THIS view - ie, a landscape orientation.
    boolean frameLayoutLoaded;

    //a handle to the currently loaded fragment
    private Fragment loadedFragment;

    /**
     * Connects the instance variable handles to the loaded View objects, and defines
     * button behaviors.
     *
     * @param savedInstanceState The previously loaded state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_activity_program_thermostat);

        //define insstance handles
        lv = findViewById(R.id.rulesView);

        tdh = new m_GlobalDatabaseHelper(this);
        db = tdh.getWritableDatabase();

        progressBarTextView = findViewById(R.id.t_currentTaskTextView);
        progressBar = findViewById(R.id.progressBar);
        addButton = findViewById(R.id.addButton);

        //set up toolbar
        Toolbar t_Toolbar = findViewById(R.id.t_toolbar);
        setSupportActionBar(t_Toolbar);
        getSupportActionBar().setTitle("Thermostat Programmer");

        //read the
        aRead = new asyncRead();
        aRead.execute();

        rulesAdapter = new RuleAdapter(this);
        lv.setAdapter(rulesAdapter);

        frameLayoutLoaded = findViewById(R.id.t_frameLayout) != null;

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle info = new Bundle();
                info.putInt("mode", 1);
                info.putString("rule", rules_list.get(position));
                info.putInt("listPosition", position);
                info.putLong("databaseID", id);

                if (frameLayoutLoaded) {
                    t_editRuleFragment editFragment = new t_editRuleFragment();
                    editFragment.setArguments(info);
                    loadedFragment = editFragment;
                    getFragmentManager().beginTransaction()
                            .replace(R.id.t_frameLayout, editFragment)
                            .commit();
                } else {
                    Intent detailsIntent = new Intent(t_ThermostatProgramActivity.this, t_DetailView.class);
                    detailsIntent.putExtras(info);
                    startActivityForResult(detailsIntent, EDIT_REQUEST_CODE);
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle info = new Bundle();
                info.putInt("mode", 2);
                info.putString("rule", "");
                info.putInt("listPosition", 0);

                if (frameLayoutLoaded) {
                    t_addRuleFragment addFragment = new t_addRuleFragment();
                    addFragment.setArguments(info);
                    loadedFragment = addFragment;
                    getFragmentManager().beginTransaction()
                            .replace(R.id.t_frameLayout, addFragment)
                            .commit();
                } else {
                    Intent detailsIntent = new Intent(t_ThermostatProgramActivity.this, t_DetailView.class);
                    detailsIntent.putExtras(info);
                    startActivityForResult(detailsIntent, ADD_REQUEST_CODE);
                }
            }
        });
    }

    /**
     * Inflates the Toolbar entries.
     *
     * @param menu The menu reference to be inflated into.
     * @return Boolean representation of successful inflation.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.t_menu, menu);
        return true;
    }

    /**
     * Handles interactions with Menu entries.
     * @param menuItem The menu entry that has been clicked on.
     * @return Boolean representation of successful method call.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.menu_exercise:
                startActivity(new Intent(t_ThermostatProgramActivity.this, a_ActivityTrackerActivity.class));
                break;

            case R.id.menu_food:
                startActivity(new Intent(t_ThermostatProgramActivity.this, n_NutritionTrackerActivity.class));
                break;

            case R.id.menu_car:
                startActivity(new Intent(t_ThermostatProgramActivity.this, c_CarTrackerActivity.class));
                break;

            case R.id.menu_home:
                startActivity(new Intent(t_ThermostatProgramActivity.this, m_MainActivity.class));
                break;

            case R.id.menu_help:
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.t_custom_alert_dialog, null);

                TextView helpMenuTextView = rootView.findViewById(R.id.t_helpMenuTextView);
                helpMenuTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                helpMenuTextView.setText(getResources().getText(R.string.t_helpMenuText));

                AlertDialog.Builder builder = new AlertDialog.Builder(t_ThermostatProgramActivity.this);
                builder.setView(rootView);
                builder.setPositiveButton(getResources().getString(R.string.t_helpDoneButtonText),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                );

                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
        return true;
    }

    /**
     * Closes Database and Database Cursor when the Activity is shut down.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        c.close();
        db.close();
    }

    /**
     * Handles updates to the Configuration (set to be changes to orientation) to redraw the
     * application when the user rotates the screen.
     * @param newConfig The Configuration that has been changed.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        startActivity(new Intent(this, t_ThermostatProgramActivity.class));

        //update & sort the entire list
        sortList();
        rulesAdapter.notifyDataSetChanged();
    }

    /**
     * Dispatches calls to methods to save/add/discard the results of the FragmentActivity that was launched.
     * @param requestCode The Initial Code used to launch the Activity.
     * @param resultCode The Result Code that the Activity set itself to finish with.
     * @param data The Intent containing the Bundle containing the rule to be saved/added.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != t_ThermostatProgramActivity.DISCARD_RESULT_CODE && data != null) {

            Bundle resultBundle = data.getExtras();

            if (requestCode == ADD_REQUEST_CODE && resultCode == SAVE_RESULT_CODE) {
                saveRuleAsNew(resultBundle);
            } else if (requestCode == EDIT_REQUEST_CODE) {
                if (resultCode == SAVE_AS_NEW_RESULT_CODE) {
                    saveRuleAsNew(resultBundle);
                } else if (resultCode == SAVE_RESULT_CODE) {
                    saveRuleOverwrite(resultBundle);
                }
            }
        } else {
            discardMethod();
        }
    }

    /**
     * Displays a Snackbar notification that the rule modification has been discarded.
     */
    void discardMethod() {
        Snackbar.make(findViewById(R.id.t_toolbar),
                "Rule Discarded",
                Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Saves a Rule contained in resultBundle to the ArrayList and Database in the position
     * previously occupied by the rule that was changed.
     *
     * @param resultBundle The intent bundle resulting from the Add/Edit fragment.
     */
    void saveRuleOverwrite(Bundle resultBundle) {

        //delete the old rule
        deleteRule(resultBundle.getLong("deleteID"), resultBundle.getInt("deletePosition"));

        //grab the new rule out of the resultBundle
        String rule = resultBundle.getString("ruleToAdd");

        //add new rule to the list
        rules_list.add(rule);

        //add new rule to the database
        ContentValues newRule = new ContentValues();
        newRule.put(m_GlobalDatabaseHelper.T_RULE_COL_NAME, rule);
        db.insert(m_GlobalDatabaseHelper.T_TABLE_NAME, null, newRule);

        //update & sort the entire list
        sortList();
        rulesAdapter.notifyDataSetChanged();
    }

    /**
     * Adds a Rule contained in resultBundle to the ArrayList and Database as a new Rule.
     *
     * @param resultBundle The intent bundle resulting from the Add/Edit fragment.
     */
    void saveRuleAsNew(Bundle resultBundle) {

        String rule = resultBundle.getString("ruleToAdd");

        rules_list.add(rule);
        ContentValues newRule = new ContentValues();
        newRule.put(m_GlobalDatabaseHelper.T_RULE_COL_NAME, rule);

        db.insert(m_GlobalDatabaseHelper.T_TABLE_NAME, null, newRule);

        //update & sort the entire list
        sortList();
        rulesAdapter.notifyDataSetChanged();
    }

    /**
     * Deletes the Rule at ArrayList Position position and with Database ID... id.
     * @param id The Database ID (shocker)
     * @param position The ArrayList Position.
     */
    private void deleteRule(long id, int position) {

        //remove fragment (edit / add details) if it exists
        //to prevent conflicts
        if (loadedFragment != null) {
            getFragmentManager().beginTransaction().remove(loadedFragment).commit();
        }

        //remove the arraylist entry
        rules_list.remove(position);

        //remove the database entry
        db.delete(m_GlobalDatabaseHelper.T_TABLE_NAME,
                m_GlobalDatabaseHelper.T_RULE_ID + " = ? ",
                new String[]{String.valueOf(id)});

        //notify the List Adapter and the User of the removal.
        rulesAdapter.notifyDataSetChanged();    //do NOT need to sort in the case of a deletion
        Snackbar.make(findViewById(R.id.t_toolbar),
                "Rule Deleted",
                Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Defines a Collection Sort method, then applies the sort to the ArrayList.
     * <p>
     * Called after an insertion/edit/removal.
     */
    private void sortList() {

        //https://stackoverflow.com/questions/43429998/how-to-sort-strings-containing-day-of-week-names-in-ascending-order
        Comparator<String> dayOfWeekComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.CANADA);
                    Date d1 = format.parse(s1);
                    Date d2 = format.parse(s2);
                    if (d1.equals(d2)) {
                        return 0;
                    } else {
                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal1.setTime(d1);
                        cal2.setTime(d2);
                        return cal1.get(Calendar.DAY_OF_WEEK) - cal2.get(Calendar.DAY_OF_WEEK);
                    }
                } catch (ParseException pe) {
                    throw new RuntimeException(pe);
                }
            }
        };

        Collections.sort(rules_list, dayOfWeekComparator);
    }

    /**
     * Defines a custom ArrayAdapter to populate the ListView.
     */
    class RuleAdapter extends ArrayAdapter<String> {

        RuleAdapter(Context ctx) {
            super(ctx, 0);
        }

        /**
         * Returns the Database ID of the element ar ArrayList[Position]
         * @param position The position of the ArrayList entry in question.
         * @return THE DATABASE ID GOD I JUST TOLD YOU.
         */
        @Override
        public long getItemId(int position) {
            c = db.rawQuery(m_GlobalDatabaseHelper.T_SELECT_ALL_SQL, null);
            c.moveToPosition(position);
            return c.getLong(c.getColumnIndex(m_GlobalDatabaseHelper.T_RULE_ID));
        }

        /**
         * Returns the number of elements in the ArrayList.
         * @return the number of elements in the ArrayList.
         */
        public int getCount() {
            return rules_list.size();
        }

        /**
         * Returns the Rule at the parameter position in the ArrayList.
         * @param position The position in question.
         * @return The Rule.
         */
        public String getItem(int position) {
            return rules_list.get(position);
        }

        /**
         * Inflates each row into the ListView on screen.
         * @param position The Position being inflated.
         * @param recycled The incoming View to be inflated.  May be recycled.
         * @param parent The ViewGroup, i.e., the ArrayAdapter/ListView.
         * @return The inflated view.
         */
        @NonNull
        @Override
        public View getView(final int position, View recycled, @NonNull ViewGroup parent) {

            View view = recycled;

            if (view == null) {
                LayoutInflater inflater = t_ThermostatProgramActivity.this.getLayoutInflater();
                view = inflater.inflate(R.layout.t_rule_row, parent, false);
            }

            TextView ruleView = view.findViewById(R.id.ruleTextView);
            ruleView.setText(getItem(position));

            RelativeLayout selected = view.findViewById(R.id.t_deleteRuleRowButton);
            selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long id = rulesAdapter.getItemId(position);
                    deleteRule(id, position);
                }
            });

            return view;
        }
    }

    /**
     * Defines a custom AsyncTask to handle Database Reads.
     */
    private class asyncRead extends AsyncTask<String, Integer, String> {

        /**
         * @param args Not used.
         * @return A confirmation message of a successful read sent to onPostExecute().
         */
        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(String... args) {

            progressBarTextView.setText("Reading Database:");
            progressBar.setVisibility(View.VISIBLE);

            //sends fake progress percentage data to the progress bar update method.
            for (int i = 0; i < 50; i++) {
                publishProgress(i*2);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Reads the database into the ArrayList
            c = db.rawQuery(m_GlobalDatabaseHelper.T_SELECT_ALL_SQL, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String rule_text = c.getString(c.getColumnIndex(m_GlobalDatabaseHelper.T_RULE_COL_NAME));
                rules_list.add(rule_text);
                c.moveToNext();
            }

            //update & sort the entire list
            sortList();

            return "Database Read Complete!";
        }

        /**
         * Sends progress values to the ProgressBar.
         * @param values the percentage progress the database read is currently at.
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        /**
         * Finishes the database read by clearing the progress bar and progress bar label, and
         * notifies the user via a toast.
         * @param result A Message that the Database Read completed.
         */
        @Override
        protected void onPostExecute(String result) {
            progressBarTextView.setText("");
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(t_ThermostatProgramActivity.this, result, Toast.LENGTH_LONG)
                    .show();

        }
    }
}