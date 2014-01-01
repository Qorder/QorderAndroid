package qorder.clientprototype.extensions;

import java.util.List;

import qorder.clientprototype.R;
import qorder.clientprototype.model.DetailsHolder;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class DetailsCustomList extends ArrayAdapter<DetailsHolder> {

	  public final List<DetailsHolder> list;
	  private final Activity context;

	  public DetailsCustomList(Activity context, List<DetailsHolder> list) {
	    super(context, R.layout.details_listview, list);
	    this.context = context;
	    this.list = list;
	  }

	  static class ViewHolder {
	    protected CheckBox checkbox;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View view = null;
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.details_listview, null);
	      final ViewHolder viewHolder = new ViewHolder();
	      
	      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox_details);
	      viewHolder.checkbox.setText(list.get(position).getName());
	      viewHolder.checkbox
	          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	            @Override
	            public void onCheckedChanged(CompoundButton buttonView,
	                boolean isChecked) {
	            	DetailsHolder element = (DetailsHolder) viewHolder.checkbox
	                  .getTag();
	              element.setSelected(buttonView.isChecked());
	              
	            }
	          });
	      view.setTag(viewHolder);
	      viewHolder.checkbox.setTag(list.get(position));
	    } else {
	      view = convertView;
	      ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
	    }
	    ViewHolder holder = (ViewHolder) view.getTag();
	    holder.checkbox.setChecked(list.get(position).isSelected());
	    return view;
	  }
	} 