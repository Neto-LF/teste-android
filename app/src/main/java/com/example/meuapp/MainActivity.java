package com.example.meuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    TextView textView;
    Button button;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);


        Network network = new BasicNetwork(new HurlStack());


        requestQueue = new RequestQueue(cache, network);


        requestQueue.start();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                exibelista();
                elementos();

            }
        });


    }


    public void exibelista() {
        List<String> lista = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            lista.add(String.valueOf(i));


            System.out.println(i);
        }

        String resultado = "  ";

        for (int i = 0; i < lista.size(); i++) {
            resultado += lista.get(i) + "  \n  ";
        }


        textView.setText(resultado);


    }

//    public void elementos() {
//
//        String url = "https://gorest.co.in/public-api/users";
//
//        StringBuilder sb = new StringBuilder();
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            Gson gson = new Gson();
//
//
//                            List<Data> list = new ArrayList<>();
//
//                            Type tp = new TypeToken<List<Data>>() {
//                            }.getType();
//
//
//                            list = gson.fromJson(response,tp );
//
//                            for (int i = 0; i < list.size(); i++) {
//                                sb.append(list.get(i).name + " -- " + list.get(i).email);
//                                sb.append("\n");
//
//                            }
//
////                        System.out.println(sb.toString());
//
//                            textView.setText(sb.toString());
//                        } catch (JsonSyntaxException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        System.out.println();
//                    }
//                });
//
//
//        requestQueue.add(stringRequest);
//
//
//    }
    public void elementos() {

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
                            list = gson.fromJson(response.getJSONArray("data").toString(),tp );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < list.size(); i++) {
                            sb.append(list.get(i).name + " -- " + list.get(i).email);
                            sb.append("\n");

                        }

//                        System.out.println(sb.toString());
                        Log.d(TAG, "onResponse: " + sb.toString());

                        textView.setText(sb.toString());

//                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        requestQueue.add(jsonObjectRequest);


    }



}


