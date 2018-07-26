package hu.psprog.leaflet.bridge.client.impl;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.client.handler.InvocationFactory;
import hu.psprog.leaflet.bridge.client.handler.ResponseReader;
import hu.psprog.leaflet.bridge.client.request.RequestAdapter;
import hu.psprog.leaflet.bridge.client.request.strategy.CallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.DeleteCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.GetCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PostCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PutCallStrategy;
import hu.psprog.leaflet.mobile.BuildConfig;

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
    public RequestAdapter requestAdapter() {
        return new AndroidRequestAdapter();
    }

    @Provides
    public BridgeClient createBridgeClient(InvocationFactory invocationFactory, ResponseReader responseReader) {
        return new BridgeClientImpl(createWebTarget(), invocationFactory, responseReader);
    }

    @Provides
    public ResponseReader responseReader(RequestAdapter requestAdapter) {
        return new ResponseReaderImpl(requestAdapter);
    }

    @Provides
    public InvocationFactory invocationFactory(RequestAdapter requestAdapter) {
        return new InvocationFactoryImpl(new MockRequestAuthentication(), getCallStrategies(), requestAdapter);
    }

    private WebTarget createWebTarget() {
        return ClientBuilder.newBuilder()
                .register(JacksonJsonProvider.class)
                .register(AndroidFriendlyFeature.class)
                .build()
                .target(BuildConfig.API_HOST_URL);
    }

    private List<CallStrategy> getCallStrategies() {
        return Arrays.asList(new PostCallStrategy(), new GetCallStrategy(), new PutCallStrategy(), new DeleteCallStrategy());
    }
}
