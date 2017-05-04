package net.skyestudios.mtgcardquery.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.data.Settings;
import net.skyestudios.mtgcardquery.db.CloseDatabaseTask;
import net.skyestudios.mtgcardquery.db.OpenDatabaseTask;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Settings settings;
    private TextView lastupdate_textview;
    private String lastUpdate;

    /**
     * Fragments:
     * Don't touch a constructor
     * Do initialization in onCreate()
     * UI retrieval/updating in onCreateView
     */

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = (Settings) getArguments().getSerializable("settings");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.button_forceupdate).setOnClickListener(this);
        view.findViewById(R.id.button_update).setOnClickListener(this);
        lastupdate_textview = (TextView) view.findViewById(R.id.databaselastupdate_TextView);

        String lastUpdate = String.format(Locale.US, "[%s/%s/%s]",
                settings.getLastUpdateDate().get(GregorianCalendar.MONTH) + 1,
                settings.getLastUpdateDate().get(GregorianCalendar.DAY_OF_MONTH),
                settings.getLastUpdateDate().get(GregorianCalendar.YEAR));
        lastupdate_textview.setText(lastUpdate);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_forceupdate:
                settings.setLastUpdateDate(new GregorianCalendar(TimeZone.getDefault()));
                lastUpdate = String.format(Locale.US, "[%s/%s/%s]",
                        settings.getLastUpdateDate().get(GregorianCalendar.MONTH) + 1,
                        settings.getLastUpdateDate().get(GregorianCalendar.DAY_OF_MONTH),
                        settings.getLastUpdateDate().get(GregorianCalendar.YEAR));
                lastupdate_textview.setText(lastUpdate);
                settings.recreateAssetProcessor();
                settings.getAssetProcessor().setForcedUpdate();
                new OpenDatabaseTask(settings.getMtgCardDataSource()).execute();
                settings.getAssetProcessor().execute();
                new CloseDatabaseTask(settings.getMtgCardDataSource()).execute();
                break;
            case R.id.button_update:
                settings.setLastUpdateDate(new GregorianCalendar(TimeZone.getDefault()));
                lastUpdate = String.format(Locale.US, "[%s/%s/%s]",
                        settings.getLastUpdateDate().get(GregorianCalendar.MONTH) + 1,
                        settings.getLastUpdateDate().get(GregorianCalendar.DAY_OF_MONTH),
                        settings.getLastUpdateDate().get(GregorianCalendar.YEAR));
                lastupdate_textview.setText(lastUpdate);
                settings.recreateAssetProcessor();
                new OpenDatabaseTask(settings.getMtgCardDataSource()).execute();
                settings.getAssetProcessor().execute();
                new CloseDatabaseTask(settings.getMtgCardDataSource()).execute();
        }
    }
}
