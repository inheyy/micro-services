package org.sid.eurikadiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurikaDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurikaDiscoveryApplication.class, args);
    }

}
