package GUI;

import elements.Notice;
import elements.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class NewNotice extends JDialog {
    private Notice notice;
    public NewNotice(User admin){
        notice=new Notice(admin.getId(),new Timestamp(System.currentTimeMillis()),"","",1);

        this.setVisible(true);
        this.setModal(true);
        this.setLocation(250,200);

        Container container=this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel nPanel=new JPanel(new GridLayout(2,1));
        container.add(nPanel,BorderLayout.NORTH);
        JLabel title=new JLabel("公告详情",JLabel.CENTER);
        nPanel.add(title);
        JPanel snPanel=new JPanel(new GridLayout(1,6));
        nPanel.add(snPanel);
        JLabel aidLb=new JLabel("管理员id");
        JTextField aidTF=new JTextField(((Integer)notice.getAdminID()).toString());
//        aidTF.setDocument(new NumberDocument());
        aidTF.setEditable(false);
        JLabel timeLb=new JLabel("上次编辑时间");
        JTextField timeTF=new JTextField(notice.getTimestamp().toString());
        timeTF.setEditable(false);
        JLabel statusLb=new JLabel("是否发布");
        JComboBox<String> statusCB=new JComboBox<>(new String[]{"true","false"});
        statusCB.setSelectedIndex((notice.getState()+1)%2);
        snPanel.add(aidLb);
        snPanel.add(aidTF);
        snPanel.add(timeLb);
        snPanel.add(timeTF);
        snPanel.add(statusLb);
        snPanel.add(statusCB);

        JPanel cPanel=new JPanel(new BorderLayout());
        container.add(cPanel,BorderLayout.CENTER);
        JTextField ntcTitle=new JTextField(notice.getTitle());
        ntcTitle.setToolTipText("在此处填写标题");
        cPanel.add(ntcTitle,BorderLayout.NORTH);
        JTextArea text=new JTextArea(notice.getText());
        text.setRows(10);
        JScrollPane jsp=new JScrollPane(text);
        text.setToolTipText("在此处编辑正文");
        cPanel.add(jsp,BorderLayout.CENTER);

        JPanel sPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        container.add(sPanel,BorderLayout.SOUTH);
        JLabel infoWin=new JLabel();
        JButton publish=new JButton("确认发布");
        JButton save=new JButton("保存更改但不发布");
        JButton cancel=new JButton("退出(不保存)");
        sPanel.add(infoWin);
        sPanel.add(publish);
        sPanel.add(save);
        sPanel.add(cancel);

        this.pack();

        publish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice.setState(true);
                String titleContent=ntcTitle.getText();
                if(titleContent.equals(""))
                    titleContent="Default title";
                notice.setTitle(titleContent);
                notice.setText(text.getText());
                statusCB.setSelectedIndex((notice.getState()+1)%2);
                if(Notice.newNotice(notice)){
                    infoWin.setText("发布成功");
                    publish.setEnabled(false);
                    save.setEnabled(false);
                    return;
                }
                infoWin.setText("发布失败，错误码"+Notice.sqlCode);
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice.setState(false);
                String titleContent=ntcTitle.getText();
                if(titleContent.equals(""))
                    titleContent="Default title";
                notice.setTitle(titleContent);
                notice.setText(text.getText());
                statusCB.setSelectedIndex((notice.getState()+1)%2);
                if(Notice.newNotice(notice)){
                    infoWin.setText("保存成功");
                    publish.setEnabled(false);
                    save.setEnabled(false);
                    return;
                }
                infoWin.setText("保存失败，错误码"+Notice.sqlCode);
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewNotice.this.dispose();
            }
        });
    }
}
