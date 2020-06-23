package com.example.coronavirusproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;


public class GeneralFragment extends Fragment {

    ImageView general_img;
    TextView injury_count;
    TextView death_count;
    TextView recovery_count;
    RequestQueue requestQueue;
    ArrayList<Country> topCountry;
    RecyclerView top_country_recyclerview;
    Button btn_more_details;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_more_details=getActivity().findViewById(R.id.btn_more_details);
        top_country_recyclerview=getActivity().findViewById(R.id.top_country_recyclerview);
        requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        topCountry=new ArrayList<Country>();
        general_img=getActivity().findViewById(R.id.general_img);

        injury_count=getActivity().findViewById(R.id.injury_count);
        death_count=getActivity().findViewById(R.id.death_count);
        recovery_count=getActivity().findViewById(R.id.recovery_count);

        ImageView imageView=getActivity().findViewById(R.id.general_img);
//        Bitmap bitImg = getRoundedCornerImage(BitmapFactory.decodeResource(getResources(),R.drawable.av));
//        imageView.setImageBitmap(bitImg);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://coronavirus-19-api.herokuapp.com/all",null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String allcases=response.getString("cases");
                            injury_count.setText(format(Integer.parseInt(allcases)));
                            String alldeaths=response.getString("deaths");
                            death_count.setText(format(Integer.parseInt(alldeaths)));
                            String allrecovered=response.getString("recovered");
                            recovery_count.setText(format(Integer.parseInt(allrecovered)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://coronavirus-19-api.herokuapp.com/countries",null,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=1;i<=3;i++){
                            try {
                                JSONObject o= response.getJSONObject(i);
                                Country country=new Country();
                                String name_country=o.getString("country");
                                country.setName(name_country);
                                String allcases=o.getString("cases");
                                country.setCases(format(Integer.parseInt(allcases)));
                                String alldeaths=o.getString("deaths");
                                country.setDeaths(format(Integer.parseInt(alldeaths)));
                                String allrecovered=o.getString("recovered");
                                country.setRecovered(format(Integer.parseInt(allrecovered)));
                                topCountry.add(country);
                                if(i==3){
                                    adapter_top_country_recyclerview adapterTopCountryRecyclerview=new adapter_top_country_recyclerview(topCountry,R.layout.item_top_country,getActivity().getApplicationContext());
                                    top_country_recyclerview.setAdapter(adapterTopCountryRecyclerview);
                                    LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity().getApplicationContext());
                                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    top_country_recyclerview.setLayoutManager(linearLayoutManager);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        }).start();



        btn_more_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),more_details.class);
                startActivity(intent);
            }
        });
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, " ألف"); //K
        suffixes.put(1_000_000L, " مليون"); // M
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static Bitmap getRoundedCornerImage(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 200;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
//        paint.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public GeneralFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general, container, false);
    }
}
