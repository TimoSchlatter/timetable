package de.nordakademie.iaa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static org.springframework.orm.jpa.vendor.Database.H2;

@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:~/nak;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .build();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("TIMETABLE");
        entityManagerFactory.setDataSource(getDataSource());
        entityManagerFactory.setPackagesToScan("de.nordakademie.iaa.model");
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("H2");
        adapter.setDatabase(H2);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        entityManagerFactory.setJpaVendorAdapter(adapter);
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.ddl-auto", "create-drop");
        entityManagerFactory.setJpaProperties(properties);
        return entityManagerFactory;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager getJpaTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);
        jpaTransactionManager.setDataSource(getDataSource());
        return jpaTransactionManager;
    }
}
