
package com.ibm.mobileappbuilder.contact20161001030833.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.ibm.mobileappbuilder.contact20161001030833.presenters.ContactDetailPresenter;
import com.ibm.mobileappbuilder.contact20161001030833.R;
import ibmmobileappbuilder.actions.ActivityIntentLauncher;
import ibmmobileappbuilder.actions.MailAction;
import ibmmobileappbuilder.actions.PhoneAction;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.ShareBehavior;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;
import ibmmobileappbuilder.analytics.AnalyticsReporter;
import static ibmmobileappbuilder.analytics.model.AnalyticsInfo.Builder.analyticsInfo;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;

public class ContactDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<ContactDSSchemaItem> implements ShareBehavior.ShareListener  {

    private CrudDatasource<ContactDSSchemaItem> datasource;
    private AnalyticsReporter analyticsReporter;
    public static ContactDetailFragment newInstance(Bundle args){
        ContactDetailFragment fr = new ContactDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public ContactDetailFragment(){
        super();
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

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
addBehavior(pageViewBehavior("ContactDetail"));
        analyticsReporter = AnalyticsReporterInjector.analyticsReporter(getActivity());
        // the presenter for this view
        setPresenter(new ContactDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button
        addBehavior(new FabBehaviour(this, R.drawable.ic_edit_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailCrudPresenter<ContactDSSchemaItem>) getPresenter()).editForm(getItem());
            }
        }));
        addBehavior(new ShareBehavior(getActivity(), this));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.contactdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final ContactDSSchemaItem item, View view) {
        if (item.name != null){
            
            TextView view0 = (TextView) view.findViewById(R.id.view0);
            view0.setText(item.name);
            
        }
        if (item.email != null){
            
            TextView view1 = (TextView) view.findViewById(R.id.view1);
            view1.setText(item.email);
            bindAction(view1, new MailAction(
            new ActivityIntentLauncher()
            , item.email));
        }
        if (item.phone != null){
            
            TextView view2 = (TextView) view.findViewById(R.id.view2);
            view2.setText(item.phone);
            bindAction(view2, new PhoneAction(
            new ActivityIntentLauncher()
            , item.phone));
        }
        if (item.org != null){
            
            TextView view3 = (TextView) view.findViewById(R.id.view3);
            view3.setText(item.org);
            
        }
        if (item.comments != null){
            
            TextView view4 = (TextView) view.findViewById(R.id.view4);
            view4.setText(item.comments);
            
        }
    }

    @Override
    protected void onShow(ContactDSSchemaItem item) {
        // set the title for this fragment
        getActivity().setTitle(null);
    }

    @Override
    public void navigateToEditForm() {
        Bundle args = new Bundle();

        args.putInt(Constants.ITEMPOS, 0);
        args.putParcelable(Constants.CONTENT, getItem());
        args.putInt(Constants.MODE, Constants.MODE_EDIT);

        Intent intent = new Intent(getActivity(), ContactDSSchemaItemFormActivity.class);
        intent.putExtras(args);
        startActivityForResult(intent, Constants.MODE_EDIT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_menu, menu);

        MenuItem item = menu.findItem(R.id.action_delete);
        ColorUtils.tintIcon(item, R.color.textBarColor, getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            ((DetailCrudPresenter<ContactDSSchemaItem>) getPresenter()).deleteItem(getItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onShare() {
        ContactDSSchemaItem item = getItem();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, (item.name != null ? item.name : "" ) + "\n" +
                    (item.email != null ? item.email : "" ) + "\n" +
                    (item.phone != null ? item.phone : "" ) + "\n" +
                    (item.org != null ? item.org : "" ) + "\n" +
                    (item.comments != null ? item.comments : "" ));
        analyticsReporter.sendEvent(analyticsInfo()
                            .withAction("share")
                            .withTarget((item.name != null ? item.name : "" ) + "\n" +
                    (item.email != null ? item.email : "" ) + "\n" +
                    (item.phone != null ? item.phone : "" ) + "\n" +
                    (item.org != null ? item.org : "" ) + "\n" +
                    (item.comments != null ? item.comments : "" ))
                            .withDataSource("ContactDS")
                            .build().toMap()
        );
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share)), 1);
    }
}

