package hu.psprog.leaflet.mobile.config.dagger;

import android.app.Activity;
import android.app.Application;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import hu.psprog.leaflet.mobile.config.dagger.component.DaggerLeafletMobileApplicationComponent;
import hu.psprog.leaflet.mobile.config.dagger.component.LeafletMobileApplicationComponent;

import javax.inject.Inject;

/**
 * @author Peter Smith
 */
public class LeafletMobileApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private LeafletMobileApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerLeafletMobileApplicationComponent.create();
        applicationComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public LeafletMobileApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
