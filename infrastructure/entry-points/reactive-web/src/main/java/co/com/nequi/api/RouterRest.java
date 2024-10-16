package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    private static final String URL_BASE = "/api/v1";
    @Bean
    public RouterFunction<ServerResponse> routerProduct(ProductHandler productHandler) {
        return route(POST(URL_BASE + "/product"), productHandler::createProduct)
                .andRoute(GET(URL_BASE + "/product/{id}"), productHandler::getProductById)
                .andRoute(GET(URL_BASE + "/products"), productHandler::getAllProducts)
                .andRoute(PUT(URL_BASE + "/product/{id}"), productHandler::updateProduct)
                .andRoute(GET(URL_BASE + "/product/maxStockByBranch/{branchId}"), productHandler::getProductById);
    }

    @Bean
    public RouterFunction<ServerResponse> routerBranch(BranchHandler branchHandler) {
        return route(POST(URL_BASE + "/branch"), branchHandler::createBranch)
                .andRoute(GET(URL_BASE + "/branch/{id}"), branchHandler::getBranchById)
                .andRoute(GET(URL_BASE + "/branches"), branchHandler::getAllBranches)
                .andRoute(PUT(URL_BASE + "/branch/{id}"), branchHandler::updateBranch);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFranchise(FranchiseHandler franchiseHandler) {
        return route(POST(URL_BASE + "/franchise"), franchiseHandler::createFranchise)
                .andRoute(GET(URL_BASE + "/franchise/{id}"), franchiseHandler::getFranchiseById)
                .andRoute(GET(URL_BASE + "/franchises"), franchiseHandler::getAllFranchises)
                .andRoute(PUT(URL_BASE + "/franchise/{id}"), franchiseHandler::updateFranchise);
    }
}
