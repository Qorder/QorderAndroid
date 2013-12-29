package qorder.clientprototype.extensions;

import java.util.List;

import qorder.clientprototype.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BasketCustomList extends ArrayAdapter<String> {

	private final Activity context;
	private List<String> title;
	private List<String> notes;
	private List<String> price;

	public BasketCustomList(Activity context, List<String> title,List<String> notes, List<String> price) {
		super(context, R.layout.basket_listview, title);
		this.context = context;
		setItems(title,notes,price);
	}
	
	public void setItems(List<String> title,List<String> notes, List<String> price) {
		this.notes = notes;
		this.title = title;
		this.price = price;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.basket_listview, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.large_txt);
		TextView txtPrice = (TextView) rowView.findViewById(R.id.small_txt);
		TextView txtNotes = (TextView) rowView.findViewById(R.id.price_txt);
		txtTitle.setText(title.get(position));
		txtPrice.setText(price.get(position));
		txtNotes.setText(notes.get(position));
		
		return rowView;
	}
}