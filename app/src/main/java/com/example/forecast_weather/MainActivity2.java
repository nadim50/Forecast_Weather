package com.example.forecast_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity2 extends AppCompatActivity {




    private final  String url1="https://weatherapi.com/docs";
    private final String appid1="498d71ab2fbeb6a1e9d78e2a9487bce7";


    EditText etCity;
    TextView tvResult;

    TextView p1,p2,p3,p4;

    Button btn;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        etCity=findViewById(R.id.etCity);
//        tvResult=findViewById(R.id.tvResult);
//    }

//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    app:lottie_rawRes="@raw/sunrise"
//
//    app:lottie_autoPlay="true"
//    app:lottie_loop="true"/>


    private final  String url="https://api.openweathermap.org/data/2.5/weather";
    private final String appid="498d71ab2fbeb6a1e9d78e2a9487bce7";



    DecimalFormat df=new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etCity=findViewById(R.id.etCity);
        tvResult=findViewById(R.id.tvResult);

        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);
        p3=findViewById(R.id.p3);
        p4=findViewById(R.id.p4);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               p1.setVisibility(view.GONE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p2.setVisibility(view.GONE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p3.setVisibility(view.GONE);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p4.setVisibility(view.GONE);
            }
        });



    }





    public void getWeatherDetails(View view) {

        String tempUrl="";
        String city=etCity.getText().toString().trim();

        if(city.equals(" ")){
            tvResult.setText("City field can not be empty");
        }
        else{
            //https://api.openweathermap.org/data/2.5/weather?q=Dhaka&appid=498d71ab2fbeb6a1e9d78e2a9487bce7
            tempUrl =url + "?q="+ city +"&appid=" + appid;
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Log.d("response",response);
                String output="";

                try{
                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray jsonArray=jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather=jsonArray.getJSONObject(0);
                    String description =jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain=jsonResponse.getJSONObject("main");
                    double temp=jsonObjectMain.getDouble("temp")-273.15;
                    double feelsLike= jsonObjectMain.getDouble("feels_like")-273.15;
                    float pressure= jsonObjectMain.getInt("pressure");
                    int humidity=jsonObjectMain.getInt(("humidity"));
                    JSONObject jsonObjectWind=jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds=jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys=jsonResponse.getJSONObject("sys");
                    String countryName=jsonObjectSys.getString("country");
                    String cityName=jsonResponse.getString("name");


                    // tvResult.setTextColor(Color.rgb(61,135,200));
                    output +="City: "+cityName
                            +"\n Temp:"+df.format(temp)+"c"
                            +" Feels Like:"+df.format(feelsLike)+"c"
                            +"\n Humidity:"+humidity +"%"
                            +" Cloudiness:"+clouds+"%"
                            + "\n Wind Speed:"+wind+"m/s"
                            +" Pressure: "+ pressure+"hpa"
                            +"\n Description:"+description;


                    tvResult.setText(output);
                }

                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}