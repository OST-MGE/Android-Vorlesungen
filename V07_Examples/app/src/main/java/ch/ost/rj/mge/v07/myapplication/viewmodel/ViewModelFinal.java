package ch.ost.rj.mge.v07.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ch.ost.rj.mge.v07.myapplication.model.UserPojo;

public class ViewModelFinal extends ViewModel {

    private final UserPojo user;

    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<Integer> age = new MutableLiveData();

    public ViewModelFinal(UserPojo user) {
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
