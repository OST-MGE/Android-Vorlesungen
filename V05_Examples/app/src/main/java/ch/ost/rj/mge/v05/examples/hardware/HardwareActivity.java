package ch.ost.rj.mge.v05.examples.hardware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import ch.ost.rj.mge.v05.examples.R;
import ch.ost.rj.mge.v05.examples.hardware.internet.RetrofitTodos;
import ch.ost.rj.mge.v05.examples.hardware.internet.TodoItem;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HardwareActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private static final String DEBUG_TAG = "MGE.V05";

    private OkHttpClient okHttpClient = new OkHttpClient();

    private TextView outputTextView;
    private Button subscribeLightSensorButton;
    private Button unsubscribeLightSensorButton;
    private Button subscribePositionUpdatesButton;
    private Button unsubscribePositionUpdatesButton;

    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hardware);

        // Sensors
        outputTextView = findViewById(R.id.textview_output);

        subscribeLightSensorButton = findViewById(R.id.button_lightsensor_subscribe);
        subscribeLightSensorButton.setOnClickListener(v -> subscribeToLightSensor());

        unsubscribeLightSensorButton = findViewById(R.id.button_lightsensor_unsubscribe);
        unsubscribeLightSensorButton.setEnabled(false);
        unsubscribeLightSensorButton.setOnClickListener(v -> unsubscribeFromLightSensor());

        // Vibration
        findViewById(R.id.button_vibrate).setOnClickListener(v -> vibrate());

        // Connectivity
        findViewById(R.id.button_network_status).setOnClickListener(v -> checkNetworkStatus());
        findViewById(R.id.button_internet_okhttp).setOnClickListener(v -> readDataWithOkHttp());
        findViewById(R.id.button_internet_retrofit).setOnClickListener(v -> readDataWithRetrofit());

        // Position
        subscribePositionUpdatesButton = findViewById(R.id.button_position_subscribe);
        subscribePositionUpdatesButton.setOnClickListener(v -> subscribeToPositionUpdates());

        unsubscribePositionUpdatesButton = findViewById(R.id.button_position_unsubscribe);
        unsubscribePositionUpdatesButton.setEnabled(false);
        unsubscribePositionUpdatesButton.setOnClickListener(v -> unsubscribeFromPositionUpdates());
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float lux = sensorEvent.values[0];
        setOutputText(lux + " lux");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void setOutputText(String text) {
        outputTextView.post(() -> outputTextView.setText(text));
    }

    private void subscribeToLightSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        subscribeLightSensorButton.setEnabled(false);
        unsubscribeLightSensorButton.setEnabled(true);
    }

    private void unsubscribeFromLightSensor() {
        sensorManager.unregisterListener(this);

        sensorManager = null;
        sensor = null;

        subscribeLightSensorButton.setEnabled(true);
        unsubscribeLightSensorButton.setEnabled(false);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        int sdkVersion = Build.VERSION.SDK_INT;

        if (sdkVersion >= Build.VERSION_CODES.Q) {
            VibrationEffect effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK);
            // VibrationEffect effect2 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK);
            // VibrationEffect effect3 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK);
            // VibrationEffect effect4 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK);

            vibrator.vibrate(effect);
        } else if (sdkVersion >= Build.VERSION_CODES.O) {
            //VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);

            long[] durations = new long[]{ 1000, 1000, 1000, 1000, 1000 };
            int[] amplitudes = new int[]{ 50, 100, 150, 200, 255 };
            VibrationEffect effect = VibrationEffect.createWaveform(durations, amplitudes, -1);

            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(500);
        }
    }

    private void checkNetworkStatus() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        if (activeNetwork != null) {
            int activeNetworkType = activeNetwork.getType();
            String type = activeNetworkType == ConnectivityManager.TYPE_WIFI ? "WiFi" : "Mobile";
            setOutputText("Active connection: " + type);
        } else {
            setOutputText("Active connection: none");
        }

        boolean isWifiConn = false;
        boolean isMobileConn = false;

        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }

            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }

        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
    }

    private void readDataWithOkHttp() {
        Runnable action = () -> {
            try {
                Request request = new Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/todos")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Type collectionType = new TypeToken<Collection<TodoItem>>(){}.getType();

                    Gson gson = new Gson();
                    Collection<TodoItem> todos = gson.fromJson(json, collectionType);

                    setOutputText("Received TODO-items: " + todos.size());
                }
            } catch (Exception e) {
                setOutputText("Could not read data: " + e.getMessage());
            }
        };

        new Thread(action).start();
    }

    private void readDataWithRetrofit() {
        Runnable action = () -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitTodos service = retrofit.create(RetrofitTodos.class);

            Call<List<TodoItem>> call = service.getItems();

            try {
                retrofit2.Response<List<TodoItem>> response = call.execute();
                setOutputText("Received TODO-items: " + response.body().size());
            } catch (IOException exception) {
                setOutputText("Could not read data: " + exception.getMessage());
            }
        };

        new Thread(action).start();

        // Alternative with integrated Backgrounding and Error-Handling
        /*
        call.enqueue(new Callback<List<TodoItem>>() {
            @Override
            public void onResponse(Call<List<TodoItem>> call, retrofit2.Response<List<TodoItem>> response) {
                setOutputText("Received TODO-items: " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<TodoItem>> call, Throwable t) {
                setOutputText("Could not read data: " + t.getMessage());            }
        });
        */
    }

    @SuppressLint("NewApi")
    private void subscribeToPositionUpdates() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 0);
            return;
        }

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = manager.getBestProvider(criteria, true);

        manager.requestLocationUpdates(provider, 5000L, 0, this);
        subscribePositionUpdatesButton.setEnabled(false);
        unsubscribePositionUpdatesButton.setEnabled(true);
    }

    private void unsubscribeFromPositionUpdates() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(this);
        subscribePositionUpdatesButton.setEnabled(true);
        unsubscribePositionUpdatesButton.setEnabled(false);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        setOutputText(location.getLatitude() + " | " + location.getLongitude());
    }
}