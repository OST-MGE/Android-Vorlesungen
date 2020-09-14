package ch.ost.rj.mge.v07.myapplication.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ch.ost.rj.mge.v07.myapplication.model.UserPojo;

public class ViewModelFinalFactory implements ViewModelProvider.Factory {
    private final UserPojo user;

    public ViewModelFinalFactory(UserPojo user) {
        this.user = user;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ViewModelFinal(user);
    }
}
