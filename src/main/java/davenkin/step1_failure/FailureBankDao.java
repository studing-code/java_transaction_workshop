package davenkin.step1_failure;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: davenkin
 * Date: 2/7/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 *
 * 银行账户
 */
public class FailureBankDao {
    private DataSource dataSource;

    public FailureBankDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * 从银行账户表（BANK_ACCOUNT）中帐号为bankId的用户账户中取出数量为amount的金额。
     *
     * @param bankId 账户号
     * @param amount 取出金额
     * @throws SQLException
     */
    public void withdraw(int bankId, int amount) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement selectStatement = connection.prepareStatement("SELECT BANK_AMOUNT FROM BANK_ACCOUNT WHERE BANK_ID = ?");
        selectStatement.setInt(1, bankId);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        int previousAmount = resultSet.getInt(1);
        resultSet.close();
        selectStatement.close();


        int newAmount = previousAmount - amount;
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE BANK_ACCOUNT SET BANK_AMOUNT = ? WHERE BANK_ID = ?");
        updateStatement.setInt(1, newAmount);
        updateStatement.setInt(2, bankId);
        updateStatement.execute();

        updateStatement.close();
        connection.close();

    }
}
