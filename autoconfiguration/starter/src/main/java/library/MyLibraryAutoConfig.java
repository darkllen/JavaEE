package library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLibraryAutoConfig {
    @Bean
    MyLibraryClass myLibraryClass(){
        return new MyLibraryClass();
    }
}
