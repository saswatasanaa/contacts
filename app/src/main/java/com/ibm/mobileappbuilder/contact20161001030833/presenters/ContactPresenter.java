
package com.ibm.mobileappbuilder.contact20161001030833.presenters;

import com.ibm.mobileappbuilder.contact20161001030833.R;
import com.ibm.mobileappbuilder.contact20161001030833.ds.ContactDSSchemaItem;

import java.util.List;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.ListCrudPresenter;
import ibmmobileappbuilder.mvp.view.CrudListView;
import java.util.Map;
import java.util.HashMap;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;

public class ContactPresenter extends BasePresenter implements ListCrudPresenter<ContactDSSchemaItem>,
      Datasource.Listener<ContactDSSchemaItem>{

    private final CrudDatasource<ContactDSSchemaItem> crudDatasource;
    private final CrudListView<ContactDSSchemaItem> view;

    public ContactPresenter(CrudDatasource<ContactDSSchemaItem> crudDatasource,
                                         CrudListView<ContactDSSchemaItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(ContactDSSchemaItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<ContactDSSchemaItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(ContactDSSchemaItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(ContactDSSchemaItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(ContactDSSchemaItem item) {
        logCrudAction("deleted", item.getIdentifiableId());
        view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
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

