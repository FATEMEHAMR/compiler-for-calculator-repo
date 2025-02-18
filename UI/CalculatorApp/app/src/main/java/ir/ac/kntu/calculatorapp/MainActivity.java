package ir.ac.kntu.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import java.util.List;

import ir.ac.kntu.calculatorapp.Evaluator;
import ir.ac.kntu.calculatorapp.Lexer;
import ir.ac.kntu.calculatorapp.Parser;
import ir.ac.kntu.calculatorapp.Node;
import ir.ac.kntu.calculatorapp.NumberNode;
import ir.ac.kntu.calculatorapp.BinOpNode;




public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(this::onButtonClick);
        }
    }

    private void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                input.setLength(0);
                tvResult.setText("0");
                break;
            case "=":
                try {
                    double result = evaluateExpression(input.toString());
                    tvResult.setText(String.valueOf(result));
                } catch (Exception e) {
                    tvResult.setText("Error");
                }
                break;
            default:
                input.append(buttonText);
                tvResult.setText(input.toString());
                break;
        }
    }

    private double evaluateExpression(String expression) {
        Lexer lexer = new Lexer(expression);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Node ast = parser.parse();
        Evaluator evaluator = new Evaluator();
        return evaluator.evaluate((ir.ac.kntu.calculatorapp.Node) ast);
    }
}
