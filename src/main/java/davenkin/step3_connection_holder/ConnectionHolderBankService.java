package davenkin.step3_connection_holder;

import davenkin.BankService;

import javax.sql.DataSource;

public class ConnectionHolderBankService implements BankService {
    private TransactionManager transactionManager;
    private ConnectionHolderBankDao connectionHolderBankDao;
    private ConnectionHolderInsuranceDao connectionHolderInsuranceDao;

    /**
     * @param dataSource 数据源
     *
     *
     * 事务管理类以及Dao类传入相同的DataSource
     * ThreadLocal中保存ConnectionHolder,保证线程安全,同时将事务与业务进行分离
     *
     *
     */
    public ConnectionHolderBankService(DataSource dataSource) {
        transactionManager = new TransactionManager(dataSource);
        connectionHolderBankDao = new ConnectionHolderBankDao(dataSource);
        connectionHolderInsuranceDao = new ConnectionHolderInsuranceDao(dataSource);
    }

    public void transfer(int fromId, int toId, int amount) {
        try {
            transactionManager.start();
            connectionHolderBankDao.withdraw(fromId, amount);
            connectionHolderInsuranceDao.deposit(toId, amount);
            transactionManager.commit();
        } catch (Exception e) {
            transactionManager.rollback();
        } finally {
            transactionManager.close();
        }
    }
}
