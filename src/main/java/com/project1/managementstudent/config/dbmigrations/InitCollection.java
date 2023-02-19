package com.project1.managementstudent.config.dbmigrations;

import org.springframework.data.mongodb.core.MongoTemplate;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "init-collection", order = "1")
public class InitCollection {

    private final MongoTemplate mongoTemplate;

    public InitCollection(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //Note this method / annotation is Optional
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection("student");
    }

    //Note this method / annotation is Optional
    @RollbackBeforeExecution
    public void rollbackBefore() {}

    @Execution
    public void migrationMethod() {}

    @RollbackExecution
    public void rollback() {}
}
