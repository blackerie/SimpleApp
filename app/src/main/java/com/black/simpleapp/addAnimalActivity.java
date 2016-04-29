package com.black.simpleapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.black.simpleapp.OcrScannerListener;

public class addAnimalActivity extends AppCompatActivity implements OcrScannerListener {

    //declaring all interacting elements
    EditText animalId;
    EditText fatherId;
    EditText motherId;
    Spinner genderAnimal;
    Spinner breedAnimal;
    Button saveButton;
    TextView debugDB;
    Button scanAnimalId;
    Button scanFatherId;
    Button  scanMotherId;
    OCRscanner mOCRscanner;
    //OCRscanner mOCRscannerGallery;
    ProgressDialog mProgressDialog;


    //declaring variables used for the DataBase request
    String getAnimalIdString;
    String getBreedString;
    String getGenderString;
    String getFatherIdString;
    String getMotherIdString;

    Toolbar addAnimalToolbar;

    //initiating the Database Handler class
    DatabaseHandler db;

    // initialize EasyOcrScanner instance.


    //overrides the back button only in the current activity so that
    // the propper animation is played on return to the the main activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //if any animations concerning the whole activity screen are set they are set here before setContentView
        setContentView(R.layout.activity_add_animal);

        //call the activity's setSupportActionBar() method, and pass the activity's toolbar.
        // This method sets the toolbar as the app bar for the activity.
        addAnimalToolbar = (Toolbar) findViewById(R.id.add_Animal_Toolbar);
        setSupportActionBar(addAnimalToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Get a support ActionBar corresponding to this toolbar
        ab.setDisplayHomeAsUpEnabled(true);

        // initialize OcrScanner instance.
        mOCRscanner = new OCRscanner(addAnimalActivity.this, "OCRscanner",
                Config.REQUEST_CODE_CAPTURE_IMAGE, "eng");

        mOCRscanner = new OCRscanner(addAnimalActivity.this, "OCRscanner",
                Config.REQUEST_CODE_FROM_GALLERY, "eng");

        // Set ocrScannerListener
        mOCRscanner.setOcrScannerListener(addAnimalActivity.this);



        db = new DatabaseHandler(getApplicationContext());

        //fiinding all interacting elements
        animalId = (EditText) findViewById(R.id.animalId);
        fatherId = (EditText) findViewById(R.id.fatherId);
        motherId = (EditText) findViewById(R.id.motherId);

        genderAnimal = (Spinner) findViewById(R.id.genderAnimal);
        breedAnimal = (Spinner) findViewById(R.id.breedAnimal);
        //find my debug field
        //debugDB = (TextView) findViewById(R.id.debugDB);


        saveButton = (Button) findViewById(R.id.saveButton);
        scanAnimalId = (Button) findViewById(R.id.scanAnimalId);
        scanFatherId = (Button) findViewById(R.id.scanFatherId);
        scanMotherId = (Button) findViewById(R.id.scanMotherId);


        // Spinner Drop down elements
        List<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        genderAnimal.setAdapter(dataAdapter);

        // Spinner click listener
        genderAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                       // On selecting a spinner item
                                                       String item = parent.getItemAtPosition(position).toString();
                                                       // Showing selected spinner item
                                                       //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                                                   }

                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {

                                                   }
                                               }
        );

        // Spinner Drop down elements
        final List<String> breedList = new ArrayList<String>();
        breedList.add("Karakachanka");


        // Creating adapter for spinner
        ArrayAdapter<String> breedSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, breedList);

        // Drop down layout style - list view with radio button
        breedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        breedAnimal.setAdapter(breedSpinnerAdapter);

        // Spinner click listener
        breedAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                      // On selecting a spinner item
                                                      String item = parent.getItemAtPosition(position).toString();
                                                      // Showing selected spinner item
                                                      //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                                                  }

                                                  @Override
                                                  public void onNothingSelected(AdapterView<?> parent) {

                                                  }
                                              }
        );
        //adding the data from the fields to the database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the string from the fields
                getAnimalIdString = animalId.getText().toString();
                getBreedString = breedAnimal.getSelectedItem().toString();
                getGenderString = genderAnimal.getSelectedItem().toString();
                getFatherIdString = fatherId.getText().toString();
                getMotherIdString = motherId.getText().toString();

                //invoking the add animal method for the db
                db.addAnimal(new Animal(getAnimalIdString, getBreedString, getGenderString, getFatherIdString, getMotherIdString));

                //clearing the fields after creating a DB entry

                animalId.setText("");
                fatherId.setText("");
                motherId.setText("");
                genderAnimal.setSelection(0);


                //LOGCAT DEBUG
                //Reading all animals
                Log.d("Reading: ", "Reading all animals..");
                List<Animal> animals = db.getAllAnimals();
                //clear the debug field of any prevous text
                //debugDB.setText("");

                for (Animal anim : animals) {
                    String log = "Id: " + anim.getID() + " ,Breed: " + anim.getBreed() + " ,Gender: " + anim.getGender()
                            + " , Father's ID: " + anim.getFatherId() + " , Mother's ID: " + anim.getMotherId();
                    //Writing Animals to log
                    Log.d("ID: ", log);
                    //set the log in the debug text
                    //debugDB.setText(log);
                }

            }

        });
        //the button that will launch the camera to scan an ID
        scanAnimalId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOCRscanner.takePicture();
            }

        });

        //this button requests gallery to choose a picture
        scanFatherId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOCRscanner.selectFromGallery();
            }

        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call onImageTaken() in onActivityResult.
        if (resultCode == RESULT_OK && requestCode == Config.REQUEST_CODE_CAPTURE_IMAGE){
            mOCRscanner.onImageTaken();
        }
        else if (resultCode == RESULT_OK && requestCode == Config.REQUEST_CODE_FROM_GALLERY){
            mOCRscanner.onSelectedImage();
        }
    }

    /**
     * Callback when after taking picture, scanning process starts.
     * Good place to show a progress dialog.
     * @param filePath file path of the image file being processed.
     */
    @Override
    public void onOcrScanStarted(String filePath) {
        mProgressDialog = new ProgressDialog(addAnimalActivity.this);
        mProgressDialog.setMessage("Scanning...");
        mProgressDialog.show();
    }

    /**
     * Callback when scanning is finished.
     * Good place to hide teh progress dialog.
     * @param bitmap Bitmap of image that was scanned.
     * @param recognizedText Scanned text.
     */
    @Override
    public void onOcrScanFinished(Bitmap bitmap, String recognizedText) {
        animalId.setText(recognizedText);
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }


    //overrides the toolbar up button to display the proper animation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return false;
    }
    //Intent intent = getIntent();
   // public void addAnimalActivity(View view) {

    //}
}
