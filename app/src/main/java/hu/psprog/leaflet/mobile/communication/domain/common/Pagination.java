package hu.psprog.leaflet.mobile.communication.domain.common;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
public class Pagination implements Serializable {

    private static final int DEFAULT_LIMIT = 10;
    private static final OrderBy DEFAULT_ORDER_BY = OrderBy.CREATED;
    private static final OrderDirection DEFAULT_ORDER_DIRECTION = OrderDirection.DESC;

    private int page;
    private int limit;
    private OrderBy orderBy;
    private OrderDirection orderDirection;

    public Pagination(int page, int limit, OrderBy orderBy, OrderDirection orderDirection) {
        this.page = page;
        this.limit = limit;
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
    }

    public static Pagination usingDefaults(int page) {
        return new Pagination(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION);
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public enum OrderBy {
        ID,
        CREATED
    }

    public enum OrderDirection {
        ASC,
        DESC
    }
}
