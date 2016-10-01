package com.ibm.mobileappbuilder.contact20161001030833.ui;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import ibmmobileappbuilder.ui.DrawerActivity;

import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;
import com.ibm.mobileappbuilder.contact20161001030833.R;

public class ContactMain extends DrawerActivity {

    private final SparseArray<Class<? extends Fragment>> sectionFragments = new SparseArray<>();
    {
                sectionFragments.append(R.id.entry0, ContactFragment.class);
    }

    @Override
    public SparseArray<Class<? extends Fragment>> getSectionFragmentClasses() {
      return sectionFragments;
    }

}

