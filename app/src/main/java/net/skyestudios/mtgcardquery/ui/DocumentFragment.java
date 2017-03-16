package net.skyestudios.mtgcardquery.ui;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import net.skyestudios.mtgcardquery.R;

/**
 * Created by arkeonet64 on 3/14/2017.
 */

public class DocumentFragment extends Fragment implements View.OnClickListener {

    private Button openColorIdentityDialog;
    private Button openColorDialog;
    private AlertDialog colorDialog;
    private CheckBox cb_white;
    private boolean cb_whiteState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_query, container, false);
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
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        TextView title = new TextView(getContext());
        title.setText("Color Selection");
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setTextSize(getResources().getDimension(R.dimen.dialog_textsize));
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        colorDialog = new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog)
                .setCustomTitle(title)
                .setIcon(R.mipmap.ic_launcher)
                .setView(R.layout.layout_dialog_color)
                .setCancelable(true)
                .create();
        colorDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                cb_white = (CheckBox) colorDialog.findViewById(R.id.color_query_white);
                cb_white.setChecked(cb_whiteState);
            }
        });
        colorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                cb_whiteState = cb_white.isChecked();
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        openColorDialog = (Button) getActivity().findViewById(R.id.openColorDialog);
        openColorDialog.setOnClickListener(this);
        openColorIdentityDialog = (Button) getActivity().findViewById(R.id.openColorIdentityDialog);
        openColorIdentityDialog.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openColorDialog:
                colorDialog.show();
                break;
            case R.id.openColorIdentityDialog:
                break;
        }
    }
}
