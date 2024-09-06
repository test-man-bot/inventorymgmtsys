package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql("JdbcProductRepositoryTest.sql")
public class JdbcProductRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new JdbcProductRepository(jdbcTemplate);
    }

    @Test
    void test_selectById() {
        Product product = productRepository.findByName("消しゴム");
        assertThat(product.getName()).isEqualTo("消しゴム");
        assertThat(product.getPrice()).isEqualTo(100);
        assertThat(product.getStock()).isEqualTo(10);
    }

    Product findByName() {
        Product product = productRepository.findByName("pname03");
        return product;
    }

    @Test
    void test_selectAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(12);
    }

    @Test
    void test_update() {
        Product training = new Product();
//        String productId = UUID.randomUUID().toString();
        Product product = this.findByName();
        training.setId(product.getId());
        training.setName("おばけ");
        training.setPrice(99);
        training.setStock(33);
        boolean result = productRepository.update(training);
        assertThat(result).isEqualTo(true);

        Map<String, Object> trainingMap = jdbcTemplate.queryForMap(
                "SELECT * FROM t_product WHERE id = ?", product.getId());
        assertThat(trainingMap.get("name")).isEqualTo("おばけ");
        assertThat(trainingMap.get("price")).isEqualTo(99);
        assertThat(trainingMap.get("stock")).isEqualTo(33);
    }

}
