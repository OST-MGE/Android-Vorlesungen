package ch.ost.rj.mge.v07.examples.databinding.livedata;

import android.content.Context;

import ch.ost.rj.mge.v07.examples.databinding.EventHandlerBase;

public class EventHandler extends EventHandlerBase {
    private final User user;

    public EventHandler(Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    public void incrementAge() {
        user.age.setValue(user.age.getValue() + 1);
    }
}
