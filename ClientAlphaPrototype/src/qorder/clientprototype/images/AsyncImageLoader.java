package qorder.clientprototype.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class AsyncImageLoader implements Runnable {
	ImageView image;
	String url;

	public AsyncImageLoader(ImageView image, String url) {
		this.image = image;
		this.url = url;
	}

	public void run() {
		try {
			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;
			Bitmap bm = Utils.loadBitmap(url, bmOptions);

			image.setImageBitmap(bm);
		} catch (Exception ex) {
			Log.e("Error while parsing image ", ex.getMessage());
		}

	}
}
