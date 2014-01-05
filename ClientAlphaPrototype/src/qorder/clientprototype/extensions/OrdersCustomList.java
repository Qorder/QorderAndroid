package qorder.clientprototype.extensions;

import java.util.List;

import qorder.clientprototype.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrdersCustomList extends ArrayAdapter<String> {

	private final Activity context;
	private List<String> title;
	private List<String> status;

	public OrdersCustomList(Activity context, List<String> title,List<String> status) {
		super(context, R.layout.orders_listview, title);
		this.context = context;
		setItems(title,status);
	}
	
	public void setItems(List<String> title,List<String> status) {
		this.title = title;
		this.status = status;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent){

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.orders_listview, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.order_txt);
		TextView txtStatus = (TextView) rowView.findViewById(R.id.status_txt);
		txtTitle.setText(title.get(position));
		txtStatus.setText(status.get(position));
		
		return rowView;
	}
}