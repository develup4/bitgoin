import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    // application.yml에서 정의할 수도 있지만 아래처럼 자바코드로 정의할수도 있다. 그리고 애노테이션이 트렌드이다.
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                    .filters(f -> f.addRequestHeader("first-service"))
                )

    }

}
