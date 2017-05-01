package net.skyestudios.mtgcardquery.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.ResultsActivity;

/**
 * Created by arkeonet64 on 3/14/2017.
 */

public class QueryFragment extends Fragment implements View.OnClickListener {

    private Intent resultsIntent;

    private Button openColorIdentityDialog;
    private Button openColorDialog;
    private AlertDialog colorDialog;
    private CheckBox cb_white;
    private CheckBox cb_green;
    private CheckBox cb_red;
    private CheckBox cb_black;
    private CheckBox cb_blue;
    private CheckBox cb_colorless;
    private boolean cb_whiteState;
    private boolean cb_redState;
    private boolean cb_blueState;
    private boolean cb_blackState;
    private boolean cb_greenState;
    private boolean cb_colorlessState;
    /***********************************************/
    private AlertDialog colorIdentityDialog;
    private CheckBox cb_white1;
    private CheckBox cb_green1;
    private CheckBox cb_red1;
    private CheckBox cb_black1;
    private CheckBox cb_blue1;
    private CheckBox cb_colorless1;
    private boolean cb_whiteState1;
    private boolean cb_redState1;
    private boolean cb_blueState1;
    private boolean cb_blackState1;
    private boolean cb_greenState1;
    private boolean cb_colorlessState1;
    /***********************************************/
    private EditText card_name_Query;
    private EditText super_type_text;
    private EditText subtype_text;
    private EditText text_text;
    private Spinner power_spinner;
    private Spinner power_number_spinner;
    private Spinner tough_spinner;
    private Spinner tough_number_spinner;
    private Spinner cmc_spinner;
    private Spinner cmc_number_spinner;
    /***********************************************/
    private Context context;
    private String queryString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        view.findViewById(R.id.button_query).setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_reset_query:
                resetQuery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetQuery() {
        cb_whiteState = false;
        cb_redState = false;
        cb_colorlessState = false;
        cb_blackState = false;
        cb_blueState = false;
        cb_greenState = false;

        if (cb_white != null) {
            cb_white.setChecked(false);
        }
        if (cb_green != null) {
            cb_green.setChecked(false);
        }
        if (cb_red != null) {
            cb_red.setChecked(false);
        }
        if (cb_black != null) {
            cb_black.setChecked(false);
        }
        if (cb_blue != null) {
            cb_blue.setChecked(false);
        }
        if (cb_colorless != null) {
            cb_colorless.setChecked(false);
        }

        /***********************************************/

        cb_whiteState1 = false;
        cb_redState1 = false;
        cb_colorlessState1 = false;
        cb_blackState1 = false;
        cb_blueState1 = false;
        cb_greenState1 = false;

        if (cb_white1 != null) {
            cb_white1.setChecked(false);
        }
        if (cb_green1 != null) {
            cb_green1.setChecked(false);
        }
        if (cb_red1 != null) {
            cb_red1.setChecked(false);
        }
        if (cb_black1 != null) {
            cb_black1.setChecked(false);
        }
        if (cb_blue1 != null) {
            cb_blue1.setChecked(false);
        }
        if (cb_colorless1 != null) {
            cb_colorless1.setChecked(false);
        }

        /***********************************************/

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        queryString = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context = getContext();
        } else {
            context = getActivity().getApplicationContext();
        }

        resultsIntent = new Intent(context, ResultsActivity.class);

