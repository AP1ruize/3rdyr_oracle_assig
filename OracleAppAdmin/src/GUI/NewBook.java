package GUI;

import Tools.NumberDocument;
import elements.Book;
import elements.Rule;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class NewBook extends JDialog {
    protected ImageIcon icon;
    protected JLabel picLabel;
    protected FileInputStream is;
    protected String iconPath;

    public NewBook(){
        icon=null;
        is=null;
        iconPath=null;

        this.setVisible(true);
        this.setModal(false);
        this.setLocation(300,300);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());
        int textFieldWidth=16;

        JLabel title=new JLabel("新建图书",JLabel.CENTER);
        title.setBackground(new Color(180,255,180));
        container.add(title,BorderLayout.NORTH);

        JPanel wPanel=new JPanel(new BorderLayout());
        picLabel=new JLabel();
        picLabel.setSize(Rule.showImageSize,Rule.showImageSize);
//        icon=new ImageIcon();
//        icon.setImage(icon.getImage().getScaledInstance(Rule.showImageSize,Rule.showImageSize,Image.SCALE_FAST));
        picLabel.setText("No Pic.");
        picLabel.setHorizontalAlignment(JLabel.CENTER);

        wPanel.add(picLabel,BorderLayout.CENTER);
        JButton setPic=new JButton("设置图片");
        wPanel.add(setPic,BorderLayout.SOUTH);
        container.add(wPanel,BorderLayout.WEST);

        JPanel cmPanel=new JPanel(new BorderLayout());
        container.add(cmPanel,BorderLayout.CENTER);
        JPanel cPanel=new JPanel(new GridLayout(6,1));
        cmPanel.add(cPanel,BorderLayout.CENTER);
        JPanel cP1=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP1);
        JPanel cP2=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP2);
        JPanel cP3=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP3);
        JPanel cP4=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP4);
        JPanel cP7=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP7);
//        JPanel cP8=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP8);
        JPanel cP5=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP5);
//        JPanel cP6=new JPanel(new FlowLayout(FlowLayout.LEFT));   cPanel.add(cP6);

        JLabel nameLb=new JLabel("书名：");
        JTextField nameTF=new JTextField();
        nameTF.setColumns(textFieldWidth);
        cP1.add(nameLb);  cP1.add(nameTF);

        JLabel pressLb=new JLabel("出版社:");
        JTextField pressTF=new JTextField();
        pressTF.setColumns(textFieldWidth);
        cP2.add(pressLb);  cP2.add(pressTF);

        JLabel authorLb=new JLabel("作者：");
        JTextField authorTF=new JTextField();
        authorTF.setColumns(textFieldWidth);
        cP3.add(authorLb);  cP3.add(authorTF);

        JLabel typeLb=new JLabel("类型：");
        JTextField typeTF=new JTextField();
        typeTF.setColumns(textFieldWidth);
        cP4.add(typeLb);  cP4.add(typeTF);

        JLabel amtLb=new JLabel("总数量:");
        JTextField amtTF=new JTextField();
        amtTF.setDocument(new NumberDocument());
        amtTF.setColumns(textFieldWidth);
        cP7.add(amtLb);  cP7.add(amtTF);

        JLabel introLb=new JLabel("简介：");
        cP5.add(introLb);
        JTextArea introTA=new JTextArea();
        introTA.setColumns(20);
        introTA.setRows(5);
        JScrollPane introSP=new JScrollPane(introTA);
//        cP6.add(introSP);
        cmPanel.add(introSP,BorderLayout.SOUTH);

        //cPanel end

        JPanel ePanel=new JPanel(new GridLayout(8,1));
        container.add(ePanel,BorderLayout.EAST);
        JLabel infoWin=new JLabel("");
        JButton confirm=new JButton("确认添加");
        JButton cancel=new JButton("取消并退出");
        ePanel.add(infoWin);
        ePanel.add(confirm);
        ePanel.add(cancel);

        this.pack();

        setPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setMultiSelectionEnabled(false);
                jfc.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.bmp, *.jpeg)",
                        "jpg", "png", "bmp", "jpeg"));
                int result=jfc.showOpenDialog(setPic);
                if(result==JFileChooser.APPROVE_OPTION){
                    File file=jfc.getSelectedFile();
                    iconPath=file.getAbsolutePath();
                    try {
                        is=new FileInputStream(file);
                        icon =new ImageIcon(is.readAllBytes());
                        icon.setImage(icon.getImage().getScaledInstance(Rule.showImageSize,Rule.showImageSize,Image.SCALE_DEFAULT));
                        picLabel.setIcon(icon);
                        picLabel.updateUI();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                        infoWin.setText("找不到文件");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        infoWin.setText("发生io错误");
                    }
                }
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Book.newBook(nameTF.getText(),pressTF.getText(), authorTF.getText(),
                        typeTF.getText(),introTA.getText(),Integer.parseInt(amtTF.getText()),iconPath)){
                    infoWin.setText("保存成功");
                    confirm.setEnabled(false);
                }
                else{
                    if(Book.sqlCode==0) {
                        infoWin.setText("此书已被添加");
                        confirm.setEnabled(false);
                    }
                    else infoWin.setText("发生错误，错误码"+Book.sqlCode);
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    is.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                finally {
                    NewBook.this.dispose();
                }
            }
        });
    }
}
