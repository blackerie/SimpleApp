package com.black.simpleapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by blackerie on 6.4.2016 г..
 */
public class DatabaseHandler extends SQLiteOpenHelper{

        // All Static variables
        // Database Version
        private static final int DATABASE_VERSION = 2;

        // Database Name
        private static final String DATABASE_NAME = "Farm";

        // Animals table name
        private static final String TABLE_HERD = "Herd";

        // Animals Table Columns names
        private static final String KEY_AnimalId = "AnimalId";
        private static final String KEY_BREED = "Breed";
        private static final String KEY_GENDER = "Gender";
        private static final String KEY_FatherId = "FatherId";
        private static final String KEY_MotherId = "MotherId";



        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            //String CREATE_HERD_TABLE ="CREATE TABLE Herd(id INTEGER PRIMARY KEY, Breed TEXT, Name TEXT, Father TEXT, Mother TEXT)";

            String CREATE_HERD_TABLE = "CREATE TABLE " + TABLE_HERD + "("
                   + KEY_AnimalId + " TEXT PRIMARY KEY," + KEY_BREED + " TEXT," + KEY_GENDER + " TEXT,"
                     + KEY_FatherId + " TEXT," + KEY_MotherId + " TEXT"  + ")";
            db.execSQL(CREATE_HERD_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_HERD);

            // Create tables again
            onCreate(db);
        }

        /**
         * All CRUD(Create, Read, Update, Delete) Operations
         */

        // Adding new animal
        void addAnimal(Animal animal) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_AnimalId, animal.getID()); // Animal ID
            values.put(KEY_BREED, animal.getBreed()); // Animal Breed
            values.put(KEY_GENDER, animal.getGender()); // Animal gender
            values.put(KEY_FatherId, animal.getFatherId()); // Animal Father's ID
            values.put(KEY_MotherId, animal.getMotherId()); // Animal Mother's ID

            // Inserting Row
            db.insert(TABLE_HERD, null, values);
            db.close(); // Closing database connection
        }

        // Getting single contact
        Animal getAnimal (String animal_id) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_HERD, new String[]{KEY_AnimalId,
                            KEY_BREED, KEY_GENDER, KEY_FatherId, KEY_MotherId}, KEY_AnimalId + "=?",
                    new String[]{String.valueOf(animal_id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Animal animal = new Animal(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            cursor.close();
            // return animal
            return animal;
        }

        // Getting All Animals
        public List<Animal> getAllAnimals() {
            List<Animal> animalList = new ArrayList<Animal>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_HERD;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Animal animal = new Animal();
                    animal.setID(cursor.getString(0));
                    animal.setBreed(cursor.getString(1));
                    animal.setGender(cursor.getString(2));
                    animal.setFatherId(cursor.getString(3));
                    animal.setMotherId(cursor.getString(4));
                    // Adding animal to list
                    animalList.add(animal);
                } while (cursor.moveToNext());
            }
            cursor.close();
            // return animal list
            return animalList;

        }

        // Updating single animal
        public int updateAnimal(Animal animal) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_AnimalId, animal.getID());
            values.put(KEY_BREED, animal.getBreed());
            values.put(KEY_GENDER, animal.getBreed());
            values.put(KEY_FatherId, animal.getFatherId());
            values.put(KEY_MotherId, animal.getMotherId());


            // updating row
            return db.update(TABLE_HERD, values, KEY_AnimalId + " = ?",
                    new String[]{String.valueOf(animal.getID())});
        }

        // Deleting single animal
        public void deleteAnimal(Animal animal) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_HERD, KEY_AnimalId + " = ?",
                    new String[]{String.valueOf(animal.getID())});
            db.close();
        }


        // Getting animals Count
        public int getAnimalsCount() {
            String countQuery = "SELECT  * FROM " + TABLE_HERD;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }

    }


