package com.example.matrixcaculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Calculate_Result extends AppCompatActivity {


    Matrix_Calculate Matrix = new Matrix_Calculate();


    double[][] Result;

    String Temp=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        TextView resultTV = (TextView)findViewById(R.id.textView3);

        resultTV.setText("");

        resultTV.setText(Result());

    }


    public String Result(){
        Result= Matrix.getTemp_Matrix();
        /*result.setText(String.valueOf(Result[0][0]));*/

        for(int i = 0; i < Result.length; i++) {
            for(int j = 0; j < Result[i].length; j++) {
                System.out.print( " " + Result[i][j] );
                Temp = Temp +Result[i][j] + " ";
            }
            Temp = Temp + "\n";
        }
        return Temp;
    }


}