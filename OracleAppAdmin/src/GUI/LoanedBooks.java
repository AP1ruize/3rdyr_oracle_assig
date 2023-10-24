package GUI;

import Logic.LendAndReturn;
import Logic.SearchInfo;
import Tools.NumberDocument;
import Runtime.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class LoanedBooks extends JDialog {
    private Vector<Vector> sResult;
    private Vector<String> colName;
    private JTable table;
    private int sRow;

    public LoanedBooks(){
        sRow=-1;
        sResult=new Vector<Vector>();
//        sResult.add(new Vector());
        colName= SearchInfo.loanedBookColName();

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel=new JPanel(new GridLayout(2,1));
        JLabel title=new JLabel("已借阅图书",JLabel.CENTER);
        northPanel.add(title);
        JPanel searchBar=new JPanel(new GridLayout(1,3));
        searchBar.add(new JLabel("输入用户id",JLabel.CENTER));
        JTextField searchContent=new JTextField();
        searchContent.setDocument(new NumberDocument());
        JButton search=new JButton("查询");
        searchBar.add(searchContent);
        searchBar.add(search);
        northPanel.add(searchBar);
        northPanel.setBackground(new Color(180,255,180));

        JPanel centerPanel=new JPanel();
//        generateTestData();
        generateJTable();
        JScrollPane jsp=new JScrollPane(table);
        centerPanel.add(jsp,BorderLayout.CENTER);

        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel infoWin=new JLabel();
        JButton renew =new JButton("续借");
        JButton returnBook =new JButton("归还");
        JButton missing =new JButton("丢失");
        JButton damage=new JButton("损坏");
        renew.setEnabled(false);
        returnBook.setEnabled(false);
        missing.setEnabled(false);
        damage.setEnabled(false);
        southPanel.add(infoWin);
        southPanel.add(renew);
        southPanel.add(returnBook);
        southPanel.add(missing);
        southPanel.add(damage);

        JPanel nSPanel=new JPanel(new GridLayout(2,1));
        nSPanel.add(southPanel);
        JPanel renewBar=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nSPanel.add(renewBar);
        renewBar.add(new JLabel("续借期：",JLabel.RIGHT));
        JTextField renewDur=new JTextField();
        renewDur.setColumns(8);
        renewDur.setDocument(new NumberDocument());
        renewDur.setEditable(false);
        renewBar.add(renewDur);
        JButton confRenew=new JButton("确认续借");
        confRenew.setEnabled(false);
        JButton cancRenew=new JButton("取消续借");
        cancRenew.setEnabled(false);
        renewBar.add(confRenew);

        container.add(northPanel,BorderLayout.NORTH);
        container.add(centerPanel,BorderLayout.CENTER);
        container.add(nSPanel,BorderLayout.SOUTH);
        this.pack();

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoWin.setText("");
                Vector<Vector> news=SearchInfo.searchLoanedBooks(Integer.parseInt(searchContent.getText()));
                if(news!=null){
                    connect(news);
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
                    renew.setEnabled(true);
                    returnBook.setEnabled(true);
                    missing.setEnabled(true);
                    damage.setEnabled(true);
                    return;
                }
                if(SearchInfo.sqlCode==0)
                    infoWin.setText("No results.");
                else infoWin.setText("Error ocuured, error code: "+SearchInfo.sqlCode);
            }
        });
        renew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                sRow=table.getSelectedRow();
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                confRenew.setEnabled(true);
                cancRenew.setEnabled(true);
                renewDur.setEditable(true);
                infoWin.setText("最大续借期："+SearchInfo.Renewable(sResult.get(sRow)));
                search.setEnabled(false);
                renew.setEnabled(false);
                returnBook.setEnabled(false);
                missing.setEnabled(false);
                damage.setEnabled(false);
                sRow=-1;
            }
        });
        returnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int sRow=table.getSelectedRow();
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                int reV= LendAndReturn.returnbook(new ReaderLoginState(Integer.parseInt(searchContent.getText()),null,0,0),
                        new BookInfo(Integer.parseInt((String)sResult.get(sRow).get(1))));
                //归还后处理
                if(reV>=0)
                    infoWin.setText("归还成功，距离到期还有"+reV+"天");
                else infoWin.setText("归还成功，已经过期"+(-reV)+"天");
                sRow=-1;
            }
        });
        missing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int sRow=table.getSelectedRow();
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                int reV=LendAndReturn.returnbookdamage(new ReaderLoginState(Integer.parseInt(searchContent.getText()),null,0,0),
                        new BookInfo(Integer.parseInt((String)sResult.get(sRow).get(1))),1);
                if(reV>=0){
                    infoWin.setText("登记成功，距离到期还有"+reV+"天");
                }else{
                    infoWin.setText("登记成功，已经过期"+(-reV)+"天");
                }
                sRow=-1;
            }
        });
        damage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int sRow=table.getSelectedRow();
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                int reV=LendAndReturn.returnbookdamage(new ReaderLoginState(Integer.parseInt(searchContent.getText()),null,0,0),
                        new BookInfo(Integer.parseInt((String)sResult.get(sRow).get(1))),0);
                //归还后处理
                if(reV>=0)
                    infoWin.setText("归还成功，距离到期还有"+reV+"天");
                else infoWin.setText("归还成功，已经过期"+(-reV)+"天");
                sRow=-1;
            }
        });
        confRenew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((Integer.parseInt(renewDur.getText()))>SearchInfo.Renewable(sResult.get(sRow))) {
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("超过最大续借期：" + SearchInfo.Renewable(sResult.get(sRow)));
                    return;
                }
                int rV=LendAndReturn.renewalbook(
                        new ReaderLoginState(Integer.parseInt(searchContent.getText()),null,0,0),
                        new BookInfo(Integer.parseInt((String)sResult.get(sRow).get(1))),
                        (Integer.parseInt(renewDur.getText())));
                if(rV!=1)
                {
                    infoWin.setForeground(new Color(255,50,50));
                    if(rV==0){
                        infoWin.setText("超过还书日期，无法续借");
                        return;
                    }
                    infoWin.setText("Error ocurred, code: "+rV);
                    return;
                }
                infoWin.setText("续借成功");
                confRenew.setEnabled(false);
                cancRenew.setEnabled(false);
                renewDur.setEditable(false);
                renewDur.setText("");
                search.setEnabled(true);
                renew.setEnabled(true);
                returnBook.setEnabled(true);
                missing.setEnabled(true);
                damage.setEnabled(true);
            }
        });
        cancRenew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoWin.setText("");
                confRenew.setEnabled(false);
                cancRenew.setEnabled(false);
                renewDur.setEditable(false);
                renewDur.setText("");
                search.setEnabled(true);
                renew.setEnabled(true);
                returnBook.setEnabled(true);
                missing.setEnabled(true);
                damage.setEnabled(true);
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

            @Override
            public Class<?> getColumnClass(int columnIndex) { return getValueAt(0, columnIndex).getClass(); }
        };
        table=new JTable(tm);
//        table.setRowHeight(100);
    }
    public void connect(Vector<Vector> newTpls){
        sResult.clear();
        for(int i=0;i<newTpls.size();i++)
            sResult.add(newTpls.get(i));
    }
}
