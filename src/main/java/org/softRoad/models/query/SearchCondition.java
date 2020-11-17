package org.softRoad.models.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "Simple"),
        @JsonSubTypes.Type(value = ComplexCondition.class, name = "Complex")
})
public interface SearchCondition {

}
