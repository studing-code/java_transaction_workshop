package davenkin.step4_transaction_template;

import davenkin.BankService;
import davenkin.step3_connection_holder.ConnectionHolderBankDao;
import davenkin.step3_connection_holder.ConnectionHolderInsuranceDao;

import javax.sql.DataSource;

/**
 * 通过定义Transaction的模板,将有关数据库的基本操作全部放到模板中完成,而Service层则只关注业务逻辑即可.
 *
 */
public class TransactionTemplateBankService implements BankService
{
    private DataSource dataSource;
    private ConnectionHolderBankDao connectionHolderBankDao;
    private ConnectionHolderInsuranceDao connectionHolderInsuranceDao;

    public TransactionTemplateBankService(DataSource dataSource)
    {
        this.dataSource = dataSource;
        connectionHolderBankDao = new ConnectionHolderBankDao(dataSource);
        connectionHolderInsuranceDao = new ConnectionHolderInsuranceDao(dataSource);
    }

    public void transfer(final int fromId, final int toId, final int amount)
    {
        new TransactionTemplate(dataSource)
        {
            @Override
            protected void doJob() throws Exception
            {
                connectionHolderBankDao.withdraw(fromId, amount);
                connectionHolderInsuranceDao.deposit(toId, amount);
            }
        }.doJobInTransaction();
    }
}
