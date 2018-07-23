package hu.psprog.leaflet.mobile.communication.request.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Peter Smith
 */
public enum APIRequestAction {
    PUBLIC_ENTRIES,
    ENTRY_DETAILS;

    private final static List<String> API_REQUEST_ACTION_NAME_LIST = Arrays.stream(values())
            .map(APIRequestAction::name)
            .collect(Collectors.toList());

    public static boolean contains(String action) {
        return API_REQUEST_ACTION_NAME_LIST.contains(action);
    }
}
