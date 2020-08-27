package com.robotz.braintrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationHost{
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }

        /*toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ProductGridFragment frag  = new ProductGridFragment();
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                MainActivity.this,
                findViewById(R.id.container_frag),
                new AccelerateDecelerateInterpolator(),
                MainActivity.this.getResources().getDrawable(R.drawable.shr_menu), // Menu open icon
                MainActivity.this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shr_toolbar_menu, menu);
        return true;
    }

    @Override
    public void navigateTo(Fragment fragment, String title, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
//        toolbar.setTitle(title);
    }

    public void gotoFragment(View view) {
        switch (view.getId()) {
            case (R.id.medication):
                navigateTo(new MedicationFragment(), "", true);
                break;

            case (R.id.userinfo):
                navigateTo(new UserInfoFragment(), "", true);
                break;

            case (R.id.game):
                navigateTo(new GameFragment(), "", true);
                break;

            case (R.id.licence):
                navigateTo(new LicenceFragment(), "", true);
                break;

            case (R.id.privacypolicy):
                navigateTo(new PrivacyPolicyFragment(), "", true);
                break;

            case (R.id.about):
                navigateTo(new AboutFragment(), "", true);
                break;

            case (R.id.logout):
                navigateTo(new LoginFragment(), "", false);
                break;
        }

    }

    public void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) MainActivity.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }


        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                MainActivity.this,
                view.findViewById(R.id.mainlayout),
                new AccelerateDecelerateInterpolator(),
                MainActivity.this.getResources().getDrawable(R.drawable.shr_menu), // Menu open icon
                MainActivity.this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon


    }

    /*@Override
    public void setDateValue(String date) {
        Fragment forXDaysFragment = new ForXDaysFragment();
        ((ForXDaysFragment) forXDaysFragment).ChangeDayValue(date);
    }*/
/*    @Override
    public void setDateValue(String date) {
        Fragment untildate = new UntilDateFragment();
        ((UntilDateFragment) untildate).setDateValue(date);
    }*/




/*    @Override
    public void setDayValue(int day) {
        forXDaysFragment.ChangeDayValue(day);
    }*/
}