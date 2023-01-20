package com.example.testapp.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testapp.R;

import java.util.List;


public class userGameViewAdapter extends BaseAdapter {

    private List<String> localDataSet;
    private LayoutInflater layoutInflater;
    private Context aContext;

    public userGameViewAdapter(List<String> dataSet, Context aContext) {
        localDataSet = dataSet;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return localDataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return localDataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pointer, View resultView, ViewGroup viewGroup) {
        ViewHolderGameUser holder;

        if (resultView == null) {
            resultView = layoutInflater.inflate(R.layout.game_icon, null);
            holder = new ViewHolderGameUser();
            holder.name = (TextView) resultView.findViewById(R.id.appStart);
            resultView.setTag(holder);
        } else {
            holder = (ViewHolderGameUser) resultView.getTag();
        }

        holder.name.setText(localDataSet.get(pointer));
        return resultView;
    }

    static class ViewHolderGameUser {
        TextView name;
    }
}
