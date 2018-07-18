package hu.psprog.leaflet.bridge.client.impl;

import hu.psprog.leaflet.bridge.client.request.RequestAuthentication;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Smith
 */
public class MockRequestAuthentication implements RequestAuthentication {

    @Override
    public Map<String, String> getAuthenticationHeader() {
        return new HashMap<>();
    }
}
