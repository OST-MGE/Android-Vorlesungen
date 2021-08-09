package ch.ost.rj.mge.v04.examples;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (FragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + FragmentCallback.class.getSimpleName());
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
        View fragment = inflater.inflate(R.layout.fragment_a, container, false);

        textView = fragment.findViewById(R.id.textview_in_first_fragment);
        textView.setOnClickListener(v -> callback.showMessage(textView.getText().toString()));

        return fragment;
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