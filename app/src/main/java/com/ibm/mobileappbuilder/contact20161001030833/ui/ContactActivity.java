

package com.ibm.mobileappbuilder.contact20161001030833.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.contact20161001030833.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * ContactActivity list activity
 */
public class ContactActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.contactActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return ContactFragment.class;
    }

}

