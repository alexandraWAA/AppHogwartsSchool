package com.example.ahs.controller;

import com.example.ahs.config.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.trilead.ssh2.Connection;

import javax.activation.DataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Testcontainers
public class PostgresqlIntegrationTest extends TestConfiguration {
    @Autowired
    private DataSource dataSource;

    @Test
    void testPostgresql() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}
