package cz.mfanta.tip_centrum.entity.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class MysqlConfiguration {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("Mort007");
        dataSource.setUrl("jdbc:mysql://localhost/tip_centrum");
        dataSource.setMaxWaitMillis(10_000);                   // wait max 10 sec before throwing an exception
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setValidationQueryTimeout(2);
        dataSource.setMaxConnLifetimeMillis(60_000);           // connection becomes old after 60 sec
        dataSource.setTimeBetweenEvictionRunsMillis(120_000);  // remove old connections every 120 sec
        dataSource.setTestOnCreate(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setRemoveAbandonedOnMaintenance(true);
        dataSource.setLogAbandoned(true);
        dataSource.setLogExpiredConnections(false);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        adapter.setDatabase(Database.MYSQL);
        return adapter;
    }
}
