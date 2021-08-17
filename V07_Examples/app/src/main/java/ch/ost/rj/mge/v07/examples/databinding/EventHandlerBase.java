package ch.ost.rj.mge.v07.examples.databinding;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public abstract class EventHandlerBase {
    private final Context context;

    protected EventHandlerBase(Context context) {
        this.context = context;
    }

    public void incrementAge(View view) {
        incrementAge();
    }

    public abstract void incrementAge();

    public void startSecondActivity() {
        SecondDataBindingActivity.eventHandler = this;
        context.startActivity(new Intent(context, SecondDataBindingActivity.class));
    }
}
