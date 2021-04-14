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
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *  Displays a ViewList of all stored notes
 */
public class ActivityView extends AppCompatActivity
{
    // Initialise DatabaseManipulator class
    DatabaseManipulator databaseManipulator;
    private static final String TAG = "ActivityView";
    private ListView listView;

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Initialise Database
        listView = (ListView) findViewById(R.id.list);
        databaseManipulator = new DatabaseManipulator(this);
        populateListView();
    }

    /**
     * Extract data from database and append each row to ListView
     * Then enables onItemClickListener functionality for each element in the list
     */
    private void populateListView()
    {
        Log.d(TAG, "populateListView: Extracting data from database...");

        Cursor data = databaseManipulator.getData();
        ArrayList<String> listData = new ArrayList<>();

        // Iterate through each row in the database
        while(data.moveToNext())
        {
            listData.add(data.getString(1 ));
        }
        Log.d(TAG, "Database received successfully");

        // Create and set ListView to ListAdapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

        // Applies an onItemClickListener to ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get title of selected list element, from adapterView
                String title = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: Selected note: " + title);

                // Get Cursor of the ID associated with given title
                Cursor cursor1 = databaseManipulator.getID(title);
                int id = 0;
                // Convert Cursor to int
                while(cursor1.moveToNext())
                {
                    id = cursor1.getInt(0);
                }

                // Get Cursor of the content associated given with title
                Cursor cursor2 = databaseManipulator.getContent(title);
                // Convert Cursor to String
                cursor2.moveToFirst();
                String content = cursor2.getString(cursor2.getColumnIndex("content"));

                // Package data as Extra and send it to ActivityEdit class
                Intent editScreenIntent = new Intent(ActivityView.this, ActivityEdit.class);
                editScreenIntent.putExtra("id", id);
                editScreenIntent.putExtra("title", title);
                editScreenIntent.putExtra("content", content);
                // Initialise activity: ActivityEdit
                startActivity(editScreenIntent);
            }
        });
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