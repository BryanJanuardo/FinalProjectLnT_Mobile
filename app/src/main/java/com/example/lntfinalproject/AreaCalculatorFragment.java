package com.example.lntfinalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class AreaCalculatorFragment extends Fragment {

    private Button next, prev, calculate;
    private EditText lengthSquare;
    private EditText baseTriangle, heightTriangle;
    private EditText diameterCircle;
    private TextView totalArea;
    private RelativeLayout squareContainer, triangleContainer, circleContainer;

    private enum Shape{
        SQUARE, TRIANGLE, CIRCLE
    }

    private int totalAreaValue;
    private int currShape = 0;

    private void updateTotalArea(Double totalAreaValue){
        DecimalFormat df = new DecimalFormat("#.#####");
        totalArea.setText(df.format(totalAreaValue));
    }
    private Double calculateAreaValue(){
        if(currShape == Shape.SQUARE.ordinal()){
            return Double.parseDouble(lengthSquare.getText().toString()) * Double.parseDouble(lengthSquare.getText().toString());
        }else if(currShape == Shape.TRIANGLE.ordinal()){
            return (Double.parseDouble(baseTriangle.getText().toString()) * Double.parseDouble(heightTriangle.getText().toString())) / 2;
        }else if(currShape == Shape.CIRCLE.ordinal()){
            return Math.PI * Double.parseDouble(diameterCircle.getText().toString()) * Double.parseDouble(diameterCircle.getText().toString());
        }
        return 0.0;
    }
    private boolean validationInput(){
        switch (currShape){
            case 0:
                if(TextUtils.isEmpty(lengthSquare.getText().toString()))
                    return false;
                break;
            case 1:
                if(TextUtils.isEmpty(baseTriangle.getText().toString()) || TextUtils.isEmpty(heightTriangle.getText().toString()))
                    return false;
                break;
            case 2:
                if(TextUtils.isEmpty(diameterCircle.getText().toString()))
                    return false;
                break;
        }
        return true;
    }
    private void calculateArea(){
        if(!validationInput())  return;

        updateTotalArea(calculateAreaValue());
    }

    private void changeCurrentShape(){
        switch (currShape){
            case 0:
                squareContainer.setVisibility(View.VISIBLE);
                triangleContainer.setVisibility(View.GONE);
                circleContainer.setVisibility(View.GONE);
                break;
            case 1:
                squareContainer.setVisibility(View.GONE);
                triangleContainer.setVisibility(View.VISIBLE);
                circleContainer.setVisibility(View.GONE);
                break;
            case 2:
                squareContainer.setVisibility(View.GONE);
                triangleContainer.setVisibility(View.GONE);
                circleContainer.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void validationCurrentShapeValue(){
        if(currShape > 2){
            currShape = 0;
        }else if(currShape < 0){
            currShape = 2;
        }
        changeCurrentShape();
    }

    private void nextCurrentShape(){
        currShape++;
        validationCurrentShapeValue();
    }

    private void prevCurrentShape(){
        currShape--;
        validationCurrentShapeValue();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_area_calculator, container, false);

        squareContainer = (RelativeLayout) rootView.findViewById(R.id.Shape_Container_Square);
        triangleContainer = (RelativeLayout) rootView.findViewById(R.id.Shape_Container_Triangle);
        circleContainer = (RelativeLayout) rootView.findViewById(R.id.Shape_Container_Circle);

        next = (Button) rootView.findViewById(R.id.next_button);
        prev = (Button) rootView.findViewById(R.id.prev_button);
        calculate = (Button) rootView.findViewById(R.id.calculate_button);

        lengthSquare = (EditText) rootView.findViewById(R.id.InputLengthSquare);

        baseTriangle = (EditText) rootView.findViewById(R.id.InputBaseTriangle);
        heightTriangle = (EditText) rootView.findViewById(R.id.InputHeightTriangle);

        diameterCircle = (EditText) rootView.findViewById(R.id.InputDiameterCircle);

        totalArea = (TextView) rootView.findViewById((R.id.total_area));

        next.setOnClickListener(v -> {
            nextCurrentShape();
        });

        prev.setOnClickListener(v -> {
            prevCurrentShape();
        });

        calculate.setOnClickListener(v -> {
            calculateArea();
        });

        return rootView;
    }
}