        colorDialog = new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog)
                .setTitle("Color Selection")
                .setView(R.layout.layout_dialog_color)
                .setCancelable(false)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb_whiteState = cb_white.isChecked();
                        cb_redState = cb_red.isChecked();
                        cb_blueState = cb_blue.isChecked();
                        cb_blackState = cb_black.isChecked();
                        cb_greenState = cb_green.isChecked();
                        cb_colorlessState = cb_colorless.isChecked();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb_white.setChecked(cb_whiteState);
                        cb_black.setChecked(cb_blackState);
                        cb_blue.setChecked(cb_blueState);
                        cb_green.setChecked(cb_greenState);
                        cb_colorless.setChecked(cb_colorlessState);
                        cb_red.setChecked(cb_redState);

                        dialogInterface.dismiss();
                    }
                })
                .create();
        colorDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (cb_white == null) {
                    cb_white = (CheckBox) colorDialog.findViewById(R.id.color_query_white);
                }
                if (cb_green == null) {
                    cb_green = (CheckBox) colorDialog.findViewById(R.id.color_query_green);
                }
                if (cb_red == null) {
                    cb_red = (CheckBox) colorDialog.findViewById(R.id.color_query_red);
                }
                if (cb_black == null) {
                    cb_black = (CheckBox) colorDialog.findViewById(R.id.color_query_black);
                }
                if (cb_blue == null) {
                    cb_blue = (CheckBox) colorDialog.findViewById(R.id.color_query_blue);
                }
                if (cb_colorless == null) {
                    cb_colorless = (CheckBox) colorDialog.findViewById(R.id.color_query_colorless);
                }
            }
        });

        /***********************************************/
        colorIdentityDialog = new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog)
                .setTitle("Color Selection")
                .setView(R.layout.layout_dialog_color)
                .setCancelable(false)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb_whiteState1 = cb_white1.isChecked();
                        cb_redState1 = cb_red1.isChecked();
                        cb_blueState1 = cb_blue1.isChecked();
                        cb_blackState1 = cb_black1.isChecked();
                        cb_greenState1 = cb_green1.isChecked();
                        cb_colorlessState1 = cb_colorless1.isChecked();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb_white1.setChecked(cb_whiteState1);
                        cb_black1.setChecked(cb_blackState1);
                        cb_blue1.setChecked(cb_blueState1);
                        cb_green1.setChecked(cb_greenState1);
                        cb_colorless1.setChecked(cb_colorlessState1);
                        cb_red1.setChecked(cb_redState1);

                        dialogInterface.dismiss();
                    }
                })
                .create();
        colorIdentityDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (cb_white1 == null) {
                    cb_white1 = (CheckBox) colorIdentityDialog.findViewById(R.id.color_query_white);
                }
                if (cb_green1 == null) {
                    cb_green1 = (CheckBox) colorIdentityDialog.findViewById(R.id.color_query_green);
                }
                if (cb_red1 == null) {
                    cb_red1 = (CheckBox) colorIdentityDialog.findViewById(R.id.color_query_red);
                }
                if (cb_black1 == null) {
                    cb_black1 = (CheckBox) colorIdentityDialog.findViewById(R.id.color_query_black);
                }
                if (cb_blue1 == null) {
                    cb_blue1 = (CheckBox) colorIdentityDialog.findViewById(R.id.color_query_blue);
                }
                if (cb_colorless1 == null) {
                    cb_colorless1 = (CheckBox) colorIdentityDialog.findViewById(R.id.color_query_colorless);
                }
            }
        });

        /***********************************************/
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        colorDialog.dismiss();
        colorIdentityDialog.dismiss();
    }

    @Override
    public void onResume() {
        openColorDialog = (Button) getActivity().findViewById(R.id.button_openColorDialog);
        openColorDialog.setOnClickListener(this);
        openColorIdentityDialog = (Button) getActivity().findViewById(R.id.button_openColorIdentityDialog);
        openColorIdentityDialog.setOnClickListener(this);
        /************************************************/
        card_name_Query = (EditText) getActivity().findViewById(R.id.card_name_Query);
        super_type_text = (EditText) getActivity().findViewById(R.id.super_type_text);
        subtype_text = (EditText) getActivity().findViewById(R.id.subtype_text);
        text_text = (EditText) getActivity().findViewById(R.id.text_text);
        power_spinner = (Spinner) getActivity().findViewById(R.id.power_spinner);
        power_number_spinner = (Spinner) getActivity().findViewById(R.id.power_number_spinner);
        tough_spinner = (Spinner) getActivity().findViewById(R.id.tough_spinner);
        tough_number_spinner = (Spinner) getActivity().findViewById(R.id.tough_number_spinner);
        cmc_spinner = (Spinner) getActivity().findViewById(R.id.cmc_spinner);
        cmc_number_spinner = (Spinner) getActivity().findViewById(R.id.cmc_number_spinner);
        /************************************************/
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_openColorDialog:
                colorDialog.show();
                break;
            case R.id.button_openColorIdentityDialog:
               colorIdentityDialog.show();
                break;
            case R.id.button_query:
                buildQueryString();
                resultsIntent.putExtra("queryString", queryString);
                startActivity(resultsIntent);
                break;
        }
    }

    private void buildQueryString() {

    }

}
