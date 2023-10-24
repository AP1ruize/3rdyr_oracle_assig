package GUI;

import Logic.SearchInfo;
import Tools.NumberDocument;
import elements.DBConnection;
import elements.Rule;
import elements.SearchType4BM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class BookManagement extends JDialog {
    private Vector<Vector> sResult;
    private Vector<String> colName;
    private Vector<Vector> books;
    private JTable table;
    private int beginIndex;
    private int totPages;
    public int sqlCode;
//    private JComboBox<SearchType4BM> iduType, idlType, nameType, prsType,
//            athType, tyType, rmuType, rmlType, amtuType, amtlType;

    public BookManagement(){
        books=null;
        sResult=getData(null,null,null,null,null,
                null,null,null,null,null,false);
//        sResult=new Vector<Vector>();
//        sResult.add(new Vector());
        colName= this.getColName();
        beginIndex=0;
        sqlCode=0;

        this.setVisible(true);
        this.setModal(false);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel nPanel=new JPanel(new BorderLayout());
        container.add(nPanel,BorderLayout.NORTH);
        nPanel.setBackground(new Color(180,255,180));
        nPanel.add(new JLabel("图书管理",JLabel.CENTER),BorderLayout.NORTH);
        JPanel inputPanel=new JPanel(new GridLayout(4,5));
        nPanel.add(inputPanel,BorderLayout.CENTER);
        JPanel searchPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nPanel.add(searchPanel,BorderLayout.SOUTH);

        JLabel iduLb=new JLabel("图书id上限");   inputPanel.add(iduLb);
        JLabel idlLb=new JLabel("图书id下限");   inputPanel.add(idlLb);
        JLabel nameLb=new JLabel("书名");       inputPanel.add(nameLb);
        JLabel prsLb=new JLabel("出版社");       inputPanel.add(prsLb);
        JLabel athLb=new JLabel("作者");        inputPanel.add(athLb);

        JTextField iduTF=new JTextField();
        iduTF.setDocument(new NumberDocument());     inputPanel.add(iduTF);
        JTextField idlTF=new JTextField();
        idlTF.setDocument(new NumberDocument());     inputPanel.add(idlTF);
        JTextField nameTF=new JTextField();          inputPanel.add(nameTF);
        JTextField prsTF=new JTextField();           inputPanel.add(prsTF);
        JTextField athTF=new JTextField();           inputPanel.add(athTF);

        JLabel tyLb=new JLabel("类型");         inputPanel.add(tyLb);
        JLabel rmuLb=new JLabel("库存剩余上限");  inputPanel.add(rmuLb);
        JLabel rmlLb=new JLabel("库存剩余下限");  inputPanel.add(rmlLb);
        JLabel amtuLb=new JLabel("总数量上限");   inputPanel.add(amtuLb);
        JLabel amtlLb=new JLabel("总数量下限");   inputPanel.add(amtlLb);


        JTextField tyTF=new JTextField();            inputPanel.add(tyTF);
        JTextField rmuTF=new JTextField();           inputPanel.add(rmuTF);
        rmuTF.setDocument(new NumberDocument());
        JTextField rmlTF=new JTextField();           inputPanel.add(rmlTF);
        rmlTF.setDocument(new NumberDocument());
        JTextField amtuTF=new JTextField();          inputPanel.add(amtuTF);
        amtuTF.setDocument(new NumberDocument());
        JTextField amtlTF=new JTextField();          inputPanel.add(amtlTF);
        amtuTF.setDocument(new NumberDocument());

        JComboBox<String> order=new JComboBox<>(new String[]{"升序","降序"});
        JButton search=new JButton("搜索");
        JButton newBook=new JButton("增加图书");
        searchPanel.add(order);
        searchPanel.add(search);
        searchPanel.add(newBook);

        JPanel cPanel =new JPanel();
//        generateTestData();
        generateJTable();
        JScrollPane jsp=new JScrollPane(table);
        cPanel.add(jsp,BorderLayout.CENTER);
        container.add(cPanel,BorderLayout.CENTER);

        JPanel sPanel =new JPanel(new FlowLayout(FlowLayout.RIGHT));
        container.add(sPanel,BorderLayout.SOUTH);
        JLabel infoWin=new JLabel();
        JButton nxtPage=new JButton("下一页");
        JButton lstPage=new JButton("上一页");
        nxtPage.setEnabled(false);
        lstPage.setEnabled(false);
        sPanel.add(infoWin);
        sPanel.add(nxtPage);
        sPanel.add(lstPage);

        this.pack();

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean desc=false;
                if(order.getSelectedIndex()==1)
                    desc=true;
                books=getData(Rule.convert_2i(iduTF.getText()),Rule.convert_2i(idlTF.getText()),
                        Rule.convert_2s(nameTF.getText()),Rule.convert_2s(prsTF.getText()),Rule.convert_2s(athTF.getText()),
                        Rule.convert_2s(tyTF.getText()),Rule.convert_2i(rmuTF.getText()),Rule.convert_2i(rmlTF.getText()),
                        Rule.convert_2i(amtuTF.getText()),Rule.convert_2i(amtlTF.getText()),desc);
                if(books!=null){
                    if(sResult==null){
                        sResult=new Vector<>();
                    }
                    sResult.clear();
                    beginIndex=0;
                    for(int i = beginIndex; i<beginIndex+ Rule.dspIn1Page && i<books.size(); i++){
                        sResult.add(books.get(i));
                    }
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
                    totPages=(books.size()/Rule.dspIn1Page)+1;
                    infoWin.setText("1/"+totPages+" ");
                    if(books.size()<=Rule.dspIn1Page)
                        nxtPage.setEnabled(false);
                    else nxtPage.setEnabled(true);
                    lstPage.setEnabled(false);
                }
                else {
                    if(SearchInfo.sqlCode==0)
                        infoWin.setText("No results.");
                    else infoWin.setText("Error ocuured, error code: "+SearchInfo.sqlCode);
                }
            }
        });
        newBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewBook();
            }
        });
        nxtPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lstPage.setEnabled(true);
                beginIndex+=Rule.dspIn1Page;
                sResult.clear();
                for(int i=beginIndex;i<beginIndex+ Rule.dspIn1Page && i<books.size();i++){
                    sResult.add(books.get(i));
                }
                generateJTable();
                table.updateUI();
                jsp.updateUI();
                int curPage=(beginIndex/Rule.dspIn1Page)+1;
                infoWin.setText(curPage+"/"+totPages+" ");
                if(curPage==totPages)
                    nxtPage.setEnabled(false);
                else nxtPage.setEnabled(true);
            }
        });
        lstPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nxtPage.setEnabled(true);
                beginIndex-=Rule.dspIn1Page;
                sResult.clear();
                for(int i=beginIndex;i<beginIndex+ Rule.dspIn1Page && i<books.size();i++){
                    sResult.add(books.get(i));
                }
                generateJTable();
                table.updateUI();
                jsp.updateUI();
                int curPage=(beginIndex/Rule.dspIn1Page)+1;
                infoWin.setText(curPage+"/"+totPages+" ");
                if(curPage==1)
                    lstPage.setEnabled(false);
                else lstPage.setEnabled(true);
            }
        });
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int sRow=table.getSelectedRow();
                sRow=table.rowAtPoint(e.getPoint());
                System.out.println("BookManagement.table.aMouseListener"+sRow);
                int c=e.getButton();
                if(c==MouseEvent.BUTTON1)//图书展示页面
                    new BookShow((Integer)sResult.get(sRow).get(0));
                table.clearSelection();
                sRow=-1;
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });
    }

    public Vector<Vector> getData(Integer idu,Integer idl,String name,String press,String author,String type,
                                  Integer rmu,Integer rml,Integer amtu,Integer amtl,boolean desc){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Vector<Vector> rV=new Vector<Vector>();
        Vector<Object> tpl=null;
        try {
            conn= DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
//        String sql="select bi.*,st.amount,st.remain from bookinfo bi left join stock st on bi.bookid=st.bookid ";
        String sql="select * from booksearch ";
        String suffix="";
        boolean hasSuf=false;
        if(idu!=null){
            suffix+="where ";
//            suffix+=("bi."+SearchType4BM.IDU.attrName()+idu);
            suffix+=(SearchType4BM.IDU.attrName()+idu);
            hasSuf=true;
        }
        if(idl!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
//            suffix+=("bi."+SearchType4BM.IDL.attrName()+idl);
            suffix+=(SearchType4BM.IDL.attrName()+idl);
            hasSuf=true;
        }
        if(name!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.NAME.attrName()+" like '%"+name+"%'");
            hasSuf=true;
        }
        if(press!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.PRS.attrName()+" like '%"+press+"%'");
            hasSuf=true;
        }
        if(author!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.ATH.attrName()+" like '%"+author+"%'");
            hasSuf=true;
        }
        if(type!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.TYP.attrName()+" like '%"+type+"%'");
            hasSuf=true;
        }
        if(rmu!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.RMU.attrName()+rmu);
            hasSuf=true;
        }
        if(rml!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.RML.attrName()+rml);
            hasSuf=true;
        }
        if(amtu!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.AMTU.attrName()+amtu);
            hasSuf=true;
        }
        if(amtl!=null){
            if(!hasSuf)
                suffix+="where ";
            else suffix+=" and ";
            suffix+=(SearchType4BM.AMTL.attrName()+amtl);
            hasSuf=true;
        }
