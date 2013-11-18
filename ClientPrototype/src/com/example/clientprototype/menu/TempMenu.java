package com.example.clientprototype.menu;

import com.example.clientprototype.R;
import com.example.clientprototype.R.id;
import com.example.clientprototype.menu.expandable.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;

public class TempMenu extends Activity {

	SparseArray<ListGroup> groups = new SparseArray<ListGroup>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp_menu);

		//initializeNumberPicker();
		
		createData();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
		ExpandableListAdapter adapter = new ExpandableListAdapter(this, groups);
		listView.setAdapter(adapter);

	}

	private void initializeNumberPicker()
	{
		NumberPicker numberpicker = (NumberPicker) findViewById(R.id.numberPicker1);
		numberpicker.setMaxValue(100);
		numberpicker.setMinValue(0);
	}
	
	public void createData() {
		for (int j = 0; j < 5; j++) {
			ListGroup group = new ListGroup("  item " + j);
			for (int i = 0; i < 1; i++) {
				group.children.add("item's description goes here \n \t attribute 1 \n \t attribute 2   " );
			}
			groups.append(j, group);
		}
	}
}