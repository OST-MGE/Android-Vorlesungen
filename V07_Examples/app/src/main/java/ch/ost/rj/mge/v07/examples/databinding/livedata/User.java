package ch.ost.rj.mge.v07.examples.databinding.livedata;

import androidx.lifecycle.MutableLiveData;

public class User {
    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<Integer> age = new MutableLiveData<>();

    public User(String firstName, String lastName, int age) {
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.age.setValue(age);
    }
}
