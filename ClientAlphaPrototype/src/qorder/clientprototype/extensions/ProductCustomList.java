package qorder.clientprototype.extensions;

import java.util.List;

import com.example.clientalphaprototype.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductCustomList extends ArrayAdapter<String> {

	private final Activity context;
	private final List<String> title;
	private final List<String> price;

	public ProductCustomList(Activity context, List<String> products, List<String> price) {
		super(context, R.layout.product_listview, products);
		this.context = context;
		this.title = products;
		this.price = price;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.product_listview, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.large_txt);
		TextView txtPrice = (TextView) rowView.findViewById(R.id.small_txt);

		txtTitle.setText(title.get(position));
		txtPrice.setText(price.get(position));

		return rowView;
	}
}