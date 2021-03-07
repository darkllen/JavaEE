package client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLibraryAutoConfig {

    @ConditionalOnProperty(value = "city", havingValue = "Kyiv")
    @Bean
    City CityKyivClass(){
        return new CityKyiv();
    }

    @ConditionalOnProperty(value = "city", havingValue = "Odessa")
    @Bean
    City CityOdessaClass(){
        return new CityOdessa();
    }

    @ConditionalOnBean(name = "CityKyivClass")
    @Bean
    Library LibraryKyivClass(){
        return new LibraryKyiv();
    }

    @ConditionalOnBean(name = "CityOdessaClass")
    @Bean
    Library LibraryOdessaClass(){
        return new LibraryOdessa();
    }

    @ConditionalOnMissingBean(name = {"CityKyivClass", "CityOdessaClass"})
    @Bean
    Library LibraryDefaultClass(){
        return new LibraryDefault();
    }
}
