
package com.ibm.mobileappbuilder.contact20161001030833.presenters;

import com.ibm.mobileappbuilder.contact20161001030833.R;
import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BaseFormPresenter;
import ibmmobileappbuilder.mvp.view.FormView;

public class ContactFormPresenter extends BaseFormPresenter<ContactDSSchemaItem> {

    private final CrudDatasource<ContactDSSchemaItem> datasource;

    public ContactFormPresenter(CrudDatasource<ContactDSSchemaItem> datasource, FormView<ContactDSSchemaItem> view){
        super(view);
        this.datasource = datasource;
    }

    @Override
    public void deleteItem(ContactDSSchemaItem item) {
        datasource.deleteItem(item, new OnItemDeletedListener());
    }

    @Override
    public void save(ContactDSSchemaItem item) {
        // validate
        if (validate(item)){
            datasource.updateItem(item, new OnItemUpdatedListener());
        } else {
            view.showMessage(R.string.correct_errors, false);
        }
    }

    @Override
    public void create(ContactDSSchemaItem item) {
        // validate
        if (validate(item)){
            datasource.create(item, new OnItemCreatedListener());
        } else {
            view.showMessage(R.string.correct_errors, false);
        }
    }

    private class OnItemDeletedListener extends ShowingErrorOnFailureListener {
        @Override
        public void onSuccess(ContactDSSchemaItem  item) {
            logCrudAction("deleted", item.getIdentifiableId());
            view.showMessage(R.string.item_deleted, true);
            view.close(true);
        }
    }

    private class OnItemUpdatedListener extends ShowingErrorOnFailureListener {
        @Override
        public void onSuccess(ContactDSSchemaItem item) {
            logCrudAction("updated", item.getIdentifiableId());
            view.setItem(item);
            view.showMessage(R.string.item_updated, true);
            view.close(true);
        }
    }

    private class OnItemCreatedListener extends ShowingErrorOnFailureListener {
        @Override
        public void onSuccess(ContactDSSchemaItem item) {
            logCrudAction("created", item.getIdentifiableId());
            view.setItem(item);
            view.showMessage(R.string.item_created, true);
            view.close(true);
        }
    }

    private abstract class ShowingErrorOnFailureListener implements Datasource.Listener<ContactDSSchemaItem > {
        @Override
        public void onFailure(Exception e) {
            view.showMessage(R.string.error_data_generic, true);
        }
    }

    private void logCrudAction(String action, String id) {
      Map<String, String> paramsMap = new HashMap<>(3);
      //action will be one of created, updated or deleted
      paramsMap.put("action", action);
      paramsMap.put("entity", "ContactDSSchemaItem");
      paramsMap.put("identifier", id);

      AnalyticsReporterInjector.analyticsReporter().sendEvent(paramsMap);
    }
}

