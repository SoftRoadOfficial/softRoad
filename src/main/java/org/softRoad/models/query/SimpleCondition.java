package org.softRoad.models.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SimpleCondition implements SearchCondition {
    @NotBlank
    private final String field;
    @NotNull
    private final Object value;
    @NotNull
    private final Operator operator;

    @JsonCreator
    public SimpleCondition(@JsonProperty String field, @JsonProperty Object value, @JsonProperty Operator operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    public enum Operator {
        EQUAL("="), LT("<"), LTE("<="), GT(">"), GTE(">="), LIKE("like");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
