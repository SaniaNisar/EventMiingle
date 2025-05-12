//package com.app.eventmingle.fragments;
//
//import android.os.Bundle;
//import android.view.*;
//import android.widget.TextView;
//import androidx.annotation.*;
//import androidx.fragment.app.Fragment;
//import com.app.eventmingle.R;
//import com.app.eventmingle.models.Event;
//import com.app.eventmingle.utils.FirebaseUtils;
//import com.google.firebase.database.*;
//
//public class BudgetFragmentList extends Fragment {
//    private static final String ARG_ID="eventId";
//    private String eventId;
//
//    public static BudgetFragment newInstance(String eventId){
//        Bundle b=new Bundle();
//        b.putString(ARG_ID,eventId);
//        BudgetFragment f=new BudgetFragment();
//        f.setArguments(b);
//        return f;
//    }
//
//    @Nullable @Override
//    public View onCreateView(@NonNull LayoutInflater inf,
//                             @Nullable ViewGroup c,
//                             @Nullable Bundle s) {
//        View v=inf.inflate(R.layout.fragment_budget,c,false);
//        TextView tvBudget=v.findViewById(R.id.tvBudgetValue);
//        eventId=getArguments().getString(ARG_ID);
//
//        FirebaseUtils.getEventsRef().child(eventId)
//                .addListenerForSingleValueEvent(new ValueEventListener(){
//                    @Override public void onDataChange(@NonNull DataSnapshot snap){
//                        Event e=snap.getValue(Event.class);
//                        if(e!=null){
//                            tvBudget.setText("₹ " + e.getBudget());
//                        }
//                    }
//                    @Override public void onCancelled(@NonNull DatabaseError err){}
//                });
//        return v;
//    }
//}

package com.app.eventmingle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.eventmingle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragmentList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragmentList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragmentList newInstance(String param1, String param2) {
        BudgetFragmentList fragment = new BudgetFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget_list, container, false);
    }
}