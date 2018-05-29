package com.easontesting.myfirstapp;

import android.app.FragmentTransaction;
import android.content.Intent;
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
        View fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer!=null) {
            this.itemClicked(0);
        }
    }
    @Override
    public void itemClicked(long id){
        Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName()+",id: "+ id );
        /*<fragment id/detail_frag
        //Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName()+" fragment id/detail_frag "  );
        //WorkoutDetailFragment detail_frag = (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        //detail_frag.setWorkout(id);
        */
        View fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer!=null) {
        //Layout-land
        Log.e(TAG, "easontesting " + TAG + ": " + new Throwable().getStackTrace()[0].getMethodName() + " With FragmentLayout ");
            //it reset the activity_detail.xml, but can't show on fragment id/detail_frag, so use FrameLayout
            //FrameLayout id/fragment_container
            Log.e(TAG, "easontesting " + TAG + ": " + new Throwable().getStackTrace()[0].getMethodName() + " FragmentLayout id/fragment_container ");
            WorkoutDetailFragment frag_details = new WorkoutDetailFragment();
            frag_details.setWorkout(id);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, frag_details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit(); //Trigger onCreateView, onStart of WorkoutDetailFragment
        }else {
        //Layout-port
            Log.e(TAG, "easontesting " + TAG + ": " + new Throwable().getStackTrace()[0].getMethodName() + " Without FragmentLayout ");
            Intent i = new Intent(this, WorkoutDetailIndependentActivity.class);
            i.putExtra(WorkoutDetailIndependentActivity.WORKOUT_ID, (int) id);
            startActivity(i);
        }
    }
}
