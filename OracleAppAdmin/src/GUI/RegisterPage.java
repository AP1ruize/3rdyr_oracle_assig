package GUI;

import Tools.NumberDocument;
import elements.Reader;
import elements.Rule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JDialog {
    public RegisterPage(){
        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,300);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());
        int textFieldWidth=16;

        JLabel title=new JLabel("用户信息",JLabel.CENTER);
        title.setBackground(new Color(180,255,180));
        container.add(title,BorderLayout.NORTH);


        JPanel cPanel=new JPanel(new GridLayout(3,2));
        JPanel cpRow1=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel cpRow2=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel cpRow3=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel cpRow4=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel cpRow5=new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel cLabel1=new JLabel("昵称：");
        JTextField nickTF=new JTextField();
        nickTF.setColumns(textFieldWidth);
        cpRow1.add(cLabel1);
        cpRow1.add(nickTF);
        cPanel.add(cpRow1);

        JLabel cLabel2=new JLabel("等级：");
        JTextField classTF=new JTextField();
        classTF.setDocument(new NumberDocument());
        classTF.setColumns(textFieldWidth);
        cpRow2.add(cLabel2);
        cpRow2.add(classTF);
        cPanel.add(cpRow2);

        JLabel cLabel3=new JLabel("余额：");
        JTextField blcTF=new JTextField();
        blcTF.setDocument(new NumberDocument());
        blcTF.setColumns(textFieldWidth);
        cpRow3.add(cLabel3);
        cpRow3.add(blcTF);
        cPanel.add(cpRow3);

        JLabel cLabel4=new JLabel("密码：");
        JPasswordField password=new JPasswordField();
        password.setColumns(textFieldWidth);
        cpRow4.add(cLabel4);
        cpRow4.add(password);
        cPanel.add(cpRow4);

        JLabel cLabel5=new JLabel("重复密码：");
        JPasswordField repeatPW=new JPasswordField();
        repeatPW.setColumns(textFieldWidth);
        cpRow5.add(cLabel5);
        cpRow5.add(repeatPW);
        cPanel.add(cpRow5);

        container.add(cPanel,BorderLayout.CENTER);

        JPanel ePanel=new JPanel(new GridLayout(3,1));
        JButton confirm=new JButton("确认注册");
        JButton cancel=new JButton("不保存退出");
        JLabel infoWin=new JLabel();
        ePanel.add(confirm);
        ePanel.add(cancel);
        ePanel.add(infoWin);
        container.add(ePanel,BorderLayout.EAST);

        this.pack();

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Rule.convert_2s(nickTF.getText())==null | Rule.convert_2s(new String(password.getPassword()))==null |
                        Rule.convert_2i(classTF.getText())==null | Rule.convert_2i(blcTF.getText())==null)
                {
                    infoWin.setText("信息不完全");
                    return;
                }
                if(!new String(password.getPassword()).equals(new String(repeatPW.getPassword()))){
                    infoWin.setText("两次密码不一致");
                    return;
                }
                Reader reader=Reader.register(nickTF.getText(),new String(password.getPassword()),
                        Integer.parseInt(classTF.getText()),Integer.parseInt(blcTF.getText()));
                if(reader!=null){
                    infoWin.setText("成功，id:"+reader.getId());
                    confirm.setEnabled(false);
                    return;
                }
                infoWin.setText("出错，错误码"+Reader.sqlCode);
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPage.this.dispose();
            }
        });
    }
}
