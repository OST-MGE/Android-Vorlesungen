package ch.ost.rj.mge.v07.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ch.ost.rj.mge.v07.myapplication.handler.EventHandler;
import ch.ost.rj.mge.v07.myapplication.model.UserLiveData;
import ch.ost.rj.mge.v07.myapplication.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MGE.V07.DEBUG";

    public static EventHandler handler;
    private ActivityDataBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserLiveData user = new UserLiveData("Thomas", "Kälin", 36);
        EventHandler handler = new EventHandler(this, user);

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding = ActivityDataBindingBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setUser(user);
        binding.setHandler(handler);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());

        // Gebraucht für Update-Demos
        // Variante 1: Observable Fields
        //user.age.addOnPropertyChangedCallback(
        //        new Observable.OnPropertyChangedCallback() {
        //            public void onPropertyChanged(Observable o, int i) {
        //                Log.d(LOG_TAG, "DataBindingActivity.OnPropertyChanged: user.age");
        //            }
        //        });

        // Variante 2: Observable Class
        //user.addOnPropertyChangedCallback(
        //        new Observable.OnPropertyChangedCallback() {
        //            public void onPropertyChanged(Observable o, int i) {
        //                Log.d(LOG_TAG, "DataBindingActivity.OnPropertyChanged: user");
        //            }
        //        }
        //);

        // Variante 3: LiveData
        user.age.observe(this, age -> Log.d(LOG_TAG, "DataBindingActivity.OnPropertyChanged: user.age"));

        DataBindingActivity.handler = handler;
    }
}