package com.cybersix.markme.observer;

import android.view.View;
import android.widget.TextView;

import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.model.UserModel;

import java.util.Observable;
import java.util.Observer;

public class UserObserver implements Observer {
    private TextView usernameView = null;
    private TextView phoneView = null;
    private TextView emailView = null;
    private View modifierButton = null;
    private UserProfileController userController = null;

    private final Runnable modifierAction = new Runnable() {
        @Override
        public void run() {
            if (userController == null)
                return;

            userController.modifyModel(UserObserver.this);
        }
    };

    public UserObserver(UserProfileController controller) {
        userController = controller;
    }

    public UserObserver(UserProfileController controller, TextView username, TextView phone, TextView email, View modifierButton) {
        setUsernameView(username);
        setPhoneView(phone);
        setEmailView(email);
        setModifierButton(modifierButton);
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

    public View getModifierButton() {
        return modifierButton;
    }

    public void setModifierButton(View modifierButton) {
        this.modifierButton = modifierButton;

        if (this.modifierButton == null)
            return;

        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierAction.run();
            }
        });
    }

    public void setOnModifierPressed(final View.OnClickListener listener) {
        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierAction.run();
                listener.onClick(v);
            }
        });
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
