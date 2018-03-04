package davenkin.step4_transaction_template;

import davenkin.step3_connection_holder.TransactionManager;

import javax.sql.DataSource;

/**
 * 在超类里将完成核心功能的方法声明为抽象方法，留给子类去实现，而在超类中完成一些通用操作
 */
public abstract class TransactionTemplate
{
    private TransactionManager transactionManager;

    protected TransactionTemplate(DataSource dataSource)
    {
        transactionManager = new TransactionManager(dataSource);
    }

    public void doJobInTransaction()
    {
        try
        {
            transactionManager.start();
            doJob();
            transactionManager.commit();
        } catch (Exception e)
        {
            transactionManager.rollback();
        } finally
        {
            transactionManager.close();
        }
    }

    protected abstract void doJob() throws Exception;
}
