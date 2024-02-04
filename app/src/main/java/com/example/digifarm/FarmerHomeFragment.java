package com.example.digifarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FarmerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FarmerHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private TextView cityTextView, weatherTextView,temperatureTextView;
    private RequestQueue requestQueue;

    TextView namex;

    public FarmerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FarmerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FarmerHomeFragment newInstance(String param1, String param2) {
        FarmerHomeFragment fragment = new FarmerHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        cityTextView = view.findViewById(R.id.cityTextView);
        weatherTextView = view.findViewById(R.id.weatherTextView);
        temperatureTextView=view.findViewById(R.id.temperatureTextView);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(getContext());

        // Call the method to fetch weather data
        fetchWeatherData("498295b20f96fcb697093ffec40b9c6f", "Jalgaon");

        firebaseDatabase = FirebaseDatabase.getInstance();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            //String uid = currentUser.getUid();
            Log.d("UID", "Current UID: " + uid);

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors

                    Toast.makeText(getActivity(), "Failed to read user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // No user is signed in
            Toast.makeText(getActivity(), "No user is signed in", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
    private void fetchWeatherData(String apiKey, String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response and update UI
                            String cityName = response.getString("name");
                            String weatherDescription = response.getJSONArray("weather")
                                    .getJSONObject(0)
                                    .getString("description");





                            // Extract temperature in Celsius
                            JSONObject mainObject = response.getJSONObject("main");
                            double temperatureKelvin = mainObject.getDouble("temp");
                            double temperatureCelsius = temperatureKelvin - 273.15; // Convert to Celsius

                            // Update UI
                            cityTextView.setText(cityName);
                            weatherTextView.setText(weatherDescription);
                            temperatureTextView.setText("Temperature: " + String.format("%.2f", temperatureCelsius) + " °C");


                            // You can add more TextViews to display other weather information

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }
}