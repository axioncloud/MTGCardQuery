package net.skyestudios.mtgcardquery.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.views.CardView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by arkeonet64 on 5/1/2017.
 */

public class CardViewAdapter extends ArrayAdapter<CardView> {

    List<CardView> cardViews;

    public CardViewAdapter(Context context, int resource) {
        super(context, resource);
        cardViews = new ArrayList<>();
    }

    @Override
    public void add(CardView object) {
        super.add(object);
        cardViews.add(object);
    }

    @Override
    public void addAll(Collection<? extends CardView> collection) {
        super.addAll(collection);
        cardViews = (List<CardView>) collection;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_item_card, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.card_name);
        LinearLayout manaCostHolder = (LinearLayout) rowView.findViewById(R.id.card_mana_cost);
        TextView text = (TextView) rowView.findViewById(R.id.card_text);
        TextView type = (TextView) rowView.findViewById(R.id.card_type);
        name.setText(cardViews.get(position).getName());
        convertMana(cardViews.get(position).getManaCost(), manaCostHolder);
        text.setText(cardViews.get(position).getText());
        type.setText(cardViews.get(position).getType());
        return rowView;
    }

    private void convertMana(String manaCost, LinearLayout manaCostHolder) {
        manaCost = manaCost.replaceAll("\\}", "\\},");
        String[] manaCostTokens = manaCost.split(",");

        for (String manaCostToken :
                manaCostTokens) {
            ImageView manaImageView = new ImageView(getContext());

            switch (manaCostToken) {
                case "{0}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_0));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_0));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{1}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_1));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_1));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{2}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_2));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_2));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{3}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_3));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_3));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{4}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_4));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_4));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{5}":
                    break;
                case "{6}":
                    break;
                case "{7}":
                    break;
                case "{8}":
                    break;
                case "{9}":
                    break;
                case "{10}":
                    break;
                case "{11}":
                    break;
                case "{12}":
                    break;
                case "{13}":
                    break;
                case "{14}":
                    break;
                case "{15}":
                    break;
                case "{16}":
                    break;
                case "{17}":
                    break;
                case "{18}":
                    break;
                case "{19}":
                    break;
                case "{20}":
                    break;
                case "{R}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_r));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_r));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{G}":
                    if (manaCost.contains("{G}")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_g));
                        } else {
                            manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_g));
                        }
                        manaCostHolder.addView(manaImageView);
                    }
                    break;
                case "{B}":
                    if (manaCost.contains("{B}")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_b));
                        } else {
                            manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_b));
                        }
                        manaCostHolder.addView(manaImageView);
                    }
                    break;
                case "{U}":
                    if (manaCost.contains("{U}")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_u));
                        } else {
                            manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_u));
                        }
                        manaCostHolder.addView(manaImageView);
                    }
                    break;
                case "{W}":
                    if (manaCost.contains("{W}")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_w));
                        } else {
                            manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_w));
                        }
                        manaCostHolder.addView(manaImageView);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
