

package com.ibm.mobileappbuilder.contact20161001030833;

import android.app.Application;
import ibmmobileappbuilder.injectors.ApplicationInjector;
import android.support.multidex.MultiDexApplication;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;
import ibmmobileappbuilder.cloudant.factory.CloudantDatabaseSyncerFactory;
import java.net.URI;


/**
 * You can use this as a global place to keep application-level resources
 * such as singletons, services, etc.
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationInjector.setApplicationContext(this);
        AnalyticsReporterInjector.analyticsReporter(this).init(this);
        //Syncing cloudant ds
        CloudantDatabaseSyncerFactory.instanceFor(
            "contact",
            URI.create("https://ebdcef13-3935-4d30-a516-0c1a96e08cd3-bluemix:243f7083bf0ed81b64413d93c72a53a53a34127a8d2f84b7e8f1d7acd0eae76e@ebdcef13-3935-4d30-a516-0c1a96e08cd3-bluemix.cloudant.com/contact")
        ).sync(null);
      }
}

