package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Entity {
    
    /**
     * Constrói a entidade a partir de um ResultSet.
     * 
     * @param rs ResultSet de uma consulta SQL
     * @return Instância da entidade populada com os dados do ResultSet
     * @throws SQLException se ocorrer um erro ao acessar os dados do ResultSet
     */
    Entity constructFromResultSet(ResultSet rs) throws SQLException;

    /**
     * Prepara o PreparedStatement com os valores da entidade.
     * 
     * @param preparedStatement PreparedStatement a ser preparado
     * @return PreparedStatement preparado
     * @throws SQLException se ocorrer um erro ao configurar o PreparedStatement
     */
    PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException;

    /**
     * Retorna o identificador único da entidade.
     * 
     * @return ID da entidade
     */
    int getId();
}
