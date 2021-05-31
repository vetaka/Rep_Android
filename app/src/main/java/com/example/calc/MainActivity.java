package com.example.calc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity {


    TextView expression_view,result_view;
    String strExpression = "";
    String txtOnButton;
    boolean flagBr=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // массив id кнопок
        final int[]  buttons = {R.id.but1,R.id.but2,R.id.but3,R.id.but4,R.id.but5,R.id.but6,R.id.but7,R.id.but8,R.id.but9,R.id.but0,R.id.butClear,R.id.butClear,R.id.butBracket1,R.id.butBracket2,R.id.butPlus,R.id.butMinus,R.id.butDiv,R.id.butMult,R.id.butPoint,R.id.butEqual};
        // массив кнопок (пустой)
        Button[] bt = new Button[buttons.length];
        // найдем View-элементы - текстовые строки выражения и результата
        expression_view = findViewById(R.id.expression_view);
        result_view = findViewById(R.id.result_view);
        strExpression = "";
        //final Context context = this;
        // цикл по массиву кнопок
        for(int i=0; i<buttons.length;i++){
            final int k = i;

            // найдем i-й View-элемент - кнопку и присвоим i-тому эл-ту массива кнопок
            bt[i]= (Button) findViewById(buttons[i]);
            // обработка кнопки по нажатию: создаем обработчик нажатия на i-тую кнопку
            bt[i].setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // получаем текст с кнопки
                 txtOnButton = bt[k].getText().toString();
                // и проверяем его
                switch (txtOnButton) {
                    case ("C"):
                        expression_view.setText("0");
                        result_view.setText("0");
                        strExpression = "";
                        break;
                    case ("="):
                        //

                        Context rhino = Context.enter();

                        rhino.setOptimizationLevel(-1);

                        String rec = "";

                        try {
                            Scriptable scriptable = rhino.initStandardObjects();
                            rec = rhino.evaluateString(scriptable,strExpression,"javascript",1,null).toString();
                        }catch (Exception e){
                            rec="0";
                        }
                        result_view.setText(rec);
                        strExpression = strExpression + txtOnButton ;
                        expression_view.setText(strExpression);
                        break;
                    default: // все остальные кнопки
                        strExpression = strExpression + txtOnButton ;
                        expression_view.setText(strExpression);
                        break;
                }

            }
            });

            }
        }
    }

