/**
 * Minimal Notebook - A prototype notebook system.
 *
 * Mobile Applications Development - SET08114
 * 10/04/21
 *
 * Murray F Hastie
 * 40285583
 * Software Engineering (BENG) - Year 2
 */

package com.example.minimalnotebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View saveData = findViewById(R.id.main_btnNewNote);
        saveData.setOnClickListener(this);

        View checkData = findViewById(R.id.main_btnViewNotes);
        checkData.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.main_btnNewNote:
                Intent i1 = new Intent(this, ActivityAdd.class);
                startActivity(i1);
                break;
            case R.id.main_btnViewNotes:
                Intent i2 = new Intent(this, ActivityView.class);
                startActivity(i2);
                break;
        }
    }
}