package com.github.imp1.gui;

import com.github.imp1.dbutils.Engine;
import com.github.imp1.dbutils.Rule;
import com.github.imp1.dbutils.RuleDao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;

public class MainFrame extends JFrame {
    private final int weight = 1200;
    private final int height = 800;

    private JTextPane rulePane;
    private JTextPane inputPane;
    private JTextArea logPane;
    private JPanel opButtonPane;
    private JButton beginReason;
    private JButton reverseReason;
    private JButton viewDB;
    private JButton reflushRules;
    private Font font;
    private StringBuffer logInfo;
    private final String banner = "\n" +
            "       _                    ______                      _   \n" +
            "      | |                  |  ____|                    | |  \n" +
            "      | | __ ___   ____ _  | |__  __  ___ __   ___ _ __| |_ \n" +
            "  _   | |/ _` \\ \\ / / _` | |  __| \\ \\/ / '_ \\ / _ \\ '__| __|\n" +
            " | |__| | (_| |\\ V / (_| | | |____ >  <| |_) |  __/ |  | |_ \n" +
            "  \\____/ \\__,_| \\_/ \\__,_| |______/_/\\_\\ .__/ \\___|_|   \\__|\n" +
            "                                       | |                  \n" +
            "                                       |_|                  \n";
    private final String verbose = "I think >";


    private final ImageIcon reasonIcon = new ImageIcon("resource/reason.png");
    private final ImageIcon reverseIcon = new ImageIcon("resource/reverse_reason.png");
    private final ImageIcon viewDBIcon = new ImageIcon("resource/view_db.png");
    private final ImageIcon reflushIcon = new ImageIcon("resource/reflush.png");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = null;
            try {
                frame = new MainFrame();
                MainFrame.InitGlobalFont(frame.font);
                frame.setTitle("动物识别推理系统");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
             keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    public MainFrame() throws HeadlessException, IOException, SQLException, ClassNotFoundException {
        font = new Font("楷体", Font.PLAIN, 16);
        setFont(font);
        setLayout(new GridLayout(2, 2));
        setSize(this.weight, this.height);
        addBasicPanel();
        addInputPanel();
        addLogPanel();
        addButtonPanel();
        initRulePanelContent();
    }

    public void addBasicPanel() {
        //创建一个蚀刻边界
        Border etched = BorderFactory.createEtchedBorder();
        //边界加标题
        Border titled = BorderFactory.createTitledBorder(etched, "规则面板");
        rulePane = new JTextPane();
        rulePane.setBorder(titled);
        rulePane.setSize(this.weight / 2, this.height / 2);
        rulePane.setFont(font);
        add(rulePane, 0);
    }

    public void initRulePanelContent() throws SQLException, IOException, ClassNotFoundException {
        reflushRules.doClick();
    }

    public void addInputPanel() {
        //创建一个蚀刻边界
        Border etched = BorderFactory.createEtchedBorder();
        //边界加标题
        Border titled = BorderFactory.createTitledBorder(etched, "输入事实");
        inputPane = new JTextPane();
        inputPane.setSize(this.weight / 2, this.height / 2);
        inputPane.setBorder(titled);
        inputPane.setFont(font);
        inputPane.setText("暗斑点，黄褐色，长脖子，长腿，有奶，有蹄");
        add(inputPane, 1);

    }

    public void addLogPanel() {
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "推理过程");
        logPane = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(logPane);
        logPane.setSize(this.weight / 2, this.height / 2);
        logPane.setBorder(titled);
        logPane.setFont(font);
        logPane.setText(banner + "\n" + verbose);
        add(jScrollPane, 2);
    }

    public void addButtonPanel() {
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "操作面板");
        opButtonPane = new JPanel();
        opButtonPane.setSize(this.weight / 2, this.height / 2);
        opButtonPane.setBorder(titled);
        opButtonPane.setFont(font);
        add(opButtonPane, 3);

        beginReason = new JButton("开始推理");
        beginReason.setFont(font);
        beginReason.setIcon(reasonIcon);
        reverseReason = new JButton("反向推理");
        reverseReason.setFont(font);
        reverseReason.setIcon(reverseIcon);
        viewDB = new JButton("总知识库");
        viewDB.setFont(font);
        viewDB.setIcon(viewDBIcon);
        reflushRules = new JButton("刷新规则");
        reflushRules.setFont(font);
        reflushRules.setIcon(reflushIcon);

        opButtonPane.add(beginReason);
        opButtonPane.add(reverseReason);
        opButtonPane.add(viewDB);
        opButtonPane.add(reflushRules);

        beginReason.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInfo = new StringBuffer(verbose);

