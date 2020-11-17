package org.softRoad.models.query;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    @Valid
    private SearchCondition condition;
    @Min(1)
    @Max(100)
    private Integer pageSize = 100;
    private Integer offset = 0;
    @Valid
    private List<SearchOrder> searchOrders = new ArrayList<>();

    public SearchCondition getCondition() {
        return condition;
    }

    public void setCondition(SearchCondition condition) {
        this.condition = condition;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<SearchOrder> getSearchOrders() {
        return searchOrders;
    }

    public void setSearchOrders(List<SearchOrder> searchOrders) {
        this.searchOrders = searchOrders;
    }
}
