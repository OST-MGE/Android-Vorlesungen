package ch.ost.rj.mge.v03.examples.model;

import java.util.ArrayList;

public final class UserManager {
    private static ArrayList<User> users;

     static {
        UserManager.users = new ArrayList<User>();

        for (int i = 1; i <= 100; i++) {
            UserManager.users.add(new User("Name " + i, i));
        }
    }

    public static ArrayList<User> getUsers() {
         return UserManager.users;
    }
}
