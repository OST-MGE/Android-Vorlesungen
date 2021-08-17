package ch.ost.rj.mge.v07.examples.viewmodel.baseclass;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import ch.ost.rj.mge.v07.examples.databinding.ActivityViewModelBaseClassBinding;
import ch.ost.rj.mge.v07.examples.viewmodel.ViewModelActivityBase;

public class ViewModelActivity extends ViewModelActivityBase {
    private ActivityViewModelBaseClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(user);
        ViewModel viewModel = new ViewModelProvider(this, factory).get(ViewModel.class);

        binding = ActivityViewModelBaseClassBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());
    }
}