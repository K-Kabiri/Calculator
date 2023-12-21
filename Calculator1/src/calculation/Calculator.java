package calculation;

import exception.ZeroException;
import stack.LinkedStack;

import java.text.DecimalFormat;
import java.util.Objects;

public class Calculator {
    private int numberOfDigits;
    private final String infix;
    private final StringBuilder postfix;
    private final LinkedStack<String> stack;

    public Calculator(String input) {
        this.numberOfDigits=0;
        this.infix = input;
        postfix = new StringBuilder();
        this.stack = new LinkedStack<>();
    }

    public void infixToPostfix() {
        for (int i = 0; i < infix.length(); ++i) {
            //first of stack and stack is empty
            if (stack.isEmpty() && getNumber(i) == null && !Objects.equals(infix.charAt(i), 'e') && !Objects.equals(infix.charAt(i), 'P')) {
                if (Objects.equals(infix.charAt(i), '(') || Objects.equals(infix.charAt(i), '*') ||
                        Objects.equals(infix.charAt(i), '/') || Objects.equals(infix.charAt(i), '^') || Objects.equals(infix.charAt(i), '!')) {
                    stack.push(String.valueOf(infix.charAt(i)));
                }
                //--------(-)------------------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '-')) {
                    if (i > 0 && (getNumber(i - 1) == null && !Objects.equals(infix.charAt(i - 1), 'e') && !Objects.equals(infix.charAt(i - 1), 'I') && !Objects.equals(infix.charAt(i - 1), ')')) &&
                            (getNumber(i + 1) != null || Objects.equals(infix.charAt(i + 1), '(') || Objects.equals(infix.charAt(i + 1), 'e') || Objects.equals(infix.charAt(i + 1), 'P'))) {
                        postfix.append("-1").append(" ");
                        stack.push(String.valueOf('*'));
                        if (Objects.equals(infix.charAt(i + 1), 'P'))
                            i++;
                    } else if (i == 0 && (getNumber(i + 1) != null || Objects.equals(infix.charAt(i + 1), '(') || Objects.equals(infix.charAt(i + 1), 'e') || Objects.equals(infix.charAt(i + 1), 'P'))) {
                        postfix.append("-1").append(" ");
                        stack.push(String.valueOf('*'));
                        if (Objects.equals(infix.charAt(i + 1), 'P'))
                            i++;
                    }
                    else
                        stack.push(String.valueOf(infix.charAt(i)));
                }
                //----------(+)--------------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '+')) {
                    if (i > 0 && (getNumber(i - 1) != null || Objects.equals(infix.charAt(i - 1), 'e') || Objects.equals(infix.charAt(i - 1), 'I') || Objects.equals(infix.charAt(i - 1), ')'))) {
                        stack.push(String.valueOf(infix.charAt(i)));
                    }
                }
            }
            //stack is not empty
            else if (!stack.isEmpty() && getNumber(i) == null && !Objects.equals(infix.charAt(i), 'e') && !Objects.equals(infix.charAt(i), 'P')) {
                if (Objects.equals(infix.charAt(i), '(')) {
                    stack.push(String.valueOf('('));
                }
                //----------------------------------------------------
                else if (Objects.equals(infix.charAt(i), ')')) {
                    while (!Objects.equals(stack.top(), "(")) {
                        postfix.append(stack.pop()).append(" ");
                    }
                    stack.pop();// remove ( from stack
                }
                //----------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '+')) {
                    if (getNumber(i - 1) != null || Objects.equals(infix.charAt(i - 1), 'e') || Objects.equals(infix.charAt(i - 1), 'I') || Objects.equals(infix.charAt(i - 1), ')') || Objects.equals(infix.charAt(i - 1), '!')) {
                        while ((!stack.isEmpty()) && (Objects.equals(infix.charAt(i - 1), '-')||Objects.equals(infix.charAt(i - 1), '+')||(Objects.equals(stack.top(), "*"))||(Objects.equals(stack.top(), "+"))||(Objects.equals(stack.top(), "-")) || (Objects.equals(stack.top(), "/")) || (Objects.equals(stack.top(), "^") || (Objects.equals(stack.top(), "!"))))) {
                            postfix.append(stack.pop()).append(" ");
                        }
                        stack.push(String.valueOf(infix.charAt(i)));
                    }
                }
                //----------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '-')) {
                    if ((getNumber(i - 1) == null && !Objects.equals(infix.charAt(i - 1), 'e') && !Objects.equals(infix.charAt(i - 1), 'I') && Objects.equals(infix.charAt(i - 1), ')')) && (getNumber(i + 1) != null || Objects.equals(infix.charAt(i + 1), '(') || Objects.equals(infix.charAt(i + 1), 'e') || Objects.equals(infix.charAt(i + 1), 'P'))) {
                        postfix.append("-1").append(" ");
                        stack.push(String.valueOf('*'));
                        if (Objects.equals(infix.charAt(i + 1), 'P'))
                            i++;
                    } else if ((getNumber(i-1)==null)&&(getNumber(i + 1) != null || Objects.equals(infix.charAt(i + 1), '(') || Objects.equals(infix.charAt(i + 1), 'e') || Objects.equals(infix.charAt(i + 1), 'P'))) {
                        postfix.append("-1").append(" ");
                        stack.push(String.valueOf('*'));
                        if (Objects.equals(infix.charAt(i + 1), 'P'))
                            i++;
                    } else {
                        while ((!stack.isEmpty()) && (Objects.equals(stack.top(), "-")||Objects.equals(stack.top(), "+")||(Objects.equals(stack.top(), "*")) || (Objects.equals(stack.top(), "/")) || (Objects.equals(stack.top(), "^") || (Objects.equals(stack.top(), "!"))))){
                            postfix.append(stack.pop()).append(" ");
                        }
                        stack.push(String.valueOf(infix.charAt(i)));
                    }
                }
                //-----------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '*')) {
                    while ((!stack.isEmpty()) && (Objects.equals(stack.top(), "*")||Objects.equals(stack.top(), "/")||(Objects.equals(stack.top(), "^") || (Objects.equals(stack.top(), "!"))))) {
                        postfix.append(stack.pop()).append(" ");
                    }
                    stack.push(String.valueOf(infix.charAt(i)));
                }
                //-----------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '/')) {
                    while ((!stack.isEmpty()) && (Objects.equals(stack.top(), "*")||Objects.equals(stack.top(), "/")||(Objects.equals(stack.top(), "^") || (Objects.equals(stack.top(), "!"))))) {
                        postfix.append(stack.pop()).append(" ");
                    }
                    stack.push(String.valueOf(infix.charAt(i)));
                }
                //------------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '^')) {
                    while ((!stack.isEmpty()) && (Objects.equals(stack.top(), "!"))) {
                        postfix.append(stack.pop()).append(" ");
                    }
                    stack.push(String.valueOf(infix.charAt(i)));
                }
                //-----------------------------------------------------
                else if (Objects.equals(infix.charAt(i), '!')) {
                    stack.push(String.valueOf(infix.charAt(i)));
                }
            }
            //------------------------------------------------
            else if (Objects.equals(infix.charAt(i), 'e')) {
                postfix.append(Math.E).append(" ");
            } else if (Objects.equals(infix.charAt(i), 'P')) {
                postfix.append(Math.PI).append(" ");
                i++;
            } else {
                double number = getNumber(i);
                i+=numberOfDigits-1;
                postfix.append(number).append(" ");
            }
        }
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }
        //System.out.println(postfix);
    }

    public Double getNumber(int startIndex) throws NumberFormatException {
        StringBuilder sb = new StringBuilder();
        numberOfDigits=0;
        for (int i = startIndex; i < infix.length(); i++) {
            if (Objects.equals(infix.charAt(i), '!') || Objects.equals(infix.charAt(i), '^') || Objects.equals(infix.charAt(i), '*') || Objects.equals(infix.charAt(i), '/') || Objects.equals(infix.charAt(i), '+') || Objects.equals(infix.charAt(i), '-') || Objects.equals(infix.charAt(i), '(') || Objects.equals(infix.charAt(i), ')'))
                break;
            else if ((infix.charAt(i) >= 48 && infix.charAt(i) <= 57) || Objects.equals(infix.charAt(i), '.')) {
                sb.append(infix.charAt(i));
                numberOfDigits++;
            }
        }
        if (!sb.isEmpty())
            return Double.parseDouble(sb.toString());
        return null;
    }

    public String calculate() throws Exception {
        String[] temp=postfix.toString().split(" ");
        for (String s : temp) {
            if (!Objects.equals(s, "+") && !Objects.equals(s, "-") && !Objects.equals(s, "*") && !Objects.equals(s, "/")
                    && !Objects.equals(s, "!") && !Objects.equals(s, "^")) {
                stack.push(s);
            } else if (Objects.equals(s, "+") || Objects.equals(s, "-") || Objects.equals(s, "*") || Objects.equals(s, "/") || Objects.equals(s, "^")) {
                double num2 = Double.parseDouble(stack.pop());
                double num1 = Double.parseDouble(stack.pop());
                //System.out.print(num1);
                switch (s) {
                    case "+" -> {
                        //System.out.print("+");
                        stack.push(String.valueOf(num1 + num2));}
                    case "-" -> {
                        // System.out.print("-");
                        stack.push(String.valueOf(num1 - num2));
                    }
                    case "*" -> {
                        //System.out.print("*");
                        stack.push(String.valueOf(num1 * num2));
                    }
                    case "/" -> {
                        //System.out.print("/");
                        if (num2==0) {
                            throw new ZeroException();
                        }
                        else
                            stack.push(String.valueOf(num1 / num2));
                    }
                    case "^" -> {
                        //System.out.print("^");
                        try {
                            stack.push(String.valueOf(Math.pow(num1, num2)));
                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
                //System.out.print(num2+"\n");
            }
            else if (Objects.equals(s, "!"))
            {
                //System.out.print(stack.top()+"!\n");
                double num=Double.parseDouble(stack.pop());
                stack.push(String.valueOf(factorial(num)));
            }
        }
        String number=stack.pop();
        String[] returnNumber=number.split("\\.");
        try {
            if (Objects.equals(returnNumber[1], "0"))
                return String.valueOf(Integer.parseInt(returnNumber[0]));
            return String.valueOf(Double.parseDouble(new DecimalFormat("#.000").format(Double.parseDouble(number))));
        }
        catch (Exception e) {
            e.getMessage();
        }
        return number;
    }


    private double factorial(double number){
        if ( number == 0 )
            return 1;
        return(number * factorial(number - 1));
    }
}
