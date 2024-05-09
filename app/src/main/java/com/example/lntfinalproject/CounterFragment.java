package com.example.lntfinalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CounterFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private Integer counter_value = 0;
    private Button addCounterButton, reduceCounterButton, resetCounterButton;
    private TextView counterText;

    public CounterFragment() {
        // Required empty public constructor

    }

    private void saveCounterValue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("counter_value", counter_value);
        editor.apply();
    }

    private void updateCounter(){
        counterText.setText(counter_value.toString());
        saveCounterValue();
    }
    private void addCounter(){
        counter_value++;
    }
    private void reduceCounter(){
        if(counter_value > 0)
            counter_value--;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counter, container, false);

        addCounterButton = (Button) rootView.findViewById(R.id.Add_Counter_Button);
        reduceCounterButton = (Button) rootView.findViewById(R.id.Reduce_Counter_Button);
        resetCounterButton = (Button) rootView.findViewById(R.id.Reset_Counter_Button);

        counterText = (TextView) rootView.findViewById(R.id.Counter_Value);

        sharedPreferences = requireActivity().getSharedPreferences("CounterPreference", Context.MODE_PRIVATE);
        counter_value = sharedPreferences.getInt("counter_value", 0);
        counterText.setText(counter_value.toString());

        addCounterButton.setOnClickListener(v -> {
            addCounter();
            updateCounter();
        });

        reduceCounterButton.setOnClickListener(v -> {
            reduceCounter();
            updateCounter();
        });

        resetCounterButton.setOnClickListener(v -> {
            counter_value = 0;
            updateCounter();
        });

        return rootView;
    }
}