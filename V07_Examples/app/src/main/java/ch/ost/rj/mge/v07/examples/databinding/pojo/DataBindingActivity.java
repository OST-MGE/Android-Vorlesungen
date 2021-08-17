package ch.ost.rj.mge.v07.examples.databinding.pojo;

import android.os.Bundle;

import ch.ost.rj.mge.v07.examples.databinding.ActivityDataBindingPojoBinding;
import ch.ost.rj.mge.v07.examples.databinding.DataBindingActivityBase;

public class DataBindingActivity extends DataBindingActivityBase {
    private ActivityDataBindingPojoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = new User(Firstname, Lastname, Age);
        EventHandler handler = new EventHandler(this, user);

        // Alternative Syntax zur Erzeugung des Binding-Objektes
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_pojo);

        binding = ActivityDataBindingPojoBinding.inflate(getLayoutInflater());
        binding.setUser(user);
        binding.setHandler(handler);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());
    }
}