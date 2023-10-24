package GUI;

import Tools.NumberDocument;
import elements.Preorder;
import elements.Rule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.Vector;

public class PreorderManagement extends JDialog {
    private Vector<Vector> sResult;
    private Vector<String> colName;
    private JTable table;
    public int sqlCode;
    public int sRow;

    public PreorderManagement(){
        sResult=new Vector<Vector>();
        sResult.add(new Vector());
        colName= Preorder.colName();

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel nPanel=new JPanel(new GridLayout(3,1));
        container.add(nPanel,BorderLayout.NORTH);
        JLabel title=new JLabel("预定管理",JLabel.CENTER);
        nPanel.add(title);
        JPanel lbPanel=new JPanel(new GridLayout(1,5));
        nPanel.add(lbPanel);
        lbPanel.add(new JLabel("用户id",JLabel.CENTER));
        lbPanel.add(new JLabel("图书id",JLabel.CENTER));
        lbPanel.add(new JLabel("预约日期上限",JLabel.CENTER));
        lbPanel.add(new JLabel("预约日期下限",JLabel.CENTER));
        JComboBox<String> jcb=new JComboBox<>(new String[]{"时间升序","时间降序"});
        lbPanel.add(jcb);
        JPanel searchPanel=new JPanel(new GridLayout(1,5));
        nPanel.add(searchPanel);
        JTextField uidTF=new JTextField();
        uidTF.setDocument(new NumberDocument());
        JTextField bidTF=new JTextField();
        bidTF.setDocument(new NumberDocument());
        JPanel tsuPanel=new JPanel(new GridLayout(1,2));
//        searchPanel.add()
        JPanel tsuPanelMD=new JPanel(new GridLayout(1,2));
        JTextField tsuY=new JTextField();   tsuY.setDocument(new NumberDocument()); tsuY.setToolTipText("年");
        JTextField tsuM=new JTextField();   tsuM.setDocument(new NumberDocument()); tsuM.setToolTipText("月");
        JTextField tsuD=new JTextField();   tsuD.setDocument(new NumberDocument()); tsuD.setToolTipText("日");
        tsuPanel.add(tsuY);
        tsuPanel.add(tsuPanelMD);
        tsuPanelMD.add(tsuM);
        tsuPanelMD.add(tsuD);
        JPanel tslPanel=new JPanel(new GridLayout(1,2));
        JPanel tslPanelMD=new JPanel(new GridLayout(1,2));
        JTextField tslY=new JTextField();   tslY.setDocument(new NumberDocument()); tslY.setToolTipText("年");
        JTextField tslM=new JTextField();   tslM.setDocument(new NumberDocument()); tslM.setToolTipText("月");
        JTextField tslD=new JTextField();   tslD.setDocument(new NumberDocument()); tslD.setToolTipText("日");
        tslPanel.add(tslY);
        tslPanel.add(tslPanelMD);
        tslPanelMD.add(tslM);
        tslPanelMD.add(tslD);
        JButton search=new JButton("搜索");
        searchPanel.add(uidTF);
        searchPanel.add(bidTF);
        searchPanel.add(tsuPanel);
        searchPanel.add(tslPanel);
        searchPanel.add(search);
        nPanel.setBackground(new Color(180,255,180));

        JPanel cPanel =new JPanel();
//        generateTestData();
        generateJTable();
        JScrollPane jsp=new JScrollPane(table);
        cPanel.add(jsp,BorderLayout.CENTER);
        container.add(cPanel,BorderLayout.CENTER);

        JPanel sPanel =new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel infoWin=new JLabel();
        JButton delete =new JButton("删除");
        JButton fulfill =new JButton("兑现");
        JButton back=new JButton("返回");
        delete.setEnabled(false);
        fulfill.setEnabled(false);
        sPanel.add(infoWin);
        sPanel.add(delete);
        sPanel.add(fulfill);
        sPanel.add(back);
        container.add(sPanel,BorderLayout.SOUTH);

        this.pack();

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreorderManagement.this.dispose();
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timestamp tsu=readTime(tsuY.getText(),tsuM.getText(),tsuD.getText());
                Timestamp tsl=readTime(tslY.getText(),tslM.getText(),tslD.getText());
                boolean desc=false;
                if(jcb.getSelectedIndex()==1)
                    desc=true;
                Vector<Vector> news=Preorder.search(Rule.convert_2i(uidTF.getText()),
                        Rule.convert_2i(bidTF.getText()),tsu,tsl,desc);
                if(news!=null){
                    connect(news);
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
                    delete.setEnabled(true);
                    fulfill.setEnabled(true);
                    infoWin.setText("Results:"+sResult.size());
                }
                else{
                    if(Preorder.sqlCode==0)
                        infoWin.setText("无结果");
                    else infoWin.setText("发生错误，错误码"+Preorder.sqlCode);
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int sRow=table.getSelectedRow();
                if(sRow<0) {
                    infoWin.setText("未选择要操作的条目");
                    return;
                }
                Integer uid=Integer.parseInt((String) sResult.get(sRow).get(0));
                Integer bid=Integer.parseInt((String) sResult.get(sRow).get(1));
                if(Preorder.delete(uid,bid))
                    infoWin.setText("删除成功");
                else infoWin.setText("删除失败，错误码"+Preorder.sqlCode);
                sRow=-1;
            }
        });
        fulfill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int sRow=table.getSelectedRow();
                if(sRow<0) {
                    infoWin.setText("未选择要操作的条目");
                    return;
                }
                Integer uid=Integer.parseInt((String) sResult.get(sRow).get(0));
                Integer bid=Integer.parseInt((String) sResult.get(sRow).get(1));
                String input=JOptionPane.showInputDialog(PreorderManagement.this,
                        "输入借阅天数","输入借阅天数",JOptionPane.QUESTION_MESSAGE);
                Integer moAmt=Rule.convert_2i(input);
                if(moAmt==null){
                    infoWin.setText("未输入或输入错误");
                    return;
                }
                if(Preorder.cash(uid,bid,moAmt))
                    infoWin.setText("借阅成功");
                else infoWin.setText("借阅失败，错误码"+Preorder.sqlCode);
                sRow=-1;
            }
        });

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                sRow=table.getSelectedRow();
                sRow=table.rowAtPoint(e.getPoint());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void generateJTable(){
        TableModel tm=new DefaultTableModel(sResult, colName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
//
//            @Override
//            public Class<?> getColumnClass(int columnIndex) { return getValueAt(0, columnIndex).getClass(); }
        };
        table=new JTable(tm);
//        table.setRowHeight(100);
    }

    public Timestamp readTime(String year,String month,String day){
        Integer yi= Rule.convert_2i(year);
        Integer mi=Rule.convert_2i(month);
        Integer di=Rule.convert_2i(day);
        if(yi==null | mi==null | di==null)
            return null;
        return new Timestamp(yi,mi,di,0,0,0,0);
    }
    public void connect(Vector<Vector> newTpls){
        sResult.clear();
        for(int i=0;i<newTpls.size();i++)
            sResult.add(newTpls.get(i));
    }
}
