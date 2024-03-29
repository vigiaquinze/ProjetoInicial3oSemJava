package View;

import java.util.*;
import java.util.List;
import java.util.logging.Handler;
import javax.swing.table.*;

import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import View.ToDoList.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseEvent.*;

import Control.TasksControl;

public class ToDoList extends JFrame {
    private JPanel mainPanel;
    private JTextField taskInputField;
    private JButton addButton;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JButton markDoneButton;
    private JComboBox<String> filterComboBox;
    private JButton clearCompletedButton;
    private List<Task> tasks;
    private int linhaSelecionada = -1;

    JFrame confirmationFrame;
    JPanel confirmationPanel;
    JButton confirmationButton = new JButton("Excluir");
    JButton cancelButton = new JButton("Não");

    public ToDoList() {
        // construtor
        super("To-Do List App");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome da Tarefa");

        // Inicializa o painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Inicializa a lista de tasks e a lista de tasks concluídas
        tasks = new ArrayList<>();
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        // Inicializa campos de entrada, botões e JComboBox
        taskInputField = new JTextField();
        addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            addTask();
        });

        // Botão de excluir tarefa e o ActionListener dele
        deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(e -> {
            confirmationFrame.setVisible(true);
            confirmationFrame.setBounds(100, 300, 200, 100);
            confirmationFrame.setDefaultCloseOperation(2);
            confirmationPanel.setVisible(true);
        });

        // Botão de concluir tarefa e o ActionListener dele
        markDoneButton = new JButton("Concluir");
        markDoneButton.addActionListener(e -> {
            markTaskDone();
        });

        // ComboBox para filtrar as tarefas, entre todas, as ativas e as concluídas
        filterComboBox = new JComboBox<>(new String[] { "Todas", "Ativas",
                "Concluídas" });
        filterComboBox.addActionListener(e -> {
            filterTasks();
        });

        // Botão de limpar tarefas concluídas e o ActionListener dele
        clearCompletedButton = new JButton("Limpar Concluídas");
        clearCompletedButton.addActionListener(e -> {
            clearCompletedTasks();
        });

        // Configuração do painel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Configuração do painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(filterComboBox);
        buttonPanel.add(clearCompletedButton);

        // Adiciona os componentes ao painel principal
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskTable), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        this.add(mainPanel);
        this.setDefaultCloseOperation(2);
        this.setBounds(300, 100, 439, 453);
        this.setVisible(true);

        // Janela de confirmação para excluir a tarefa
        confirmationFrame = new JFrame();
        confirmationPanel = new JPanel();
        confirmationPanel.setLayout(new BorderLayout());
        confirmationPanel.add(new JLabel("Tem certeza?"), BorderLayout.CENTER);
        JPanel confirmationButtons = new JPanel(new GridLayout(1, 2));
        confirmationPanel.add(confirmationButtons, BorderLayout.SOUTH);
        confirmationButtons.add(confirmationButton);
        confirmationButtons.add(cancelButton);
        confirmationFrame.add(confirmationPanel);
        confirmationButton.addActionListener(e -> {
            deleteTask();
            confirmationFrame.setVisible(false);
        });
        cancelButton.addActionListener(e -> {
            confirmationFrame.setVisible(false);
        });

    TasksControl operacoes = new TasksControl(tasks, tableModel, taskTable);

        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = taskTable.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputIdProduto.setText((String) taskTable.getValueAt(linhaSelecionada, 0));
                }
            }
        });

    public class HandlerEnter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent enterAdd) {
        }

        @Override
        public void keyPressed(KeyEvent enterAdd) {
            if (enterAdd.getKeyCode() == KeyEvent.VK_ENTER) {
                addTask();
            }
        }

        @Override
        public void keyReleased(KeyEvent enterAdd) {
        }

    }

    public class Handler2 implements MouseListener {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent dbl) {
            if(dbl.getClickCount()==2){
                markTaskDone();
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent dbl) {
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent dbl) {
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent dbl) {
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent dbl) {
        }

    }

    private void addTask() {
        // Adiciona uma nova task à lista de tasks
        String taskDescription = taskInputField.getText().trim();// remove espaços vazios
        if (!taskDescription.isEmpty()) {
            Task newTask = new Task(taskDescription);
            tasks.add(newTask);
            updateTaskList();
            taskInputField.setText("");
        }
    }

    private void deleteTask() {
        // Exclui a task selecionada da lista de tasks
        int selectedIndex = taskTable.rowAtPoint();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            tasks.remove(selectedIndex);
            updateTaskList();
        }
    }

    private void markTaskDone() {
        // Marca a task selecionada como concluída
        int selectedIndex = taskTable.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task task = tasks.get(selectedIndex);
            task.setDone(true);
            updateTaskList();
        }
    }

    private void filterTasks() {
        // Filtra as tasks com base na seleção do JComboBox
        String filter = (String) filterComboBox.getSelectedItem();
        listModel.clear();
        for (Task task : tasks) {
            if (filter.equals("Todas") || (filter.equals("Ativas") &&
                    !task.isDone()) || (filter.equals("Concluídas") && task.isDone())) {
                listModel.addElement(task.getDescription());
            }
        }
    }

    private void clearCompletedTasks() {
        // Limpa todas as tasks concluídas da lista
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDone()) {
                completedTasks.add(task);
            }
        }
        tasks.removeAll(completedTasks);
        updateTaskList();
    }

    private void updateTaskList() {
        // Atualiza a lista de tasks exibida na GUI
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task.getDescription() + (task.isDone() ? " (Concluída)" : ""));
        }
    }

    public void run() {
        // Exibe a janela
        this.setVisible(true);
    }
}