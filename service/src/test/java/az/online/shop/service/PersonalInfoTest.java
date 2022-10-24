package az.online.shop.service;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.dto.PersonalInfoReadDto;
import az.online.shop.util.BeanImporter;
import az.online.shop.util.TestDataImporter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
class PersonalInfoTest {

    private final PersonalInfoService personalInfoService = BeanImporter.getPersonalInfoService();

    @BeforeAll
    void init() {
        TestDataImporter.importData(BeanImporter.getSessionFactory());
    }

    @AfterAll
    void finish() {
        BeanImporter.getSessionFactory().close();
    }


    @Test
    void getByIdIfPersonalInfoExist() {
        Optional<PersonalInfoReadDto> actualResult = personalInfoService.getById(1);
        Assertions.assertThat(actualResult).isPresent();
    }

    @Test
    void getByIdIfPersonalInfoNotExist() {
        Optional<PersonalInfoReadDto> actualResult = personalInfoService.getById(Integer.MAX_VALUE);
        Assertions.assertThat(actualResult).isEmpty();
    }
}
