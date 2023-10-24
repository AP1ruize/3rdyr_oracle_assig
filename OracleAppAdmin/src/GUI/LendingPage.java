package GUI;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import Logic.LendAndReturn;
import Runtime.*;
import elements.Book;
import elements.Reader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

public class LendingPage extends JDialog {
    private ReaderLoginState reader;
    private Vector<BookInfo> books;
    private boolean trueReader=false;
    private boolean trueBooks=false;

    private JTable tDuration;
    private Vector<String> tDColName;
    private Vector<Vector> duraData;

    private JTable table;
    private Vector<String> colName;
    private Vector<Vector> tableData;
    //protected void setReader(ReaderLoginState rls){reader=rls;}

    public LendingPage(Vector<BookInfo> _books){
        trueReader=false;
        trueBooks=false;
        generateColName();
        books=_books;

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JLabel title=new JLabel("借阅页面",JLabel.CENTER);
        container.add(title,BorderLayout.NORTH);

        JPanel wPanel=new JPanel(new GridLayout(5,1));
        wPanel.setBackground(new Color(180,255,180));
        JLabel readerL=new JLabel("输入读者id或用户名");
        JTextField enterReader=new JTextField();
        JButton verify=new JButton("验证读者");
        JLabel vrfResult=new JLabel("请先点击验证读者以验证身份");
        wPanel.add(readerL);
        wPanel.add(enterReader);
        wPanel.add(verify);
        wPanel.add(vrfResult);
        container.add(wPanel,BorderLayout.WEST);

        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reader rd=Reader.verify(enterReader.getText());
                if(rd==null){
                    vrfResult.setText("验证读者信息出错"+Reader.sqlCode);return;
                }
                ReaderLoginState rls=new ReaderLoginState(rd);
                if(rls.getId()==-1){
                    vrfResult.setText("输入读者信息有误");
                }
                else{
                    vrfResult.setText("读者信息验证成功");
                    trueReader=true;
                    reader=rls;
                }
            }
        });

        JPanel cPanel=new JPanel(new BorderLayout());
        generateColName();
        generateTableData();
        generateJTable();
        JTableHeader jth=table.getTableHeader();
        cPanel.add(jth,BorderLayout.NORTH);
        cPanel.add(table,BorderLayout.CENTER);
        container.add(cPanel,BorderLayout.CENTER);


        JPanel ePanel=new JPanel(new BorderLayout());
        JTableHeader jth2=tDuration.getTableHeader();
        ePanel.add(jth2,BorderLayout.NORTH);
        ePanel.add(tDuration,BorderLayout.CENTER);
        container.add(ePanel,BorderLayout.EAST);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                trueBooks=false;
            }
        });

        JPanel sPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sPanel.setBackground(new Color(200,200,255));
        JButton submit=new JButton("提交");
        JButton verifyBook=new JButton("验证图书信息");
        JLabel submitNtc=new JLabel();
        sPanel.add(submitNtc);
        sPanel.add(verifyBook);
        sPanel.add(submit);
        container.add(sPanel,BorderLayout.SOUTH);

        this.pack();

        verifyBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean noError=true;
                int rowCount=table.getModel().getRowCount();
                int colCount=table.getModel().getColumnCount();
                BookInfo bi=null;
                for(int i=0;i<rowCount;i++){
                    Integer bid=Integer.parseInt((String)table.getModel().getValueAt(i,0));
                    Book bk=Book.verify(bid.intValue());
                    if(bk==null){
                        table.getModel().setValueAt("验证错误",i,colCount-1);
                        noError=false;
                    }
                    bi=new BookInfo(bk);
                    if(bi==null){
                        table.getModel().setValueAt("验证错误",i,colCount-1);
                        noError=false;
                    }else{
                        table.getModel().setValueAt(bi.getName(),i,1);
                        table.getModel().setValueAt(bi.getAuthor(),i,2);
                        table.getModel().setValueAt(bi.getType(),i,3);
                        table.getModel().setValueAt("验证成功",i,colCount-1);
                    }
                }
                table.updateUI();
                if(noError)
                    trueBooks=true;
                else trueBooks=false;
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!trueReader){
                    submitNtc.setText("用户未验证");return;
                }
                if(!trueBooks){
                    submitNtc.setText("图书未验证");return;
                }
                Integer enterDura;
                int rowCount =table.getModel().getRowCount();
                int colCount =table.getModel().getColumnCount();
                for(int i=0;i<rowCount;i++){
                    try{
                        enterDura=Integer.parseInt(tDuration.getModel().getValueAt(i,0).toString());
                    }catch (Exception nfe){
                        enterDura=-1;
                    }
                    if(enterDura<=0){
                        table.getModel().setValueAt("借阅期错误",i,colCount-1);
                        table.updateUI();
                        return;
                    }
                    BookInfo bi=new BookInfo(Integer.parseInt((String)table.getModel().getValueAt(i,0)));
                    if(LendAndReturn.borrowbook(reader,bi,enterDura)!=0){
                        table.getModel().setValueAt("借阅失败",i,colCount-1);
                    }else{
                        table.getModel().setValueAt("借阅成功",i,colCount-1);
                    }
                }
                table.updateUI();
            }
        });
    }

    private void generateColName(){
        //id name author type
        colName=new Vector<>();
        colName.add("id");
        colName.add("书名");
        colName.add("作者");
        colName.add("类型");
        colName.add("借阅结果");

        tDColName=new Vector<>();
        tDColName.add("借阅期");
    }
    private void generateTableData(){
        tableData=new Vector<Vector>();
        duraData=new Vector<Vector>();
        if(books==null){
            Vector<String> tpl=new Vector<String>();
            for(int i=0;i<5;i++)
                tpl.add("");
            tableData.add(tpl);
            tpl=new Vector<String>();
            tpl.add("");
            duraData.add(tpl);
            return;
        }
        trueBooks=true;
        for(int i=0;i<books.size();i++){
            Vector<String> tpl=new Vector<String>();
            tpl.add(new Integer(books.get(i).getId()).toString());
            tpl.add(books.get(i).getName());
            tpl.add(books.get(i).getAuthor());
            tpl.add(books.get(i).getType());
            tpl.add("");
            tpl.add("待提交");
            tableData.add(tpl);

            tpl=new Vector<String>();
            tpl.add("");
            duraData.add(tpl);
        }
    }
    private void generateJTable(){
        TableModel tm=new DefaultTableModel(tableData, colName){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0)
                    return true;
                return false;
            }
        };
        table=new JTable(tm);
        tDuration=new JTable(duraData,tDColName);
    }
}
