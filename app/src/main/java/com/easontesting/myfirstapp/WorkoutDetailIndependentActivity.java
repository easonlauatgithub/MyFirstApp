package com.easontesting.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class WorkoutDetailIndependentActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    public static final String WORKOUT_ID =  "WORKOUT_ID defined in WorkoutDetailIndependentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": " + new Throwable().getStackTrace()[0].getMethodName() );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail_independent);
        int workoutId = (int) getIntent().getExtras().get(WORKOUT_ID);
        WorkoutDetailFragment detail_independent_frag = (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_independent_frag);
        detail_independent_frag.setWorkout(workoutId);
    }
}
