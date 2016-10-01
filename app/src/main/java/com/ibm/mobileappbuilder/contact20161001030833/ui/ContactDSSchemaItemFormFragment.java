
package com.ibm.mobileappbuilder.contact20161001030833.ui;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import com.ibm.mobileappbuilder.contact20161001030833.presenters.ContactFormPresenter;
import com.ibm.mobileappbuilder.contact20161001030833.R;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.ui.FormFragment;
import ibmmobileappbuilder.views.TextWatcherAdapter;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

public class ContactDSSchemaItemFormFragment extends FormFragment<ContactDSSchemaItem> {

    private CrudDatasource<ContactDSSchemaItem> datasource;

    public static ContactDSSchemaItemFormFragment newInstance(Bundle args){
        ContactDSSchemaItemFormFragment fr = new ContactDSSchemaItemFormFragment();
        fr.setArguments(args);

        return fr;
    }

    public ContactDSSchemaItemFormFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // the presenter for this view
        setPresenter(new ContactFormPresenter(
                (CrudDatasource) getDatasource(),
                this));

        addBehavior(pageViewBehavior("Contact"));
    }

    @Override
    protected ContactDSSchemaItem newItem() {
        return new ContactDSSchemaItem();
    }

    @Override
    protected int getLayout() {
        return R.layout.contact_form;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final ContactDSSchemaItem item, View view) {
        
        bindString(R.id.name, item.name, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.name = s.toString();
            }
        });
        
        
        bindString(R.id.email, item.email, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.email = s.toString();
            }
        });
        
        
        bindString(R.id.phone, item.phone, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.phone = s.toString();
            }
        });
        
        
        bindString(R.id.org, item.org, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.org = s.toString();
            }
        });
        
        
        bindString(R.id.comments, item.comments, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.comments = s.toString();
            }
        });
        
    }

    @Override
    public Datasource<ContactDSSchemaItem> getDatasource() {
      if (datasource != null) {
        return datasource;
      }
       datasource = CloudantDatasource.cloudantDatasource(
              CloudantDatastoresFactory.create("contact"),
              URI.create("https://ebdcef13-3935-4d30-a516-0c1a96e08cd3-bluemix:243f7083bf0ed81b64413d93c72a53a53a34127a8d2f84b7e8f1d7acd0eae76e@ebdcef13-3935-4d30-a516-0c1a96e08cd3-bluemix.cloudant.com/contact"),
              ContactDSSchemaItem.class,
              new SearchOptions(),
              null
      );
        return datasource;
    }
}

