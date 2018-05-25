package com.easontesting.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutDetailFragment extends Fragment {
    private long workoutId;
    public WorkoutDetailFragment() {/* Required empty public constructor */}

    @Override
    public void onStart(){
        super.onStart();
        Workout workout = Workout.workouts[(int)workoutId];
        View v = getView();
        if(v!=null){
            TextView title = (TextView) v.findViewById(R.id.textTitle);
            title.setText(workout.getName());
            TextView description = (TextView) v.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("workoutId", workoutId);
    }
    public void setWorkout(long id){
        this.workoutId = id;
    }
}
/*
* F - fragment
* A - activity
* onAttach() - F connects with A
* onCreate() - F onCreate()
* onCreateView() - F uses layout inflater
* onActivityCreated() - after A onCreate()
* onStart() - F becomes visible
* onResume() - F becomes visible and on focus
* onPause() - F becomes visible and not on focus
* onStop() - F becomes invisible
* onDestroyView() - F clears view
* onDestroy - F clears all
* onDetach - F disconnects with A
*/