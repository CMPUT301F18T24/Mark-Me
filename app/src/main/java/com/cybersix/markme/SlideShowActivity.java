package com.cybersix.markme;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cybersix.markme.fragment.FullGalleryFragment;

public class SlideShowActivity extends AppCompatActivity {

    public static final String PHOTO_CONTENT = "ca.cybersix.photo";
    private static ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            int position = getIntent().getIntExtra(PHOTO_CONTENT,0);
            init(position);
        }
    }

    private void init(int currentItem) {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(SlideShowActivity.this));
        mPager.setCurrentItem(currentItem);
    }
}
