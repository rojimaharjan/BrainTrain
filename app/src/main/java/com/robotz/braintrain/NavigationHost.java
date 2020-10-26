package com.robotz.braintrain;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface NavigationHost {

    void navigateTo(Fragment fragment, String title, boolean addToBackstack);

    void setUpToolbar(View view);
    void setUpBackBtn(View view);
}
