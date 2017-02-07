package com.example.beahildehrandt.financecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by beahildehrandt on 29.01.17.
 */

public class MyCustomBaseAdapter extends BaseAdapter {
    private ArrayList<Entry> searchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<Entry> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        searchArrayList = new ArrayList<Entry>();
    }

    public void addAll(ArrayList<Entry> addAll) {
        searchArrayList = addAll;
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.content_list_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtAmount = (TextView) convertView.findViewById(R.id.amount);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getName());
        holder.txtAmount.setText(searchArrayList.get(position).getAmount().toString()+ "€");
        holder.txtDate.setText("Datum: " + searchArrayList.get(position).getDate());

        return convertView;
    }
    class ViewHolder {
        TextView txtName;
        TextView txtAmount;
        TextView txtDate;
    }
}
