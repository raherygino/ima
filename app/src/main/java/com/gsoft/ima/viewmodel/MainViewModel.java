package com.gsoft.ima.viewmodel;

import static com.gsoft.ima.constants.data.KeysModelConstants.RESULTS;
import static com.gsoft.ima.constants.network.NetworkConstants.LOCATION_PERMISSION_REQUEST_CODE;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gsoft.ima.BR;
import com.gsoft.ima.api.LocationTrack;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.models.Venue;
import com.gsoft.ima.utils.VenueParser;
import android.Manifest;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class MainViewModel extends BaseObservable {

    Double latitude = 41.8781; //Default Latitude
    Double longitude = -87.6298; //Default Longitude

    private final Context context;
    private final MutableLiveData<String> _snackbarMessage = new MutableLiveData<>();
    public LiveData<String> snackbarMessage = _snackbarMessage;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    public ObservableField<String> city = new ObservableField<>();
    public ObservableList<Venue> venues = new ObservableArrayList<>();
    public MutableLiveData<String> queryValue = new MutableLiveData<>();

    public MainViewModel(Context mContext) {
        context = mContext;
        LocationTrack locationTrack = new LocationTrack(context);

        if (locationTrack.canGetLocation()) {
            if (locationTrack.getLatitude() != 0) {
                longitude = locationTrack.getLongitude();
                latitude = locationTrack.getLatitude();
            }
        } else {
            locationTrack.showSettingsAlert();
        }
        getMyLocation();
    }


    public void setupAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView, Context context) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, new String[]{
                "Bakery", "Breakfast", "Coffee", "Grocery", "Hotel", "Restaurant"
        });
        autoCompleteTextView.setAdapter(adapter);
        queryValue.observeForever(s -> {
            adapter.getFilter().filter(s);
        });
    }

    @Bindable
    public ObservableList<Venue> getVenues() {
        return venues;
    }

    public void searchVenues() {
        _isLoading.setValue(true);
        _snackbarMessage.setValue("");
        venues.clear();
        notifyPropertyChanged(BR.venues);

        Call<ResponseBody> query = RetrofitClient.searchVenues(queryValue.getValue(), city.get());
        query.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().source().readUtf8());
                        JSONArray results = json.getJSONArray(RESULTS);

                        if (results.length() == 0) {
                            if (queryValue.getValue() == null || city.get() == null) {
                                _snackbarMessage.setValue("City and Query required");
                            } else {
                                _snackbarMessage.setValue("No result for \""+queryValue.getValue()+"\" at \""+city.get()+"\"");
                            }
                        }

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject venueObject = results.getJSONObject(i);
                            venues.add(new VenueParser().Parse(venueObject));
                        }
                        notifyPropertyChanged(BR.venues);

                    } catch (IOException | JSONException e) {
                        _snackbarMessage.setValue("Check your network");
                    }
                } else {
                    _snackbarMessage.setValue(response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _snackbarMessage.setValue(t.getMessage());
                _isLoading.setValue(false);
            }
        });
    }

    public void getMyLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider callin
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(context)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            longitude = locationResult.getLocations().get(latestlocIndex).getLongitude();
                            searchCity();
                        }
                    }
                }, Looper.getMainLooper());

    }


    public void searchCity() {
        Call<ResponseBody> getCity = RetrofitClient.searchCity(Double.toString(latitude), Double.toString(longitude));
        getCity.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().source().readUtf8());
                        JSONArray results = json.getJSONArray(RESULTS);
                        String venueCity = null;

                        for (int i = 0; i < results.length(); i++) {
                            Venue venue = new VenueParser().Parse(results.getJSONObject(i));
                            if (venue.location.locality != null && venueCity == null) {
                                venueCity = venue.location.locality;
                            }
                        }
                        city.set(venueCity);

                    } catch (IOException | JSONException e) {
                        _snackbarMessage.setValue(e.getMessage());
                    }
                } else {
                    _snackbarMessage.setValue("City not found");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _snackbarMessage.setValue("Check your network");
            }
        });
    }

    public void clearResult() {
        venues.clear();
        notifyPropertyChanged(BR.venues);
    }
}