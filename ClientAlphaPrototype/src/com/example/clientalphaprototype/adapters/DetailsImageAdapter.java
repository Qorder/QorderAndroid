package com.example.clientalphaprototype.adapters;

import com.example.clientalphaprototype.R;

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

    private Integer[] mImageIds = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
    };

    public DetailsImageAdapter(Context context) 
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
	public View getView(int index, View view, ViewGroup viewGroup) 
    {
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}