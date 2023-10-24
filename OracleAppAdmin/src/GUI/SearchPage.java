package GUI;

import Logic.SearchInfo;
import elements.Rule;
import elements.SearchType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import Runtime.*;

import static elements.SearchType.*;

public class SearchPage extends JDialog{
    private Vector<Vector> sResult;
    private Vector<String> colName;
    private Vector<Vector> books;
    private JTable table;
    private int beginIndex;
    private int totPages;
    private JPopupMenu menu;
    private int[] selectedRows;

    public SearchPage(){
        InitMenu();
        books=null;
        sResult=new Vector<Vector>();
//        Vector<String> init=new Vector();
//        init.add("");init.add("");init.add("");init.add("");init.add("");
//        sResult.add(init);
        colName=BookInfo.getColName();
        beginIndex=0;

        this.setVisible(true);
        this.setModal(false);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel=new JPanel(new GridLayout(2,1));
        JLabel title=new JLabel("图书搜索",JLabel.CENTER);
        northPanel.add(title);
        JPanel searchBar=new JPanel(new GridLayout(1,3));
        JTextField searchContent=new JTextField();
        //searchContent.setSize(100,20);
        SearchType[] sList=new SearchType[]{NAME,AUTHOR,TYPE,BID};
        JComboBox<SearchType> types = new JComboBox<>(sList);
        JButton search=new JButton("搜索");
        searchBar.add(types);
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
        JButton nxtPage=new JButton("下一页");
        JButton lstPage=new JButton("上一页");
        JButton back=new JButton("返回");
        nxtPage.setEnabled(false);
        lstPage.setEnabled(false);
        southPanel.add(infoWin);
        southPanel.add(nxtPage);
        southPanel.add(lstPage);
        southPanel.add(back);

        container.add(northPanel,BorderLayout.NORTH);
        container.add(centerPanel,BorderLayout.CENTER);
        container.add(southPanel,BorderLayout.SOUTH);
        this.pack();

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchPage.this.dispose();
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                books= SearchInfo.searchBooks(searchContent.getText(),types.getItemAt(types.getSelectedIndex()));
                System.out.println("SearchPage.searchAListener:books.size"+books.size());
                if(books!=null){
//                    if(sResult==null){
//                        sResult=new Vector<>();
//                    }
                    sResult.clear();
                    beginIndex=0;
                    for(int i=beginIndex;i<beginIndex+ Rule.dspIn1Page && i<books.size();i++){
                        sResult.add(books.get(i));
                    }
                    System.out.println("SearchPage.searchAListener:sResult.size"+sResult.size());
                    System.out.println("SearchPage.searchAListener:sResult.get(0)"+sResult.get(0).get(0).toString());
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
                    centerPanel.updateUI();
                    totPages=(books.size()/Rule.dspIn1Page)+1;
                    infoWin.setText("1/"+totPages+" ");
                    if(books.size()<=Rule.dspIn1Page)
                        nxtPage.setEnabled(false);
                    else nxtPage.setEnabled(true);
                    lstPage.setEnabled(false);
                }
                else infoWin.setText("Error ocuured, error code: "+SearchInfo.sqlCode);
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
                int c=e.getButton();
                if(c==MouseEvent.BUTTON1)//图书展示页面
                    new BookShow(Integer.parseInt((String)sResult.get(sRow).get(0)));//图书展示页面
                table.clearSelection();
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedRows=table.getSelectedRows();
                if(e.isPopupTrigger()){
                    menu.show(table,e.getX(),e.getY());
                    table.clearSelection();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
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
        table.setRowHeight(100);
    }

    private void InitMenu(){
        menu=new JPopupMenu();
        JMenuItem batLend=new JMenuItem("批量借阅");
        menu.add(batLend);

        batLend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<BookInfo> bookInfos=new Vector<BookInfo>();
                for(int i=0;i<selectedRows.length;i++){
                    bookInfos.add(new BookInfo(sResult.get(selectedRows[i])));
                }
                new LendingPage(bookInfos);
            }
        });
    }
}
