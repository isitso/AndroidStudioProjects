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

public class MainActivity extends ActionBarActivity {

    final String[] bStr = {"7", "8", "9", "/", "4", "5", "6", "x", "1", "2", "3", "-", ".", "0", "=", "+"};
    EditText et;
    Expr expr;
    double num1, num2;
    boolean gotNum, gotOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Expression
        expr = new Expr();
    }

    // Handle buttons
    public void bHandler(String s){

        if (s.equalsIgnoreCase("0") || s.equalsIgnoreCase("1") || s.equalsIgnoreCase("2") || s.equalsIgnoreCase("3")
                || s.equalsIgnoreCase("4") || s.equalsIgnoreCase("5") || s.equalsIgnoreCase("6") || s.equalsIgnoreCase("7")
                ||s.equalsIgnoreCase("8") || s.equalsIgnoreCase("9"))
            et.append(s);       // Button pressed was a digit
        else if (s.equalsIgnoreCase("+") || s.equalsIgnoreCase("/") || s.equalsIgnoreCase("*")){
            if (gotNum){
                num1 = Double.parseDouble(et.getText().toString());
                expr.setNum1(num1)
                expr.setOp(s);
                gotOp = true;
            }
        }else if (s.equalsIgnoreCase())
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
        }else if (id == R.id.extra){
            Intent intent = new Intent(MainActivity.this, Extra.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
