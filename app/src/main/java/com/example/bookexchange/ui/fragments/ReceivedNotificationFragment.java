package com.example.bookexchange.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.TabAdapter;
import com.google.android.material.tabs.TabLayout;


public class ReceivedNotificationFragment extends Fragment {

    View inflate;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_received_notification, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tab_layout_received);
        viewPager = view.findViewById(R.id.view_pager_received);

        // Create an adapter to provide the fragments for each tab
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());

        // Add your fragments to the adapter
        adapter.addFragment(new ReceivedPostFragment(), "Posts");
        adapter.addFragment(new ReceivedRequestFragment(), "Requests");

        // Set the adapter to the ViewPager
        viewPager.setAdapter(adapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);



    }
}