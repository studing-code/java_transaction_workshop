package davenkin;

/**
 * Created with IntelliJ IDEA.
 * User: davenkin
 * Date: 2/5/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceFactory
{

    private static final BasicDataSource dataSource = new BasicDataSource();

    static
    {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("oaec");
        dataSource.setUrl("jdbc:mysql://localhost:3306/studytrans?useUnicode=true&characterEncoding=utf8");
    }

    public static DataSource createDataSource()
    {
        return dataSource;
    }
}