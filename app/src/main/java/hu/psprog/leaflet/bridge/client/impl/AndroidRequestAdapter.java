package hu.psprog.leaflet.bridge.client.impl;

import hu.psprog.leaflet.bridge.client.request.RequestAdapter;
import hu.psprog.leaflet.mobile.BuildConfig;

/**
 * @author Peter Smith
 */
public class AndroidRequestAdapter implements RequestAdapter {

    @Override
    public String provideDeviceID() {
        return BuildConfig.API_CLIENT_ID;
    }

    @Override
    public String provideClientID() {
        return BuildConfig.API_CLIENT_ID;
    }

    @Override
    public void consumeAuthenticationToken(String token) {

    }
}
