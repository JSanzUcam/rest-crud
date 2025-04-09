package edu.ucam.restcrud.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(
    basePackages = "edu.ucam.restcrud.database.universidad",
    entityManagerFactoryRef = "universidadEntityManager",
    transactionManagerRef = "universidadTransactionManager"
)
class UniversidadConfiguration {
    @Autowired
    Environment env

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean universidadEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean()
        em.setDataSource(universidadDataSource())
        em.setPackagesToScan(new String[] {"edu.ucam.restcrud.database.universidad"})

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter()
        em.setJpaVendorAdapter(vendorAdapter)

        HashMap<String, Object> properties = new HashMap<>()
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource.hibernate.ddl-auto"))
        properties.put("hibernate.dialect", env.getProperty("spring.datasource.hibernate.dialect"))
        properties.put("hibernate.show_sql", env.getProperty("spring.datasource.show-sql"))
        em.setJpaPropertyMap(properties)

        return em
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource universidadDataSource() {
        return DataSourceBuilder.create().build()
    }

    @Primary
    @Bean
    PlatformTransactionManager universidadTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager()
        transactionManager.setEntityManagerFactory(universidadEntityManager().getObject())
        return transactionManager
    }

    @Primary
    @Bean("universidadJdbcTemplate")
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(universidadDataSource())
    }
}
