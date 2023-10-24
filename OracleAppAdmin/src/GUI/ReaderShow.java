package GUI;

import javax.swing.*;
import Runtime.ReaderLoginState;
import Tools.NumberDocument;
import elements.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ReaderShow extends JDialog {
    private User reader;
    private boolean admin;

    public ReaderShow(ReaderLoginState loginState){ init(loginState);}
    public ReaderShow(int uid){ init(uid);}
    private void init(ReaderLoginState loginState){ init(loginState.getId());}
    private void init(int uid){
        if(!getReaderInfo(uid)){
            reader=null;
            admin=false;
            return;
        }

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,300);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());
        int textFieldWidth=16;

        JLabel title=new JLabel("用户信息",JLabel.CENTER);
        title.setBackground(new Color(180,255,180));
        container.add(title,BorderLayout.NORTH);

        JPanel wPanel=new JPanel(new GridLayout(2,1));
        JLabel idLabel=new JLabel("UserID: "+reader.getId(),JLabel.CENTER);
        JLabel adminLabel=new JLabel();   adminLabel.setHorizontalAlignment(JLabel.CENTER);
        if(admin)
            adminLabel.setText("管理员");
        else adminLabel.setText("普通读者");
        wPanel.add(idLabel);
        wPanel.add(adminLabel);
        container.add(wPanel,BorderLayout.WEST);

        JPanel cPanel=new JPanel(new GridLayout(3,1));
        JPanel cpRow1=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel cpRow2=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel cpRow3=new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel cLabel1=new JLabel("昵称：");
        JTextField nickTF=new JTextField(reader.getNickname());
        nickTF.setEditable(false);
        nickTF.setColumns(textFieldWidth);
        cpRow1.add(cLabel1);
        cpRow1.add(nickTF);
        cPanel.add(cpRow1);

        JLabel cLabel2=new JLabel("等级：");
        JTextField classTF=new JTextField(((Integer)reader.getExp()).toString());
//        classTF.setDocument(new NumberDocument());
        classTF.setEditable(false);
        classTF.setText(((Integer)reader.getExp()).toString());
        classTF.setColumns(textFieldWidth);
        cpRow2.add(cLabel2);
        cpRow2.add(classTF);
        cPanel.add(cpRow2);

        JLabel cLabel3=new JLabel("余额：");
        JTextField blcTF=new JTextField(((Integer)reader.getCredit()).toString());
