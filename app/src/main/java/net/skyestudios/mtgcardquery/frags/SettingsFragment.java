package net.skyestudios.mtgcardquery.frags;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.assets.AssetProcessor;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    AssetProcessor assetProcessor;
    MTGCardDataSource mtgCardDataSource;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mtgCardDataSource = new MTGCardDataSource(getContext());
        assetProcessor = new AssetProcessor(getActivity(), mtgCardDataSource);
        assetProcessor.setForcedUpdate();
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.force_updateButton:
                assetProcessor.execute();
                break;
        }
    }
}
