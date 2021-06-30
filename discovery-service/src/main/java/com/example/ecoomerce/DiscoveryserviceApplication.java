package com.example.ecoomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication  // 이 애노테이션이 있는 클래스를 진입점으로 잡는다
@EnableEurekaServer     // 유레카 서버를 등록한다. 마이크로서비스들의 위치를 알려주는 서비스이다. 게이트웨이에서 유레카를 통해 접근점을 얻어온다.
public class DiscoveryserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryserviceApplication.class, args);
    }

}
