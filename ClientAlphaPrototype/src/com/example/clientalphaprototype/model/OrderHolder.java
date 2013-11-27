package com.example.clientalphaprototype.model;

import java.util.ArrayList;
import java.util.List;

public class OrderHolder {

        static List<BasketProduct> order = new ArrayList<BasketProduct>();

        public void add(BasketProduct basketProduct)
        {
                order.add(basketProduct);
        }
        
        public void replace(List<BasketProduct> order)
        {
                OrderHolder.order =order;
        }

        public List<BasketProduct> getOrder() {
                return order;
        }

}