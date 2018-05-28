package com.easontesting.myfirstapp;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WorkoutListFragment extends ListFragment {
    private final String TAG = this.getClass().getSimpleName();
    static interface WorkoutListListener{
        void itemClicked(long id);
    }

    private WorkoutListListener listener;

    @Override
    public void onAttach(Activity a){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onAttach(a);
        this.listener = (WorkoutListListener) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        /*TEST1
        String[] names = new String[Workout.workouts.length];
        for (int i=0; i<names.length; i++){
            names[i] = Workout.workouts[i].getName();
        }
        */
        Workout[] arrWorkoutData = Workout.workouts;
        //DrinkCategoryActivity.java ListView listViewOfDrinks = getListView(); listViewOfDrinks.setAdapter(adapter1);
        //TEST1        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
        ArrayAdapter<Workout> adapter = new ArrayAdapter<Workout>(inflater.getContext(), android.R.layout.simple_list_item_1, arrWorkoutData);
        //getListView().setAdapter(adapter); //IllegalStateException: Content view not yet created
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onListItemClick(ListView listView, View itemView, int position, long id){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        Log.e(TAG, "easontesting id: "+ id );
        if(listener != null){
            listener.itemClicked(id);
        }
    }
}