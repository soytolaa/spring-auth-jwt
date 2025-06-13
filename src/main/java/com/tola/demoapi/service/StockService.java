package com.tola.demoapi.service;

import com.tola.demoapi.model.request.StockRequest;
import com.tola.demoapi.model.response.StockResponse;
import java.util.List;

public interface StockService {
    List<StockResponse> getAllStocks();

    StockResponse createStock(StockRequest stockRequest);

    StockResponse updateStock(Integer id, StockRequest stockRequest);

    StockResponse getStockById(Integer id);
}
