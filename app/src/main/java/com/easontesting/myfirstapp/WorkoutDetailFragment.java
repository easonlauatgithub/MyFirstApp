package com.easontesting.myfirstapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutDetailFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private long workoutId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
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
    public void setWorkout(long id){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        this.workoutId = id;
    }
}