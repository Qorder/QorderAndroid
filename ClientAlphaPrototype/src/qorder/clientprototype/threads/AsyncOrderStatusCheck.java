package qorder.clientprototype.threads;

import qorder.clientprototype.activities.OrdersActivity;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.util.NetworkUtil;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncOrderStatusCheck extends AsyncTask<String, Void, Void> {

	OrdersActivity activity;
	OrderHolder orderHolder;

	public AsyncOrderStatusCheck(OrdersActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(String... uris) {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Log.e("Error in thread while checking order status",
						e.getMessage());
				return null;
			}
			for (int i = 0; i < uris.length; i++) {
				try {
					if (isCancelled())
						return null;
					Log.e("Checking uri:", uris[i]);
					String status = NetworkUtil.checkOrderStatus(uris[i]);
					if (status != null) {
						if (!OrderHolder.getOrderStatusAt(i).equals(status)) {
							Log.e("new order status "
									+ OrderHolder.getOrderStatusAt(i), status);
							OrderHolder.setOrderStatusAt(i, status);
							publishProgress();

						}
					}
				} catch (Exception e) {
					Log.e("Error while checking order status", e.getMessage());
				}
			}
		}

	}

	@Override
	protected void onPostExecute(Void result) {
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		activity.initializeActivityArrayAdapter();
	}

}
