package qorder.clientprototype.extensions;

import java.util.ArrayList;
import java.util.List;

import qorder.clientprototype.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class DetailsImageAdapter extends BaseAdapter 
{
    private Context mContext;
	List<Integer> imgIds;
	
    public DetailsImageAdapter(Context context) 
    {
    	imgIds = new ArrayList<Integer>();
    	setImages();
    	
        mContext = context;
    }
   
    public int getCount() {

        return imgIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
	public View getView(int index, View view, ViewGroup viewGroup) 
    {
        ImageView i = new ImageView(mContext);

        i.setImageResource(imgIds.get(index));
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        
        return i;
    }
    
    private void setImages(){
    	for(int i=0;i<3;i++)
    	{
    		imgIds.add(R.drawable.image1);
			imgIds.add(R.drawable.image2);
			imgIds.add(R.drawable.image3);
    	}
    }
}