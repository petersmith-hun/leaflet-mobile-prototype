package hu.psprog.leaflet.mobile.communication.request.handler.impl.registry;

import android.content.Intent;
import hu.psprog.leaflet.mobile.communication.exception.APICallException;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static hu.psprog.leaflet.mobile.config.dagger.module.APIRequestHandlerModule.API_REQUEST_HANDLER_LIST_DEPENDENCY;

/**
 * @author Peter Smith
 */
@Singleton
public class APIRequestHandlerRegistry {

    private static final String UNRECOGNIZED_ACTION_MESSAGE = "Provided API request action [%s] cannot be recognized.";
    private static final String NO_EXISTING_HANDLER_FOR_ACTION_MESSAGE = "Request handler does not exist for action [%s]";

    private Map<APIRequestAction, APIRequestHandler> handlerMapping;

    @Inject
    public APIRequestHandlerRegistry(@Named(API_REQUEST_HANDLER_LIST_DEPENDENCY) List<APIRequestHandler> apiRequestHandlerList) {
        handlerMapping = apiRequestHandlerList.stream()
                .collect(Collectors.toMap(APIRequestHandler::assignedAction, Function.identity()));
    }

    public Serializable handleRequest(Intent intent) {

        return Optional.ofNullable(intent.getAction())
                .filter(this::actionExists)
                .map(APIRequestAction::valueOf)
                .map(this::getRequiredRequestHandler)
                .filter(Objects::nonNull)
                .map(handler -> handler.handleRequest(intent))
                .orElse(null);
    }

    private boolean actionExists(String action) {

        if (!APIRequestAction.contains(action)) {
            throw new APICallException(String.format(UNRECOGNIZED_ACTION_MESSAGE, action));
        }

        return true;
    }

    private APIRequestHandler getRequiredRequestHandler(APIRequestAction action) {

        APIRequestHandler handler = handlerMapping.get(action);
        if (Objects.isNull(handler)) {
            throw new APICallException(String.format(NO_EXISTING_HANDLER_FOR_ACTION_MESSAGE, action));
        }

        return handler;
    }
}
