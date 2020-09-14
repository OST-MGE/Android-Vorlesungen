package ch.ost.rj.mge.v07.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import ch.ost.rj.mge.v07.myapplication.databinding.ActivityViewModelBinding;
import ch.ost.rj.mge.v07.myapplication.model.UserPojo;
import ch.ost.rj.mge.v07.myapplication.viewmodel.ViewModelFinal;
import ch.ost.rj.mge.v07.myapplication.viewmodel.ViewModelFinalFactory;

public class ViewModelActivity extends AppCompatActivity {
    private ActivityViewModelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserPojo user = new UserPojo("Thomas", "Kälin", 36);

        //ViewModelObservableFields viewModel = new ViewModelObservableFields(user);
        //ViewModelLiveData viewModel = new ViewModelLiveData(user);

        ViewModelFinalFactory factory = new ViewModelFinalFactory(user);
        ViewModelFinal viewModel = new ViewModelProvider(this, factory).get(ViewModelFinal.class);

        binding = ActivityViewModelBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());
    }
}