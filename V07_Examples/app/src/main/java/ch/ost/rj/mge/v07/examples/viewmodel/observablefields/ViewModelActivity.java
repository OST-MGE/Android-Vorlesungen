package ch.ost.rj.mge.v07.examples.viewmodel.observablefields;

import android.os.Bundle;

import ch.ost.rj.mge.v07.examples.databinding.ActivityViewModelObservableFieldsBinding;
import ch.ost.rj.mge.v07.examples.viewmodel.ViewModelActivityBase;

public class ViewModelActivity extends ViewModelActivityBase {
    private ActivityViewModelObservableFieldsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModel viewModel = new ViewModel(user);

        binding = ActivityViewModelObservableFieldsBinding.inflate(getLayoutInflater());
        binding.setVm(viewModel);
        binding.dbAgePicker.setMinValue(0);
        binding.dbAgePicker.setMaxValue(120);

        setContentView(binding.getRoot());
    }
}