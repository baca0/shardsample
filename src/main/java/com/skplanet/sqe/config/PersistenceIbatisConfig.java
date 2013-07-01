package com.skplanet.sqe.config;

import com.skplanet.sqe.datasource.ShardDatasource;
import com.skplanet.sqe.datasource.ShardDbType;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.skplanet.sqe.user"} )
public class PersistenceIbatisConfig implements TransactionManagementConfigurer {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${dataSource.driverClassName}")
	private String driver;
	@Value("${dataSource.masterurl}")
	private String masterurl;

    @Value("${dataSource.slave1url}")
    private String slave1url;

    @Value("${dataSource.slave2url}")
    private String slave2url;


    @Value("classpath*:com/skplanet/config/mappers/**/*.xml")
    private Resource[] mapperLocations;

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



    @Bean(name={"sqlSessionFactory"})
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        sqlSessionFactoryBean.setDataSource(configureDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSource configureMasterDatasoure() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(masterurl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    @Bean
    public DataSource configureSlave1Datasoure() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(slave1url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

    @Bean
    public DataSource configureSlave2Datasoure() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(slave2url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }



	@Bean	
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(configureDataSource());
	}

}
