package ir.ac.kntu.calculatorapp;

class Evaluator {
    int evaluate(Node node) {
        if (node instanceof NumberNode) {
            return ((NumberNode) node).value;
        } else if (node instanceof BinOpNode) {
            BinOpNode binOpNode = (BinOpNode) node;
            int left = evaluate(binOpNode.left);
            int right = evaluate(binOpNode.right);
            switch (binOpNode.op.type) {
                case PLUS:
                    return left + right;
                case MINUS:
                    return left - right;
                case MULTIPLY:
                    return left * right;
                case DIVIDE:
                    return left / right;
                default:
                    throw new RuntimeException("Unexpected operator: " + binOpNode.op.type);
            }
        }
        throw new RuntimeException("Unexpected node type: " + node.getClass().getName());
    }
}
