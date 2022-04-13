package com.example.matrixcaculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public Matrix_Calculate Matrix = new Matrix_Calculate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void results_intent(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Calculate_Result.class);
        startActivity(intent);
    }

    public void Matrix_add(View view) {
        Matrix.plus();
        this.results_intent();
    }

    public void Matrix_subtraction(View view) {
        Matrix.minus();
        this.results_intent();
    }

    public void Matrix_multiply(View view) {
        Matrix.times();
        this.results_intent();
    }

    public void Matrix_scalar_multiplication(View view) {
    }

    public void Matrix_transpose(View view) {
    }

    public void inputtext(){

        TextView A_T1 = (TextView)findViewById(R.id.matrix_number_A1);
        TextView A_T2 = (TextView)findViewById(R.id.matrix_number_A2);
        TextView A_T3 = (TextView)findViewById(R.id.matrix_number_A3);
        TextView A_T4 = (TextView)findViewById(R.id.matrix_number_A4);
        TextView A_T5 = (TextView)findViewById(R.id.matrix_number_A5);
        TextView A_T6 = (TextView)findViewById(R.id.matrix_number_A6);
        TextView A_T7 = (TextView)findViewById(R.id.matrix_number_A7);
        TextView A_T8 = (TextView)findViewById(R.id.matrix_number_A8);
        TextView A_T9 = (TextView)findViewById(R.id.matrix_number_A9);

        TextView B_T1 = (TextView)findViewById(R.id.matrix_number_B1);
        TextView B_T2 = (TextView)findViewById(R.id.matrix_number_B2);
        TextView B_T3 = (TextView)findViewById(R.id.matrix_number_B3);
        TextView B_T4 = (TextView)findViewById(R.id.matrix_number_B4);
        TextView B_T5 = (TextView)findViewById(R.id.matrix_number_B5);
        TextView B_T6 = (TextView)findViewById(R.id.matrix_number_B6);
        TextView B_T7 = (TextView)findViewById(R.id.matrix_number_B7);
        TextView B_T8 = (TextView)findViewById(R.id.matrix_number_B8);
        TextView B_T9 = (TextView)findViewById(R.id.matrix_number_B9);



    }
}
