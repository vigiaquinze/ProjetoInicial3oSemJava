package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import App.ToDoList;
import App.Task;

public class TasksDAO {
    private Connection connection;
    private Statement stmt;
    List<Task> tarefas;

    // construtor
    public TasksDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // métodos
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS produtos_supermercado (IDPRODUTO VARCHAR(255) PRIMARY KEY, NOMEPRODUTO VARCHAR(255), QUANTIDADE VARCHAR(255), VALORUNITARIO VARCHAR(255))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Listar todos os valores cadastrados
    public List<Estoque> listarTodos() {
        PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        estoque = new ArrayList<>();
        // Cria uma lista para armazenar os produtos recuperados do banco de dados
        try {
            stmt = connection.prepareStatement("SELECT * FROM produtos_supermercado");
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto produtos com os valores do
                // registro
                Estoque produtos = new Estoque(
                        rs.getString("idProduto"),
                        rs.getString("nomeProduto"),
                        rs.getString("quantidade"),
                        rs.getString("valorUnitario"));
                estoque.add(produtos); // Adiciona o objeto produtos à lista de estoque
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);

            // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return estoque; // Retorna a lista de estoque recuperado do banco de dados
    }

    // Cadastrar produto no banco
    public void cadastrar(String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String sql = "INSERT INTO produtos_supermercado (idProduto, nomeProduto, quantidade, valorUnitario) VALUES (?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, idProduto.trim());
            stmt.setString(2, nomeProduto.trim().toUpperCase());
            stmt.setString(3, quantidade.trim());
            stmt.setString(4, valorUnitario.trim());
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    public void editar(String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produtos_supermercado SET nomeProduto = ?, quantidade = ?, valorUnitario = ? WHERE idProduto = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(4, idProduto.trim());
            stmt.setString(1, nomeProduto.trim().toUpperCase());
            stmt.setString(2, quantidade.trim());
            stmt.setString(3, valorUnitario.trim());
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String sql = "UPDATE produtos_supermercado SET nomeProduto = ?, quantidade = ?, valorUnitario = ? WHERE idProduto = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, idProduto.trim());
            stmt.setString(2, nomeProduto.trim().toUpperCase());
            stmt.setString(3, quantidade.trim());
            stmt.setString(4, valorUnitario.trim());
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(String idProduto) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela id do produto
        String sql = "DELETE FROM produtos_supermercado WHERE idProduto = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, idProduto);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}
