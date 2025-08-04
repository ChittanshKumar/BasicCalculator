package com.example.basiccalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView calcTV, resultTV;
    MaterialButton buttonClear, buttonBracketOpen, buttonBracketClose, buttonDivide;
    MaterialButton button7, button8, button9, buttonMultiply;
    MaterialButton button4, button5, button6, buttonMinus;
    MaterialButton button1, button2, button3, buttonPlus;
    MaterialButton buttonAC, button0, buttonDot, buttonEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        resultTV = findViewById(R.id.result_tv);
        calcTV = findViewById(R.id.calc_tv);

        buttonClear = assignId(R.id.button_clear);
        buttonBracketOpen = assignId(R.id.button_bracketopen);
        buttonBracketClose = assignId(R.id.button_bracketclose);
        buttonDivide = assignId(R.id.button_divide);

        button7 = assignId(R.id.button_7);
        button8 = assignId(R.id.button_8);
        button9 = assignId(R.id.button_9);
        buttonMultiply = assignId(R.id.button_multiply);

        button4 = assignId(R.id.button_4);
        button5 = assignId(R.id.button_5);
        button6 = assignId(R.id.button_6);
        buttonMinus = assignId(R.id.button_minus);

        button1 = assignId(R.id.button_1);
        button2 = assignId(R.id.button_2);
        button3 = assignId(R.id.button_3);
        buttonPlus = assignId(R.id.button_plus);

        buttonAC = assignId(R.id.button_allclear);
        button0 = assignId(R.id.button_0);
        buttonDot = assignId(R.id.button_dot);
        buttonEquals = assignId(R.id.button_equals);


    }

    MaterialButton assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }


    @Override
    public void onClick(View v) {

        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = calcTV.getText().toString();
        if (buttonText.equals("AC")) {
            calcTV.setText("");
            resultTV.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            calcTV.setText(resultTV.getText());
            return;
        }
        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        calcTV.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("ERR")){
            resultTV.setText(finalResult);
        }
    }
    String getResult(String data){
        if (data == null || data.trim().isEmpty()) {
            return "0";
        }
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        }
        catch (Exception e){
            return "ERR";
        }

    }
}