package GUI;

import javax.swing.*;
import Runtime.BookInfo;
import elements.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static elements.Rule.convert_2i;

public class BookShow extends JDialog {
    private Book book;
    private Stock stock;
//    protected boolean modStockBtn;

    public BookShow(BookInfo bookInfo){ init(bookInfo);}
    public BookShow(int bookId){ init(bookId);}
    private void init(BookInfo bookInfo){init(bookInfo.getId());}
    private void init(int bookId){
        if(!getBookInfo(bookId)){
            book=null;
            return;
        }
        stock=Stock.getStock(bookId);
        if(stock==null){
            stock=new Stock(bookId,-1,-1);
        }
//        modStockBtn=false;

        this.setVisible(true);
        this.setModal(false);
        this.setLocation(300,300);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());
        int textFieldWidth=16;

        JLabel title=new JLabel("图书信息",JLabel.CENTER);
        title.setBackground(new Color(180,255,180));
        container.add(title,BorderLayout.NORTH);

        JPanel wPanel=new JPanel(new BorderLayout());
        ImageIcon icon=null;
        JLabel picLabel=new JLabel();
        picLabel.setSize(Rule.showImageSize,Rule.showImageSize);
        if(book.getPic()!=null) {
            icon = book.getPic();
            if(icon.getImage()!=null)
                icon.setImage(icon.getImage().getScaledInstance(Rule.showImageSize, Rule.showImageSize, Image.SCALE_FAST));
        }
        if(icon.getImage()==null) {
            picLabel.setText("No Pic.");
            picLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        else picLabel.setIcon(icon);
        wPanel.add(picLabel,BorderLayout.NORTH);
        wPanel.add(new JLabel("ID: "+book.getId(),JLabel.CENTER),BorderLayout.CENTER);
        container.add(wPanel,BorderLayout.WEST);

        JPanel ncPanel=new JPanel(new BorderLayout());
        container.add(ncPanel,BorderLayout.CENTER);
        JPanel cPanel=new JPanel(new GridLayout(7,1));
        ncPanel.add(cPanel,BorderLayout.CENTER);
        JPanel cP1=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP1);
        JPanel cP2=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP2);
        JPanel cP3=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP3);
        JPanel cP4=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP4);
        JPanel cP7=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP7);
        JPanel cP8=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP8);
        JPanel cP5=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP5);
        JPanel cP6=new JPanel(new FlowLayout(FlowLayout.LEFT));   ncPanel.add(cP6,BorderLayout.SOUTH);

        JLabel nameLb=new JLabel("书名：");
        JTextField nameTF=new JTextField(book.getName());
        nameTF.setEditable(false);
        nameTF.setColumns(textFieldWidth);
        cP1.add(nameLb);  cP1.add(nameTF);

        JLabel pressLb=new JLabel("出版社:");
        JTextField pressTF=new JTextField(book.getPress());
        pressTF.setEditable(false);
        pressTF.setColumns(textFieldWidth);
        cP2.add(pressLb);  cP2.add(pressTF);

        JLabel authorLb=new JLabel("作者：");
        JTextField authorTF=new JTextField(book.getAuthor());
        authorTF.setEditable(false);
        authorTF.setColumns(textFieldWidth);
        cP3.add(authorLb);  cP3.add(authorTF);

        JLabel typeLb=new JLabel("类型：");
        JTextField typeTF=new JTextField(book.getType());
        typeTF.setEditable(false);
        typeTF.setColumns(textFieldWidth);
        cP4.add(typeLb);  cP4.add(typeTF);

        JLabel amtLb=new JLabel("总数量:");
        JTextField amtTF=new JTextField(((Integer)stock.getAmount()).toString());
        amtTF.setEditable(false);
        amtTF.setColumns(textFieldWidth);
        cP7.add(amtLb);  cP7.add(amtTF);

        JLabel rmLb=new JLabel("剩余：");
        JTextField rmTF=new JTextField(((Integer)stock.getRemain()).toString());
        rmTF.setEditable(false);
        rmTF.setColumns(textFieldWidth);
        cP8.add(rmLb);  cP8.add(rmTF);

        JLabel introLb=new JLabel("简介：");
        cP5.add(introLb);
        JTextArea introTA=new JTextArea(book.getIntro());
        introTA.setEditable(false);
        introTA.setColumns(22);
        introTA.setRows(3);
        JScrollPane introSP=new JScrollPane(introTA);
        cP6.add(introSP);

        //cPanel end

        JPanel ePanel=new JPanel(new GridLayout(9,1));
        container.add(ePanel,BorderLayout.EAST);
        JButton edit=new JButton("编辑图书信息");
        JButton lend=new JButton("借阅");
        JButton preorder=new JButton("预约");
        JButton delete=new JButton("删除图书");
        JButton incrStock =new JButton("增加库存");
        JButton dcrStock=new JButton("减少库存");
        JLabel infoWin=new JLabel("");
        JButton confirm=new JButton("确认修改");
        JButton cancel=new JButton("取消修改");
        confirm.setEnabled(false);
        cancel.setEnabled(false);
        ePanel.add(edit);
        ePanel.add(lend);
        ePanel.add(preorder);
        ePanel.add(delete);
        ePanel.add(incrStock);
        ePanel.add(dcrStock);
        ePanel.add(infoWin);
        ePanel.add(confirm);
        ePanel.add(cancel);

        this.pack();

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete.setEnabled(false);
                lend.setEnabled(false);
                confirm.setEnabled(true);
                cancel.setEnabled(true);
                incrStock.setEnabled(false);
                dcrStock.setEnabled(false);
                preorder.setEnabled(false);

                nameTF.setEditable(true);
                pressTF.setEditable(true);
                authorTF.setEditable(true);
                typeTF.setEditable(true);
                introTA.setEditable(true);
            }
        });

        incrStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input=JOptionPane.showInputDialog(BookShow.this,
                        "输入增加的数量","输入增加的数量",JOptionPane.QUESTION_MESSAGE);
                Integer moAmt=Rule.convert_2i(input);
                if(moAmt!=null){
                    stock.setAmount(stock.getAmount()+moAmt);
                    stock.setRemain(stock.getRemain()+moAmt);
                    if(stock.save()) {
                        infoWin.setText("修改成功");
                        rmTF.setText(((Integer)stock.getRemain()).toString());
                        amtTF.setText(((Integer)stock.getAmount()).toString());
                    }
                    infoWin.setText("修改失败，错误码"+Stock.sqlCode);
                }
                infoWin.setText("未输入或输入错误");
            }
        });

        dcrStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input=JOptionPane.showInputDialog(BookShow.this,
                        "输入减少的数量","输入减少的数量",JOptionPane.QUESTION_MESSAGE);
                Integer moAmt=Rule.convert_2i(input);
                if(moAmt!=null){
                    stock.setAmount(stock.getAmount()-moAmt);
                    stock.setRemain(stock.getRemain()-moAmt);
                    if(stock.save()) {
                        infoWin.setText("修改成功");
                        rmTF.setText(((Integer)stock.getRemain()).toString());
                        amtTF.setText(((Integer)stock.getAmount()).toString());
                    }
                    infoWin.setText("修改失败，错误码"+Stock.sqlCode);
                }
                infoWin.setText("未输入或输入错误");
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt=JOptionPane.showConfirmDialog(container,"确认删除？","确认删除",JOptionPane.YES_NO_OPTION);
                if(opt==JOptionPane.YES_OPTION){
                    if(Book.deleteBook(book)){
                        infoWin.setForeground(new Color(0,0,0));
                        infoWin.setText("图书已成功删除");
                        delete.setEnabled(false);
                        edit.setEnabled(false);
                        lend.setEnabled(false);
                        incrStock.setEnabled(false);
                        dcrStock.setEnabled(false);
                        preorder.setEnabled(false);

                        return;
                    }
                    else{
                        infoWin.setForeground(new Color(255,50,50));
                        infoWin.setText("删除失败，错误码："+Book.sqlCode);
                    }
                }
            }
        });

        lend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<BookInfo> books=new Vector<BookInfo>();
                books.add(Book.toBookInfo(book));
                new LendingPage(books);
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt=JOptionPane.showConfirmDialog(container,"确认修改？","确认修改",JOptionPane.YES_NO_OPTION);
                if(opt==JOptionPane.YES_OPTION){
                    book.setName(nameTF.getText());
                    book.setPress(pressTF.getText());
                    book.setAuthor(authorTF.getText());
                    book.setType(typeTF.getText());
                    book.setIntro(introTA.getText());
                    if(book.modifyInfo()){
                        infoWin.setForeground(new Color(0,0,0));
                        infoWin.setText("修改成功");
                    }
                    else{
                        infoWin.setForeground(new Color(255,50,50));
                        infoWin.setText("修改失败，错误码："+Book.sqlCode);
                        nameTF.setText(book.getName());
                        pressTF.setText(book.getPress());
                        authorTF.setText(book.getAuthor());
                        typeTF.setText(book.getType());
                        introTA.setText(book.getIntro());
                    }
                    nameTF.setEditable(false);
                    pressTF.setEditable(false);
                    authorTF.setEditable(false);
                    typeTF.setEditable(false);
                    introTA.setEditable(false);
                    edit.setEnabled(true);
                    delete.setEnabled(true);
                    lend.setEnabled(true);
                    confirm.setEnabled(false);
                    cancel.setEnabled(false);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameTF.setText(book.getName());
                nameTF.setEditable(false);
                pressTF.setText(book.getPress());
                pressTF.setEditable(false);
                authorTF.setText(book.getAuthor());
                authorTF.setEditable(false);
                typeTF.setText(book.getType());
                typeTF.setEditable(false);
                introTA.setText(book.getIntro());
                introTA.setEditable(false);
                edit.setEnabled(true);
                delete.setEnabled(true);
                lend.setEnabled(true);
                confirm.setEnabled(false);
                cancel.setEnabled(false);
            }
        });

        preorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input=JOptionPane.showInputDialog(BookShow.this,
                        "输入借阅用户id","输入借阅用户id",JOptionPane.QUESTION_MESSAGE);
                Integer moAmt=Rule.convert_2i(input);
                if(moAmt==null){
                    infoWin.setText("未输入或输入错误");
                    return;
                }
                if(Preorder.makePreorder(moAmt,book.getId())){
                    infoWin.setText("预定成功");
                }
                else {
                    if(Preorder.sqlCode==0)
                        infoWin.setText("因不满足预定\n条件而失败");
                    else infoWin.setText("预定出错，错误码"+Preorder.sqlCode);
                }
            }
        });
    }

    public static int sqlCode=0;
    private boolean getBookInfo(int bid){
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Vector<Vector> results=null;
        try {
            conn= DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        stmt="select * from bookinfo where bookid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,bid);
            rs=pstmt.executeQuery();
            if(rs.next()){
                book=new Book();
                if(rs.getBlob("pic")!=null) {
                    book.readInfo(rs.getInt("bookid"), rs.getString("bookname"), rs.getString("press"),
                            rs.getString("author"), rs.getString("type"), rs.getString("intro"),
                            new ImageIcon(rs.getBlob("pic").getBinaryStream().readAllBytes()));
                }
                else {
                    book.readInfo(rs.getInt("bookid"), rs.getString("bookname"), rs.getString("press"),
                            rs.getString("author"), rs.getString("type"), rs.getString("intro"),
                            new ImageIcon());
                }
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
