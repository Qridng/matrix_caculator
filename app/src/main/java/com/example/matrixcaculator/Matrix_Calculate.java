package com.example.matrixcaculator;

import android.content.Intent;
import android.widget.TextView;

import Jama.Matrix;

public class Matrix_Calculate {

    double[][] a={{1,2,3},{4,5,6},{7,8,9}};
    double[][] b={{4,5,6},{7,8,9},{10,11,12}};

    Matrix A = new Matrix(a);
    Matrix B = new Matrix(b);
    Matrix Temp;
    static double[][] Temp_Matrix;

    static String result;



    public String plus(){
        Matrix plus = A.plus(B);
        Temp_Matrix = plus.getArray();
        return result;
    }

    public Matrix minus(){
        Matrix minus = A.minus(B);
        minus.getMatrix(0, 2, 0, 2).print(0,0);
        Temp_Matrix = minus.getArray();
        return minus;
    }

    public Matrix times(){
        Matrix times = A.times(B);
        times.getMatrix(0, 2, 0, 2).print(0, 0);
        Temp_Matrix = times.getArray();
        return times;
    }

    public double[][] getTemp_Matrix() {
       /* System.out.println(String.valueOf(Temp_Matrix[0][0]));*/
        return Temp_Matrix;
    }

    public double[][] inputtext(double A_T,double B_T) {
        return a;
    }

    public String getResult(){
        return result;
    }


}
