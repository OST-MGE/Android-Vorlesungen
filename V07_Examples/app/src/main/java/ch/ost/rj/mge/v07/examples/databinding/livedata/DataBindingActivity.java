package ch.ost.rj.mge.v07.examples.databinding.livedata;

import android.os.Bundle;

import ch.ost.rj.mge.v07.examples.databinding.ActivityDataBindingLiveDataBinding;
import ch.ost.rj.mge.v07.examples.databinding.DataBindingActivityBase;

public class DataBindingActivity extends DataBindingActivityBase {
    private ActivityDataBindingLiveDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = new User(Firstname, Lastname, Age);
        EventHandler handler = new EventHandler(this, user);

        // Alternative Syntax zur Erzeugung des Binding-Objektes
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_live_data);

        binding = ActivityDataBindingLiveDataBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setUser(user);
        binding.setHandler(handler);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());

        // Benachrichtigung im Code abonnieren
        user.age.observe(this, age -> Log("OnPropertyChanged", "user.age has been updated"));
    }
}