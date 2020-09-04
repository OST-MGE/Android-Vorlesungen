package ch.ost.rj.mge.v03.examples;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OutputFragment extends Fragment {
    private static final String TEXT_KEY = "text";

    private OutputFragmentCallback callback;
    private String textParameter = "Hello from Fragment";
    private TextView textOutput;

    public static OutputFragment create(String text) {
        OutputFragment fragment = new OutputFragment();

        Bundle args = new Bundle();
        args.putString(TEXT_KEY, text);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OutputFragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OutputFragmentCallback");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_output, container, false);

        textOutput = fragment.findViewById(R.id.output_text);
        textOutput.setOnClickListener(v -> {
            callback.onTextTapped(textOutput.getText().toString());
        });

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateText(textParameter);
    }

    public void updateText(String text) {
        textOutput.setText(text);
    }
}