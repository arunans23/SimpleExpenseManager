package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

/**
 * Created by arunan on 11/16/16.
 */

public class ExpenseManagerSchema {
    public static final class TransactionTable{
        public static final String NAME = "transactionLog";

        public static final class Cols{
            public static final String DATE = "date";
            public static final String ACCOUNT_NO = "account_no";
            public static final String TYPE = "type";
            public static final String AMOUNT = "amount";

        }
    }
    public static final class AccountTable{
        public static final String NAME= "account";

        public static final class Cols{

            public static final String ACCOUNT_NO = "account_no";
            public static final String BANK_NAME = "bank_name";
            public static final String HOLDER = "account_holder";
            public static final String BALANCE = "balance";

        }
    }
}
