package qorder.clientprototype.threads;

import qorder.clientprototype.activities.DetailsActivity;
import qorder.clientprototype.images.Utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncImageLoader extends AsyncTask<String, Void, Void> {

	DetailsActivity activity;
	Bitmap bm;

	public AsyncImageLoader(DetailsActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(String... uris) {
		try {
			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;
			bm = Utils.loadBitmap(uris[0], bmOptions);

			if(isCancelled()) return null;
				
		} catch (Exception ex) {
			Log.e("Error while parsing image ", ex.getMessage());
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		activity.setImage(bm);
	}

}