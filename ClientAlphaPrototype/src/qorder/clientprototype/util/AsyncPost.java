package qorder.clientprototype.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import qorder.clientprototype.R;
import qorder.clientprototype.activities.BasketActivity;
import qorder.clientprototype.jsonparsers.JsonOrderParser;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.OrderHolder;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncPost extends AsyncTask<BasketProduct, Boolean, Boolean> {

	private BasketActivity basketActivity;
	private ProgressDialog dialog;

	public AsyncPost(BasketActivity basketActivity) {
		this.basketActivity = basketActivity;
	}

	protected void onPreExecute() {
		this.dialog = new ProgressDialog(basketActivity);
		this.dialog.setMessage(basketActivity.getResources().getString(
				R.string.title_postdialog));
		this.dialog.show();
	}

	@Override
	protected Boolean doInBackground(BasketProduct... products) {
		List<BasketProduct> order = new ArrayList<BasketProduct>();

		for (int i = 0; i < products.length; i++)
			order.add(products[i]);

		JsonOrderParser jsonOrderParser = new JsonOrderParser();
		boolean isPostSuccessful = false;
		try {
			JSONObject orderJson = jsonOrderParser.parse(order);

			isPostSuccessful = NetworkUtil.sendJson(orderJson.toString(),
					OrderHolder.getWSPostUrI());
		} catch (Exception e) {
			Log.e("Json post AsyncTask failed", e.getMessage());
			return isPostSuccessful;
		}
		return isPostSuccessful;

	}

	protected void onPostExecute(final Boolean result) {
		if (this.dialog.isShowing()) {
			this.dialog.dismiss();
		}
		if (result) {
			Toast.makeText(
					basketActivity,
					basketActivity.getResources().getString(
							R.string.text_postsuccess), Toast.LENGTH_LONG)
					.show();
			OrderHolder.reset();
			basketActivity.initializeBasket();
		} else {
			Toast.makeText(
					basketActivity,
					basketActivity.getResources().getString(
							R.string.text_postfail), Toast.LENGTH_LONG).show();
		}
	}

}
