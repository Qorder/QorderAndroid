package qorder.clientprototype.tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;












import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.Category;
import qorder.clientprototype.model.DetailedProduct;
import qorder.clientprototype.model.Product;
import qorder.clientprototype.util.JsonUtil;
import android.test.AndroidTestCase;


















import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonUtilTest extends AndroidTestCase  {

	public JsonUtilTest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testJsonToPojoCategoryParser() throws JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
		String url = ("http://83.212.118.113/mockJsons/mockCategoryJson.json");
		List<Category > event = JsonUtil.<List<Category>>JsonToPojoParser(url, Category.class);
		Category category = new Category(1, "mockName","mockUri");
		List<Category> expectedEvent = new ArrayList<Category>();
		expectedEvent.add(category);
		
		assertEquals(event, expectedEvent);
	}
	
	public void testJsonToPojoProductParser() throws JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
		String url = ("http://83.212.118.113/mockJsons/mockProductJson.json");
		List<Product> event = JsonUtil.<List<Product>>JsonToPojoParser(url, Product.class);
		Product product = new Product(1,"mockName",BigDecimal.valueOf(5.5), "mockUri");
		List<Product> expectedEvent = new ArrayList<Product>();
		expectedEvent.add(product);
		
		assertEquals(event, expectedEvent);
	}
	
	public void testJsonToPojoDetailedProductParser() throws JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
		String url = ("http://83.212.118.113/mockJsons/mockDetailedProductJson.json");
		List<DetailedProduct> event = JsonUtil.<List<DetailedProduct>>JsonToPojoParser(url, DetailedProduct.class);
		DetailedProduct detailedProduct = new DetailedProduct(1, "mockName", BigDecimal.valueOf(5.5),"mockNote",null,"mockUri");
		List<DetailedProduct> expectedEvent = new ArrayList<DetailedProduct>();
		expectedEvent.add(detailedProduct);
		
		assertEquals(event, expectedEvent);
	}
	
	public void testJsonToPojoBasketProductParser() throws JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
		String url = ("http://83.212.118.113/mockJsons/mockBasketProductJson.json");
		List<BasketProduct> event = JsonUtil.<List<BasketProduct>>JsonToPojoParser(url, BasketProduct.class);
		BasketProduct basketProduct = new BasketProduct(1, "mockName",BigDecimal.valueOf(5.5),"mockNote","mockUri",1);
		List<BasketProduct> expectedEvent = new ArrayList<BasketProduct>();
		expectedEvent.add(basketProduct);
		
		assertEquals(event, expectedEvent);
	}

	public void testPojoCategoryToJsonParser() throws JsonProcessingException{
		Category category = new Category(1, "mockName","mockUri");
		List<Category> event = new ArrayList<Category>();
		event.add(category);
		String excpectedEvent;
		excpectedEvent=JsonUtil.PojoToJsonParser(event);

		assertNotNull(excpectedEvent);
	}
	
	public void testPojoProductToJsonParser() throws JsonProcessingException{
		Product product = new Product(1, "mockName",BigDecimal.valueOf(5.5), "mockUri");
		List<Product> event = new ArrayList<Product>();
		event.add(product);
		String excpectedEvent;
		excpectedEvent=JsonUtil.PojoToJsonParser(event);

		assertNotNull(excpectedEvent);
	}
	
	public void testPojoDetailedProductToJsonParser() throws JsonProcessingException{
		DetailedProduct detailedProduct = new DetailedProduct(1, "mockName", BigDecimal.valueOf(5.5),"mockNote","mockUri","mockattr");
		List<DetailedProduct> event = new ArrayList<DetailedProduct>();
		event.add(detailedProduct);
		String excpectedEvent;
		excpectedEvent=JsonUtil.PojoToJsonParser(event);

		assertNotNull(excpectedEvent);
	}
	
	public void testPojoBasketProductToJsonParser() throws JsonProcessingException{
		BasketProduct basketProduct = new BasketProduct(1, "mockName", BigDecimal.valueOf(5.5),"mockNote","mockUri",1);
		List<BasketProduct> event = new ArrayList<BasketProduct>();
		event.add(basketProduct);
		String excpectedEvent;
		excpectedEvent=JsonUtil.PojoToJsonParser(event);

		assertNotNull(excpectedEvent);
	}
}
