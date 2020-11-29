package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public abstract class SoftRoadModel extends PanacheEntityBase {

    @Transient
    @JsonIgnore
    public List<String> presentFields = new ArrayList<>();

}
