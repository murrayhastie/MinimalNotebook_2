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

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Add a new note
 */
public class ActivityAdd extends AppCompatActivity implements View.OnClickListener
{
    DatabaseManipulator databaseManipulator = new DatabaseManipulator(this);

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        View saveButton = findViewById(R.id.add_btnSave);
        saveButton.setOnClickListener(this);

        View back = findViewById(R.id.add_btnBack);
        back.setOnClickListener(this);
    }

    /**
     * onClick method
     * @param v
     */
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_btnBack:
                this.finish();
                break;

            case R.id.add_btnSave:
                String title = ((EditText) findViewById(R.id.add_etTitle)).getText().toString();
                String content = ((EditText) findViewById(R.id.add_etContent)).getText().toString();

                // If title is empty, prompt toast
                if (title.length() != 0)
                {
                    AddData(title, content);
                    this.finish();
                }
                else
                {
                    toastMessage("Note must contain a title");
                }
                break;
        }
    }

    /**
     * Attempts to add data to the database
     * @param title
     * @param content
     */
    public void AddData(String title, String content)
    {
        // Call addData method from DatabaseManipulator
        boolean insertCheck = databaseManipulator.addData(title, content);

        // If returns true, prompt confirmation toast
        if (insertCheck)
        {
            toastMessage("Note saved.");
        }
        else
        {
            toastMessage("A conflict error occurred... Note was not saved.");
        }
    }

    /**
     * Custom Toast
     * @param message
     */
    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}