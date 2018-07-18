package hu.psprog.leaflet.bridge.client.impl;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.client.request.strategy.CallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.DeleteCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.GetCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PostCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PutCallStrategy;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter Smith
 */
public class AndroidBridgeClientFactory {

    private BridgeClient bridgeClient;

    public BridgeClient createBridgeClient() {

        if (Objects.isNull(bridgeClient)) {
            bridgeClient = new BridgeClientImpl(createWebTarget(), getInvocationFactory(), getResponseReader());
        }

        return bridgeClient;
    }

    private ResponseReader getResponseReader() {
        return new ResponseReader(new MockHttpServletResponse());
    }

    private InvocationFactory getInvocationFactory() {
        return new InvocationFactory(new MockRequestAuthentication(), getCallStrategies(), new MockHttpServletRequest());
    }


    private WebTarget createWebTarget() {
        return ClientBuilder.newBuilder()
                .register(JacksonJsonProvider.class)
                .register(AndroidFriendlyFeature.class)
                .build()
                .target("https://api.psprog.hu/");
    }

    private List<CallStrategy> getCallStrategies() {
        return Arrays.asList(new PostCallStrategy(), new GetCallStrategy(), new PutCallStrategy(), new DeleteCallStrategy());
    }
}
