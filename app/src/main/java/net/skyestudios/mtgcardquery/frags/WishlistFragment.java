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
public class WishlistFragment extends Fragment implements View.OnClickListener {


    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        view.findViewById(R.id.fab_wishlist_add).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_wishlist_add:
                //TODO: Dimitrei - Show dialog with input for a new wishlist name
                Toast.makeText(getContext(), "Adding a wishlist", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
