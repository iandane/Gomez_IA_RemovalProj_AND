package com.rigcpstone.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText number1, number2;
    private TextView result;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        result = findViewById(R.id.result);

        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('+');
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('-');
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('*');
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('/');
            }
        });
    }

    private void performOperation(char operator) {
        double num1 = Double.parseDouble(number1.getText().toString());
        double num2 = Double.parseDouble(number2.getText().toString());
        double resultValue = 0;

        switch (operator) {
            case '+':
                resultValue = num1 + num2;
                break;
            case '-':
                resultValue = num1 - num2;
                break;
            case '*':
                resultValue = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    resultValue = num1 / num2;
                } else {
                    result.setText("Cannot divide by zero");
                    return;
                }
                break;
        }

        // Display result with 2 decimal places
        result.setText(String.format("%.2f", resultValue));
    }
}