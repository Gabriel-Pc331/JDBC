package com.unimater.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.dao.ProductDAO;
import com.unimater.model.Product;
import com.unimater.model.ProductType;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;

public class ProductHandler implements HttpHandler {

    private final ProductDAO productDAO;

    public ProductHandler(Connection connection) {
        this.productDAO = new ProductDAO(connection);
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
        // List all products
        List<Product> products = productDAO.getAll();
        String response = products.toString();
        sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        // Example of inserting a new product
        Product newProduct = new Product(0, new ProductType(1, "Eletrônicos"), "Smartphone", 999.99);
        productDAO.upsert(newProduct);
        sendResponse(exchange, 201, "Product created: " + newProduct.getDescription());
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        // Example of updating a product
        Product updatedProduct = new Product(1, new ProductType(1, "Eletrônicos"), "Smartphone atualizado", 899.99);
        productDAO.upsert(updatedProduct);
        sendResponse(exchange, 200, "Product updated: " + updatedProduct.getDescription());
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        // Example of deleting a product by ID
        int productId = 1; // This can be parsed from the URL in a real case
        productDAO.delete(productId);
        sendResponse(exchange, 200, "Product deleted with ID: " + productId);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.close();
    }
}
