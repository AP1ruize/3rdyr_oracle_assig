package GUI;

import elements.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame {
    private User user;
    public MainFrame(User u){
        user=u;
        JFrame frame=new JFrame("图书管理系统管理员端");
        frame.setLocation(100,100);

        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel mPanel=new JPanel(new GridLayout(2,3));
        JButton lending=new JButton("借书");        mPanel.add(lending);
        JButton returning=new JButton("还书");      mPanel.add(returning);
        JButton preorder=new JButton("预约管理");       mPanel.add(preorder);
//        JButton search=new JButton("图书搜索");      mPanel.add(search);
        JButton bookMng=new JButton("图书管理与搜索");     mPanel.add(bookMng);
        JButton readerMng=new JButton("读者管理");   mPanel.add(readerMng);
//        JButton stockMng=new JButton("库存管理");    mPanel.add(stockMng);
        JButton notice=new JButton("公告管理");      mPanel.add(notice);

//        JPanel ntcPanel=new JPanel();
//        ntcPanel.setSize(50,50);
//        JLabel ntcLabel=new JLabel("暂无公告");
//        ntcPanel.add(ntcLabel);

        panel.add(mPanel,BorderLayout.CENTER);
//        panel.add(ntcPanel,BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        lending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog=new LendingPage(null);
            }
        });
        returning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoanedBooks();
            }
        });
//        preorder.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new SearchPage();
//            }
//        });
//        search.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JDialog dialog=new SearchPage();
//            }
//        });
        bookMng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookManagement();
            }
        });
        readerMng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserManagement();
            }
        });
        notice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NoticeManagement(user);
            }
        });
        preorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PreorderManagement();
            }
        });
    }
}
