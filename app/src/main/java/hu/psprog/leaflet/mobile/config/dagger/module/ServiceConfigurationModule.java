package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Binds;
import dagger.Module;
import hu.psprog.leaflet.mobile.communication.service.EntryAPIRequestService;
import hu.psprog.leaflet.mobile.communication.service.impl.EntryAPIRequestServiceImpl;

/**
 * @author Peter Smith
 */
@Module
public abstract class ServiceConfigurationModule {

    @Binds
    public abstract EntryAPIRequestService bindEntryAPIRequestService(EntryAPIRequestServiceImpl entryAPIRequestServiceImpl);
}
