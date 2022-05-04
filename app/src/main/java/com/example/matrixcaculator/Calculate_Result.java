package com.example.matrixcaculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;

public class Calculate_Result extends AppCompatActivity {


    Matrix_Calculate Matrix = new Matrix_Calculate();


    double[][] Result;

    String Temp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        TextView resultTV = (TextView)findViewById(R.id.result);

        resultTV.setText("");

        resultTV.setText(Result());

    }


    public String Result(){
        Result= Matrix.getTemp_Matrix();
        /*result.setText(String.valueOf(Result[0][0]));*/

        for(int i = 0; i < Result.length; i++) {
            for(int j = 0; j < Result[i].length; j++) {


                if (getDec(Result[i][j])==0) {
                    Temp = Temp + (int) Result[i][j] + "  ";
                }
                else{
                    Temp = Temp + Result[i][j] + "  ";
                }
            }
            Temp = Temp + "\n";
        }
        return Temp;
    }


    public static int getDec(double input) {
        String stringIn = String.valueOf(input);

        int i = stringIn.substring(stringIn.indexOf(".")).length() - 1;
        if (i == 1 && stringIn.substring(stringIn.indexOf(".") + 1).matches("0")) {
            i--;
        }
        return i;
    }

}