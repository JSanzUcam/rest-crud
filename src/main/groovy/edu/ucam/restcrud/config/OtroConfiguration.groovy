package edu.ucam.restcrud.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource

@Configuration
@PropertySource("classpath:database.properties")
@EnableJpaRepositories(
    basePackages = "edu.ucam.restcrud.database.otro",
    entityManagerFactoryRef = "otroEntityManager",
    transactionManagerRef = "otroTransactionManager"
)
class OtroConfiguration {
    @Autowired
    Environment env

    @Bean
    LocalContainerEntityManagerFactoryBean otroEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean()
        em.setDataSource(otroDataSource())
        em.setPackagesToScan(new String[] {"edu.ucam.restcrud.database.otro"})

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter()
        em.setJpaVendorAdapter(vendorAdapter)

        HashMap<String, Object> properties = new HashMap<>()
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"))
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"))
        em.setJpaPropertyMap(properties)

        return em
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.otro-datasource")
    DataSource otroDataSource() {
        return DataSourceBuilder.create().build()
    }

    @Bean
    PlatformTransactionManager otroTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager()
        transactionManager.setEntityManagerFactory(otroEntityManager().getObject())
        return transactionManager
    }
}
