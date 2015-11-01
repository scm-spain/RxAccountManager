package com.alorma.rxaccounts;

import android.accounts.Account;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bernat.borras on 1/11/15.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,  false));
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {
        holder.text.setText(accounts.get(position).name);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void add(Account account) {
        this.accounts.add(account);
        notifyDataSetChanged();
    }

    public void addAll(Collection<Account> accounts) {
        this.accounts.addAll(accounts);
        notifyDataSetChanged();
    }

    public class AccountHolder  extends RecyclerView.ViewHolder{
        private final TextView text;

        public AccountHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
