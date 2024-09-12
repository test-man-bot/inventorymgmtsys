package com.ims.inventorymgmtsys.api;

import com.ims.inventorymgmtsys.entity.Product;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/JdbcProductRepositoryTest.sql")
@Sql(value = "/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CatalogApiRestControllerIntegrationApServerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    void test_getProducts() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity("/api/catalog/productList", Product[].class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Product[] products = responseEntity.getBody();
        assertThat(products.length).isEqualTo(5);
        assertThat(products[0].getName()).isEqualTo("NIKE　エアジョーダン1");
    }


}