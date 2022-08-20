package ch.ost.rj.mge.v04.examples;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.TextView;

public class FragmentA extends Fragment {
    private static final String TEXT_KEY = "text";

    private FragmentCallback callback;
    private String textParameter = "No parameter passed into fragment";
    private TextView textView;

    public static FragmentA create(String text) {
        FragmentA fragment = new FragmentA();

        Bundle args = new Bundle();
        args.putString(TEXT_KEY, text);
        fragment.setArguments(args);

        return fragment;
    }

    public FragmentA() {
        super(R.layout.fragment_a);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callback = (FragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement " + FragmentCallback.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            textParameter = getArguments().getString(TEXT_KEY);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textView = view.findViewById(R.id.textview_in_first_fragment);
        textView.setOnClickListener(v -> callback.showMessage(textView.getText().toString()));
    }

    @Override
    public void onResume() {
        super.onResume();

        updateText(textParameter);
    }

    public void updateText(String text) {
        textView.setText(text);
    }
}