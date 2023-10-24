package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import Logic.SearchInfo;
import Runtime.ReaderLoginState;
//import elements.SearchType;
import Tools.NumberDocument;
import elements.Rule;
import elements.SearchType4UM;

public class UserManagement extends JDialog {
    private Vector<Vector> sResult;
    private Vector<String> colName;
    private Vector<Vector> users;
    private JTable table;
    private int beginIndex;
    private int totPages;
    private JComboBox<SearchType4UM> iduType, idlType, nickType, clType, blcType;

    public UserManagement(){
        users=null;
        sResult=new Vector<Vector>();
        sResult.add(new Vector());
        colName= ReaderLoginState.getColName();
        beginIndex=0;

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel=new JPanel(new GridLayout(2,1));
        JLabel title=new JLabel("用户管理",JLabel.CENTER);
        northPanel.add(title);
        JPanel searchBar=new JPanel(new GridLayout(1,12));
        iduType = new JComboBox<>(new SearchType4UM[]{SearchType4UM.IDU,SearchType4UM.NULL});
        JTextField idu=new JTextField();
        idu.setDocument(new NumberDocument());
        searchBar.add(iduType);
        searchBar.add(idu);
        idlType = new JComboBox<>(new SearchType4UM[]{SearchType4UM.IDL,SearchType4UM.NULL});
        JTextField idl=new JTextField();
        idl.setDocument(new NumberDocument());
        searchBar.add(idlType);
        searchBar.add(idl);
        nickType = new JComboBox<>(new SearchType4UM[]{SearchType4UM.NICK,SearchType4UM.NULL});
        JTextField nick=new JTextField();
        searchBar.add(nickType);
        searchBar.add(nick);
        clType = new JComboBox<>(new SearchType4UM[]{SearchType4UM.CL,SearchType4UM.NULL});
        JTextField cl=new JTextField();
        cl.setDocument(new NumberDocument());
        searchBar.add(clType);
        searchBar.add(cl);
        blcType = new JComboBox<>(new SearchType4UM[]{SearchType4UM.BLC,SearchType4UM.NULL});
        JTextField blc=new JTextField();
        blc.setDocument(new NumberDocument());
        searchBar.add(blcType);
        searchBar.add(blc);
        JButton search=new JButton("搜索");
        JButton newUser=new JButton("新用户");
        searchBar.add(search);
        searchBar.add(newUser);
        northPanel.add(searchBar);
        northPanel.setBackground(new Color(180,255,180));

        JPanel centerPanel=new JPanel();
//        generateTestData();
        generateJTable();
        JScrollPane jsp=new JScrollPane(table);
        centerPanel.add(jsp,BorderLayout.CENTER);
        jsp.setHorizontalScrollBar(new JScrollBar());
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel infoWin=new JLabel();
        JButton back=new JButton("返回");
//        JButton nxtPage=new JButton("下一页");
//        JButton lstPage=new JButton("上一页");
//        nxtPage.setEnabled(false);
//        lstPage.setEnabled(false);
        southPanel.add(infoWin);
        southPanel.add(back);
//        southPanel.add(nxtPage);
//        southPanel.add(lstPage);

        container.add(northPanel,BorderLayout.NORTH);
        container.add(centerPanel,BorderLayout.CENTER);
        container.add(southPanel,BorderLayout.SOUTH);
        this.pack();

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                users= SearchInfo.searchUsersAdv(Rule.convert_2i(idu.getText()),Rule.convert_2i(idl.getText()),
                        Rule.convert_2s(nick.getText()),Rule.convert_2i(cl.getText()),Rule.convert_2i(blc.getText()));
                if(users!=null){
                    if(sResult==null){
                        sResult=new Vector<>();
                    }
                    connect(users);
//                    beginIndex=0;
//                    for(int i = beginIndex; i<beginIndex+ Rule.dspIn1Page && i<users.size(); i++){
//                        sResult.add(users.get(i));
//                    }
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
                    centerPanel.updateUI();
                    infoWin.setText("Results:"+sResult.size());
//                    totPages=(users.size()/Rule.dspIn1Page)+1;
//                    infoWin.setText("1/"+totPages+" ");
//                    if(users.size()<=Rule.dspIn1Page)
//                        nxtPage.setEnabled(false);
//                    else nxtPage.setEnabled(true);
//                    lstPage.setEnabled(false);
                }
                else {
                    if(SearchInfo.sqlCode==0)
                        infoWin.setText("No results.");
                    else infoWin.setText("Error ocuured, error code: "+SearchInfo.sqlCode);
                }
            }
        });
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterPage();
            }
        });

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int sRow=table.getSelectedRow();
                sRow=table.rowAtPoint(e.getPoint());
                int c=e.getButton();
                if(c==MouseEvent.BUTTON1)//用户展示页面
                    new ReaderShow(Integer.parseInt((String)sResult.get(sRow).get(0)));
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

    public void generateJTable(){
        TableModel tm=new DefaultTableModel(sResult, colName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
