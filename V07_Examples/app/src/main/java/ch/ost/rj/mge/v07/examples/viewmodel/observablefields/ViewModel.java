package ch.ost.rj.mge.v07.examples.viewmodel.observablefields;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import ch.ost.rj.mge.v07.examples.viewmodel.User;

public class ViewModel {

    private final User user;

    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();

    public ViewModel(User user) {
        this.user = user;

        firstName.set(user.firstName);
        lastName.set(user.lastName);
        age.set(user.age);
    }

    public void save() {
        user.firstName = firstName.get();
        user.lastName = lastName.get();
        user.age = age.get();
    }

    public void incrementAge() {
        int newAge = age.get() + 1;
        age.set(newAge);
    }
}
