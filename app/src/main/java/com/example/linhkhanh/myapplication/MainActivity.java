package com.example.linhkhanh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import org.json.JSONException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.linhkhanh.myapplication.MESSAGE";
    public static final String urlHouston = "https://api.openweathermap.org/data/2.5/weather?q=Houston,us&APPID=40e62dfb320f3f801abf9c752e12d9af";
    public static final String urlCity = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static final String apiKey = ",us&APPID=40e62dfb320f3f801abf9c752e12d9af";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.stop();
    }


    public void getHoustonWeather(View view) throws IOException {
        final Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        final String cityName = editText.getText().toString();
        final String url = urlCity + cityName + apiKey;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            WeatherReport weatherReport = new WeatherReport(response, cityName);
                            String description = weatherReport.getDescription();
                            intent.putExtra(EXTRA_MESSAGE, description);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new InvalidCityDialogFragment();

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(request);



    }
}
