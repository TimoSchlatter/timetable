package de.nordakademie.iaa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.nordakademie.iaa.util.LocalDateDeserializer;
import de.nordakademie.iaa.util.LocalDateSerializer;
import de.nordakademie.iaa.util.LocalTimeDeserializer;
import de.nordakademie.iaa.util.LocalTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Main class.
 *
 * @author Timo Schlatter
 */
@SpringBootApplication
@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = ofPattern("HH:mm:ss");

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

}
