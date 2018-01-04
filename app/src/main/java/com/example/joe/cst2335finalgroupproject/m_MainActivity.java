package com.example.joe.cst2335finalgroupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * This Class serves as the Launcher/Home Screen of the App.
 */
public class m_MainActivity extends AppCompatActivity {

    /**
     * Inflates the main view and the Toolbar, and sets the behaviors of the
     * Buttons in the main portion of the screen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_activity_main);

        final Toolbar m_toolbar = findViewById(R.id.m_toolbar);
        setSupportActionBar(m_toolbar);

        setButtonListeners();
    }

    /**
     * Sets each button to launch its corresponding Activity.
     */
    private void setButtonListeners() {

        ImageView activityButton = findViewById(R.id.m_ivActivity);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(m_MainActivity.this, a_ActivityTrackerActivity.class));
            }
        });

        ImageView nutritionButton = findViewById(R.id.m_ivFood);
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(m_MainActivity.this, n_NutritionTrackerActivity.class));
            }
        });

        ImageView thermostatButton = findViewById(R.id.m_ivThermostat);
        thermostatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(m_MainActivity.this, t_ThermostatProgramActivity.class));
            }
        });

        ImageView automobileButton = findViewById(R.id.m_ivAutomobile);
        automobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(m_MainActivity.this, c_CarTrackerActivity.class));
            }
        });
    }

    /**
     * Inflates the Toolbar Menu.
     *
     * @param menu The Menu to be inflated.
     * @return Boolean true reflecting a successful inflation.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.m_menu, menu);
        return true;
    }

    /**
     * Sets the behavior of an OnClick event in the Toolbar.
     *
     * @param menuItem The MenuItem that was clicked.
     *
     * @return boolean true (dummy value).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.menu_exercise:
                startActivity(new Intent(m_MainActivity.this, a_ActivityTrackerActivity.class));
                break;

            case R.id.menu_food:
                startActivity(new Intent(m_MainActivity.this, n_NutritionTrackerActivity.class));
                break;

            case R.id.menu_thermostat:
                startActivity(new Intent(m_MainActivity.this, t_ThermostatProgramActivity.class));
                break;

            case R.id.menu_automobile:
                startActivity(new Intent(m_MainActivity.this, c_CarTrackerActivity.class));
                break;
        }
        return true;
    }
}