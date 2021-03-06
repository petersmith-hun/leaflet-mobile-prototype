package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestHandler;
import hu.psprog.leaflet.mobile.communication.request.handler.impl.EntryDetailsAPIRequestHandler;
import hu.psprog.leaflet.mobile.communication.request.handler.impl.PublicEntriesAPIRequestHandler;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Smith
 */
@Module
public class APIRequestHandlerModule {

    public static final String API_REQUEST_HANDLER_LIST_DEPENDENCY = "apiRequestHandlerList";

    @Singleton
    @Provides
    @Named(API_REQUEST_HANDLER_LIST_DEPENDENCY)
    public List<APIRequestHandler> provideApiRequestHandlerList(EntryBridgeService entryBridgeService) {
        return Arrays.asList(
                new PublicEntriesAPIRequestHandler(entryBridgeService),
                new EntryDetailsAPIRequestHandler(entryBridgeService));
    }
}
