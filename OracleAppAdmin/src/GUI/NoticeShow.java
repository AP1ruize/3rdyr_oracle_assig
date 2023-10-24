package GUI;

import Tools.NumberDocument;
import elements.Notice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class NoticeShow extends JDialog {
    private Notice notice;
    public NoticeShow(Integer aid, Timestamp ts){
        notice=Notice.getNotice(aid,ts);
        if(notice==null)
            return;

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
        JLabel statusLb=new JLabel("是否在展示中");
        JComboBox<String> statusCB=new JComboBox<>(new String[]{"true","false"});
        statusCB.setSelectedIndex(0);
        if(notice.getState()==0)
            statusCB.setSelectedIndex(1);
        statusCB.setEnabled(false);
        snPanel.add(aidLb);
        snPanel.add(aidTF);
        snPanel.add(timeLb);
        snPanel.add(timeTF);
        snPanel.add(statusLb);
        snPanel.add(statusCB);

        JPanel cPanel=new JPanel(new BorderLayout());
        container.add(cPanel,BorderLayout.CENTER);
        JTextField ntcTitle=new JTextField(notice.getTitle());
        ntcTitle.setEditable(false);
        cPanel.add(ntcTitle,BorderLayout.NORTH);
        JTextArea text=new JTextArea(notice.getText());
        text.setRows(10);
        JScrollPane jsp=new JScrollPane(text);
        text.setEditable(false);
        cPanel.add(jsp,BorderLayout.CENTER);

        JPanel sPanel=new JPanel(new GridLayout(2,1));
        container.add(sPanel,BorderLayout.SOUTH);
        JPanel nsPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sPanel.add(nsPanel);
        JLabel infoWin=new JLabel();
        nsPanel.add(infoWin);
        JButton edit=new JButton("编辑");
        JButton delete=new JButton("删除");
        JButton showOff=new JButton("展示/撤回");
        nsPanel.add(edit);
        nsPanel.add(delete);
        nsPanel.add(showOff);
        JPanel ssPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sPanel.add(ssPanel);
        JButton confirm=new JButton("确认更改");
        confirm.setEnabled(false);
        JButton cancel=new JButton("取消更改");
        cancel.setEnabled(false);
        ssPanel.add(confirm);
        ssPanel.add(cancel);

        this.pack();

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                aidTF.setEditable(true);
//                aidTF.setDocument(new NumberDocument());
//                timeTF.setEditable(true);
                statusCB.setEnabled(true);
                ntcTitle.setEditable(true);
                text.setEditable(true);
                edit.setEnabled(false);
                delete.setEnabled(false);
                showOff.setEnabled(false);
                confirm.setEnabled(true);
                cancel.setEnabled(true);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Notice.deleteNotice(notice.getAdminID(),notice.getTimestamp())){
                    infoWin.setText("删除成功");
                    edit.setEnabled(false);
                    showOff.setEnabled(false);
                }
                else infoWin.setText("删除失败");
            }
        });
        showOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String curSta="true";
                if(notice.getState()==0)
                    curSta="false";
                if(Notice.showNotice(notice.getAdminID(),notice.getTimestamp(),curSta)){
                    infoWin.setText("展示状态更改成功");
                    statusCB.setSelectedIndex(notice.getState());
                    if(curSta.equals("true"))
                        notice.setState(false);
                    else notice.setState(true);
                }
                else infoWin.setText("展示状态更改失败");
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice.setState((statusCB.getSelectedIndex()+1)%2);
                notice.setTitle(ntcTitle.getText());
                notice.setText(text.getText());
                if(Notice.update(notice)){
                    infoWin.setText("修改成功");
                }else{
                    notice=Notice.getNotice(aid,ts);
                    statusCB.setSelectedIndex((notice.getState()+1)%2);
                    ntcTitle.setText(notice.getTitle());
                    text.setText(notice.getText());
                }
                aidTF.setEditable(false);
                statusCB.setEnabled(false);
                ntcTitle.setEditable(false);
                text.setEditable(false);
                edit.setEnabled(true);
                delete.setEnabled(true);
                showOff.setEnabled(true);
                confirm.setEnabled(false);
                cancel.setEnabled(false);
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aidTF.setEditable(false);
                aidTF.setText(((Integer)notice.getAdminID()).toString());
                statusCB.setEnabled(false);
                statusCB.setSelectedIndex((notice.getState()+1)%2);
                ntcTitle.setEditable(false);
                ntcTitle.setText(notice.getTitle());
                text.setEditable(false);
                text.setText(notice.getText());
                edit.setEnabled(true);
                delete.setEnabled(true);
                showOff.setEnabled(true);
                confirm.setEnabled(false);
                cancel.setEnabled(false);
            }
        });
    }

}
