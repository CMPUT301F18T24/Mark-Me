package com.cybersix.markme;

import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class UserView implements Observer {
    private TextView username = null;
    private TextView phone = null;
    private TextView email = null;

    public UserView(TextView username, TextView phone, TextView email) {
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public void update(Observable o, Object arg) {
        UserModel model = (UserModel) o;

        updateUsername(model.getUsername());
        updateEmail(model.getEmail());
        updatePhone(model.getPhone());
    }

    public void updateUsername(String username) {
        if (this.username == null || username == null)
            return;
        this.username.setText(username);
    }

    public void updatePhone(String phone) {
        if (this.phone == null || phone == null)
            return;
        this.phone.setText(phone);
    }

    public void updateEmail(String email) {
        if (this.email == null || email == null)
            return;
        this.email.setText(email);
    }
}
