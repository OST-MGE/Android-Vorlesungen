package ch.ost.rj.mge.v07.examples.viewmodel.baseclass;

import androidx.lifecycle.MutableLiveData;

import ch.ost.rj.mge.v07.examples.viewmodel.User;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private final User user;

    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<Integer> age = new MutableLiveData();

    public ViewModel(User user) {
        this.user = user;

        firstName.setValue(user.firstName);
        lastName.setValue(user.lastName);
        age.setValue(user.age);
    }

    public void save() {
        user.firstName = firstName.getValue();
        user.lastName = lastName.getValue();
        user.age = age.getValue();
    }

    public void incrementAge() {
        int newAge = age.getValue() + 1;
        age.setValue(newAge);
    }
}
