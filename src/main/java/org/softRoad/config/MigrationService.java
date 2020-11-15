/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.softRoad.config;

import io.quarkus.liquibase.LiquibaseFactory;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSetStatus;

@ApplicationScoped
public class MigrationService
{
    // You can Inject the object if you want to use it manually
    @Inject
    LiquibaseFactory liquibaseFactory;

    public void checkMigration() throws Exception
    {
        // Get the list of liquibase change set statuses
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            List<ChangeSetStatus> status = liquibase.getChangeSetStatuses(liquibaseFactory.createContexts(),
                    liquibaseFactory.createLabels());
        }
    }
}
