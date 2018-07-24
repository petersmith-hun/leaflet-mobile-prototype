package hu.psprog.leaflet.mobile.config.dagger.component;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import hu.psprog.leaflet.bridge.client.impl.AndroidBridgeClientConfiguration;
import hu.psprog.leaflet.bridge.service.impl.AndroidBridgeServiceConfiguration;
import hu.psprog.leaflet.mobile.communication.intent.APIRequestIntentService;
import hu.psprog.leaflet.mobile.config.dagger.LeafletMobileApplication;
import hu.psprog.leaflet.mobile.config.dagger.module.APIRequestHandlerModule;
import hu.psprog.leaflet.mobile.config.dagger.module.LeafletMobileApplicationModule;
import hu.psprog.leaflet.mobile.config.dagger.module.ServiceConfigurationModule;

import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        LeafletMobileApplicationModule.class,
        APIRequestHandlerModule.class,
        ServiceConfigurationModule.class,
        AndroidBridgeClientConfiguration.class,
        AndroidBridgeServiceConfiguration.class})
public interface LeafletMobileApplicationComponent extends AndroidInjector<LeafletMobileApplication> {

    void inject(APIRequestIntentService apiRequestIntentService);
}
