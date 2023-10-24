package GUI;

//import Logic.SearchInfo;
import Tools.NumberDocument;
import elements.Notice;
import elements.Rule;
import elements.SearchType4NM;
import elements.User;

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

public class NoticeManagement extends JDialog {
    private Vector<Vector> sResult;
    private Vector<String> colName;
    private JTable table;
    private JComboBox<SearchType4NM> aidType,tsuType,tslType;
    private int sRow;
    private User admin;

    public NoticeManagement(User ad){
        admin=ad;
        sRow=-1;
        sResult=new Vector<Vector>();
//        sResult.add(new Vector());
//        sResult=Notice.getNotices(null,null,null);
        colName= Notice.colName();

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel=new JPanel(new BorderLayout());
        JLabel title=new JLabel("公告管理",JLabel.CENTER);
        northPanel.add(title,BorderLayout.NORTH);
        JPanel searchBar=new JPanel(new GridLayout(2,2));
        northPanel.add(searchBar,BorderLayout.CENTER);

        JPanel sbP1=new JPanel(new GridLayout(1,3));
        searchBar.add(sbP1);
        tslType=new JComboBox<SearchType4NM>(new SearchType4NM[]{SearchType4NM.STL,SearchType4NM.NULL});
        JTextField tslTF_Y =new JTextField();
        tslTF_Y.setDocument(new NumberDocument());
        JPanel tslMD=new JPanel(new GridLayout(1,2));
        JTextField tslTF_M=new JTextField();
        tslTF_M.setDocument(new NumberDocument());
        JTextField tslTF_D=new JTextField();
        tslTF_D.setDocument(new NumberDocument());
        tslMD.add(tslTF_M);
        tslMD.add(tslTF_D);
        sbP1.add(tslType);
        sbP1.add(tslTF_Y);
        sbP1.add(tslMD);

        JPanel sbP2=new JPanel(new GridLayout(1,3));
        searchBar.add(sbP2);
        tsuType=new JComboBox<SearchType4NM>(new SearchType4NM[]{SearchType4NM.STU,SearchType4NM.NULL});
        JTextField tsuTF_Y =new JTextField();
        tsuTF_Y.setDocument(new NumberDocument());
        JPanel tsuMD=new JPanel(new GridLayout(1,2));
        JTextField tsuTF_M=new JTextField();
        tsuTF_M.setDocument(new NumberDocument());
        JTextField tsuTF_D=new JTextField();
        tsuTF_D.setDocument(new NumberDocument());
        tsuMD.add(tsuTF_M);
        tsuMD.add(tsuTF_D);
        sbP2.add(tsuType);
        sbP2.add(tsuTF_Y);
        sbP2.add(tsuMD);

        JPanel sbP3=new JPanel(new GridLayout(1,2));
        searchBar.add(sbP3);
        aidType=new JComboBox<SearchType4NM>(new SearchType4NM[]{SearchType4NM.AID,SearchType4NM.NULL});
        JTextField aidTF=new JTextField();
        aidTF.setDocument(new NumberDocument());
        sbP3.add(aidType);
        sbP3.add(aidTF);

        JPanel sbP4=new JPanel(new GridLayout(1,3));
        searchBar.add(sbP4);
        JButton search=new JButton("查询");
        JButton searchAll=new JButton("查询全部");
        JButton newNotice=new JButton("新建公告");
        sbP4.add(search);
        sbP4.add(searchAll);
        sbP4.add(newNotice);
        container.add(northPanel,BorderLayout.NORTH);
        northPanel.setBackground(new Color(180,255,180));

        JPanel centerPanel=new JPanel();
//        generateTestData();
        generateJTable();
        JScrollPane jsp=new JScrollPane(table);
//        centerPanel.add(table,BorderLayout.CENTER);
        centerPanel.add(jsp,BorderLayout.CENTER);
        container.add(centerPanel,BorderLayout.CENTER);

        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel infoWin=new JLabel();
        JButton delete =new JButton("删除");
        JButton showOff =new JButton("发布/撤下");
        JButton detail=new JButton("详细信息");
        southPanel.add(infoWin);
        southPanel.add(delete);
        southPanel.add(showOff);
        southPanel.add(detail);
        container.add(southPanel,BorderLayout.SOUTH);

        this.pack();

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<Vector> news=Notice.getNotices(Rule.convert_2i(aidTF.getText()),
                        NoticeManagement.convert(tsuTF_Y.getText(),tsuTF_M.getText(),tsuTF_D.getText()),
                        NoticeManagement.convert(tslTF_Y.getText(),tslTF_M.getText(),tslTF_D.getText()));
                if(news!=null){
//                    System.out.println("sResult size:"+sResult.size());
                    connect(news);
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
                }
                else {
                    if(Notice.sqlCode==0)
                        infoWin.setText("No results.");
                    else infoWin.setText("Error ocuured, error code: "+Notice.sqlCode);
                }
            }
        });
        searchAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<Vector> news=Notice.getNotices(null,null,null);
//                sResult
                if(news!=null){
                    connect(news);
//                    System.out.println("sResult size:"+sResult.size());
                    generateJTable();
                    table.updateUI();
                    jsp.updateUI();
//                    jsp.add(table);
                }
                else {
                    if(Notice.sqlCode==0)
                        infoWin.setText("No results.");
                    else infoWin.setText("Error ocuured, error code: "+Notice.sqlCode);
                }
            }
        });
        newNotice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewNotice(admin);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                sRow=table.getSelectedRow();
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                if(Notice.deleteNotice
                        (Integer.parseInt((String)sResult.get(sRow).get(0)),(Timestamp)sResult.get(sRow).get(1))) {
                    infoWin.setForeground(new Color(0,0,0));
                    infoWin.setText("删除成功");
                }
                else{
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("删除失败");
                }
                sRow=-1;
            }
        });
        showOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                sRow=table.getSelectedRow();
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                if(Notice.showNotice(Integer.parseInt((String)sResult.get(sRow).get(0)),
                        (Timestamp)sResult.get(sRow).get(1),(String)sResult.get(sRow).get(3)))
                {
                    infoWin.setForeground(new Color(0,0,0));
                    infoWin.setText("修改状态成功");
                }
                else{
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("修改状态失败");
                }
                sRow=-1;
            }
        });
        detail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                sRow=table.getSelectedRow();
//                sRow=table.rowAtPoint(mouseEvent.getPoint());
                if(sRow<0){
                    infoWin.setForeground(new Color(255,50,50));
                    infoWin.setText("请先选择要操作的条目");
                    return;
                }
                new NoticeShow(Integer.parseInt((String)sResult.get(sRow).get(0)),
                        (Timestamp)sResult.get(sRow).get(1));
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
        };
        if(table!=null)
            table.removeAll();
        table=new JTable(tm);
//        table.setRowHeight(100);
    }
    public static Timestamp convert(String strY,String strM,String strD){
        Integer year=Rule.convert_2i(strY);
        Integer month=Rule.convert_2i(strM);
        Integer day=Rule.convert_2i(strD);
        if(year==null || month==null || day==null)
            return null;
        Timestamp reV=new Timestamp(year-1800,month-1,day,0,0,0,0);
        return reV;
    }

    public void connect(Vector<Vector> newTpls){
        sResult.clear();
        for(int i=0;i<newTpls.size();i++)
            sResult.add(newTpls.get(i));
    }
}
