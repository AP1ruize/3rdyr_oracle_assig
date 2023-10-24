package GUI;

import elements.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginPage {
    public LoginPage(){
        JFrame frame=new JFrame("图书管理系统管理员端-登录");
        frame.setLocation(450,250);

        JPanel panel=new JPanel();
        //panel.setLocation(500,450);
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        JLabel welcome=new JLabel("欢迎登录图书管理系统管理员端",JLabel.CENTER);
        panel.add(welcome,BorderLayout.NORTH);

        JPanel mPanel=new JPanel(new GridLayout(2,2));
        JLabel IDorNameLabel=new JLabel(("管理员id或用户名："));
        JLabel passLabel=new JLabel("密码：");
        JTextField enterIDofName=new JTextField();
        JPasswordField enterPass=new JPasswordField();
        mPanel.add(IDorNameLabel);
        mPanel.add(enterIDofName);
        mPanel.add(passLabel);
        mPanel.add(enterPass);
        panel.add(mPanel,BorderLayout.CENTER);

        JPanel southPanel=new JPanel(new GridLayout(2,1));
        panel.add(southPanel,BorderLayout.SOUTH);
        JButton login=new JButton("登录");
        southPanel.add(login);

        JLabel loginResult=new JLabel("",JLabel.CENTER);
        southPanel.add(loginResult);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin ad=Admin.login(enterIDofName.getText(), String.valueOf(enterPass.getPassword()));
                if(ad==null){
                    loginResult.setText("登录失败 "+Admin.sqlCode);
                }else{
                    frame.setVisible(false);
                    new MainFrame(ad);
                }
            }
        });

        frame.setVisible(true);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }
}
