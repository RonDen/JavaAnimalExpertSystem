package com.github.imp1.gui;

import com.github.imp1.dbutils.Rule;
import com.github.imp1.dbutils.RuleDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class DBTableFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JPanel buttonPanel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            try {
                DBTableFrame frame = new DBTableFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public DBTableFrame() throws SQLException, IOException, ClassNotFoundException {
        Font font = new Font("楷体", Font.PLAIN, 14);

        setLayout(new BorderLayout());
        setSize(800, 400);
        tableModel = new DefaultTableModel();
        table = new JTable();
        initTable(table, tableModel);
        table.setFont(font);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setSize(800, 200);

        addButton = new JButton("添加");
        addButton.setFont(font);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String condition = JOptionPane.showInputDialog("输入要添加的规则信息（空格分离多个），如：有奶 有毛");
                String conclusion = JOptionPane.showInputDialog("输入要添加的规则信息，如：哺乳动物");
                int length = table.getRowCount();
                Rule rule = new Rule(condition, conclusion,1);
                try {
                    RuleDao.saveRule(rule);
                    initTable(table, tableModel);
                } catch (IOException | SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        deleteButton = new JButton("删除");
        deleteButton.setFont(font);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if(row == -1 || column == 0){
                    return;
                } else {
                    Vector<String> vector =  (Vector<String>)tableModel.getDataVector().elementAt(row);
                    int id = Integer.parseInt(vector.elementAt(0));
                    try {
                        RuleDao.deleteById(id);
                        vector.set(4, String.valueOf(0));
                    } catch (SQLException | IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        modifyButton = new JButton("修改");
        modifyButton.setFont(font);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if(row == -1 || column == 0){
                    return;
                } else {
                    Vector<String> vector =  (Vector<String>)tableModel.getDataVector().elementAt(row);
                    int id = Integer.parseInt(vector.elementAt(0));
                    String condition = vector.get(1);
                    String conclusion = vector.get(3);
                    String newCondition = JOptionPane.showInputDialog("输入新的条件", condition);
                    String newConclusion = JOptionPane.showInputDialog("输入新的结论", conclusion);
                    if((newConclusion.trim().equals(conclusion) && newCondition.trim().equals(condition)) || (newConclusion.trim().equals("") && newCondition.trim().equals(""))){
                        return;
                    } else {
                        try {
                            Rule rule = new Rule(id, newCondition, newConclusion);
                            RuleDao.updateRule(rule);
                            vector.set(1, newCondition);
                            vector.set(3, newConclusion);
                        } catch (SQLException | IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void initTable(JTable table, DefaultTableModel tableModel) throws SQLException, IOException, ClassNotFoundException {
        String[] columnNames = {
                "规则编号","有的属性","没有的属性", "结论","是否在用"
        };
        ArrayList<Rule> rules = RuleDao.getAllRule();
        String[][] dataVector = new String[rules.size()][4];

        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            dataVector[i] = new String[]{
                    String.valueOf(rule.getRuleid()),
                    rule.getHava(),
                    rule.getNotHave(),
                    rule.getConclusion(),
                    String.valueOf(rule.getIsUse())
            };
        }
        tableModel.setDataVector(dataVector, columnNames);
        table.setModel(tableModel);
    }
}
