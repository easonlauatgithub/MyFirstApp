package com.easontesting.myfirstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutDetailFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    //Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
    private long workoutId;

    public void onAttach(Bundle savedInstanceState){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
    }

    @TargetApi(17)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        if(savedInstanceState != null){
            workoutId = savedInstanceState.getLong("workoutId");
        }
        if(savedInstanceState == null) {
            StopwatchFragment frag_stopwatch = new StopwatchFragment();
            //FragmentTransaction ft = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.stopwatch_container, frag_stopwatch);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit(); //Trigger onCreateView, onStart of WorkoutDetailFragment
        }
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
    }

    @Override
    public void onStart(){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onStart();
        View view = getView(); //Fragment is not a view, cannot directly findViewById, so get root view id:detail_frag first
        if(view!=null){
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            TextView desc = (TextView) view.findViewById(R.id.textDescription);
            Workout workout = Workout.workouts[(int) workoutId];
            title.setText(workout.getName());
            desc.setText(workout.getDescription());
        }
    }
    @Override
    public void onPause() {
        Log.e(TAG, "easontesting " + TAG + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting " + TAG + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
        //super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("workoutId", workoutId);
    }
    @Override
    public void onStop() {
        Log.e(TAG, "easontesting " + TAG + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onStop();
    }
    public void setWorkout(long id){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        this.workoutId = id;
    }
}