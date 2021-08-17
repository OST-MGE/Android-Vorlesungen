package ch.ost.rj.mge.v07.examples.databinding.observableclass;

import android.os.Bundle;

import androidx.databinding.Observable;

import ch.ost.rj.mge.v07.examples.databinding.ActivityDataBindingObservableClassBinding;
import ch.ost.rj.mge.v07.examples.databinding.DataBindingActivityBase;

public class DataBindingActivity extends DataBindingActivityBase {
    private ActivityDataBindingObservableClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = new User(Firstname, Lastname, Age);
        EventHandler handler = new EventHandler(this, user);

        // Alternative Syntax zur Erzeugung des Binding-Objektes
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_observable_class);

        binding = ActivityDataBindingObservableClassBinding.inflate(getLayoutInflater());
        binding.setUser(user);
        binding.setHandler(handler);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());

        // Benachrichtigung im Code abonnieren
        user.addOnPropertyChangedCallback(
            new Observable.OnPropertyChangedCallback() {
                public void onPropertyChanged(Observable o, int i) {
                    Log("OnPropertyChanged", "user.age has been updated");
                }
            }
        );
    }
}