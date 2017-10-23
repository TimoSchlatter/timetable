package de.nordakademie.iaa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(DataSourceProperties.class)
@EntityScan("de.nordakademie.iaa.model")
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .username("sa")
                .password("")
                .url("jdbc:h2:~/nak;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .driverClassName("org.h2.Driver")
                .build();
    }

//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSource getDataSource() {
//        DataSource dataSource = DataSourceBuilder.create().build();
//        return dataSource;
//    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("TIMETABLE");
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan("de.nordakademie.iaa.model");
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("H2");
        adapter.setDatabase(H2);
        entityManagerFactoryBean.setJpaVendorAdapter(adapter);
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create");
        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager getJpaTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);
        jpaTransactionManager.setDataSource(getDataSource());
        return jpaTransactionManager;
    }
}
