package com.example.lntfinalproject;

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

public class VolumeCalculatorFragment extends Fragment {

    private Button next, prev, calculate;
    private EditText lengthCubic;
    private EditText widthPyramid, lengthPyramid , heightPyramid;
    private EditText diameterCylinder, heightCylinder;
    private TextView totalVolume;
    private RelativeLayout cubicContainer, pyramidContainer, cylinderContainer;

    private enum Shape{
        CUBIC, PYRAMID, CYLINDER
    }

    private int totalVolumeValue;
    private int currShape = 0;
    public VolumeCalculatorFragment() {
        // Required empty public constructor
    }

    private void updateTotalVolume(Double totalVolumeValue){
        DecimalFormat df = new DecimalFormat("#.#####");
        totalVolume.setText(df.format(totalVolumeValue));
    }
    private Double calculateVolumeValue(){
        if(currShape == VolumeCalculatorFragment.Shape.CUBIC.ordinal()){
            return Double.parseDouble(lengthCubic.getText().toString()) * Double.parseDouble(lengthCubic.getText().toString()) * Double.parseDouble(lengthCubic.getText().toString());
        }else if(currShape == VolumeCalculatorFragment.Shape.PYRAMID.ordinal()){
            return (Double.parseDouble(lengthPyramid.getText().toString()) * Double.parseDouble(widthPyramid.getText().toString()) * Double.parseDouble(heightPyramid.getText().toString())) / 3;
        }else if(currShape == VolumeCalculatorFragment.Shape.CYLINDER.ordinal()){
            return Math.PI * Double.parseDouble(diameterCylinder.getText().toString()) * Double.parseDouble(diameterCylinder.getText().toString()) * Double.parseDouble(heightCylinder.getText().toString());
        }
        return 0.0;
    }
    private boolean validationInput(){
        switch (currShape){
            case 0:
                if(TextUtils.isEmpty(lengthCubic.getText().toString()))
                    return false;
                break;
            case 1:
                if(TextUtils.isEmpty(lengthPyramid.getText().toString()) || TextUtils.isEmpty(widthPyramid.getText().toString()) || TextUtils.isEmpty(heightPyramid.getText().toString()))
                    return false;
                break;
            case 2:
                if(TextUtils.isEmpty(diameterCylinder.getText().toString()) || TextUtils.isEmpty(heightCylinder.getText().toString()))
                    return false;
                break;
        }
        return true;
    }
    private void calculateVolume(){
        if(!validationInput())  return;

        updateTotalVolume(calculateVolumeValue());
    }

    private void changeCurrentShape(){
        switch (currShape){
            case 0:
                cubicContainer.setVisibility(View.VISIBLE);
                pyramidContainer.setVisibility(View.GONE);
                cylinderContainer.setVisibility(View.GONE);
                break;
            case 1:
                cubicContainer.setVisibility(View.GONE);
                pyramidContainer.setVisibility(View.VISIBLE);
                cylinderContainer.setVisibility(View.GONE);
                break;
            case 2:
                cubicContainer.setVisibility(View.GONE);
                pyramidContainer.setVisibility(View.GONE);
                cylinderContainer.setVisibility(View.VISIBLE);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_volume_calculator, container, false);

        cubicContainer = (RelativeLayout) rootView.findViewById(R.id.Shape_Container_Cubic);
        pyramidContainer = (RelativeLayout) rootView.findViewById(R.id.Shape_Container_Pyramid);
        cylinderContainer = (RelativeLayout) rootView.findViewById(R.id.Shape_Container_Cylinder);

        next = (Button) rootView.findViewById(R.id.next_button);
        prev = (Button) rootView.findViewById(R.id.prev_button);
        calculate = (Button) rootView.findViewById(R.id.calculate_button);

        lengthCubic = (EditText) rootView.findViewById(R.id.InputLengthCubic);

        lengthPyramid = (EditText) rootView.findViewById(R.id.InputLengthPyramid);
        widthPyramid = (EditText) rootView.findViewById(R.id.InputWidthPyramid);
        heightPyramid = (EditText) rootView.findViewById(R.id.InputHeightPyramid);

        diameterCylinder = (EditText) rootView.findViewById(R.id.InputDiameterCylinder);
        heightCylinder = (EditText) rootView.findViewById(R.id.InputHeightCylinder);

        totalVolume = (TextView) rootView.findViewById((R.id.total_volume));

        next.setOnClickListener(v -> {
            nextCurrentShape();
        });

        prev.setOnClickListener(v -> {
            prevCurrentShape();
        });

        calculate.setOnClickListener(v -> {
            calculateVolume();
        });

        return rootView;
    }
}