package com.black.simpleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;


public class MainActivity extends AppCompatActivity {


    Toolbar mainToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        setContentView(R.layout.activity_main);

        //call the activity's setSupportActionBar() method, and pass the activity's toolbar.
        // This method sets the toolbar as the app bar for the activity.
        mainToolbar = (Toolbar) findViewById(R.id.Main_Toolbar);
        setSupportActionBar(mainToolbar);

        //finding interactable elements
        Button addButton = (Button) findViewById(R.id.addButton);
        // closing the app button
        Button closeApp = (Button) findViewById(R.id.closeApp);
        closeApp.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            finish();
                                            System.exit(0);
                                        }
                                    }
        );

        //add button functionality
        addButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                Intent intent = new Intent(MainActivity.this, addAnimalActivity.class);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                            }
                                        }
                );


            //@Override
            // public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            //    getMenuInflater().inflate(R.menu.menu_main, menu);
            //    return true;
            // }

            // @Override
            // public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            //    int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            //   if (id == R.id.action_settings) {
            //       return true;
            //   }

            //    return super.onOptionsItemSelected(item);
            //}


    }
}
