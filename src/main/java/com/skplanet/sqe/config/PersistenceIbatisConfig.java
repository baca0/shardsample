package com.skplanet.sqe.config;

import com.skplanet.sqe.datasource.ShardDatasource;
import com.skplanet.sqe.datasource.ShardDbType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
@EnableTransactionManagement
@MapperScan("com.skplanet.sqe.user")
@ImportResource(value = "classpath:spring-mybatis-context.xml")
public class PersistenceIbatisConfig implements TransactionManagementConfigurer {
	
	@Value("${dataSource.driverClassName}")
	private String driver;
	@Value("${dataSource.masterurl}")
	private String masterurl;

    @Value("${dataSource.slave1url}")
    private String slave1url;

    @Value("${dataSource.slave2url}")
    private String slave2url;


    @Value("${dataSource.username}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;

	@Bean(name="dataSource")
	public DataSource configureDataSource() {
        DataSource master = configureMasterDatasoure();

        Map<Object,Object> map = new HashMap<>();
        map.put(ShardDbType.MASTER,master);
        map.put(ShardDbType.SLAVE1,configureSlave1Datasoure());
        map.put(ShardDbType.SLAVE2,configureSlave2Datasoure());
        ShardDatasource shardDatasource = new ShardDatasource();
        shardDatasource.setTargetDataSources(map);
        shardDatasource.setDefaultTargetDataSource(master);
		return shardDatasource;
	}


    @Bean
    public DataSource configureMasterDatasoure() {
        DriverManagerDataSource master = new DriverManagerDataSource();
        master.setDriverClassName(driver);
        master.setUrl(masterurl);
        master.setUsername(username);
        master.setPassword(password);
        return master;
    }

    @Bean
    public DataSource configureSlave1Datasoure() {
        DriverManagerDataSource slave1 = new DriverManagerDataSource();
        slave1.setDriverClassName(driver);
        slave1.setUrl(slave1url);
        slave1.setUsername(username);
        slave1.setPassword(password);

        return slave1;
    }

    @Bean
    public DataSource configureSlave2Datasoure() {
        DriverManagerDataSource slave2 = new DriverManagerDataSource();
        slave2.setDriverClassName(driver);
        slave2.setUrl(slave2url);
        slave2.setUsername(username);
        slave2.setPassword(password);

        return slave2;
    }



	@Bean	
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(configureDataSource());
	}

}
