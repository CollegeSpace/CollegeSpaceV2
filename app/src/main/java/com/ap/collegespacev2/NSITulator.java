package com.ap.collegespacev2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class NSITulator  extends BaseActivity
{
    Button btn_done;
    Spinner spin_branch, spin_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsitulator, 4);

        getSupportActionBar().setTitle(getString(R.string.nsitulatorTitle));

        btn_done = (Button)findViewById(R.id.nsitulator_proceed);
        spin_branch = (Spinner)findViewById(R.id.spin_branch);
        spin_semester = (Spinner)findViewById(R.id.spin_semester);

        btn_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent marks_calc = new Intent(NSITulator.this, Calculator.class);
                marks_calc.putExtra("nsitulator_branch", (String)spin_branch.getSelectedItem());
                marks_calc.putExtra("nsitulator_semester", (String)spin_semester.getSelectedItem());
                startActivity(marks_calc);
            }
        });
    }
}
