package calculation;

import exception.InvalidCharacter;
import exception.InvalidFormat;
import exception.ParenthesesException;
import exception.ZeroException;
import stack.LinkedStack;

import java.util.Objects;

public class Validation {
    private final String str;

    public Validation(String input) {
        this.str = input;
    }

    //-----------parentheses---------------------------------------
    public void checkParentheses() throws ParenthesesException {
        LinkedStack<Character> stack = new LinkedStack<>();
        for (int i = 0; i < str.length(); i++) {
            if (Objects.equals(str.charAt(i), '(')) {
                stack.push(str.charAt(i));
            } else if (Objects.equals(str.charAt(i), ')')) {
                if (stack.isEmpty())
                    throw new ParenthesesException();
                else
                    stack.pop();
            }
        }
        if (!stack.isEmpty())
            throw new ParenthesesException();
    }

    //-----------zero-----------------------------------------------
    public void checkZero() throws ZeroException {
        for (int i = 0; i < str.length(); i++) {
            if (Objects.equals(str.charAt(i), '/') && Objects.equals(str.charAt(i + 1), '0'))
                throw new ZeroException();
        }
    }

    //------------format---------------------------------------------
    public boolean checkFormat() throws InvalidFormat {
        for (int i = 0; i < str.length(); i++) {
            // first of string
            if (i == 0) {
                if (Objects.equals(str.charAt(i), ')') || Objects.equals(str.charAt(i), '*') || Objects.equals(str.charAt(i), '/') || Objects.equals(str.charAt(i), '!') || Objects.equals(str.charAt(i), '^'))
                    throw new InvalidFormat();
            }
            // last of string
            if (i == str.length() - 1) {
                if (Objects.equals(str.charAt(i), '*') || Objects.equals(str.charAt(i), '/') || Objects.equals(str.charAt(i), '+') || Objects.equals(str.charAt(i), '-') || Objects.equals(str.charAt(i), '^'))
                    throw new InvalidFormat();
                break;
            }
            //---------(------------------------
            if (Objects.equals(str.charAt(i), '(')) {
                if (Objects.equals(str.charAt(i + 1), '*') || Objects.equals(str.charAt(i + 1), '/') || Objects.equals(str.charAt(i + 1), '^') || Objects.equals(str.charAt(i + 1), '!') || Objects.equals(str.charAt(i + 1), ')'))
                    throw new InvalidFormat();
            }
            //---------num------------------------------
            else if ((findNumber(i) != null) || Objects.equals(str.charAt(i), 'e') || (Objects.equals(str.charAt(i), 'P') && Objects.equals(str.charAt(i + 1), 'I'))) {
                if (Objects.equals(str.charAt(i + 1), '(') || Objects.equals(str.charAt(i + 1), 'e') || (Objects.equals(str.charAt(i + 1), 'P') && Objects.equals(str.charAt(i + 2), 'I')))
                    throw new InvalidFormat();
            }
            //--------- +,-,*,/ ------------------------
            else if (Objects.equals(str.charAt(i), '+') || Objects.equals(str.charAt(i), '-') || Objects.equals(str.charAt(i), '*') || Objects.equals(str.charAt(i), '/')) {
                if (Objects.equals(str.charAt(i + 1), '!') || Objects.equals(str.charAt(i + 1), '^') || Objects.equals(str.charAt(i + 1), '*') || Objects.equals(str.charAt(i + 1), '/') || Objects.equals(str.charAt(i + 1), '+') || Objects.equals(str.charAt(i + 1), '-') || Objects.equals(str.charAt(i + 1), ')'))
                    throw new InvalidFormat();
            }
            //-----------!,^--------------------------
            else if (Objects.equals(str.charAt(i), '^')) {
                if (!((Objects.equals(str.charAt(i + 1), '(')|| Objects.equals(str.charAt(i + 1), 'e') || (Objects.equals(str.charAt(i + 1), 'P') && Objects.equals(str.charAt(i + 2), 'I')) || (findNumber(i + 1) != null))))
                    throw new InvalidFormat();
            }
            else if (Objects.equals(str.charAt(i), '!')) {
                if ((Objects.equals(str.charAt(i + 1), '(') || Objects.equals(str.charAt(i + 1), 'e') || (Objects.equals(str.charAt(i + 1), 'P') && Objects.equals(str.charAt(i + 2), 'I')) || (findNumber(i + 1) != null)))
                    throw new InvalidFormat();
            }
            //--------------)--------------------------
            else if (Objects.equals(str.charAt(i), ')')) {
                if (Objects.equals(str.charAt(i + 1), '(') || Objects.equals(str.charAt(i + 1), 'e') || (Objects.equals(str.charAt(i + 1), 'P') && Objects.equals(str.charAt(i + 2), 'I')) || (findNumber(i + 1) != null))
                    throw new InvalidFormat();
            }

        }
        return true;
    }

    //-------------invalid character--------------------------------
    public void checkCharacter() throws InvalidCharacter {
        boolean check = false;
        for (int i = 0; i < str.length(); i++) {
            if ((findNumber(i) != null) || Objects.equals(str.charAt(i), '(') || Objects.equals(str.charAt(i), ')') || Objects.equals(str.charAt(i), '*') || Objects.equals(str.charAt(i), '/') || Objects.equals(str.charAt(i), '!') || Objects.equals(str.charAt(i), '^')
                    || Objects.equals(str.charAt(i), '+') || Objects.equals(str.charAt(i), '-') || Objects.equals(str.charAt(i), 'e')) {
                check = true;
            } else if ((i + 1 < str.length()) && (Objects.equals(str.charAt(i), 'P') && Objects.equals(str.charAt(i + 1), 'I'))) {
                check = true;
                i = i + 1;
            } else
                throw new InvalidCharacter();
        }
        if (!check)
            throw new InvalidCharacter();
    }

    //------------------------------------------------------------------------------------------------------------------------------
    public Number findNumber(int startIndex) throws NumberFormatException {
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < str.length(); i++) {
            if (Objects.equals(str.charAt(i), '!') || Objects.equals(str.charAt(i), '^') || Objects.equals(str.charAt(i), '*') || Objects.equals(str.charAt(i), '/') || Objects.equals(str.charAt(i), '+') || Objects.equals(str.charAt(i), '-') || Objects.equals(str.charAt(i), '(') || Objects.equals(str.charAt(i), ')'))
                break;
            else if ((str.charAt(i) >= 48 && str.charAt(i) <= 57) || Objects.equals(str.charAt(i), '.')) {
                sb.append(str.charAt(i));
            }
            else
                break;
        }
        boolean isInt = true;
        if (!sb.isEmpty()) {
            for (int j = 0; j < sb.length(); j++) {
                if (Objects.equals(sb.charAt(j), '.')) {
                    isInt = false;
                    break;
                }
            }

            if (isInt)
                return Integer.parseInt(sb.toString());
            return Double.parseDouble(sb.toString());
        }
        return null;
    }
}
