package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * This Activity handles the Fragments with the Phone is in a Portrait Orientation.
 * <p>
 * Author: Joe Ireland - 040 767 052
 */
public class t_DetailView extends Activity {

    /**
     * Sets the
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the view to the Layout that only contains an empty FrameLayout
        setContentView(R.layout.t_fragment_detail_view);

        //get the
        Bundle passedInfo = getIntent().getExtras();

        int mode = passedInfo.getInt("mode");

        if (mode == 1) {    //edit

            t_editRuleFragment fragment = new t_editRuleFragment();
            fragment.setArguments(passedInfo);
            getFragmentManager().beginTransaction()
                    .replace(R.id.t_frameLayout, fragment)
                    .commit();

        } else if (mode == 2) { //add

            t_addRuleFragment fragment = new t_addRuleFragment();
            fragment.setArguments(passedInfo);
            getFragmentManager().beginTransaction()
                    .replace(R.id.t_frameLayout, fragment)
                    .commit();

        } else {
            Log.i("Error", "You really should not be here.");
        }
    }
}
