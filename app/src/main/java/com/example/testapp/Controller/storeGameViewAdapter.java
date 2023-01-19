package com.example.testapp.Controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapp.Object.Game;
import com.example.testapp.Object.GlobalSingleton;
import com.example.testapp.R;

import java.util.List;


public class storeGameViewAdapter extends BaseAdapter {

    private List<Game> localDataSet;
    private LayoutInflater layoutInflater;
    private Context aContext;
    private GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
    private Game game;

    public storeGameViewAdapter(List<Game> dataSet, Context aContext) {
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
            resultView = layoutInflater.inflate(R.layout.store_game_icon, null);
            holder = new ViewHolderGameUser();
            holder.name = (TextView) resultView.findViewById(R.id.textGameName);
            holder.basePrice = (TextView) resultView.findViewById(R.id.textBasePrice);
            holder.discount = (TextView) resultView.findViewById(R.id.textDiscount);
            holder.salePrice = (TextView) resultView.findViewById(R.id.textSalePrice);
            holder.genre1 = (TextView) resultView.findViewById(R.id.textGenre1);
            holder.genre2 = (TextView) resultView.findViewById(R.id.textGenre2);
            resultView.setTag(holder);
        } else {
            holder = (ViewHolderGameUser) resultView.getTag();
        }

        holder.name.setText(localDataSet.get(pointer).getName());
        holder.basePrice.setText("$" + String.valueOf(localDataSet.get(pointer).getBasePrice()));
        String discountPercent = String.valueOf(100-(10*localDataSet.get(pointer).getDiscount())) + "%";
        if (localDataSet.get(pointer).getDiscount() < 1) {
            holder.discount.setText(localDataSet.get(pointer).getName());
            holder.salePrice.setText("$" + String.valueOf(localDataSet.get(pointer).getSalePrice()));
            holder.name.setTextColor(Color.GRAY);
            holder.discount.setTextColor(Color.GREEN);
        }
        holder.genre1.setText(localDataSet.get(pointer).getGenre().get(0));
        holder.genre2.setText(localDataSet.get(pointer).getGenre().get(1));
        Button buyButton = (Button) resultView.findViewById(R.id.buyButton);
        Boolean isOwned = false;
        for (int i = 0; i < globalSingleton.getCurrentUser().getGameList().size(); i++) {
            if (localDataSet.get(pointer).getId().equals(globalSingleton.getCurrentUser().getGameList().get(i))) {
                isOwned = true;
                break;
            }
        }

        if (isOwned) {
            buyButton.setText("Owned");
            holder.discount.setText("");
            holder.salePrice.setText("");
            holder.basePrice.setText("Item already bought");
        }else {
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    globalSingleton.updateBuyGame(globalSingleton.getGameList().get(pointer));
                }
            });
        }
        return resultView;
    }

    static class ViewHolderGameUser {
        TextView name;
        TextView basePrice;
        TextView discount;
        TextView salePrice;
        TextView genre1;
        TextView genre2;
    }
}
