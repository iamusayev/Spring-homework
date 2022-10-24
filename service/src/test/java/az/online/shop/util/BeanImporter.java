package az.online.shop.util;

import az.online.shop.config.ApplicationConfiguration;
import az.online.shop.dao.CustomerRepository;
import az.online.shop.dao.OrderRepository;
import az.online.shop.dao.PersonalInfoRepository;
import az.online.shop.dao.ProductRepository;
import az.online.shop.service.CustomerService;
import az.online.shop.service.OrderService;
import az.online.shop.service.PersonalInfoService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanImporter {

    public static final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    public static SessionFactory getSessionFactory() {
        return context.getBean(SessionFactory.class);
    }

    public static Session getSession() {
        return context.getBean(Session.class);
    }

    public static CustomerRepository getCustomerRepository() {
        return context.getBean(CustomerRepository.class);
    }

    public static OrderRepository getOrderRepository() {
        return context.getBean(OrderRepository.class);
    }

    public static PersonalInfoRepository getPersonalInfoRepository() {
        return context.getBean(PersonalInfoRepository.class);
    }

    public static ProductRepository getProductRepository() {
        return context.getBean(ProductRepository.class);
    }

    public static CustomerService getCustomerService() {
        return context.getBean(CustomerService.class);
    }

    public static OrderService getOrderService() {
        return context.getBean(OrderService.class);
    }

    public static PersonalInfoService getPersonalInfoService() {
        return context.getBean(PersonalInfoService.class);
    }
}
