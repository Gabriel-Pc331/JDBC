package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.ProductTypeDAO;
import com.unimater.model.ProductType;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;

public class ProductTypeHandler implements HttpHandler {

    private final ProductTypeDAO productTypeDAO;

    public ProductTypeHandler(Connection connection) {
        this.productTypeDAO = new ProductTypeDAO(connection);
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
        List<ProductType> productTypes = productTypeDAO.getAll();
        String response = productTypes.toString();
        sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        ProductType newProductType = new ProductType(0, "Novo Tipo");
        productTypeDAO.upsert(newProductType);
        sendResponse(exchange, 201, "ProductType created: " + newProductType.getDescription());
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        ProductType updatedProductType = new ProductType(1, "Atualizado Tipo");
        productTypeDAO.upsert(updatedProductType);
        sendResponse(exchange, 200, "ProductType updated: " + updatedProductType.getDescription());
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        int productTypeId = 1; // Parse from URL in real case
        productTypeDAO.delete(productTypeId);
        sendResponse(exchange, 200, "ProductType deleted with ID: " + productTypeId);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.close();
    }
}
