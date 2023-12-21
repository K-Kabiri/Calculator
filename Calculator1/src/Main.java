import calculation.Calculator;
import calculation.Validation;
import exception.InvalidCharacter;
import exception.InvalidFormat;
import exception.ParenthesesException;
import exception.ZeroException;

import java.util.Objects;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if (!Objects.equals(input, "exit")) {
                Validation v = new Validation(input);
                try {
                    v.checkFormat();
                    v.checkZero();
                    v.checkParentheses();
                    v.checkCharacter();
                    Calculator c = new Calculator(input);
                    c.infixToPostfix();
                    System.out.println(c.calculate());
                } catch (InvalidFormat e) {
                    //e.getMessage();
                    System.out.println("FORMAT-ERROR");
                } catch (ZeroException e) {
                    //e.getMessage();
                    System.out.println("ZERO-ERROR");
                } catch (ParenthesesException e) {
                    //e.getMessage();
                    System.out.println("PARENTHESES-ERROR");
                } catch (InvalidCharacter e) {
                    //e.getMessage();
                    System.out.println("CHARACTER-ERROR");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            else
                return;
        }
    }
}
