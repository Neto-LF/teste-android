package com.example.meuapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity<elementos> extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView textView, textView2;
    Button btnAvanca, btnVoltar;
    RequestQueue requestQueue;
    int posicaoCorrente = 0;

    List<Data> listaRegistros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        btnVoltar = findViewById(R.id.btnVolta);
        btnAvanca = findViewById(R.id.btnAvanca);


        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);


        Network network = new BasicNetwork(new HurlStack());


        requestQueue = new RequestQueue(cache, network);


        requestQueue.start();


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (posicaoCorrente < listaRegistros.size() && posicaoCorrente > 0) {

                    posicaoCorrente--;
                    Data data = listaRegistros.get(posicaoCorrente);
                    textView.setText(data.getName());
                    textView2.setText(data.getEmail());

                    System.out.println(data.getName() + "-" + data.getEmail());
                }

            }
        });

        btnAvanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (posicaoCorrente < listaRegistros.size()-1) {


                    Data data1 = listaRegistros.get(posicaoCorrente);
                    posicaoCorrente++;
                    textView.setText(data1.getName());
                    textView2.setText(data1.getEmail());

                    System.out.println(data1.getName() + "-" + data1.getEmail());

               }
            }

        });

    }


    public int elementos() {

        final List<Data>[] retorno = new ArrayList[1];
        String url = "https://gorest.co.in/public-api/users";

        StringBuilder sb = new StringBuilder();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();


                        List<Data> list = new ArrayList<>();

                        Type tp = new TypeToken<List<Data>>() {
                        }.getType();


                        try {
                            list = gson.fromJson(response.getJSONArray("data").toString(), tp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listaRegistros = list;


                        Log.d(TAG, "onResponse: " + list.size());


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        requestQueue.add(jsonObjectRequest);

        return 0;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                elementos();
            }
        });

        thread.start();
    }
}