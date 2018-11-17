/**
 * Jose: This is the generic list activity that will be shared amoung the problems activity
 *       and the records activity
 *
 *  Description: This will be the base activity for the list view for both the problems list
 *               and the records list
 *  Version 0.1
 *
 *  Date: 2018-11-10
 *
 *  Copyright notice: TODO: what is the copyright among us?
 */
package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.gps:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.body:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.gallery:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.list:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
