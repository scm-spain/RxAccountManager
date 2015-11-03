package com.alorma.rxaccounts.adapter;

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
public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.AccountHolder> {

    private List<String> types = new ArrayList<>();

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,  false));
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {
        holder.type.setText(types.get(position));
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public void add(String type) {
        this.types.add(type);
        notifyDataSetChanged();
    }

    public void addAll(Collection<String> types) {
        this.types.addAll(types);
        notifyDataSetChanged();
    }

    public void clear() {
        types.clear();
        notifyDataSetChanged();
    }

    public class AccountHolder  extends RecyclerView.ViewHolder{
        private final TextView type;

        public AccountHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
