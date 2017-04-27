package net.skyestudios.mtgcardquery.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.skyestudios.mtgcardquery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DecksFragment extends Fragment implements View.OnClickListener {


    public DecksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decks, container, false);
        view.findViewById(R.id.fab_decks_add).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_decks_add:
                //TODO: Dimitrei - Show dialog with input for a new deck name
                Toast.makeText(getContext(), "Adding a deck", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
