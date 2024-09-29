package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.saleDAO;
import com.unimater.model.Sale;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class SaleHandler implements HttpHandler {

    private final saleDAO saleDAO;

    public SaleHandler(Connection connection) {
        this.saleDAO = new saleDAO(connection);
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
        List<Sale> sales = saleDAO.getAll();
        String response = sales.toString();
        sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Sale newSale = new Sale(0, new Timestamp(System.currentTimeMillis()));
        saleDAO.upsert(newSale);
        sendResponse(exchange, 201, "Sale created: " + newSale.getId());
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        Sale updatedSale = new Sale(1, new Timestamp(System.currentTimeMillis()));
        saleDAO.upsert(updatedSale);
        sendResponse(exchange, 200, "Sale updated: " + updatedSale.getId());
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        int saleId = 1; // Parse from URL in real case
        saleDAO.delete(saleId);
        sendResponse(exchange, 200, "Sale deleted with ID: " + saleId);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.close();
    }
}
