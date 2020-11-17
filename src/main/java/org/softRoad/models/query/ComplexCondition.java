package org.softRoad.models.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ComplexCondition implements SearchCondition {
    @NotNull
    @Valid
    private final List<SearchCondition> conditions;
    @NotNull
    private final Operator operator;

    @JsonCreator
    public ComplexCondition(@JsonProperty List<SearchCondition> conditions, @JsonProperty Operator operator) {
        this.conditions = conditions;
        this.operator = operator;
    }

    public List<SearchCondition> getConditions() {
        return conditions;
    }

    public Operator getOperator() {
        return operator;
    }

    public enum Operator {
        AND, OR
    }
}
