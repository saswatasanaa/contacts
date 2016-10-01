package ibmmobileappbuilder.analytics;

import android.app.Application;
import android.util.Log;

import com.worklight.common.Logger;
import com.worklight.common.WLAnalytics;
import com.worklight.common.WLAnalytics.DeviceEvent;
import com.worklight.wlclient.api.WLAccessTokenListener;
import com.worklight.wlclient.api.WLAuthorizationManager;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.auth.AccessToken;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;

public class MfpAnalyticsReporter implements AnalyticsReporter {

    private static final String TAG = MfpAnalyticsReporter.class.getSimpleName();
    private static final String PAGE_VIEW = "page_view";
    private static final String EVENT = "event";

    @Override
    public void init(Application application) {
        WLAnalytics.init(application);
        WLAnalytics.addDeviceEventListener(DeviceEvent.NETWORK);
        WLAnalytics.addDeviceEventListener(DeviceEvent.LIFECYCLE);
        WLAuthorizationManager.getInstance().obtainAccessToken("", new WLAccessTokenListener() {

            @Override
            public void onSuccess(AccessToken accessToken) {
                Log.i("MobileFirst Quick Start", " Success ");
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                String errorMsg=wlFailResponse.getErrorMsg();
                if (errorMsg != null)
                    errorMsg=errorMsg.replace("\n","").replace("\r","").replace("\t","");
                Log.i("MobileFirst Quick Start", "failure: " + errorMsg);
            }
        });
    }

    @Override
    public void sendView(String pageName) {
        WLAnalytics.log(PAGE_VIEW, new JSONObject(Collections.singletonMap("page", pageName)));
        WLAnalytics.send();
    }

    @Override
    public void sendEvent(Map<String, String> paramsMap) {
        WLAnalytics.log(EVENT, new JSONObject(paramsMap));
        WLAnalytics.send();
    }

    @Override
    public void sendHandledException(String tag, String message, Throwable exception) {
        Logger.getInstance(tag).error(message, exception);
        Logger.send();
    }

}
