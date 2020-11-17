package org.softRoad.models.query;

public class SearchOrder {

    private String field;
    private boolean descending = false;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isDescending()
    {
        return descending;
    }

    public void setDescending(boolean descending)
    {
        this.descending = descending;
    }
}
