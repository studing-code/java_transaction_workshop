package davenkin.step3_connection_holder;

import davenkin.step2_ugly.UglyBankDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SingleThreadConnectionHolder
{
    /**
     * 该类保证一个类的实例变量在各个线程中都有一份单独的拷贝，从而不会影响其他线程中的实例变量。
     */
    private static ThreadLocal<ConnectionHolder> localConnectionHolder = new ThreadLocal<ConnectionHolder>();

    public static Connection getConnection(DataSource dataSource) throws SQLException
    {
        return getConnectionHolder().getConnection(dataSource);
    }

    public static void removeConnection(DataSource dataSource)
    {
        getConnectionHolder().removeConnection(dataSource);
    }

    private static ConnectionHolder getConnectionHolder()
    {
        ConnectionHolder connectionHolder = localConnectionHolder.get();
        if (connectionHolder == null)
        {
            connectionHolder = new ConnectionHolder();
            localConnectionHolder.set(connectionHolder);
        }
        return connectionHolder;
    }

}
