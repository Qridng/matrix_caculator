package com.example.matrixcaculator;

import android.content.Intent;
import android.widget.TextView;

import Jama.LUDecomposition;
import Jama.Matrix;

public class Matrix_Calculate {

    static double[][] a={{0,0,0},{0,0,0},{0,0,0}};
    static double[][] b={{0,0,0},{0,0,0},{0,0,0}};

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

    public Matrix scalar_multiplication(double mul){
        Matrix multiplication = A.timesEquals(mul);
        Temp_Matrix = multiplication.getArray();
        return multiplication;
    }

    public Matrix transpose(){
        Matrix transpose = A.transpose();
        Temp_Matrix = transpose.getArray();
        return transpose;
    }

    public Matrix inverse(){
        Matrix inverse = A.inverse();
        inverse.getMatrix(0, 2, 0, 2).print(0, 0);
        Temp_Matrix = inverse.getArray();

        return inverse;
    }

    public boolean isNonsingular(){

        return true;
    }

    public double[][] getTemp_Matrix() {
       /* System.out.println(String.valueOf(Temp_Matrix[0][0]));*/
        return Temp_Matrix;
    }

    public void inputtext(int x, int y ,double A_T,double B_T) {
        a[x][y] = A_T;
        b[x][y] = B_T;
    }

    public String getResult(){
        return result;
    }


}
