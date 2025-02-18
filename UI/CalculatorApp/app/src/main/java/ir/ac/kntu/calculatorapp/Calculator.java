package ir.ac.kntu.calculatorapp;

import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter expression: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                Lexer lexer = new Lexer(input);
                List<Token> tokens = lexer.tokenize();
                Parser parser = new Parser(tokens);
                Node ast = parser.parse();
                Evaluator evaluator = new Evaluator();
                int result = evaluator.evaluate(ast);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
