package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.HelloWorldHandler;
import com.unimater.dao.ProductTypeDAO;
import com.unimater.model.ProductType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            // Criando servidor HTTP na porta 8080
            HttpServer servidor = HttpServer.create(
                    new InetSocketAddress(8080), 0
            );

            // Adicionando contexto de helloWorld
            servidor.createContext("/helloWorld", new HelloWorldHandler());

            // Conectando ao banco de dados PostgreSQL
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/your_db", "postgres", "gpierin44"
            );

            // Criando instância do ProductTypeDAO
            ProductTypeDAO productTypeDAO = new ProductTypeDAO(connection);

            // Exibindo todos os tipos de produtos
            productTypeDAO.getAll().forEach(System.out::println);

            // Inserindo um novo tipo de produto
            productTypeDAO.upsert(new ProductType(0, "Teste"));

            // Exibindo todos os tipos de produtos após inserção
            productTypeDAO.getAll().forEach(System.out::println);

            // Atualizando um tipo de produto
            productTypeDAO.upsert(new ProductType(4, "Teste 2"));

            // Exibindo todos os tipos de produtos após atualização
            productTypeDAO.getAll().forEach(System.out::println);

            // Obtendo um tipo de produto por ID
            System.out.println(productTypeDAO.getById(1));

            // Deletando um tipo de produto por ID
            productTypeDAO.delete(5);

            // Exibindo todos os tipos de produtos após exclusão
            productTypeDAO.getAll().forEach(System.out::println);

            // Iniciando o servidor
            servidor.setExecutor(null);
            servidor.start();
            System.out.println("Servidor rodando na porta 8080");
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
