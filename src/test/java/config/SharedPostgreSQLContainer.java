package config;

import org.testcontainers.containers.PostgreSQLContainer;

public class SharedPostgreSQLContainer {

    public static final PostgreSQLContainer<?> INSTANCE =
            new PostgreSQLContainer<>("postgres:17-alpine")
                    .withReuse(true)
                    .withUrlParam("TC_REUSABLE", "true");

    static {
        INSTANCE.start();
    }
}
