package hu.psprog.leaflet.bridge.client.impl;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.client.request.strategy.CallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.DeleteCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.GetCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PostCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PutCallStrategy;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Smith
 */
@Module
public class AndroidBridgeClientConfiguration {

    @Provides
    public BridgeClient createBridgeClient(InvocationFactory invocationFactory, ResponseReader responseReader) {
        return new BridgeClientImpl(createWebTarget(), invocationFactory, responseReader);
    }

    @Provides
    public ResponseReader responseReader() {
        return new ResponseReader(new MockHttpServletResponse());
    }

    @Provides
    public InvocationFactory invocationFactory() {
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
