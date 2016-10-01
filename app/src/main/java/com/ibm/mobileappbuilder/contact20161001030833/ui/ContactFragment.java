package com.ibm.mobileappbuilder.contact20161001030833.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ibm.mobileappbuilder.contact20161001030833.presenters.ContactPresenter;
import com.ibm.mobileappbuilder.contact20161001030833.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.SelectionBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ui.ListGridFragment;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.ViewHolder;
import java.util.List;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;
import ibmmobileappbuilder.mvp.view.CrudListView;
import ibmmobileappbuilder.ds.CrudDatasource;
import android.content.Intent;
import ibmmobileappbuilder.util.Constants;

import static ibmmobileappbuilder.util.NavigationUtils.generateIntentToAddOrUpdateItem;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

/**
 * "ContactFragment" listing
 */
public class ContactFragment extends ListGridFragment<ContactDSSchemaItem> implements CrudListView<ContactDSSchemaItem> {

    private CrudDatasource<ContactDSSchemaItem> datasource;

    // "Add" button
    private FabBehaviour fabBehavior;

    public static ContactFragment newInstance(Bundle args) {
        ContactFragment fr = new ContactFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
addBehavior(pageViewBehavior("Contact"));
        setPresenter(new ContactPresenter(
            (CrudDatasource) getDatasource(),
            this
        ));
        // Multiple selection
        SelectionBehavior<ContactDSSchemaItem> selectionBehavior = new SelectionBehavior<>(
            this,
            R.string.remove_items,
            R.drawable.ic_delete_alpha);

        selectionBehavior.setCallback(new SelectionBehavior.Callback<ContactDSSchemaItem>() {
            @Override
            public void onSelected(List<ContactDSSchemaItem> selectedItems) {
                getPresenter().deleteItems(selectedItems);
            }
        });
        addBehavior(selectionBehavior);
        // FAB button
        fabBehavior = new FabBehaviour(this, R.drawable.ic_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().addForm();
            }
        });
        addBehavior(fabBehavior);
    }

    protected SearchOptions getSearchOptions() {
      SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
      return searchOptionsBuilder.build();
    }


    /**
    * Layout for the list itselft
    */
    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    /**
    * Layout for each element in the list
    */
    @Override
    protected int getItemLayout() {
        return R.layout.contact_item;
    }

    @Override
    protected Datasource<ContactDSSchemaItem> getDatasource() {
      if (datasource != null) {
        return datasource;
      }
      datasource = CloudantDatasource.cloudantDatasource(
              CloudantDatastoresFactory.create("contact"),
              URI.create("https://ebdcef13-3935-4d30-a516-0c1a96e08cd3-bluemix:243f7083bf0ed81b64413d93c72a53a53a34127a8d2f84b7e8f1d7acd0eae76e@ebdcef13-3935-4d30-a516-0c1a96e08cd3-bluemix.cloudant.com/contact"),
              ContactDSSchemaItem.class,
              getSearchOptions(),
              null
      );
      return datasource;
    }

    @Override
    protected void bindView(ContactDSSchemaItem item, View view, int position) {
        
        TextView title = ViewHolder.get(view, R.id.title);
        
        if (item.name != null){
            title.setText(item.name);
            
        }
        
        TextView subtitle = ViewHolder.get(view, R.id.subtitle);
        
        if (item.email != null){
            subtitle.setText(item.email);
            
        }
    }

    @Override
    protected void itemClicked(final ContactDSSchemaItem item, final int position) {
        fabBehavior.hide(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getPresenter().detail(item, position);
            }
        });
    }

    @Override
    public void showDetail(ContactDSSchemaItem item, int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.ITEMPOS, position);
        args.putParcelable(Constants.CONTENT, item);
        Intent intent = new Intent(getActivity(), ContactDetailActivity.class);
        intent.putExtras(args);

        if (!getResources().getBoolean(R.bool.tabletLayout)) {
            startActivityForResult(intent, Constants.DETAIL);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void showAdd() {
        startActivityForResult(generateIntentToAddOrUpdateItem(null,
                        0,
                        getActivity(),
                        ContactDSSchemaItemFormActivity.class
                ), Constants.MODE_CREATE
        );
    }

    @Override
    public void showEdit(ContactDSSchemaItem item, int position) {
    startActivityForResult(
                generateIntentToAddOrUpdateItem(item,
                        position,
                        getActivity(),
                        ContactDSSchemaItemFormActivity.class
                ), Constants.MODE_EDIT
        );
    }
}

