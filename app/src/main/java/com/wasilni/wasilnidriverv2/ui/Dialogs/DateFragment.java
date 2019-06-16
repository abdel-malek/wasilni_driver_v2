package com.wasilni.wasilnidriverv2.ui.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.timessquare.CalendarPickerView;
import com.wasilni.wasilnidriverv2.R;
import com.wasilni.wasilnidriverv2.util.UtilFunction;

import java.util.ArrayList;



public class DateFragment extends DialogFragment {


    private OnFragmentInteractionListener mListener;
    ArrayList<String> datesDetailsaux = new ArrayList<>();
    public DateFragment() {
        // Required empty public constructor
    }


    public static DateFragment newInstance(String param1, String param2) {
        DateFragment fragment = new DateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
        Button ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean repeatedRide);
    }
}