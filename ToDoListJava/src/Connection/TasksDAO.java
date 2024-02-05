package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import View.Task;
import View.ToDoList;

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
        String sql = "CREATE TABLE IF NOT EXISTS tarefas_cliente (NOMETAREFA VARCHAR(255))";
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
    public List<Task> listarTodos() {
        PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        tarefas = new ArrayList<>();
        // Cria uma lista para armazenar os produtos recuperados do banco de dados
        try {
            stmt = connection.prepareStatement("SELECT * FROM tarefas_cliente");
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto produtos com os valores do
                // registro
                Task tarefinhas = new Task(
                        rs.getString("taskDescription"));
                tarefas.add(tarefinhas); // Adiciona o objeto produtos à lista de estoque
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);

            // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return tarefas; // Retorna a lista de estoque recuperado do banco de dados
    }

    // Cadastrar produto no banco
    public void cadastrar(String taskDescription) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String sql = "INSERT INTO tarefas_cliente (taskDescription) VALUES (?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, taskDescription.trim().toUpperCase());
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String taskDescription) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String sql = "UPDATE produtos_supermercado SET taskDescription = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, taskDescription.trim().toUpperCase());
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
        String sql = "DELETE FROM taskDescription";
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
