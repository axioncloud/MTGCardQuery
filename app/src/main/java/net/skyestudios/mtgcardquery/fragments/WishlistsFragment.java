package net.skyestudios.mtgcardquery.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.data.Wishlist;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    List<Wishlist> wishlists;
    ListView list_wishlists;
    ArrayAdapter arrayAdapter;

    public WishlistsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishlists = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        view.findViewById(R.id.fab_wishlist_add).setOnClickListener(this);
        list_wishlists = (ListView) view.findViewById(R.id.wishlist_ListView);
        list_wishlists.setAdapter(arrayAdapter);
        list_wishlists.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_wishlist_add:
                final EditText wishlistName = new EditText(getContext());
                AlertDialog createWishlistDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Create New Wishlist")
                        .setView(wishlistName)
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (wishlistName.getText().toString().equals("")) {

                                } else {
                                    arrayAdapter.add(wishlistName.getText().toString());
                                    arrayAdapter.notifyDataSetChanged();
                                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(wishlistName.getWindowToken(), 0);
                                    dialogInterface.dismiss();
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO: Start WishlistEditorActivity with wishlist number
    }
}
