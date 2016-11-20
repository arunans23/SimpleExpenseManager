package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by arunan on 11/16/16.
 */

public class AccountCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public AccountCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Account getAccount(){
        String account_no = getString(getColumnIndex(ExpenseManagerSchema.AccountTable.Cols.ACCOUNT_NO));
        String bank = getString(getColumnIndex(ExpenseManagerSchema.AccountTable.Cols.BANK_NAME));
        String holder = getString(getColumnIndex(ExpenseManagerSchema.AccountTable.Cols.HOLDER));
        double balance = getDouble(getColumnIndex(ExpenseManagerSchema.AccountTable.Cols.BALANCE));

        return new Account(account_no, bank, holder, balance);
    }


}