//        blcTF.setDocument(new NumberDocument());
        blcTF.setEditable(false);
        blcTF.setText(((Integer)reader.getCredit()).toString());
        blcTF.setColumns(textFieldWidth);
        cpRow3.add(cLabel3);
        cpRow3.add(blcTF);
        cPanel.add(cpRow3);

        container.add(cPanel,BorderLayout.CENTER);

        JPanel ePanel=new JPanel(new GridLayout(3,1));
        JButton edit=new JButton("编辑");
        JButton delete=new JButton("删除此用户");
        if(admin)
            delete.setEnabled(false);
        JLabel infoWin=new JLabel();
        ePanel.add(edit);
        ePanel.add(delete);
        ePanel.add(infoWin);
        container.add(ePanel,BorderLayout.EAST);

        JPanel sPanel=new JPanel(new BorderLayout());
        JPanel sePanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sPanel.add(sePanel,BorderLayout.EAST);
        JButton confirm=new JButton("确认修改");
        JButton cancel=new JButton("取消修改");
        confirm.setEnabled(false);
        cancel.setEnabled(false);
        sePanel.add(confirm,BorderLayout.WEST);
        sePanel.add(cancel,BorderLayout.CENTER);
        JPasswordField password=new JPasswordField();
        password.setColumns(10);
        password.setToolTipText("在此输入新密码");
        password.setEnabled(false);
        JPasswordField repeatPW=new JPasswordField();
        repeatPW.setColumns(10);
        repeatPW.setForeground(new Color(204,204,204));
        repeatPW.setToolTipText("在此重复新密码");
        repeatPW.setEnabled(false);
        sPanel.add(password,BorderLayout.WEST);
        sPanel.add(repeatPW,BorderLayout.CENTER);
        container.add(sPanel,BorderLayout.SOUTH);

        this.pack();

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickTF.setEditable(true);
                if(!admin){
                    classTF.setEditable(true);
//                    classTF.setDocument(new NumberDocument());
                    blcTF.setEditable(true);
//                    blcTF.setDocument(new NumberDocument());
                }
                password.setEditable(true);
                repeatPW.setEditable(true);
                confirm.setEnabled(true);
                cancel.setEnabled(true);
                delete.setEnabled(false);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除用户
                int opt=JOptionPane.showConfirmDialog(container,"确认删除？","确认删除",JOptionPane.YES_NO_OPTION);
                if(opt==JOptionPane.YES_OPTION){
                    if(reader.deleteUser())
                    {
                        //JOptionPane.showConfirmDialog(container,"删除成功！","删除成功",JOptionPane.YES_OPTION);
                        infoWin.setForeground(new Color(0,0,0));
                        infoWin.setText("账号已成功删除");
                        delete.setEnabled(false);
                        edit.setEnabled(false);
                        return;
                    }
                    else {
                        infoWin.setForeground(new Color(255,50,50));
                        infoWin.setText("删除失败");
                    }
                }
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //确认更改
                int opt=JOptionPane.showConfirmDialog(container,"确认修改？","确认修改",JOptionPane.YES_NO_OPTION);
                if(opt==JOptionPane.YES_OPTION) {
                    String pass1=new String(password.getPassword());
                    String pass2=new String(repeatPW.getPassword());
                    if(!pass1.equals("")){
                        //改了密码
                        if(!pass1.equals(pass2)){
                            infoWin.setForeground(new Color(255,50,50));
                            infoWin.setText("两次新密码输入不一致");
                            return;
                        }
                        reader.setPassword(pass1);
                    }
                    reader.setNickname(nickTF.getText());
                    reader.setExp(Integer.parseInt(classTF.getText()));
                    reader.setCredit(Integer.parseInt(blcTF.getText()));
                    if(reader.modifyInfo()) {
                        infoWin.setForeground(new Color(0,0,0));
                        infoWin.setText("修改成功");
                    }
                    else {
                        infoWin.setForeground(new Color(255,50,50));
                        infoWin.setText("修改失败");
                    }
                    delete.setEnabled(true);
                    nickTF.setEditable(false);
                    if(!admin){
                        classTF.setEditable(false);
                        blcTF.setEditable(false);
                    }

                    password.setEnabled(false);
                    repeatPW.setEnabled(false);
                    confirm.setEnabled(false);
                    cancel.setEnabled(false);
                }
                else {//和点取消相同效果
                    delete.setEnabled(true);
                    nickTF.setText(reader.getNickname());
                    nickTF.setEditable(false);
                    if(!admin){
                        classTF.setText(((Integer)reader.getExp()).toString());
                        classTF.setEditable(false);
                        blcTF.setText(((Integer)reader.getCredit()).toString());
                        blcTF.setEditable(false);
                    }
                    password.setEnabled(false);
                    repeatPW.setEnabled(false);
                    confirm.setEnabled(false);
                    cancel.setEnabled(false);
                }
            }
        });
        cancel.addActionListener(new ActionListener() {//取消更改
            @Override
            public void actionPerformed(ActionEvent e) {
                nickTF.setText(reader.getNickname());
                nickTF.setEditable(false);
                if(!admin){
                    classTF.setText(((Integer)reader.getExp()).toString());
                    classTF.setEditable(false);
                    blcTF.setText(((Integer)reader.getCredit()).toString());
                    blcTF.setEditable(false);
                }
                password.setEnabled(false);
                repeatPW.setEnabled(false);
                confirm.setEnabled(false);
                cancel.setEnabled(false);
                delete.setEnabled(true);
            }
        });
    }

    public static int sqlCode=0;
    private boolean getReaderInfo(int uid){
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
        stmt="select * from userinfo where userid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,uid);
            rs=pstmt.executeQuery();
            if(rs.next()){
                if(rs.getInt("admin")>0){
                    reader=new Admin(rs.getInt("userid"),rs.getString("nickname"),
                            rs.getString("password"));
                    admin=true;
                }
                else {
                    reader=new Reader(rs.getInt("userid"),rs.getString("password"),
                        rs.getString("nickname"),rs.getInt("exp"),rs.getInt("credit"));
                    admin=false;
                }
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        return true;
    }


}
