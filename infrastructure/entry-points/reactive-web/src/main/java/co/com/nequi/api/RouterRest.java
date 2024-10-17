package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    private static final String URL_BASE = "/api/v1";
    private static final String PATH_PRODUCT_CREATE = URL_BASE + "/product";
    private static final String PATH_PRODUCT_GET_BY_ID = URL_BASE + "/product/{id}";
    private static final String PATH_PRODUCTS_GET_ALL = URL_BASE + "/products";
    private static final String PATH_PRODUCT_UPDATE = URL_BASE + "/product/{id}";
    private static final String PATH_PRODUCT_UPDATE_NAME = URL_BASE + "/product/name/{id}";
    private static final String PATH_PRODUCT_UPDATE_STOCK = URL_BASE + "/product/stock/{id}";
    private static final String PATH_PRODUCT_DELETE = URL_BASE + "/product/{id}";
    private static final String PATH_PRODUCT_MAX_STOCK_BY_BRANCH = URL_BASE + "/product/maxStockByBranch/{branchId}";

    private static final String PATH_BRANCH_CREATE = URL_BASE + "/branch";
    private static final String PATH_BRANCH_GET_BY_ID = URL_BASE + "/branch/{id}";
    private static final String PATH_BRANCHES_GET_ALL = URL_BASE + "/branches";
    private static final String PATH_BRANCH_UPDATE = URL_BASE + "/branch/{id}";
    private static final String PATH_BRANCH_UPDATE_NAME = URL_BASE + "/branch/name/{id}";

    private static final String PATH_FRANCHISE_CREATE = URL_BASE + "/franchise";
    private static final String PATH_FRANCHISE_GET_BY_ID = URL_BASE + "/franchise/{id}";
    private static final String PATH_FRANCHISES_GET_ALL = URL_BASE + "/franchises";
    private static final String PATH_FRANCHISE_UPDATE = URL_BASE + "/franchise/{id}";
    private static final String PATH_FRANCHISE_UPDATE_NAME = URL_BASE + "/franchise/name/{id}";

    private static final String PATH_HEALTH = "/health";

    @Bean
    public RouterFunction<ServerResponse> routerProduct(ProductHandler productHandler) {
        return route(POST(PATH_PRODUCT_CREATE), productHandler::createProduct)
                .andRoute(GET(PATH_PRODUCT_GET_BY_ID), productHandler::getProductById)
                .andRoute(GET(PATH_PRODUCTS_GET_ALL), productHandler::getAllProducts)
                .andRoute(PATCH(PATH_PRODUCT_UPDATE), productHandler::updateProduct)
                .andRoute(PATCH(PATH_PRODUCT_UPDATE_NAME), productHandler::updateNameProduct)
                .andRoute(PATCH(PATH_PRODUCT_UPDATE_STOCK), productHandler::updateStockProduct)
                .andRoute(DELETE(PATH_PRODUCT_DELETE), productHandler::deleteProduct)
                .andRoute(GET(PATH_PRODUCT_MAX_STOCK_BY_BRANCH ), productHandler::getProductWithMaxStockByBranchId);
    }

    @Bean
    public RouterFunction<ServerResponse> routerBranch(BranchHandler branchHandler) {
        return route(POST(PATH_BRANCH_CREATE), branchHandler::createBranch)
                .andRoute(GET(PATH_BRANCH_GET_BY_ID), branchHandler::getBranchById)
                .andRoute(GET(PATH_BRANCHES_GET_ALL), branchHandler::getAllBranches)
                .andRoute(PATCH(PATH_BRANCH_UPDATE), branchHandler::updateBranch)
                .andRoute(PATCH(PATH_BRANCH_UPDATE_NAME), branchHandler::updateNameBranch);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFranchise(FranchiseHandler franchiseHandler) {
        return route(POST(PATH_FRANCHISE_CREATE), franchiseHandler::createFranchise)
                .andRoute(GET(PATH_FRANCHISE_GET_BY_ID), franchiseHandler::getFranchiseById)
                .andRoute(GET(PATH_FRANCHISES_GET_ALL), franchiseHandler::getAllFranchises)
                .andRoute(PATCH(PATH_FRANCHISE_UPDATE), franchiseHandler::updateFranchise)
                .andRoute(PATCH(PATH_FRANCHISE_UPDATE_NAME), franchiseHandler::updateNameFranchise);
    }

    @Bean
    public RouterFunction<ServerResponse> routerHealth(FranchiseHandler franchiseHandler) {
        return route(GET(PATH_HEALTH), req -> ServerResponse.ok().bodyValue(true));
    }
}
