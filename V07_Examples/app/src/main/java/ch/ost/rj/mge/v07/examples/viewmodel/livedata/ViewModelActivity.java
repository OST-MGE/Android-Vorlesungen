package ch.ost.rj.mge.v07.examples.viewmodel.livedata;

import android.os.Bundle;

import ch.ost.rj.mge.v07.examples.databinding.ActivityViewModelLiveDataBinding;
import ch.ost.rj.mge.v07.examples.viewmodel.ViewModelActivityBase;

public class ViewModelActivity extends ViewModelActivityBase {
    private ActivityViewModelLiveDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModel viewModel = new ViewModel(user);

        binding = ActivityViewModelLiveDataBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());
    }
}