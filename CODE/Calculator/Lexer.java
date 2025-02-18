import java.util.ArrayList;
import java.util.List;

enum TokenType {
    NUMBER, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN, EOF
}

class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}

class Lexer {
    private final String input;
    private int pos = 0;
    private char currentChar;

    Lexer(String input) {
        this.input = input;
        this.currentChar = input.charAt(pos);
    }

    private void advance() {
        pos++;
        if (pos >= input.length()) {
            currentChar = '\0'; // EOF
        } else {
            currentChar = input.charAt(pos);
        }
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private String number() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                tokens.add(new Token(TokenType.NUMBER, number()));
                continue;
            }

            if (currentChar == '+') {
                tokens.add(new Token(TokenType.PLUS, "+"));
                advance();
                continue;
            }

            if (currentChar == '-') {
                tokens.add(new Token(TokenType.MINUS, "-"));
                advance();
                continue;
            }

            if (currentChar == '*') {
                tokens.add(new Token(TokenType.MULTIPLY, "*"));
                advance();
                continue;
            }

            if (currentChar == '/') {
                tokens.add(new Token(TokenType.DIVIDE, "/"));
                advance();
                continue;
            }

            if (currentChar == '(') {
                tokens.add(new Token(TokenType.LPAREN, "("));
                advance();
                continue;
            }

            if (currentChar == ')') {
                tokens.add(new Token(TokenType.RPAREN, ")"));
                advance();
                continue;
            }

            throw new RuntimeException("Unexpected character: " + currentChar);
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
