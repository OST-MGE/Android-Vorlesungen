package ch.ost.rj.mge.v07.myapplication.model;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class UserObservableFields {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();

    public UserObservableFields(String firstName, String lastName, int age) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.age.set(age);
    }
}
