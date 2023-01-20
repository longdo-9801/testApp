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

    private List<Game> localGameDataSet;
    private LayoutInflater layoutInflater;
    private Context aContext;
    private GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
    private Game game;
    Boolean isOwned = false;

    public storeGameViewAdapter(List<Game> dataSet, Context aContext) {
        localGameDataSet = dataSet;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return localGameDataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return localGameDataSet.get(i);
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

        holder.name.setText(localGameDataSet.get(pointer).getName());
        holder.basePrice.setText("$" + String.valueOf(localGameDataSet.get(pointer).getBasePrice()));
        String discountPercent = String.valueOf(100-(10* localGameDataSet.get(pointer).getDiscount())) + "%";
        if (localGameDataSet.get(pointer).getDiscount() < 1) {
            holder.discount.setText(discountPercent);
            holder.salePrice.setText("$" + String.valueOf(localGameDataSet.get(pointer).getSalePrice()));
            holder.basePrice.setTextColor(Color.GRAY);
            holder.discount.setTextColor(Color.GREEN);
        }
        holder.genre1.setText(localGameDataSet.get(pointer).getGenre().get(0));
        holder.genre2.setText(localGameDataSet.get(pointer).getGenre().get(1));
        Button buyButton = (Button) resultView.findViewById(R.id.buyButton);

        if (globalSingleton.getLogin()) {
            if (!globalSingleton.getCurrentUser().getGameList().isEmpty()) {
                for (int i = 0; i < globalSingleton.getCurrentUser().getGameList().size(); i++) {
                    if (localGameDataSet.get(pointer).getId().equals(globalSingleton.getCurrentUser().getGameList().get(i))) {
                        isOwned = true;
                        break;
                    } else {
                        System.out.println("DEBUG");
                        System.out.println(pointer);
                        System.out.println(i);
                    }
                }
            }

        }


        if (isOwned) {
            buyButton.setText("Owned");
            holder.discount.setText("");
            holder.salePrice.setText("");
            holder.basePrice.setText("Item already bought");
        } else {
            buyButton.setText("Buy");
            if (globalSingleton.getLogin()) {
                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!globalSingleton.getCurrentUser().getGameList().isEmpty())
                        {
                            globalSingleton.updateBuyGame(localGameDataSet.get(pointer));
                            buyButton.setText("Owned");
                            holder.discount.setText("");
                            holder.salePrice.setText("");
                            holder.basePrice.setText("Item already bought");
                        }


                    }
                });
            }

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
