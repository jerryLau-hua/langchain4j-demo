package com.jerry.springaialibabamcpsseserver;

import com.jerry.springaialibabamcpsseserver.config.ToolConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ToolConfiguration.class)
public class SpringAiAlibabaMcpSseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiAlibabaMcpSseServerApplication.class, args);
    }

}
