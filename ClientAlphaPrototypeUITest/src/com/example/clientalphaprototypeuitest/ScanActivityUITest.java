package com.example.clientalphaprototypeuitest;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class ScanActivityUITest extends UiAutomatorTestCase {   

	public void testDemo() throws UiObjectNotFoundException, RemoteException, InterruptedException {  

		//Check if screen is asleep and wake/unlock as needed
		if (!getUiDevice().isScreenOn()){
			getUiDevice().wakeUp();
		}

		// Simulate a short press on the HOME button.
		getUiDevice().pressHome();

		UiObject allAppsButton = new UiObject(new UiSelector()
		.description("Apps"));

		// Simulate a click to bring up the All Apps screen.
		allAppsButton.clickAndWaitForNewWindow();

		UiObject appsTab = new UiObject(new UiSelector()
		.text("Apps"));

		// Simulate a click to enter the Apps tab.
		appsTab.click();

		UiScrollable appViews = new UiScrollable(new UiSelector()
		.scrollable(true));

		// Set the swiping mode to horizontal (the default is vertical)
		appViews.setAsHorizontalList();

		UiObject settingsApp = appViews.getChildByText(new UiSelector()
		.className(android.widget.TextView.class.getName()), 
				"ClientPrototype");
		settingsApp.clickAndWaitForNewWindow();

		// Validate that the package name is the expected one
		UiObject clientalphaprototypeValidation = new UiObject(new UiSelector()
		.packageName("qorder.clientprototype"));
		assertTrue("Unable to detect Qorder", 
				clientalphaprototypeValidation.exists()); 

		//Press Help Text
		//UiObject infoLayout = new UiObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
		//UiObject textinfo = infoLayout.getChild(new UiSelector()
		//.className("android.widget.TextView").index(0));
		//textinfo.click();
		
		/*if(textinfo.exists()) 
		{	
			textinfo.click();	
		}*/
		
		//Press "I am ready to order"
		UiObject button_scan = new UiObject(new UiSelector().className("android.widget.Button").index(1));

		if(button_scan.exists() && button_scan.isEnabled()) 
		{
			button_scan.click();
		}

		UiScrollable categoryItem = new UiScrollable(new UiSelector()
		.className("android.widget.ListView"));

		//Press the 2nd Category in the list
		UiObject categoryitemlist = categoryItem.getChild(new UiSelector()
		.className("android.widget.RelativeLayout").index(1));
		categoryitemlist.click();

		//Press back button
		UiDevice.getInstance().pressBack();

		//Press the 1st Category in the list
		UiObject categoryitemlist2 = categoryItem.getChild(new UiSelector()
		.className("android.widget.TextView").index(0));
		categoryitemlist2.click();

		//Press the 1st Product in the list
		UiScrollable productItem = new UiScrollable(new UiSelector()
		.className("android.widget.ListView"));
		UiObject productitemlist = productItem.getChild(new UiSelector()
		.className("android.widget.RelativeLayout").index(0));

		productitemlist.click();

		//Write down some notes for the product
		UiObject noteButton= new UiObject(new UiSelector().className("android.widget.Button").index(4));
		noteButton.click();
		UiObject noteField = new UiObject(new UiSelector().className("android.widget.EditText").index(1));
		noteField.clearTextField();
		noteField.setText("I am writing some text here ;) ");
		UiObject readyButton = new UiObject(new UiSelector().resourceId("android:id/button1"));
		readyButton.click();
		
		UiObject quantityButton = new UiObject(new UiSelector().resourceId("qorder.clientprototype:id/button_quantity"));
		quantityButton.click();
		
		UiObject quantitySelection = new UiObject(new UiSelector().className("android.widget.Button").index(2));
		quantitySelection.click();
		
		readyButton.click();
		
		UiObject itemSpecialSelection = new UiObject(new UiSelector().resourceId("qorder.clientprototype:id/checkbox_details").index(0));
		itemSpecialSelection.click();
		
		
		UiObject basketaddButton = new UiObject(new UiSelector().resourceId("qorder.clientprototype:id/button_addToBasket"));
		basketaddButton.click();

		UiObject basketButton = new UiObject(new UiSelector().resourceId("qorder.clientprototype:id/basket_button"));
		basketButton.click();
		
		UiObject submitButton = new UiObject(new UiSelector().resourceId("qorder.clientprototype:id/submit_button"));
		submitButton.click();
		
		UiObject yesButton = new UiObject(new UiSelector().resourceId("android:id/button1"));
		yesButton.click();

		
		

		UiObject menuButton = new UiObject(new UiSelector()
		.className("android.widget.ImageButton"));
		
		menuButton.click();
		
		
		UiObject menuButton1 = new UiObject(new UiSelector().className("android.widget.LinearLayout").index(1));
		menuButton1.click();
		
		UiObject menuList = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
		
		UiObject menuButtonChild2 = menuList.getChild(new UiSelector()
		.className("android.widget.LinearLayout").index(1));
		menuButtonChild2.click();
		

	}   
}

