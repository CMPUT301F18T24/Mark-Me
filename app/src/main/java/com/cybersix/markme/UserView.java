package com.cybersix.markme;

import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class UserView implements Observer {
    private TextView usernameView = null;
    private TextView phoneView = null;
    private TextView emailView = null;

    public UserView() {

    }

    public UserView(TextView username, TextView phone, TextView email) {
        this.usernameView = username;
        this.phoneView = phone;
        this.emailView = email;
    }

    public TextView getUsernameView() {
        return usernameView;
    }

    public void setUsernameView(TextView usernameView) {
        this.usernameView = usernameView;
    }

    public TextView getPhoneView() {
        return phoneView;
    }

    public void setPhoneView(TextView phoneView) {
        this.phoneView = phoneView;
    }

    public TextView getEmailView() {
        return emailView;
    }

    public void setEmailView(TextView emailView) {
        this.emailView = emailView;
    }

    public void update(Observable o, Object arg) {
        UserModel model = (UserModel) o;

        updateUsername(model.getUsername());
        updateEmail(model.getEmail());
        updatePhone(model.getPhone());
    }

    public void updateUsername(String username) {
        if (this.usernameView == null || username == null)
            return;
        this.usernameView.setText(username);
    }

    public void updatePhone(String phone) {
        if (this.phoneView == null || phone == null)
            return;
        this.phoneView.setText(phone);
    }

    public void updateEmail(String email) {
        if (this.emailView == null || email == null)
            return;
        this.emailView.setText(email);
    }
}
