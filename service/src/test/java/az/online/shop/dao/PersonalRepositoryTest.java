package az.online.shop.dao;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.entity.PersonalInfo;
import az.online.shop.util.BeanImporter;
import az.online.shop.util.TestDataImporter;
import java.util.Optional;
import lombok.Cleanup;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
class PersonalRepositoryTest {


    private final PersonalInfoRepository personalInfoRepository = BeanImporter.getPersonalInfoRepository();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(BeanImporter.getSessionFactory());
    }

    @AfterAll
    public void finish() {
        BeanImporter.getSessionFactory().close();
    }


    @Test
    void getByIdIfPersonalInfoExist() {
        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        Optional<PersonalInfo> actualResult = personalInfoRepository.findById(1);
        Assertions.assertThat(actualResult).isPresent();

        session.clear();
    }

    @Test
    void getByIdIfPersonalInfoIsNotExist() {
        @Cleanup Session session = BeanImporter.getSession();
        session.beginTransaction();

        Optional<PersonalInfo> actualResult = personalInfoRepository.findById(Integer.MAX_VALUE);
        Assertions.assertThat(actualResult).isNotPresent();

        session.clear();
    }
}
