package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.repository.JdbcProductRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    @Sql(scripts = "/com/ims/inventorymgmtsys/JdbcProductRepositoryTest.sql")
    @Transactional
    void test_findById() {
        Product product = productService.findByName("消しゴム");
        assertThat(product.getStock()).isEqualTo(10);
    }

    @Test
    void test_findAll() {
        List<Product> products = productService.findAll();
        assertThat(products.get(0).getName()).isEqualTo("ASICS マジックスピード4");
    }

    @Test
    void test_update() {
        List<Product> products = productService.findAll();
        Map<String, Object> productMap = jdbcTemplate.queryForMap("SELECT * FROM t_product WHERE id = ?", products.get(3).getId());
        assertThat(productMap.get("name")).isEqualTo("Patagonia フリース");
        assertThat(productMap.get("price")).isEqualTo(20000);
        assertThat(productMap.get("stock")).isEqualTo(40);
    }
}
