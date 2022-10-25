package com.dmdev;

import com.dmdev.spring.config.ApplicationConfiguration;
import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.service.CompanyService;
import java.io.Serializable;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        String value = "hello";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));
        System.out.println(Serializable.class.isAssignableFrom(value.getClass()));

        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("web");
            context.refresh();

            //      clazz -> String -> Map<String, Object>



            var companyService = context.getBean("companyService", CompanyService.class);
            System.out.println(companyService.findById(1));

            System.out.println("PRIVET KAK TOVI DELA");
        }
    }
}