package com.github.imp1.dbutils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableTest {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public MyFrame() throws HeadlessException {
        setTitle("TableTest");
        setLocation(150,150);
        setSize(700, 400);

        String[][] dataVector={{"1","李强","Jerry"},
                {"2","何静","lily"}};
        String[] columnNames={"personId","personName","nickName"};//用来记录列的名称

        tableModel = new DefaultTableModel(dataVector,columnNames);//创建模式
        table = new JTable(tableModel);//创建表
        JScrollPane scrollPane = new JScrollPane(table);//增加滚动条
        add(scrollPane);

        //增加一行
        //String [] newData={"3","王良","Mike"};
        //tableModel.addRow(newData);

        //删除一行
        //tableModel.removeRow(1);//从0开始编号

        //修改
        tableModel.setValueAt("4",0,0);//设置0行0列处的值为4
    }
}
