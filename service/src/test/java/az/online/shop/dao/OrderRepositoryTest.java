package az.online.shop.dao;


import static org.assertj.core.api.Assertions.assertThat;

import az.online.shop.entity.Order;
import az.online.shop.model.Status;
import az.online.shop.util.BeanImporter;
import az.online.shop.util.TestDataImporter;
import java.time.LocalDate;
import java.util.List;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryTest {

    private final OrderRepository orderRepository = BeanImporter.getOrderRepository();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(BeanImporter.getSessionFactory());
    }

    @AfterAll
    public void finish() {
        BeanImporter.getSessionFactory().close();
    }

    @Test
    void findAllIfStatusIsActive() {
        Status status = Status.ACTIVE;

        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        List<Order> actualResult = orderRepository.findAllByStatus(status);
        assertThat(actualResult).hasSize(7);

        List<LocalDate> registrationDates = actualResult.stream().map(Order::getRegistrationDate).toList();
        assertThat(registrationDates).containsExactlyInAnyOrder(LocalDate.of(2021, 5, 6), LocalDate.of(2022, 6, 7), LocalDate.of(2022, 6, 7),
                LocalDate.of(2022, 6, 7), LocalDate.of(2022, 6, 7), LocalDate.of(2021, 5, 6), LocalDate.of(2022, 6, 7));

        session.clear();
    }
}

