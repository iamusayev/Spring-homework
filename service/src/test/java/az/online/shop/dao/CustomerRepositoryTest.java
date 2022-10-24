package az.online.shop.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.entity.Customer;
import az.online.shop.model.Status;
import az.online.shop.util.BeanImporter;
import az.online.shop.util.TestDataImporter;
import java.util.List;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
class CustomerRepositoryTest {

    private final CustomerRepository customerRepository = BeanImporter.getCustomerRepository();

    @BeforeAll
    void initDb() {
        TestDataImporter.importData(BeanImporter.getSessionFactory());
    }

    @AfterAll
    void finish() {
        BeanImporter.getSessionFactory().close();
    }

    @Test
    void findAllByStatusIfStatusActive() {
        var status = Status.ACTIVE;

        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        List<Customer> actualResult = customerRepository.findAllCustomersByStatus(status);
        assertThat(actualResult).hasSize(3);

        List<String> names = actualResult.stream().map(Customer::getFirstName).toList();
        assertThat(names).containsExactlyInAnyOrder("Cleveland", "Isobelle", "Findlay");

        session.clear();
    }

    @Test
    void findAllCustomerByStatusIfStatusInactive() {
        var status = Status.INACTIVE;

        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        List<Customer> actualResult = customerRepository.findAllCustomersByStatus(Status.INACTIVE);
        assertThat(actualResult).hasSize(2);

        List<String> names = actualResult.stream().map(Customer::getFirstName).toList();
        assertThat(names).containsExactlyInAnyOrder("Findlay", "Cleveland");

        session.clear();
    }
}
