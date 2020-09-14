package ch.ost.rj.mge.v07.myapplication.handler;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import ch.ost.rj.mge.v07.myapplication.SecondActivity;
import ch.ost.rj.mge.v07.myapplication.model.UserLiveData;

public class EventHandler {

    private final Context context;
    private final UserLiveData user;

    public EventHandler(Context context, UserLiveData user) {
        this.context = context;
        this.user = user;
    }

    public void onButtonClicked(View view) {
        Toast.makeText(view.getContext(), "EventHandler.onButtonClicked", Toast.LENGTH_SHORT).show();
    }

    public void onButtonClicked(View view, String text) {
        Toast.makeText(view.getContext(), "EventHandler.onButtonClicked: " + text, Toast.LENGTH_SHORT).show();
    }

    public void incrementAge() {
        // Observable Fields
        //int newAge = user.age.get() + 1;
        //user.age.set(newAge);

        // Observable Class
        //user.setAge(user.getAge() + 1);

        // Live Data
        user.age.setValue(user.age.getValue() + 1);
    }

    public void startSecondActivity() {
        context.startActivity(new Intent(context, SecondActivity.class));
    }
}
