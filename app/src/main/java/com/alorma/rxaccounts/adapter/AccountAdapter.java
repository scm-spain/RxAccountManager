package com.alorma.rxaccounts.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alorma.androidreactiveaccounts.accountmanager.RxAccount;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bernat.borras on 1/11/15.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<RxAccount> accounts = new ArrayList<>();

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent,  false));
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {
        holder.accountName.setText(accounts.get(position).name);
        holder.accountType.setText(accounts.get(position).type);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void add(RxAccount account) {
        this.accounts.add(account);
        notifyDataSetChanged();
    }

    public void addAll(Collection<RxAccount> accounts) {
        this.accounts.addAll(accounts);
        notifyDataSetChanged();
    }

    public void clear() {
        accounts.clear();
        notifyDataSetChanged();
    }

    public class AccountHolder  extends RecyclerView.ViewHolder{
        private final TextView accountName;
        private final TextView accountType;

        public AccountHolder(View itemView) {
            super(itemView);
            accountName = (TextView) itemView.findViewById(android.R.id.text1);
            accountType = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
