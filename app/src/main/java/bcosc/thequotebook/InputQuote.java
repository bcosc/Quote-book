package bcosc.thequotebook;

import java.util.Scanner;

/**
 * Created by Bryan on 4/18/2015.
 */
public class InputQuote {
    public static void main() {
        Scanner user_input = new Scanner(System.in);

        String input_quote;
        System.out.print("Enter your quote: ");
        input_quote = user_input.next();

        String input_name;
        System.out.print("Enter your name: ");
        input_name = user_input.next();

        System.out.println(input_quote + input_name);
    }

}