//        suffix+=" order by bi.bookid";
        suffix+=" order by bookid";
        if(desc)
            suffix+=" desc";
        sql+=suffix;
        System.out.println("BookManagement.getData:sql:"+sql);
        ImageIcon image=null;
        try {
            Statement stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                tpl=new Vector<Object>();
                tpl.add(rs.getInt("bookid"));
                tpl.add(rs.getString("bookname"));
                tpl.add(rs.getString("press"));
                tpl.add(rs.getString("author"));
                tpl.add(rs.getInt("remain"));
                tpl.add(rs.getInt("amount"));
                if(rs.getBlob("pic")!=null) {
                    image = new ImageIcon(rs.getBlob("pic").getBinaryStream().readAllBytes());
                    image.setImage(image.getImage().getScaledInstance(Rule.showImageSize, Rule.showImageSize, Image.SCALE_FAST));
                    tpl.add(image);
//                    new ImageIcon();
                }
                else tpl.add(new ImageIcon());
                rV.add(tpl);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return rV;
    }
    public Vector<String> getColName(){
        Vector<String> rV=new Vector<String>();
        rV.add("图书id");
        rV.add("书名");
        rV.add("出版社");
        rV.add("作者");
        rV.add("库存剩余");
        rV.add("总数量");
        rV.add("图片");
        return rV;
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
        table.setRowHeight(100);
    }
}
