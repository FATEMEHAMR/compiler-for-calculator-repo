package ir.ac.kntu.calculatorapp;

import java.util.List;

class Parser {
    private final List<Token> tokens;
    private int pos = 0;
    private Token currentToken;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(pos);
    }

    private void eat(TokenType type) {
        if (currentToken.type == type) {
            pos++;
            currentToken = tokens.get(pos);
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }

    private Node factor() {
        Token token = currentToken;
        if (token.type == TokenType.NUMBER) {
            eat(TokenType.NUMBER);
            return new NumberNode(Integer.parseInt(token.value));
        } else if (token.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            Node node = expression();
            eat(TokenType.RPAREN);
            return node;
        }
        throw new RuntimeException("Unexpected token: " + token);
    }

    private Node term() {
        Node node = factor();
        while (currentToken.type == TokenType.MULTIPLY || currentToken.type == TokenType.DIVIDE) {
            Token token = currentToken;
            if (token.type == TokenType.MULTIPLY) {
                eat(TokenType.MULTIPLY);
            } else if (token.type == TokenType.DIVIDE) {
                eat(TokenType.DIVIDE);
            }
            node = new BinOpNode(node, token, factor());
        }
        return node;
    }

    private Node expression() {
        Node node = term();
        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            Token token = currentToken;
            if (token.type == TokenType.PLUS) {
                eat(TokenType.PLUS);
            } else if (token.type == TokenType.MINUS) {
                eat(TokenType.MINUS);
            }
            node = new BinOpNode(node, token, term());
        }
        return node;
    }

    Node parse() {
        return expression();
    }
}

abstract class Node {}

class NumberNode extends Node {
    int value;

    NumberNode(int value) {
        this.value = value;
    }
}

class BinOpNode extends Node {
    Node left;
    Token op;
    Node right;

    BinOpNode(Node left, Token op, Node right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}
