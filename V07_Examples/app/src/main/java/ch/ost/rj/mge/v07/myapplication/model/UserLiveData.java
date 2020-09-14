package ch.ost.rj.mge.v07.myapplication.model;

import androidx.lifecycle.MutableLiveData;

public class UserLiveData {
    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<Integer> age = new MutableLiveData<>();

    public UserLiveData(String firstName, String lastName, int age) {
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.age.setValue(age);
    }
}
