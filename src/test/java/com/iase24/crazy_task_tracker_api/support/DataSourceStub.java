package com.iase24.crazy_task_tracker_api.support;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@TestConfiguration
public class DataSourceStub {

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schema;

    @Value("${spring.liquibase.database-change-log-table}")
    private String table;

    @Value("${spring.liquibase.liquibase-schema}")
    private String schemas;


    private static final String CLEAN_FORMAT = "DROP SCHEMA IF EXIST %s CASCADE; CREATE SCHEMA %s;";
    private static final String CLEAN_PUBLIC = "DROP SCHEMA IF EXIST public CASCADE; CREATE SCHEMA public;";
    private static final String SELECT_FORMAT = "ALTER USER %s SET search_path to %s;";

    private final static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:10")
            .withDatabaseName("task_4_dbt")
            .withCommand("postgres -c max_connections=300");

    static {
        POSTGRES.start();
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser(POSTGRES.getUsername());
        dataSource.setPassword(POSTGRES.getPassword());
        dataSource.setURL(POSTGRES.getJdbcUrl());
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(CLEAN_PUBLIC).execute();
            connection.prepareStatement(String.format(CLEAN_FORMAT, schema, schema)).execute();
            connection.prepareStatement(String.format(SELECT_FORMAT, POSTGRES.getUsername(), schema)).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
