package com.mpn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.mpn.controller", "com.mpn.services","com.mpn.repository","com.mpn.security"})
@EntityScan("com.mpn.model")
public class PersoFxServerApplication {

    static Logger log = LoggerFactory.getLogger(PersoFxServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PersoFxServerApplication.class, args);
//        http://localhost:1234/springrest/employee
    }
}
