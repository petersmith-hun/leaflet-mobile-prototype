package hu.psprog.leaflet.bridge.service.impl;

import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.client.impl.AndroidBridgeClientFactory;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;

/**
 * @author Peter Smith
 */
public class BridgeServiceRegistry {

    private EntryBridgeService entryBridgeService;

    public BridgeServiceRegistry() {
        BridgeClient bridgeClient = new AndroidBridgeClientFactory().createBridgeClient();
        this.entryBridgeService = new EntryBridgeServiceImpl(bridgeClient);
    }

    public EntryBridgeService getEntryBridgeService() {
        return entryBridgeService;
    }
}
