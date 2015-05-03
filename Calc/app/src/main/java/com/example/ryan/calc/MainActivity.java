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

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends ActionBarActivity {

    final String[] bStr = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", ".", "0", "=", "+"};
    EditText et;
    String exprStr;
    double num1, num2;
    boolean gotNum, gotOp, gotDot;
    Stack<String> input;
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
            bLayout.addView(bList[i], 250, 250);
            bList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    //et.append(b.getText());
                    bHandler(b.getText().toString());
                }
            });
        }
        final Button delButton = (Button)findViewById(R.id.delB);
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){delHandler();}
        });
        input = new Stack<String>();
        exprStr = "";
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

        if (Expr.isOp(s)) {
            if  (!input.empty() && Expr.isNum(input.peek())) {
                input.push(s);
                exprStr += " " + s + " ";
            }
        }else if (Expr.isNum(s)) {
            if (input.empty() || (Expr.isOp(input.peek())) || input.peek().equalsIgnoreCase("("))
                input.push(s);
            else if (Expr.isNum(input.peek()) || input.peek().equalsIgnoreCase(".")) {
                String tmp = input.pop() + s;
                input.push(tmp);
            }
            exprStr += s;
        }else if (s.equalsIgnoreCase(".")){
            if (input.empty() || Expr.isOp(input.peek())) {
                    input.push(".");
                    exprStr += ".";
            }else if ( Expr.isNum(input.peek())){
                if (Expr.isNum(input.peek() + ".")){
                    String tmp = input.pop();
                    input.push(tmp + ".");
                    exprStr += ".";
                }
            }
        }

        // http://technopaper.blogspot.com/2009/06/converting-double-to-string-without-e.html
        if (s.equalsIgnoreCase("=")) {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            String tmp = "";
            while (!input.empty())
                tmp = input.pop() + " " + tmp;
            et.setText(nf.format(new Expr().evaluate(tmp)));
            exprStr = "";
            input.clear();
        }else
            et.setText(exprStr);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        exprStr = (intent.getStringExtra("expr") != null) ?  intent.getStringExtra("expr") : "";
        getInputStack(exprStr);
        et.setText(exprStr);
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
            intent.putExtra("expr", exprStr);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
