package com.example.ryan.calc;
import java.lang.Math;
import java.util.Stack;
/**
 * Created by Ryan on 5/1/2015.
 * Expression class: this class will solve the expression
 * Options: num, +, -, /, *, !, sin, cos, tan, ^, %, sqrt, ln, log
 *
 * http://en.wikipedia.org/wiki/Reverse_Polish_notation
 * http://users.ece.gatech.edu/mleach/revpol/
 * http://en.wikipedia.org/wiki/Shunting-yard_algorithm
 *
 * =========== HOW IT WORKS=======================
 * Convert infix notation into Reverse Polish Notation (RPN)
 * Solve it in RPN
 */

//	REQUIREMENT: Everything must have space between, (3 + 4) will not work
// 	Must be ( 3 + 4 )
public class Expr {
    private Stack<Double> v;

    public Expr(){

    }

    public double evaluate(String str){
        v = new Stack<Double>();
        str = infixToRPN(str);
        try{
            String expr[] = str.trim().split(" ");

            for (int i = 0; i < expr.length; i++){
                String s = expr[i];
                if (isNum(s))
                    v.push(Double.parseDouble(s));
                else{	// should be operator
                    switch (s){
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                        case "%":
                        case "^":
                            // Need 2 operands
                            if (v.size() < 2)
                                throw new Exception("Lacking val for the operator " + s);
                            else
                                v.push(doOp2(s, v.pop(), v.pop()));
                            break;
                        case "!":
                        case "sin":
                        case "cos":
                        case "tan":
                        case "sqrt":
                        case "ln":
                        case "log":
                            // Need at least 1 operands
                            if (v.size() < 1)
                                throw new Exception("Lack val for the operator " + s);
                            else
                                v.push(doOp1(s, v.pop()));
                    }
                }
            }
            if (v.size() == 1)
                return v.pop();
            else
                throw new Exception("Something went wrong. Can't solve it.");
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isNum(String s){
        try{
            Double d = Double.parseDouble(s);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    // Do operator on 1 val
    //  !, sin, cos, tan, sqrt, ln, log
    public double doOp1(String s, double d){
        switch (s){
            case "!":
                return factorial((int)d);
            case "sin":
                return Math.sin(d);
            case "cos":
                return Math.cos(d);
            case "tan":
                return Math.tan(d);
            case "sqrt":
                return Math.sqrt(d);
            case "ln":
                return Math.log(d);
            case "log":
                return Math.log10(d);
        }
        return 0;
    }

    // Do operator on 2 vals
    //  +, -, /, *, ^, %,
    // 2nd operands must be passed 1st
    public double doOp2(String s, double d2, double d1){
        switch (s){
            case "+":
                return d1 + d2;
            case "-":
                return d1 - d2;
            case "*":
                return d1 * d2;
            case "/":
                return d1 / d2;
            case "%":
                return d1 % d2;
            case "^":
                return Math.pow(d1, d2);
        }
        return 0;
    }

    //================ INFIX TO RPN==========================
	/* Convert infix to Reverse Polish Notation
	 * http://en.wikipedia.org/wiki/Shunting-yard_algorithm
	 *
	 * REQUIREMENT: ~~
	 ======================================================*/
    private Stack<String> stack;

    public String infixToRPN(String str){
        String rpn = "";
        stack = new Stack<String>();
        String expr[] = str.split(" ");
        for (String s : expr){
            if (isNum(s))
                rpn += s + " ";
            else if (isOp(s)){
                // Should be an operator
                while (!stack.isEmpty() && ((isLeftAssociativity(s) && (getPrecedence(s) <= getPrecedence(stack.peek()))) ||
                        (!isLeftAssociativity(s) && (getPrecedence(s) < getPrecedence(stack.peek())))))
                    rpn += stack.pop() + " ";
                stack.push(s);
            }else if (s.equalsIgnoreCase("("))
                stack.push(s);
            else if (s.equalsIgnoreCase(")")){
                String op = stack.pop();
                while (!op.equalsIgnoreCase("(")){
                    rpn += op + " ";
                    op = stack.pop();
                }
            }
        }
        while (!stack.isEmpty())
            rpn += stack.pop() + " ";
        return rpn;
    }

    public int getPrecedence(String s){
        switch (s) {
            case "^":
            case "!":
            case "sin":
            case "cos":
            case "tan":
            case "sqrt":
            case "ln":
            case "log":
                return 5;
            case "*":
            case "/":
            case "%":
                return 4;
            case "+":
            case "-":
                return 3;
        }
        return 0;
    }

    public boolean isLeftAssociativity(String s){
        switch (s) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                return true;
            case "^":
            case "!":
            case "sin":
            case "cos":
            case "tan":
            case "sqrt":
            case "ln":
            case "log":
                return false;
        }
        return false;
    }

    public static boolean isOp(String s){
        switch (s) {
            case "^":
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "!":
            case "sin":
            case "cos":
            case "tan":
            case "sqrt":
            case "ln":
            case "log":
                return true;
            default:
                return false;
        }
    }

    public static boolean isFunction(String s){
        switch (s) {
            case "sin":
            case "cos":
            case "tan":
            case "sqrt":
            case "ln":
            case "log":
                return true;
            default:
                return false;
        }
    }
    //===============END OF INFIX TO RPN=====================

	/*============================ MATH FUNCTIONS =/ ================
	 *
	 */

    // Factorial
    public long factorial(int x){
        long r = 1;
        for (int i = 1; i <= x; i++)
            r *= i;
        return r;
    }

    //=========================END OF MATH FUNCTIONS====================
}

