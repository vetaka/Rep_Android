package com.example.calc;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "@@@ MainActivity";
    public static final String KEY_CALCULATOR = "key_calculator";

    TextView expression_view,result_view;
    private String strExpression;
    private String result;
    private String txtOnButton;
    private Calculator calculator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[]  buttons = {R.id.but_1, R.id.but_2, R.id.but_3, R.id.but_4, R.id.but_5, R.id.but_6,
                R.id.but_7, R.id.but_8, R.id.but_9, R.id.but_0,R.id.but_clear,R.id.but_bracket1,
                R.id.but_bracket2,R.id.but_plus,R.id.but_minus,R.id.but_div,R.id.but_mult,
                R.id.but_point,R.id.but_equal};

        expression_view = findViewById(R.id.expression_view);
        result_view = findViewById(R.id.result_view);

        Button[] bt = new Button[buttons.length];

        result = "0";
        strExpression = "";

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CALCULATOR)) {
            calculator = savedInstanceState.getParcelable(KEY_CALCULATOR);
            result   = calculator.getResult();
            strExpression = calculator.getStrExpression();
        } else calculator = new Calculator(strExpression,result);

        updateText();

        // цикл по массиву кнопок
        for(int i=0; i<buttons.length;i++){
            final int K = i;
            // найдем i-й View-элемент - кнопку и присвоим i-тому эл-ту массива кнопок
            bt[i]= findViewById(buttons[i]);
            // обработка кнопки по нажатию: создаем обработчик нажатия на i-тую кнопку
            bt[i].setOnClickListener(v -> {
                // получаем текст с кнопки
                 txtOnButton = bt[K].getText().toString();
                // и проверяем его
                switch (txtOnButton) {
                    case ("C"):
                        result ="0";
                        strExpression = "";
                        updateText();
                        break;
                    case ("="):
                        result = calculator.Calculate(strExpression);
                        strExpression = strExpression + txtOnButton ;
                        updateText();
                      break;
                    default:
                        strExpression = strExpression + txtOnButton ;
                        updateText();
                        break;
                }
            });
        }
    }
    private void updateText(){
            expression_view.setText(strExpression);
            result_view.setText(result);
    }

   @Override
   public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
       super.onRestoreInstanceState(savedInstanceState, persistentState);
   }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(KEY_CALCULATOR, calculator);
        super.onSaveInstanceState(outState);
    }

}

