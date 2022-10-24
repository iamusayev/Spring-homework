package az.online.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.dto.OrderReadDto;
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

@TestInstance(PER_CLASS)
public class OrderServiceTest {

    private final OrderService orderService = BeanImporter.getOrderService();

    @BeforeAll
    void init() {
        TestDataImporter.importData(BeanImporter.getSessionFactory());
    }

    @AfterAll
    public void finish() {
        BeanImporter.getSessionFactory().close();
    }

    @Test
    void getByIdIfOrderExist() {
        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        var actualResult = orderService.findById(1);
        assertThat(actualResult).isPresent();

        session.clear();
    }

    @Test
    void getByIdIfOrderNotExist() {
        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        var actualResult = orderService.findById(Integer.MAX_VALUE);
        assertThat(actualResult).isEmpty();

        session.clear();
    }

    @Test
    void getAllByActiveStatus() {
        var status = Status.ACTIVE;

        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        List<OrderReadDto> actualResult = orderService.getAllByStatus(status);
        assertThat(actualResult).hasSize(7);

        List<LocalDate> localDates = actualResult.stream().map(OrderReadDto::registrationDate).toList();
        assertThat(localDates).containsExactlyInAnyOrder(
                LocalDate.of(2021, 5, 6),
                LocalDate.of(2022, 6, 7),
                LocalDate.of(2022, 6, 7),
                LocalDate.of(2022, 6, 7),
                LocalDate.of(2022, 6, 7),
                LocalDate.of(2022, 6, 7),
                LocalDate.of(2021, 5, 6)
        );

        session.close();
    }
}