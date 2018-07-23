package hu.psprog.leaflet.mobile.communication.request.handler.impl;

import android.content.Intent;
import hu.psprog.leaflet.bridge.client.domain.OrderDirection;
import hu.psprog.leaflet.mobile.communication.domain.common.Constants;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.exception.APICallException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Peter Smith
 */
abstract class AbstractPaginationCapableAPIRequestHandler<T extends Enum<T>> {

    private List<String> enumFieldNames;

    AbstractPaginationCapableAPIRequestHandler() {
        this.enumFieldNames = Arrays.stream(orderByEnumType().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    Pagination extractPagination(Intent intent) {

        Serializable pagination = intent.getSerializableExtra(Constants.INTENT_PARAMETER_CALL_PARAMETERS);
        if (Objects.isNull(pagination) || !(pagination instanceof Pagination)) {
            throw new APICallException("Intent service parameter does not contain pagination information.");
        }

        return (Pagination) pagination;
    }

    T convertOrderBy(Pagination pagination) {
        return Optional.ofNullable(pagination.getOrderBy())
                .filter(this::orderingFieldExists)
                .map(this::convertOrderingField)
                .orElse(defaultOrderBy());
    }

    OrderDirection convertOrderDirection(Pagination pagination) {
        return Optional.ofNullable(pagination.getOrderDirection())
                .map(Enum::name)
                .map(OrderDirection::valueOf)
                .orElse(OrderDirection.DESC);
    }

    private T convertOrderingField(Pagination.OrderBy orderBy) {
        return Enum.valueOf(orderByEnumType(), orderBy.name());
    }

    private boolean orderingFieldExists(Pagination.OrderBy orderBy) {

        boolean exists = false;
        if (enumFieldNames.contains(orderBy.name())) {
            exists = true;
        }

        return exists;
    }

    abstract Class<T> orderByEnumType();

    abstract T defaultOrderBy();
}
