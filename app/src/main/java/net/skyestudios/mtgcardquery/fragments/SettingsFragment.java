package net.skyestudios.mtgcardquery.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.data.Settings;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Settings settings;

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
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_forceupdate:
                settings.recreateAssetProcessor();
                settings.getAssetProcessor().setForcedUpdate();
                settings.getAssetProcessor().execute();
                break;
            case R.id.button_update:
                settings.recreateAssetProcessor();
                settings.getAssetProcessor().execute();
        }
    }
}
