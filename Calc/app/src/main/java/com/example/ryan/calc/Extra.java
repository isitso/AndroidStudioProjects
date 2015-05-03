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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;


public class Extra extends ActionBarActivity {
    final String[] bStr = {"sin", "cos", "tan", "", "ln", "log", "pi", "e", "%", "!", "sqrt", "^", "(", ")", "", ""};
    EditText et;
    String exprStr;
    Stack<String> input;
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
                    bHandler(b.getText().toString());
                }
            });
        }

        final Button delButton = (Button)findViewById(R.id.delB);
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){delHandler();}
        });

        // Get values passed from main activity
        Intent intent = getIntent();
        exprStr = (intent.getStringExtra("expr") != null) ? intent.getStringExtra("expr") : "";
        getInputStack(exprStr);
        et.setText(exprStr);
    }

    // Handle Del button
    public void delHandler(){
        if (!input.empty()) {
            int id = exprStr.lastIndexOf(input.pop());
            if (id > -1)
                exprStr = exprStr.substring(0, id);
            et.setText(exprStr);
        }else
            et.setText("");
    }

    // Generate stack from string
    public void getInputStack(String s){
        String[] tmp = s.split(" ");
        input = new Stack<String>();
        for (String x : tmp) {
            if (x.equalsIgnoreCase("e"))
                input.push(String.valueOf(Math.E));
            else if (x.equalsIgnoreCase("pi"))
                input.push(String.valueOf(Math.PI));
            else if (!x.isEmpty())
                input.push(x);
        }
    }
    // Handle buttons
    public void bHandler(String s){
        if (Expr.isFunction(s)){    // sin, cos, tan, ln, log
            // they must follow an op, cannot be a number
            if (input.empty() || (Expr.isOp(input.peek()) || input.peek().equalsIgnoreCase("("))) {  // good, go ahead and add them
                input.push(s);
                input.push("(");
                exprStr += " " + s + " (" + " ";
            }
        }else if (Expr.isOp(s)) {
            if  (!input.empty() && Expr.isNum(input.peek())) {
                input.push(s);
                exprStr += " " + s + " ";
            }
        }else if (s.equalsIgnoreCase("(") ||s.equalsIgnoreCase(")")){
            input.push(s);
            exprStr += " " + s + " ";
        }else if (s.equalsIgnoreCase("e")) {
            if (input.empty() || (Expr.isOp(input.peek())) || input.peek().equalsIgnoreCase("(")){
                input.push(String.valueOf(Math.E));
                exprStr += " " + s + " ";
            }
        }else if (s.equalsIgnoreCase("pi")){
            if (input.empty() || (Expr.isOp(input.peek())) || input.peek().equalsIgnoreCase("(")){
                input.push(String.valueOf(Math.PI));
                exprStr += " " + s + " ";
            }
        }

        et.setText(exprStr);

    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        exprStr = intent.getStringExtra("expr");
        getInputStack(exprStr);
        et.setText(exprStr);
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
            intent.putExtra("expr", exprStr);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
