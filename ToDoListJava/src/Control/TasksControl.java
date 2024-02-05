package Control;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import Connection.TasksDAO;
import View.ToDoList;
import View.Task;

public class TasksControl {
    // atributos
    private List<Task> tarefas;
    private DefaultTableModel tableModel;
    private JTable table;

    // ctor
    public TasksControl(List<Task> tarefas, DefaultTableModel tableModel, JTable table) {
        this.tarefas = tarefas;
        this.tableModel = tableModel;
        this.table = table;
       }

       
    // CRUD
    public void cadastrarProduto(String taskDescription) {
        Task produtos = new Task(taskDescription);
        new TasksDAO().cadastrar(taskDescription);
        tarefas.add(produtos);
        atualizarTabela();
    }

    public void apagarProduto(String taskDescription) {
        new TasksDAO().apagar(taskDescription);
        atualizarTabela();
    }

    public void atualizar(String taskDescription) {
        new TasksDAO().atualizar(taskDescription);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    private void atualizarTabela() {
        tarefas = new TasksDAO().listarTodos();
        tableModel.setRowCount(0);
        for (Task tarefas : tarefas) {
            tableModel.addRow(new Object[] { tarefas.getTaskDescription()});
        }
    }
}
