package com.example.ryan.calc;
import java.lang.Math
/**
 * Created by Ryan on 5/1/2015.
 * Expression class: this class will solve the expression
 * Options: num, +, -, /, *, !, sin, cos, tan, ^, %, sqrt, ln, log
 */
public class Expr {
    private double num1, num2;
    private Expr result;
    private String op;

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public Expr getResult() {
        result = new Expr();
        if (isNum())
            return this;
        else if (op.equalsIgnoreCase("-"))
            result.num1 = num1 - num2;
        else if (op.equalsIgnoreCase("+"))
            result.num1 = num1 + num2;
        else if (op.equalsIgnoreCase("/"))
            result.num1 = num1 / num2;
        else if (op.equalsIgnoreCase("*"))
            result.num1 = num1 * num2;
        else if(op.equalsIgnoreCase("!"))
            result.num1 = factorial((int)num1);
        else if (op.equalsIgnoreCase("^"))
            result.num1 = Math.pow(num1, num2);
        else if (op.equalsIgnoreCase("%"))
            result.num1 = (long)num1 % (long)num2;
        else if (op.equalsIgnoreCase("sqrt"))
            result.num1 = Math.sqrt(num1);
        else if (op.equalsIgnoreCase("ln"))
            result.num1 = Math.log(num1);
        else if (op.equalsIgnoreCase("log"))
            result.num1 = Math.log10(num1);
        else if(op.equalsIgnoreCase("sin"))
            result.num1 = Math.sin(num1);
        else if (op.equalsIgnoreCase("cos"))
            result.num1 = Math.cos(num1);
        else if (op.equalsIgnoreCase("tan"))
            result.num1 = Math.tan(num1);
        return result;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String toString(){
        if (isNum())
            return String.valueOf(num1);
        else
            return String.valueOf(num1) + " " + op + " " + String.valueOf(num2);
    }

    public boolean isNum(){
        return op.isEmpty() || op.equalsIgnoreCase("num");
    }

    // Some functions to use

    // Factorial
    public long factorial(int x){
        long r = 1;
        for (int i = 1; i <= x; i++)
            r *= i;
        return r;
    }
}

