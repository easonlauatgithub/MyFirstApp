package com.easontesting.myfirstapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FragmentActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener {
    /* DEBUG  */private static final String TAG8 = "FragmentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //WorkoutDetailFragment frag = (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        //frag.setWorkout(1);
    }
    @Override
    public void itemClicked(long id){
        Log.e(TAG8, "easontesting FragmentActivity itemClicked id: "+id);
        WorkoutDetailFragment details = new WorkoutDetailFragment();
        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        details.setWorkout(id);
        ftrans.replace(R.id.fragment_container, details);
        ftrans.addToBackStack(null);
        ftrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ftrans.commit();

    }
}
