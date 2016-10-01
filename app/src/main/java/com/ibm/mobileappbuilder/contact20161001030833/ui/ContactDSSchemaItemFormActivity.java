

package com.ibm.mobileappbuilder.contact20161001030833.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ibmmobileappbuilder.ui.BaseDetailActivity;

/**
 * ContactDSSchemaItemFormActivity form activity
 */
public class ContactDSSchemaItemFormActivity extends BaseDetailActivity {
  	
  	@Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return ContactDSSchemaItemFormFragment.class;
    }
}


