/*
Jose M. Aguilar (jaguil61)
CS 478 - Project 1

MainActivity.java
*/

package edu.jaguil16.project1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String theName;
    private int theResultCode;

    protected static final String nameKey = "NAME_STRING"; // used for savedInstance
    protected  static final String resultCodeKey = "RESULT_KEY"; // used for savedInstance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // no previous information was saved
        if (savedInstanceState == null)
        {
            theName = null;
            theResultCode = RESULT_CANCELED;
        }

        // saved info found
        else
        {
            theName = savedInstanceState.getString(nameKey);
            theResultCode = savedInstanceState.getInt(resultCodeKey);
        }
    }

    // called when app loses focus
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(nameKey, theName);
        outState.putInt(resultCodeKey, theResultCode);
    }


    // starts the NameActivity
    public void startNameActivity(View v) {
        Intent intent = new Intent(MainActivity.this, NameActivity.class);
        startActivityForResult(intent, 66);
    }

    // retrieves the data that NameActivity returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);

        theResultCode = resultCode;
        theName = i.getStringExtra("theName");

        Log.i("Result Returned ", "ResultCode: " + resultCode);
        Log.i("Result Returned", "RequestCode: " + requestCode);
        Log.i("Result Returned", "The name returned was " + i.getStringExtra("theName"));
    }

    public void buttonTwoAction (View v)
    {
        if (theResultCode == RESULT_OK) // valid name
            openContacts();

        else // invalid name so display toast message
            toastMessage();
    }

    // displays toast if the name is incorrect
    private void toastMessage() {
        Toast.makeText(getApplicationContext(), "Sorry, " + theName + " is not a valid name.", Toast.LENGTH_SHORT).show();
    }


    // start contacts app
    private void openContacts() {

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, theName);
        startActivity(intent);
    }
}