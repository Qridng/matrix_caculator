package com.example.matrixcaculator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgproc.CHAIN_APPROX_SIMPLE;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2BGR;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RETR_EXTERNAL;
import static org.bytedeco.javacpp.opencv_imgproc.MORPH_RECT;
import static org.bytedeco.javacpp.opencv_imgproc.RETR_EXTERNAL;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_TOZERO;
import static org.bytedeco.javacpp.opencv_imgproc.boundingRect;
import static org.bytedeco.javacpp.opencv_imgproc.cvBoundingRect;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.dilate;
import static org.bytedeco.javacpp.opencv_imgproc.erode;
import static org.bytedeco.javacpp.opencv_imgproc.findContours;
import static org.bytedeco.javacpp.opencv_imgproc.getStructuringElement;
import static org.bytedeco.javacpp.opencv_imgproc.medianBlur;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;



public class MainActivity extends AppCompatActivity {

    opencv_core.Mat mat,mat2;

    AndroidFrameConverter converterToBitmap;
    OpenCVFrameConverter.ToIplImage converterToIplImage;
    OpenCVFrameConverter.ToMat converterToMat;


    static TesseractOCR mTessOCR;

    static ImageView imgSrc;
    static TextView txtResult;




    public int Temp=1;
    public int recognition=0;

    Bitmap bitmap;
    Bitmap Gray;
    Bitmap bin;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    static TextView[] A_T = new TextView[10];
    static TextView[] B_T = new TextView[10];
    static EditText mul;



    public Matrix_Calculate Matrix = new Matrix_Calculate();





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        A_T[1] = (TextView) findViewById(R.id.matrix_number_A1);
        A_T[2] = (TextView) findViewById(R.id.matrix_number_A2);
        A_T[3] = (TextView) findViewById(R.id.matrix_number_A3);
        A_T[4] = (TextView) findViewById(R.id.matrix_number_A4);
        A_T[5] = (TextView) findViewById(R.id.matrix_number_A5);
        A_T[6] = (TextView) findViewById(R.id.matrix_number_A6);
        A_T[7] = (TextView) findViewById(R.id.matrix_number_A7);
        A_T[8] = (TextView) findViewById(R.id.matrix_number_A8);
        A_T[9] = (TextView) findViewById(R.id.matrix_number_A9);

        B_T[1] = (TextView) findViewById(R.id.matrix_number_B1);
        B_T[2] = (TextView) findViewById(R.id.matrix_number_B2);
        B_T[3] = (TextView) findViewById(R.id.matrix_number_B3);
        B_T[4] = (TextView) findViewById(R.id.matrix_number_B4);
        B_T[5] = (TextView) findViewById(R.id.matrix_number_B5);
        B_T[6] = (TextView) findViewById(R.id.matrix_number_B6);
        B_T[7] = (TextView) findViewById(R.id.matrix_number_B7);
        B_T[8] = (TextView) findViewById(R.id.matrix_number_B8);
        B_T[9] = (TextView) findViewById(R.id.matrix_number_B9);

        imgSrc = (ImageView) findViewById(R.id.imageView);
        txtResult = (TextView) findViewById(R.id.digital_result);

        String language = "eng";
        mTessOCR = new TesseractOCR(this, language);


    }



    private void askCameraPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imgSrc.setImageBitmap(image);

            try {
                String ocrResult = ocrWithEnglish();
                String newResult = ocrResult.replaceAll("[^\\d]", "");
                newResult = newResult.replace(""," ").trim();

                txtResult.setText(newResult);

                String[] split = newResult.split(" ");

                if(recognition==0) {
                    for (int i = 0; i < split.length; i++) {
                        if (Temp < 10) {
                            if (split[i] != " ") {
                                A_T[Temp].setText(split[i]);
                                System.out.print(split[i]);
                                Temp++;
                            }
                        }
                    }
                    Temp = 1;
                }

                if(recognition==1) {
                    for (int i = 0; i < split.length; i++) {
                        if (Temp < 10) {
                            if (split[i] != " ") {
                                B_T[Temp].setText(split[i]);
                                System.out.print(split[i]);
                                Temp++;
                            }
                        }
                    }
                    Temp = 1;
                }

                imgSrc.setDrawingCacheEnabled(false);
            }catch(Exception e){

            }

        }
    }


    public void results_intent() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Calculate_Result.class);
        startActivity(intent);
    }

    public void Matrix_add(View view) {
        inputtext();
        Matrix.plus();
        this.results_intent();
    }

    public void Matrix_subtraction(View view) {
        inputtext();
        Matrix.minus();
        this.results_intent();
    }

    public void Matrix_multiply(View view) {
        inputtext();
        Matrix.times();
        this.results_intent();
    }

    public void Matrix_scalar_multiplication(View view) {
        mul = (EditText) findViewById(R.id.multiply_edit);
        inputtext();
        Matrix.scalar_multiplication(Double.parseDouble(mul.getText().toString()));
        this.results_intent();
    }


    public void Matrix_transpose(View view) {
        inputtext();
        Matrix.transpose();
        this.results_intent();
    }

    public void Matrix_determinant(View view) {
        inputtext();
        Matrix.determinant();
        this.results_intent();
    }

    public void matrix_getL(View view) {
        inputtext();
        Matrix.getL();
        this.results_intent();
    }

    public void matrix_getU(View view) {
        inputtext();
        Matrix.getU();
        this.results_intent();

    }


    public void inputtext() {

        try {

            int temp = 1;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if ("".equals(A_T[temp].getText().toString().trim())) {
                        A_T[temp].setText("0");
                    }
                    if ("".equals(B_T[temp].getText().toString().trim())) {
                        B_T[temp].setText("0");
                    }
                    Matrix.inputtext(i, j, Double.parseDouble(A_T[temp].getText().toString()), Double.parseDouble(B_T[temp].getText().toString()));
                    temp++;
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "輸入錯誤！！！", Toast.LENGTH_SHORT).show();
        }
    }


    public void recognition(View view) {
        askCameraPermissions();
        recognition=0;

    }
    public void recognitionB(View view) {
        askCameraPermissions();
        recognition=1;
    }
    public String ocrWithEnglish() {
        String resString = "";

        imgSrc.setDrawingCacheEnabled(true);

        setLocked(imgSrc);

        BitmapDrawable drawable = (BitmapDrawable) imgSrc.getDrawable();
        bitmap = drawable.getBitmap();


        converterToBitmap = new AndroidFrameConverter();
        converterToIplImage = new OpenCVFrameConverter.ToIplImage();
        converterToMat = new OpenCVFrameConverter.ToMat();

        Frame frame = converterToBitmap.convert(bitmap);

        mat = converterToIplImage.convertToMat(frame);



        threshold(mat,mat,140,225,THRESH_BINARY);


        
        Frame Resultframe = converterToMat.convert(mat);
        bin = converterToBitmap.convert(Resultframe);


        imgSrc.setImageBitmap(bin);
        resString = mTessOCR.getOCRResult(bin);



        imgSrc.setDrawingCacheEnabled(false);
        return  resString;
    }

    public static void  setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(120);

    }



}


