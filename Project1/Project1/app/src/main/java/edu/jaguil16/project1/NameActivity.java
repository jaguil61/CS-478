/*
Jose M. Aguilar (jaguil61)
CS 478 - Project 1

NameActivity.java
*/
package edu.jaguil16.project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NameActivity extends AppCompatActivity {
    private EditText textField;
    private String name = null;

    protected static final String nameKey = "NAME_STRING"; // used for savedInstance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        // bind interface EditText
        textField = (EditText) findViewById(R.id.editText1); // bind EditText to textField

        // attach listener to it (defined below)
        textField.setOnEditorActionListener(textFieldListener);

        // no previous information was saved
        if (savedInstanceState == null) {
            name = null;
        }

        // saved info found
        else {
            name = savedInstanceState.getString(nameKey);
        }
    }

    // called when app loses focus
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(nameKey, name);
    }

    /*
    grabs the text/name that was entered in teh EditText field after pressing "done"/"return" on the
    the keyboard.
    will also return the text/name to the main activity
    */
    public TextView.OnEditorActionListener textFieldListener = (v, actionId, event) -> {
        if(actionId == EditorInfo.IME_ACTION_DONE)
        {
            name = textField.getText().toString();

            Log.i("Editor Listener", "Returning True");
            Log.i("Editor Listener", "The name was " + name);

            Intent intent = new Intent();
            intent.putExtra("theName", name); //add the name to the intent
            setResult(validateName(), intent);
            finish();

            return true;
        }

        Log.i("Editor Listener", "Returning False");

        return true;
    };

    /*
    function that validates name
    Returns RESULT_OK or RESULT_CANCELED
    */
    private int validateName()
    {
        // the pattern that is trying to be matched (only letters and spaces)
        String regex = "[a-zA-Z\\s]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(name);

        if (name == null) { // name is empty
            return RESULT_CANCELED;
        }

        else if(match.matches()) { // valid name
            Log.i("Regex", "Name was valid");
            return RESULT_OK;
        }

        //else invalid name
        Log.i("Regex", "Name was not valid");
        return RESULT_CANCELED;
    }

}
