package com.example.coronavirusproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class more_details extends AppCompatActivity {
    RequestQueue requestQueue;
    ArrayList<String> Countrys;
    SearchableSpinner searchableSpinner;
    TextView name_country;
    TextView death_country;
    TextView recovery_country;
    TextView injury_country;
    TextView active_country;
    TextView critical_country;
    TextView total_tests_country;
    TextView today_cases_country;
    TextView today_deaths_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        name_country=findViewById(R.id.name_country);
        death_country=findViewById(R.id.death_country);
        recovery_country=findViewById(R.id.recovery_country);
        injury_country=findViewById(R.id.injury_country);
        active_country=findViewById(R.id.active_country);
        critical_country=findViewById(R.id.critical_country);
        total_tests_country=findViewById(R.id.total_tests_country);
        today_cases_country=findViewById(R.id.today_cases_country);
        today_deaths_country=findViewById(R.id.today_deaths_country);

        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Countrys=new ArrayList<String>();
        searchableSpinner=findViewById(R.id.searchableSpinner);
        Countrys.add("قم بإختيار اسم الدولة");
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://coronavirus-19-api.herokuapp.com/countries",null,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<=response.length();i++){
                            try {
                                JSONObject o= response.getJSONObject(i);
                                String name_country=o.getString("country");
                                Countrys.add(name_country);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        }).start();

        searchableSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Countrys));
        searchableSpinner.setTitle("");
        searchableSpinner.setPositiveButton("إغلاق");
        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    TextView oTextView =(TextView) parent.getChildAt(0);
                    oTextView.setTextColor(Color.WHITE);
                    oTextView.setTextSize(25);
                    oTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }else{
                    TextView oTextView =(TextView)view;
                    oTextView.setTextColor(Color.WHITE);
                    oTextView.setTextSize(25);
                    oTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    String Selectcountry=parent.getItemAtPosition(position).toString();
                    final String path="https://coronavirus-19-api.herokuapp.com/countries/"+Selectcountry;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObjectRequest jsonObject=new JsonObjectRequest(Request.Method.GET, path,null,new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String country= response.getString("country");
                                        name_country.setText(country);


                                        if(!response.isNull("deaths")){
                                            String deathcountry= response.getString("deaths");
                                            String deathcountry_num= NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(deathcountry));
                                            death_country.setText(deathcountry_num);
                                        }else{
                                            death_country.setText("");
                                        }

                                        if(!response.isNull("recovered")){
                                            String recoverycountry= response.getString("recovered");
                                            String recoverycountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(recoverycountry));
                                            recovery_country.setText(recoverycountry_num);
                                        }else{
                                            recovery_country.setText("");
                                        }

                                        if(!response.isNull("cases")){
                                            String injurycountry= response.getString("cases");
                                            String injurycountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(injurycountry));
                                            injury_country.setText(injurycountry_num);
                                        }else{
                                            injury_country.setText("");
                                        }

                                        if(!response.isNull("active")){
                                            String activecountry= response.getString("active");
                                            String activecountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(activecountry));
                                            active_country.setText(activecountry_num);
                                        }else{
                                            active_country.setText("");
                                        }

                                        if(!response.isNull("critical")){
                                            String criticalcountry= response.getString("critical");
                                            String criticalcountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(criticalcountry));
                                            critical_country.setText(criticalcountry_num);
                                        }else{
                                            critical_country.setText("");
                                        }

                                        if(!response.isNull("todayCases")){
                                            String todaycasescountry= response.getString("todayCases");
                                            String todaycasescountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(todaycasescountry));
                                            today_cases_country.setText(todaycasescountry_num);
                                        }else{
                                            today_cases_country.setText("");
                                        }

                                        if(!response.isNull("todayDeaths")){
                                            String todaydeathscountry= response.getString("todayDeaths");
                                            String todaydeathscountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(todaydeathscountry));
                                            today_deaths_country.setText(todaydeathscountry_num);
                                        }else{
                                            today_deaths_country.setText("");
                                        }

                                        if(!response.isNull("totalTests")){
                                            String totaltestscountry= response.getString("totalTests");
                                            String totaltestscountry_num=NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(totaltestscountry));
                                            total_tests_country.setText(totaltestscountry_num);
                                        }else{
                                            total_tests_country.setText("");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                            requestQueue.add(jsonObject);
                        }
                    }).start();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
