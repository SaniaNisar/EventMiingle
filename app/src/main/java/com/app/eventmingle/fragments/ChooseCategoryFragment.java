package com.app.eventmingle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.eventmingle.R;

public class ChooseCategoryFragment extends Fragment {

    public ChooseCategoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_category, container, false);

        view.findViewById(R.id.vendorVenue).setOnClickListener(v -> openVendorFragment("VENUES"));
        view.findViewById(R.id.vendorPhotographer).setOnClickListener(v -> openVendorFragment("PHOTOGRAPHERS"));
        view.findViewById(R.id.vendorMakeupArtist).setOnClickListener(v -> openVendorFragment("MAKEUP ARTISTS"));
        view.findViewById(R.id.vendorDecor).setOnClickListener(v -> openVendorFragment("DECOR"));
        view.findViewById(R.id.vendorCatering).setOnClickListener(v -> openVendorFragment("CATERING"));
        view.findViewById(R.id.vendorHennaArtist).setOnClickListener(v -> openVendorFragment("HENNA ARTISTS"));
        view.findViewById(R.id.vendorEventStationery).setOnClickListener(v -> openVendorFragment("EVENT STATIONERY"));
        view.findViewById(R.id.vendorDresses).setOnClickListener(v -> openVendorFragment("EVENT WEAR"));

        return view;
    }

    private void openVendorFragment(String category) {
        VendorsFragment vendorsFragment = new VendorsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORY", category); // Passing the category to the vendor fragment
        vendorsFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, vendorsFragment)
                .addToBackStack(null) // Add to back stack if necessary
                .commit();
    }

}

