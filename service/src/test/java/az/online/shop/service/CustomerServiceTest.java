package az.online.shop.service;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.dto.CustomerReadDto;
import az.online.shop.model.Status;
import az.online.shop.util.BeanImporter;
import az.online.shop.util.TestDataImporter;
import java.util.List;
import java.util.Optional;
import lombok.Cleanup;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
public class CustomerServiceTest {

    private final CustomerService customerService = BeanImporter.getCustomerService();

    @BeforeAll
    void init() {
        TestDataImporter.importData(BeanImporter.getSessionFactory());
    }

    @AfterAll
    void close() {
        BeanImporter.getSessionFactory().close();
    }

    @Test
    void getAllCustomersWhenOrderStatusIsActive() {
        var status = Status.ACTIVE;

        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        List<CustomerReadDto> actualResult = customerService.getAllCustomersByOrderStatus(status);
        Assertions.assertThat(actualResult).hasSize(3);

        List<String> names = actualResult.stream().map(CustomerReadDto::firstName).toList();
        Assertions.assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

        session.clear();
    }

    @Test
    void getAllCustomersWhenOrderStatusIsNotActive() {
        var status = Status.INACTIVE;

        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        List<CustomerReadDto> actualResult = customerService.getAllCustomersByOrderStatus(status);
        Assertions.assertThat(actualResult).hasSize(2);

        List<String> names = actualResult.stream().map(CustomerReadDto::firstName).toList();
        Assertions.assertThat(names).containsExactlyInAnyOrder("Findlay", "Cleveland");

        session.clear();
    }

    @Test
    void getByIdIfCustomerExist() {
        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        Optional<CustomerReadDto> actualResult = customerService.findById(1);
        Assertions.assertThat(actualResult).isNotEmpty();

        session.clear();
    }

    @Test
    void getByIdIfCustomerNotExist() {
        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        Optional<CustomerReadDto> actualResult = customerService.findById(Integer.MAX_VALUE);
        Assertions.assertThat(actualResult).isEmpty();

        session.clear();
    }
}