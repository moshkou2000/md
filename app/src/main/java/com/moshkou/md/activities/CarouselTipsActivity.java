package com.moshkou.md.activities;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.Button;

import com.moshkou.md.fragments.CarouselTipsFragment;
import com.moshkou.md.R;
import com.moshkou.md.services.Auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarouselTipsActivity extends FragmentActivity {


    private final Context context = this;
    
    private List<Fragment> pages = new ArrayList<>();
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout dots;
    private Button next;
    private Button done;
    private Button skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel_tips);

        pages.add(new CarouselTipsFragment());
        pages.add(new CarouselTipsFragment());
        pages.add(new CarouselTipsFragment());

        pager = findViewById(R.id.pager);
        dots = findViewById(R.id.dots);
        next = findViewById(R.id.next);
        done = findViewById(R.id.done);
        skip = findViewById(R.id.skip);

        // Instantiate a ViewPager and a PagerAdapter.
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        dots.setupWithViewPager(pager, true);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                if (i + 1 == pages.size()) {
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                } else {
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAlert();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAlert();
            }
        });
    }

    private void gotoAlert() {


//        JSONObject param = new JSONObject();
//        try {
//            param.put("email", "hassan.n@tractive.com.my");
//            param.put("password", "123456");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Auth.login(param);

//        Intent i = new Intent(context, AlertActivity.class);
//        startActivity(i);
//        finish();
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            // If the USER is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }
}
