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

import net.skyestudios.mtgcardquery.MainActivity;
import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.ResultsActivity;

import static net.skyestudios.mtgcardquery.db.MTGCardSQLiteHelper.MAIN_TABLE_NAME;

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

        card_name_Query.setText("");
        super_type_text.setText("");
        subtype_text.setText("");
        text_text.setText("");
        power_spinner.setSelection(0);
        power_number_spinner.setSelection(0);
        tough_spinner.setSelection(0);
        tough_number_spinner.setSelection(0);
        cmc_spinner.setSelection(0);
        cmc_number_spinner.setSelection(0);
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
                if (!((MainActivity) getActivity()).settings.getAssetProcessor().isRunning()) {
                    buildQueryString();
                    resultsIntent.putExtra("queryString", queryString);
                    startActivity(resultsIntent);
                }
                break;
        }
    }

    private void buildQueryString() {
        String name = card_name_Query.getText().toString().trim();
        String superType = super_type_text.getText().toString().trim();
        String subType = subtype_text.getText().toString().trim();
        String text = text_text.getText().toString().trim();
        String powerSym = power_spinner.getSelectedItem().toString();
        String powerNum = power_number_spinner.getSelectedItem().toString();
        String power;
        String toughSym = tough_spinner.getSelectedItem().toString();
        String toughNum = tough_number_spinner.getSelectedItem().toString();
        String tough;
        String cmcSym = cmc_spinner.getSelectedItem().toString();
        String cmcNum = cmc_number_spinner.getSelectedItem().toString();
        String cmc;
        String color_white = "";
        String color_green = "";
        String color_red = "";
        String color_black = "";
        String color_blue = "";
        String color_colorless = "";
        String colorId_white = "";
        String colorId_green = "";
        String colorId_red = "";
        String colorId_black = "";
        String colorId_blue = "";
        String colorId_colorless = "";

        if (!name.isEmpty()) {
            name = "name LIKE '%" + name + "%'";
        } else {
            name = "name IS NOT NULL";
        }
        if (!superType.isEmpty()) {
            superType = "type LIKE '%" + superType + "%'";
        } else {
            superType = "type IS NOT NULL";
        }
        if (!subType.isEmpty()) {
            subType = "subtypes LIKE '%" + subType + "%'";
        } else {
            subType = "subtypes IS NOT NULL";
        }
        if (!text.isEmpty()) {
            text = "text LIKE '%" + text + "%'";
        } else {
            text = "text IS NOT NULL";
        }
        if (cb_white != null && cb_white.isChecked()) {
            color_white = " AND colors LIKE '%White%' ";
        } else {
            color_white = " AND colors IS NOT NULL ";
        }
        if (cb_green != null && cb_green.isChecked()) {
            color_green = " AND colors LIKE '%Green%' ";
        } else {
            color_green = " AND colors IS NOT NULL ";
        }
        if (cb_red != null && cb_red.isChecked()) {
            color_red = " AND colors LIKE '%Red%' ";
        } else {
            color_red = " AND colors IS NOT NULL ";
        }
        if (cb_black != null && cb_black.isChecked()) {
            color_black = " AND colors LIKE '%Black%' ";
        } else {
            color_black = " AND colors IS NOT NULL ";
        }
        if (cb_blue != null && cb_blue.isChecked()) {
            color_blue = " AND colors LIKE '%Blue%' ";
        } else {
            color_blue = " AND colors IS NOT NULL ";
        }
        if (cb_colorless != null && cb_colorless.isChecked()) {
            color_colorless = " AND colors = '' ";
        } else {
            color_colorless = " AND colors IS NOT NULL ";
        }
        if (cb_white1 != null && cb_white1.isChecked()) {
            colorId_white = " AND colorIdentity LIKE '%W%' ";
        } else {
            colorId_white = " AND colorIdentity IS NOT NULL ";
        }
        if (cb_green1 != null && cb_green1.isChecked()) {
            colorId_green = " AND colorIdentity LIKE '%G%' ";
        } else {
            colorId_green = " AND colorIdentity IS NOT NULL ";
        }
        if (cb_red1 != null && cb_red1.isChecked()) {
            colorId_red = " AND colorIdentity LIKE '%R%' ";
        } else {
            colorId_red = " AND colorIdentity IS NOT NULL ";
        }
        if (cb_black1 != null && cb_black1.isChecked()) {
            colorId_black = " AND colorIdentity LIKE '%B%' ";
        } else {
            colorId_black = " AND colorIdentity IS NOT NULL ";
        }
        if (cb_blue1 != null && cb_blue1.isChecked()) {
            colorId_blue = " AND colorIdentity LIKE '%U%' ";
        } else {
            colorId_blue = " AND colorIdentity IS NOT NULL ";
        }
        if (cb_colorless1 != null && cb_colorless1.isChecked()) {
            colorId_colorless = " AND colorIdentity =  '' ";
        } else {
            colorId_colorless = " AND colorIdentity IS NOT NULL ";
        }
        if (!powerSym.isEmpty() && power_spinner.getSelectedItem() != null) {
            if (!powerNum.isEmpty() && power_number_spinner.getSelectedItem() != null) {
                power = " AND " + "power " + powerSym + " " + powerNum;
            } else {
                power = " AND power IS NOT NULL ";
            }
        } else {
            power = " AND power IS NOT NULL ";
        }
        if (!toughSym.isEmpty() && tough_spinner.getSelectedItem() != null) {
            if (!toughNum.isEmpty() && tough_number_spinner.getSelectedItem() != null) {

                tough = " AND " + "toughness " + toughSym + " " + toughNum;
            } else {
                tough = " AND toughness IS NOT NULL ";
            }
        } else {
            tough = " AND toughness IS NOT NULL ";
        }
        if (!cmcSym.isEmpty() && cmc_spinner.getSelectedItem() != null) {
            if (!cmcNum.isEmpty() && cmc_number_spinner.getSelectedItem() != null) {
                cmc = " AND " + "cmc " + cmcSym + " " + Double.parseDouble(cmcNum);
            } else {
                cmc = " AND cmc IS NOT NULL ";
            }
        } else {
            cmc = " AND cmc IS NOT NULL ";
        }
        //force-update does not handle duplicates
        queryString = "SELECT * FROM " + MAIN_TABLE_NAME
                + " WHERE " + name
                + " AND " + superType
                + " AND " + subType
                + " AND " + text
                + color_white
                + color_green
                + color_red
                + color_black
                + color_blue
                + color_colorless //Need to figure value
                + colorId_white
                + colorId_green
                + colorId_red
                + colorId_black
                + colorId_blue
                + colorId_colorless //Need to figure value
                + power
                + tough
                + cmc // '=' symbol returns 0 results
                + " ORDER BY name ASC;";

    }

}
