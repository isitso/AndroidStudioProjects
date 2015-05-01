package com.example.ryan.calc;

/**
 * Created by Ryan on 5/1/2015.
 * Expression class: this class will solve the expression
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
        if (op.isEmpty() || op.equalsIgnoreCase("num"))
            return this;
        else if (op.equalsIgnoreCase("-"))
            result.num1 = num1 - num2;
        else if (op.equalsIgnoreCase("+"))
            result.num1 = num1 + num2;
        else if (op.equalsIgnoreCase("/"))
            result.num1 = num1 / num2;
        else if (op.equalsIgnoreCase("*"))
            result.num1 = num1 * num2;
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
}
