package com.atom.remainder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nelson Andrew on 06-02-2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<ListOfData> listOfData;

    public CustomAdapter(Context context, int layout, ArrayList<ListOfData> listOfData) {

        this.context = context;
        this.layout = layout;
        this.listOfData = listOfData;
    }



    @Override
    public int getCount() {
        return listOfData.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{

        TextView idTxt;
        TextView nameTxt;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;

        ViewHolder holder = new ViewHolder();

        if (row == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.idTxt = (TextView)row.findViewById(R.id.text_id);
            holder.nameTxt = (TextView)row.findViewById(R.id.text_name);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        ListOfData listData = listOfData.get(position);

        holder.idTxt.setText(listData.getId());
        holder.nameTxt.setText(listData.getName());

        return row;
    }
//    public void remove(ListOfData object){
//        listOfData.remove(object);
//        notifyDataSetChanged();
//
//    }
}
