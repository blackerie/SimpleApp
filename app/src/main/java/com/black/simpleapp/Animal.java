package com.black.simpleapp;

/**
 * Created by blackerie on 6.4.2016 г..
 */
public class Animal {

    //private variables
    String _animal_id;
    String _breed;
    String _gender;
    String _father_id;
    String _mother_id;


    // Empty constructor
    public Animal(){

    }
    // constructor
    public Animal(String animal_id, String breed, String gender, String father_id, String mother_id){
        this._animal_id = animal_id;
        this._breed = breed;
        this._gender = gender;
        this._father_id = father_id;
        this._mother_id = mother_id;

    }

     //constructor
    public Animal(String animal_id, String breed, String gender){
        this._animal_id = animal_id;
        this._breed = breed;
        this._gender = gender;

    }
    // getting ID
    public String getID(){
        return this._animal_id;
    }

    // setting id
    public void setID(String animal_id){
        this._animal_id = animal_id;
    }

    // getting gender
    public String getGender(){
       return this._gender;
    }

    // setting gender
    public void setGender(String gender){
        this._gender = gender;
    }
    // getting breed
    public String getBreed(){
        return this._breed;
    }

    // setting breed
    public void setBreed(String breed) {
        this._breed = breed;
    }
    // getting father's id
    public String getFatherId(){
        return this._father_id;
    }

    // setting father's id
    public void setFatherId(String fatherId) {
        this._father_id = fatherId;
    }
    // getting mother's id
    public String getMotherId(){
        return this._mother_id;
    }

    // setting mother's id
    public void setMotherId(String motherId) {
        this._mother_id = motherId;
    }
}
