package com.example.ryan.calc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;


public class Extra extends ActionBarActivity {
    final String[] bStr = {"sin", "cos", "tan", "?", "ln", "log", "pi", "e", "%", "!", "sqrt", "^", "(", ")", "", ""};
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        // Get EditText
        et = (EditText)findViewById(R.id.et);

        // Generate buttons
        final Button[] bList = new Button[16];
        GridLayout bLayout = (GridLayout) findViewById(R.id.buttonLayout);
        int w = bLayout.getWidth() / 4,
                h = bLayout.getHeight() / 4;
        for (int i = 0; i < 16; i++) {
            bList[i] = new Button(this);
            bList[i].setText(bStr[i]);
//            bList[i].getLayoutParams().width = 200;
//            bList[i].getLayoutParams().height = 200;
            bLayout.addView(bList[i], 250, 250);
            bList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    et.append(b.getText());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_extra, menu);
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
        }else if (id == R.id.basic){
            Intent intent = new Intent(Extra.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