                String line = inputPane.getText();
                HashSet<String> facts = stringToHashSet(line);
                try {
                    ArrayList<Rule> rules = RuleDao.getAllUsedRule();
                    for (Rule rule : rules) {
                        Double mathRate = Engine.matchLevle1(rule, facts);
                        if (mathRate == 1) {
                            logInfo.append("该动物为：" + rule.getConclusion());
                            logInfo.append("\n事实状态为：");
                            logInfo.append(facts.toString());
                            logInfo.append('\n');
                            logInfo.append(verbose);
                        }
                    }
                    logPane.setText(logInfo.toString());
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        reverseReason.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String line = inputPane.getText();
                    ArrayList<Rule> ruleSet = RuleDao.getAllRuleAllConclusion(line);
                    HashSet<String> factSet = new HashSet<String>();

                    for (Rule rule : ruleSet) {
                        StringBuffer message = new StringBuffer();
                        if (rule.isMatch(factSet)) {
                            showSuccess(rule.getConclusion());
                            return;
                        } else {
                            String[] ruleCondition = rule.getHava().split(" ");
                            for (String condition : ruleCondition) {
                                if (!factSet.contains(condition)) {
                                    if (!RuleDao.isConclusion(condition)) {
                                        message = new StringBuffer();
                                        int result = showConfirm(message, condition);
                                        if (result == JOptionPane.YES_OPTION) {
                                            factSet.add(condition);
                                            if (rule.isMatch(factSet)) {
                                                showSuccess(rule.getConclusion());
                                                return;
                                            }
                                        }
                                    } else {
                                        ArrayList<Rule> conditionRuleSet = RuleDao.getAllRuleAllConclusion(condition);
                                        for (Rule r : conditionRuleSet) {
                                            if (factSet.contains(condition)) {
                                                break;
                                            }
                                            String[] basicCondition = r.getHava().split(" ");
                                            for (String s : basicCondition) {
                                                if (!factSet.contains(s)) {
                                                    message = new StringBuffer();
                                                    int result = showConfirm(message, s);
                                                    if (result == JOptionPane.YES_OPTION) {
                                                        factSet.add(s);
                                                        if (r.isMatch(factSet)) {
                                                            factSet.add(condition);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (rule.isMatch(factSet)) {
                                            showSuccess(rule.getConclusion());
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    showFailure(line);
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        viewDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBTableFrame frame = new DBTableFrame();
                    frame.setTitle("动物识别系统知识库");
                    frame.setVisible(true);
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        reflushRules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuffer content = new StringBuffer();
                content.append("基础规则如下：\n");
                try {
                    ArrayList<Rule> rules = RuleDao.getAllUsedRule();
                    int cnt = 1;
                    for(Rule r: rules){
                        content.append(cnt);
                        cnt ++;
                        content.append("：");
                        for (String condition: r.getHava().split(" ")){
                            content.append(condition);
                            content.append("\t");
                        }
                        content.append(r.getConclusion());
                        content.append("\n");
                    }
                    rulePane.setText(content.toString());
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public HashSet<String> stringToHashSet(String line) {
        HashSet<String> facts = new HashSet<String>();
        line = line.trim();
        if (line.contains(",")) {
            facts.addAll(Arrays.asList(line.split(",")));
        } else if (line.contains("，")) {
            facts.addAll(Arrays.asList(line.split("，")));
        } else if (line.contains("\t")){
            facts.addAll(Arrays.asList(line.split("\t")));
        } else {
            facts.addAll(Arrays.asList(line.split(" ")));
        }
        return facts;
    }

    public int showConfirm(StringBuffer message, String condition) {
        message = new StringBuffer();
        message.append("该对象有：");
        message.append(condition);
        message.append("属性吗？");
        int result = JOptionPane.showConfirmDialog(null, message.toString(), "逆向推理", JOptionPane.YES_NO_OPTION);
        return result;
    }

    public void showSuccess(String conclusion) {
        StringBuffer message = new StringBuffer();
        message.append("推理成功！");
        message.append("该对象为" + conclusion);
        JOptionPane.showMessageDialog(null, message.toString(), "逆向推理", JOptionPane.PLAIN_MESSAGE);
    }

    public void showFailure(String conclusion) {
        StringBuffer message = new StringBuffer();
        message.append("推理失败！");
        message.append("根据先有知识不能推出该对象为" + conclusion);
        JOptionPane.showMessageDialog(null, message.toString(), "逆向推理", JOptionPane.WARNING_MESSAGE);
    }
}

/*

 暗斑点，黄褐色，长脖子，长腿，有奶，有蹄

 */
