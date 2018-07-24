package hu.psprog.leaflet.bridge.service.impl;

import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;

/**
 * @author Peter Smith
 */
@Module
public class AndroidBridgeServiceConfiguration {

    @Provides
    public EntryBridgeService entryBridgeService(BridgeClient bridgeClient) {
        return new EntryBridgeServiceImpl(bridgeClient);
    }
}
