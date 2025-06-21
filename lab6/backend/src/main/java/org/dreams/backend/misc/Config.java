package org.dreams.backend.misc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean(name = "jsonUtil")
    public JsonUtil jsonUtil() {
        return new JsonUtil();
    }
}
