package client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class LibraryOdessa implements InitializingBean, Library {
    @Value("${visitors}")
    private int visitors;

    @Override
    public void printInfo() {
        System.out.println("this is library in Odessa with " + visitors + " visitors");
    }

    @Override
    public void afterPropertiesSet() {
        printInfo();
    }
}