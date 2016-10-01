
package com.ibm.mobileappbuilder.contact20161001030833.presenters;

import com.ibm.mobileappbuilder.contact20161001030833.R;
import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;
import java.util.Map;
import java.util.HashMap;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;

public class ContactDetailPresenter extends BasePresenter implements DetailCrudPresenter<ContactDSSchemaItem>,
      Datasource.Listener<ContactDSSchemaItem> {

    private final CrudDatasource<ContactDSSchemaItem> datasource;
    private final DetailView view;

    public ContactDetailPresenter(CrudDatasource<ContactDSSchemaItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(ContactDSSchemaItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(ContactDSSchemaItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(ContactDSSchemaItem item) {
        logCrudAction("deleted", item.getIdentifiableId());
        view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
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

