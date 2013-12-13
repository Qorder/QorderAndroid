package com.example.clientalphaprototype.adapters;

import java.util.List;

import com.example.clientalphaprototype.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoriesCustomList extends ArrayAdapter<String> {

	private final Activity context;
	private final List<String> title;


	public CategoriesCustomList(Activity context, List<String> title) {
		super(context, R.layout.categories_listview, title);
		this.context = context;
		this.title = title;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.categories_listview, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.category_txt);

		txtTitle.setText(title.get(position));

		return rowView;
	}
}