package cz.mfanta.tip_centrum.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Import({
        MysqlConfiguration.class
})
public class DaoConfiguration {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Configuration
    public static class EntityManagerConfig {

        @Autowired
        private DataSource dataSource;

        @Autowired
        private JpaVendorAdapter jpaVendorAdapter;

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            Properties props = new Properties();
            props.setProperty("hibernate.format_sql", "true");

            LocalContainerEntityManagerFactoryBean emfb =
                    new LocalContainerEntityManagerFactoryBean();
            emfb.setDataSource(dataSource);
            emfb.setPackagesToScan("cz.mfanta.tip_centrum.entity");
            emfb.setJpaProperties(props);
            emfb.setJpaVendorAdapter(jpaVendorAdapter);

            return emfb;
        }
    }

    @Bean
    public IPredictionDao predictionDao() {
        return new PredictionDao(entityManager);
    }

    @Bean
    public IResultDao resultDao() {
        return new ResultDao(entityManager);
    }

    @Bean
    public ITeamDao teamDao() {
        return new TeamDao(entityManager);
    }

    @Bean
    public ITeamAliasDao teamAliasDao() {
        return new TeamAliasDao(entityManager);
    }

    @Bean
    public IFixtureDao fixtureDao() {
        return new FixtureDao(entityManager);
    }

    @Bean
    public IOddsDao oddsDao() {
        return new OddsDao(entityManager);
    }
}
