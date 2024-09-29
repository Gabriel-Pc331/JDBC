package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.SaleItemDAO;
import com.unimater.model.SaleItem;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;

public class SaleItemHandler implements HttpHandler {

    private final SaleItemDAO saleItemDAO;

    public SaleItemHandler(Connection connection) {
        this.saleItemDAO = new SaleItemDAO(connection);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            case "PUT":
                handlePut(exchange);
                break;
            case "DELETE":
                handleDelete(exchange);
                break;
            default:
                sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        List<SaleItem> saleItems = saleItemDAO.getAll();
        String response = saleItems.toString();
        sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        SaleItem newSaleItem = new SaleItem(0, null, 10, 5.0, 1); // Example data
        saleItemDAO.upsert(newSaleItem);
        sendResponse(exchange, 201, "SaleItem created: " + newSaleItem.getQuantity());
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        SaleItem updatedSaleItem = new SaleItem(1, null, 20, 4.5, 1); // Example data
        saleItemDAO.upsert(updatedSaleItem);
        sendResponse(exchange, 200, "SaleItem updated: " + updatedSaleItem.getQuantity());
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        int saleItemId = 1; // Parse from URL in real case
        saleItemDAO.delete(saleItemId);
        sendResponse(exchange, 200, "SaleItem deleted with ID: " + saleItemId);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.close();
    }
}
