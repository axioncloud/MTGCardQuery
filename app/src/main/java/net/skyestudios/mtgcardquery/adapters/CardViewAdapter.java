package net.skyestudios.mtgcardquery.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;
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

            manaImageView.setPadding(0, 0, ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getContext().getResources().getDisplayMetrics())), 0);

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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_5));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_5));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{6}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_6));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_6));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{7}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_7));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_7));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{8}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_8));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_8));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{9}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_9));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_9));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{10}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_10));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_10));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{11}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_11));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_11));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{12}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_12));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_12));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{13}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_13));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_13));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{14}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_14));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_14));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{15}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_15));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_15));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{16}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_16));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_16));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{17}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_17));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_17));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{18}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_18));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_18));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{19}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_19));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_19));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{20}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_20));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_20));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{X}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_x));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_x));
                    }
                    manaCostHolder.addView(manaImageView);
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

                case "{2/B}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_2b));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_2b));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{2/G}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_2g));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_2g));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{2/U}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_2u));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_2u));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{2/R}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_2r));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_2r));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;
                case "{2/W}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_2w));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_2w));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;

                case "{B/G}":
                    break;
                case "{B/U}":
                    break;
                case "{B/R}":
                    break;
                case "{B/W}":
                    break;

                case "{G/B}":
                    break;
                case "{G/U}":
                    break;
                case "{G/R}":
                    break;
                case "{G/W}":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manaImageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_gw));
                    } else {
                        manaImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_gw));
                    }
                    manaCostHolder.addView(manaImageView);
                    break;

                case "{U/G}":
                    break;
                case "{U/B}":
                    break;
                case "{U/R}":
                    break;
                case "{U/W}":
                    break;
                default:
                    break;
            }
        }
    }
}
