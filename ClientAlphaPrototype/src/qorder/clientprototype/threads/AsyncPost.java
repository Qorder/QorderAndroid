package qorder.clientprototype.threads;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import qorder.clientprototype.R;
import qorder.clientprototype.activities.BasketActivity;
import qorder.clientprototype.jsonparsers.JsonOrderParser;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.model.OrderInfo;
import qorder.clientprototype.util.NetworkUtil;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncPost extends AsyncTask<BasketProduct, OrderInfo, OrderInfo> {

	private BasketActivity basketActivity;
	private ProgressDialog dialog;
	private final boolean mockSend = false;

	public AsyncPost(BasketActivity basketActivity) {
		this.basketActivity = basketActivity;
	}

	protected void onPreExecute() {
		this.dialog = new ProgressDialog(basketActivity);
		this.dialog.setMessage(basketActivity.getResources().getString(
				R.string.title_postdialog));
		dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected OrderInfo doInBackground(BasketProduct... products) {
		List<BasketProduct> order = new ArrayList<BasketProduct>();
		OrderInfo orderInfo = new OrderInfo(null,null,false);
		
		if (mockSend) {
			OrderHolder.sendOrder("1", "pending");
			return orderInfo;
		}

		for (int i = 0; i < products.length; i++)
			order.add(products[i]);

		JsonOrderParser jsonOrderParser = new JsonOrderParser();

		try {
			JSONObject orderJson = jsonOrderParser.parse(order);
			Log.i("order json:",orderJson.toString());
			orderInfo = NetworkUtil.sendJson(orderJson.toString(),
					OrderHolder.getWSPostUrI());
		} catch (Exception e) {
			Log.e("Json post AsyncTask failed", e.getMessage());
			return (new OrderInfo(null,null,false));
		}
		return orderInfo;

	}

	protected void onPostExecute(final OrderInfo result) {
		if (this.dialog.isShowing()) {
			this.dialog.dismiss();
		}
		if (result.getResult()) {
			Toast.makeText(
					basketActivity,
					basketActivity.getResources().getString(
							R.string.text_postsuccess), Toast.LENGTH_LONG)
					.show();
			if (!mockSend)
			{
				OrderHolder.sendOrder(result.getId(),result.getStatus());
			}
			basketActivity.initializeBasket();
		} else {
			Toast.makeText(
					basketActivity,
					basketActivity.getResources().getString(
							R.string.text_postfail), Toast.LENGTH_LONG).show();
		}
	}
	

}
