package ch.ost.rj.mge.v07.examples.viewmodel.baseclass;

import androidx.lifecycle.ViewModelProvider;

import ch.ost.rj.mge.v07.examples.viewmodel.User;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final User user;

    public ViewModelFactory(User user) {
        this.user = user;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        return (T) new ViewModel(user);
    }
}
