package com.example.meuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibelista();

            }
        });


    }

    public void exibelista() {
        List<String> lista = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            lista.add(String.valueOf(i));


            System.out.println(i);
        }

        textView.append(String.valueOf(lista));


    }


}
