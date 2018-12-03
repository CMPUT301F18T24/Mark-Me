package com.cybersix.markme.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersix.markme.R;
import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.io.GeneralIO;
import com.cybersix.markme.io.OnTaskComplete;
import com.cybersix.markme.model.DataModel;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public class TransferAccountFragment extends Fragment {

    UserModel userModel = null;
    UserProfileController userController = null;
    private DataModel mData = null;

    @Override
    public void onCreate(@NonNull Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_transfer_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userModel = ((MainActivity) getActivity()).getUser();
        userController = new UserProfileController(userModel);

        initUI();
    }

    public void initUI() {
        // Initialize the button listeners
        Button transferAccountButton = (Button) getActivity().findViewById(R.id.fragment_transfer_account_transferAccountButton);
        transferAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferAccount();
            }
        });

        Button generateCodeButton = (Button) getActivity().findViewById(R.id.fragment_transfer_account_transferCodeGenerateButton);
        generateCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCode();
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText("Transfer Account");
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });
    }

    public void transferAccount() {
        // Query elastic search for the username
        // If exists, change the user model, rewrite the password file
        // If not, print error.
        TextView transferAccountCode = getActivity().findViewById(R.id.fragment_transfer_account_transferAccountText);
        String username = userController.transferAccount(transferAccountCode.getText().toString());
        Toast toast;

        if (username != null) {
            userController.setModel(userModel);
            ((MainActivity) getActivity()).setUser(username);
            userController.updateSecurityTokenFile(this.getContext());
            toast = Toast.makeText(getActivity(), getString(R.string.transfer_account_success), Toast.LENGTH_SHORT);
            Log.d("TransferAccountFragment: ", "Successful");

        } else {
            // Toast that nothing happens
            toast = Toast.makeText(getActivity(), getString(R.string.transfer_account_failure), Toast.LENGTH_SHORT);
        }

        toast.show();

    }

    public void generateCode() {
        // Generate a short code with current usermodel username.
        // Assumption: User is logged in.
        TextView transferCodeDisplay = getActivity().findViewById(R.id.fragment_transfer_account_transferCodeGenerateText);
        transferCodeDisplay.setText(userController.generateTransferCode());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        userController = null;
        userModel = null;
    }

}
