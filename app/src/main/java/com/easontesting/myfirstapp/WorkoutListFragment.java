package com.easontesting.myfirstapp;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutListFragment extends ListFragment {
    static interface WorkoutListListener{
        void itemClicked(long id);
    }
    private WorkoutListListener listener;

    public WorkoutListFragment() {/* Required empty public constructor */}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] names = new String[Workout.workouts.length];
        for (int i=0; i<names.length; i++){
            names[i] = Workout.workouts[i].getName();
        }
        //DrinkCategoryActivity.java ListView listViewOfDrinks = getListView(); listViewOfDrinks.setAdapter(adapter1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.listener = (WorkoutListListener) activity;
    }

    //    public void onListItemClick(ListView listView, View listViewItem, int pos, long id){
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        if(listener != null){
            listener.itemClicked(id);
        }
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
