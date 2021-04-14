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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ActivityEdit class
 * Receives data from ActivityView class
 * Populates input fields with received data
 * Edit a stored note
 * Delete a stored note
 */
public class ActivityEdit extends AppCompatActivity
{
    private static final String TAG = "ActivityEdit";
    // Initialise database
    DatabaseManipulator databaseManipulator;

    // Initialise interface
    private Button btnSave;
    private Button btnDelete;
    private EditText editTitle;
    private EditText editContent;

    // Initialise global variables
    private int ID;
    private String Title;
    private String Content;

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Link variables to interface
        btnSave = (Button) findViewById(R.id.add_btnSave);
        btnDelete = (Button) findViewById(R.id.edit_btnDelete);
        editTitle= (EditText) findViewById(R.id.add_etTitle);
        editContent = (EditText) findViewById(R.id.add_etContent);

        databaseManipulator = new DatabaseManipulator(this);

        // Get the relevant intent from ActivityView
        Intent data = getIntent();

        // Get the data which was passed as Extra
        ID = data.getIntExtra("id",-1);
        Title = data.getStringExtra("title");
        Content = data.getStringExtra("content");

        // Set text to show current selected title
        editTitle.setText(Title);
        editContent.setText(Content);

        // Save note functionality
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Tries to save input fields
                String newTitle = editTitle.getText().toString();
                String newContent = editContent.getText().toString();


                // Abort if title field is empty
                if(!newTitle.equals(""))
                {
                    // Call database update methods
                    databaseManipulator.updateTitle(ID, Title, newTitle);
                    databaseManipulator.updateContent(ID, Content, newContent);
                    toastMessage("Note Saved");
                    finish();
                }
                else
                {
                    toastMessage("You must enter a title");
                }
            }
        });

        // Delete note functionality
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Call confirmation dialogue
                confirmDelete();
            }
        });
    }

    /**
     * Logic for AlertDialogue to confirm deletion of note
     */
    protected void confirmDelete()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(ActivityEdit.this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            builder = new AlertDialog.Builder(ActivityEdit.this);
        }
        builder.setMessage(R.string.delete_dialog_message);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.delete_dialog_confirm_no,
                new DialogInterface.OnClickListener()
                {
                    // Cancel deletion process
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
        builder.setPositiveButton(R.string.delete_dialog_confirm_yes,
                new DialogInterface.OnClickListener()
                {
                    // Confirm deletion process
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Call database method to delete note
                        databaseManipulator.deleteNote(ID, Title);
                        toastMessage("Note deleted");
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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