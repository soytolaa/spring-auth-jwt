package com.tola.demoapi.service.serviceImp;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.tola.demoapi.repository.StockRepository;
import com.tola.demoapi.service.StockService;
import lombok.RequiredArgsConstructor;
import com.tola.demoapi.model.request.StockRequest;
import com.tola.demoapi.model.response.StockResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import com.tola.demoapi.model.entities.Stock;
import com.tola.demoapi.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class StockServiceImp implements StockService {
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StockResponse> getAllStocks() {
        return stockRepository.findAll().stream().map(stock -> modelMapper.map(stock, StockResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public StockResponse createStock(StockRequest stockRequest) {
        Stock stock = modelMapper.map(stockRequest, Stock.class);
        stock.setCreatedAt(LocalDateTime.now());
        stock.setUpdatedAt(LocalDateTime.now());
        return modelMapper.map(stockRepository.save(stock), StockResponse.class);
    }

    @Override
    public StockResponse updateStock(Integer id, StockRequest stockRequest) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException("Stock not found!"));
        modelMapper.map(stockRequest, stock);
        stock.setUpdatedAt(LocalDateTime.now());
        return modelMapper.map(stockRepository.save(stock), StockResponse.class);
    }

    @Override
    public StockResponse getStockById(Integer id) {
        return modelMapper.map(
                stockRepository.findById(id).orElseThrow(() -> new NotFoundException("Stock not found!")),
                StockResponse.class);
    }
}
