package com.example.ryan.piggy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private FrameLayout die1, die2;
    private Button roll, hold;
    private int score, total, current, val1, val2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        roll = (Button) findViewById(R.id.button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
                if (val1 == 1 || val2 == 1){

                    Intent intent = new Intent(MainActivity.this,Player2.class);
                    intent.putExtra("score1", total);
                    intent.putExtra("score2", score);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }else{
                    current += val1 + val2;
                    TextView t = (TextView)findViewById(R.id.currentTextView);
                    t.setText(String.valueOf(current));
                    if ((current + total) >= 100) {     // You won
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("You win!");
                        alertDialog.setMessage("Total score: " + (current + total));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(MainActivity.this, Player2.class);

                                        // Reset the game
                                        intent.putExtra("score2", 0);
                                        intent.putExtra("score1", 0);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });

        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*The commented out code here was the code we used
                in class to send an integer to the next activty.
                It was replaced by an alert dialog to be used to indicate
                a winner (for demonstration purposes).
                Use the alert dialog code in your program where appropriate
*/
                total += current;

                Intent intent = new Intent(MainActivity.this,Player2.class);
                intent.putExtra("score1", total);
                intent.putExtra("score2", score);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


        die1 = (FrameLayout) findViewById(R.id.die1);
        die2 = (FrameLayout) findViewById(R.id.die2);

    }

    @Override
    protected  void onResume(){
        super.onResume();
        Intent intent = getIntent();
        score = intent.getIntExtra("score2", 0);
        total = intent.getIntExtra("score1", 0);
        //Toast.makeText(this, "The score is: " + score, Toast.LENGTH_LONG).show();
        TextView t = (TextView)findViewById(R.id.p2);
        t.setText("P2: " + score);
        t = (TextView)findViewById(R.id.p1);
        t.setText("P1: " + total);
    }


    //get two random ints between 1 and 6 inclusive
    public void rollDice() {
        val1 = 1 + (int) (6 * Math.random());
        val2 = 1 + (int) (6 * Math.random());
        setDie(val1, die1);
        setDie(val2, die2);

    }

    //set the appropriate picture for each die per int
    public void setDie(int value, FrameLayout layout) {
        Drawable pic = null;

        switch (value) {
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }
        layout.setBackground(pic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
/*
* AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
alertDialog.setTitle("Alert");
alertDialog.setMessage("Alert message to be shown");
alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
    new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
alertDialog.show();*/