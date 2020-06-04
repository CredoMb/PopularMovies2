package com.example.android.testforbooklistapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mSoldTextView;
    private EditText editTextDialog;
    private TextView mShippedTextView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSoldTextView = (TextView) findViewById(R.id.textView);
        editTextDialog = (EditText) findViewById(R.id.edit_text);
        mShippedTextView = (TextView) findViewById(R.id.textView_shipped);


        final AlertDialog.Builder mDialog = new AlertDialog.Builder(this);


        ((ViewGroup) editTextDialog.getParent()).removeView(editTextDialog);

        mSoldTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Set the title of the dialog
                // and place an editText inside
                mDialog.setTitle("The Sold Value");
                mDialog.setView(editTextDialog);
                editTextDialog.setVisibility(View.VISIBLE);

                        /*final int soldEditTextVal = !TextUtils.isEmpty(editTextDialog.getText()) ?
                                InventoryEntry.DEFAULT_SOLD_OR_SHIPPED
                                : Integer.parseInt(editTextDialog.getText().toString());*/

                // Set a listener for when the dialog will show.
                mDialog.setPositiveButton("ok",new DialogInterface.OnClickListener() {

                            // The following onClick works really well
                            // At least, the program get inside of it
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // If the sold value entered is greater than the shipped value,
                                // then don't close the dialog and display a Toast,
                                // so the user could correct his value

                                int soldEditTextVal = TextUtils.isEmpty(editTextDialog.getText()) ?
                                        0 : Integer.parseInt(editTextDialog.getText().toString());

                                // The if clause is working perfectly !
                                // When the soldEditText is superior, the code get inside the
                                // if statements

                                // The only problem is that the dialog closes anyway !!!
                                if (soldEditTextVal > Integer.parseInt(mShippedTextView.getText().toString())) {

                                    mSoldTextView.setText("73");
                                    Toast.makeText(MainActivity.this, "Value can't be greater than " + mSoldTextView.getText().toString(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    //Dismiss once the shipped value is greater
                                    // then the sold value.
                                    Log.e("Sold value",String.valueOf(soldEditTextVal));
                                    dialog.dismiss();
                                }
                                ((ViewGroup) editTextDialog.getParent()).removeView(editTextDialog);
                            }
                        });
                mDialog.show();
                    }
                });
            }
        }
