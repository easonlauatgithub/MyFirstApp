package com.easontesting.myfirstapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


public class FragmentActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener  {
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName()  );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //Fragment is not a view, so no findViewById, hence use getFragmentManager to findFragmentById
        //WorkoutDetailFragment detail_frag = (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        //detail_frag.setWorkout(1);
    }
    @Override
    public void itemClicked(long id){
        Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName()  );
        /*<fragment id/detail_frag
        //Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName()+" fragment id/detail_frag "  );
        //WorkoutDetailFragment detail_frag = (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        //detail_frag.setWorkout(id);
        */
        //it reset the activity_detail.xml, but can't show on fragment id/detail_frag, so use FrameLayout

        //FrameLayout id/fragment_container
        Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName()+" FragmentLayout id/fragment_container "  );
        WorkoutDetailFragment details = new WorkoutDetailFragment();
        details.setWorkout(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, details);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit(); //Trigger onCreateView, onStart of WorkoutDetailFragment

    }
}
