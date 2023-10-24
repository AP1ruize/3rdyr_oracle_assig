package elements;

import java.sql.*;

public class Reader extends User {
    private int exp;      //���
    private int credit;   //�˻����
    private int level;    

   
    
	public int getExp() {
		return exp;
	}
	
	public int getCredit() {
		return credit;
	}

	public int getLevel() {
		return level;
	}

	public int getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}
	
	public Reader() {}
	public String test() {
		return "555";
	}
	public String getpass(int id) {           //���ԭ����,security.jsp����
		String pass = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "select PASSWORD from USERINFO where USERID=?"; // SQL���
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				pass=rs.getString("PASSWORD");
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pass;
	}
	public void updatepass(int id,String pass){                   //��������,security.jsp����
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "UPDATE USERINFO SET PASSWORD=? WHERE USERID=?"; // SQL���
			stmt = conn.prepareStatement(sql);
			stmt.setString (1,pass);
			stmt.setInt (2,id);
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public Reader getUserInfo(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "select * from USERINFO where USERID=?"; // SQL���
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				this.id=id;
				this.nickname=rs.getString("nickname");
				exp=rs.getInt("exp");
				credit=rs.getInt("credit");
				return this;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			// �ͷ����ݼ�����
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			// �ͷ�������
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}
	public Reader(int _id,String pwd,String nick,int _exp,int cre){
        super();
        id=_id; password=pwd; nickname=nick; exp=_exp; credit=cre; level=Rule.getLevel(exp);
    }

    public static int sqlCode=0;//Ϊ0��û�д���
    public static Reader login(String nick_or_id,String pw)
    //����Reader������ȷ��¼ ����null�˻��������������ݿ���� �������ԭ���sqlCode
    {
        sqlCode=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Reader reader=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        Integer input_id=0;
        try{
            input_id=Integer.parseInt(nick_or_id);
            stmt="select userid,password,nickname,exp,credit from userinfo where admin = 0 and userid=? and password=?";
        } catch (NumberFormatException e) {
            input_id = -1;
            stmt="select userid,password,nickname,exp,credit from userinfo where admin = 0 and nickname=? and password=?";
        }

        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            if(input_id==-1){
                pstmt.setString(1,nick_or_id);
            }else{
                pstmt.setInt(1,input_id.intValue());
            }
            pstmt.setString(2,pw);
            rs=pstmt.executeQuery();
            if(rs.next())
                return null;
            else{
                reader=new Reader(rs.getInt("userid"),rs.getString("password"),
                        rs.getString("nickname"),rs.getInt("exp"),
                        rs.getInt("credit"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return reader;
    }
    public static int nextUserID()//����-1����
    {
        sqlCode=0;
        Connection conn=null;
        String sql;
        ResultSet rs=null;
        int result=-1;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return -1;
        }
        sql="select count(userid) from userinfo";
        try {
            Statement stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next())
                result=rs.getInt(1);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return -1;
        }
        return result+1;
    }
    public static Reader register(String nickname,String password)
    //����Reader������ȷ��¼ ����null�˻��������������ݿ���� �������ԭ���sqlCode
    {
        sqlCode=0;
        int userID=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Reader reader=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        stmt="insert into userinfo (password,nickname,exp,credit,admin) " +
                "values ( ?, ?, ?, ?, 0)";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setString(1,password);
            pstmt.setString(2,nickname);
            pstmt.setInt(3,Rule.initExp());
            pstmt.setInt(4,Rule.initCredit());
            pstmt.executeUpdate();
            stmt="select userid from userinfo where nickname = ? and password = ?";
            pstmt=conn.prepareStatement(stmt);
            pstmt.setString(2,password);
            pstmt.setString(1,nickname);
            rs=pstmt.executeQuery();
            if(rs.next())
                userID=rs.getInt("userid");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return new Reader(userID,password,nickname,Rule.initExp(),Rule.initCredit());
    }
    

}
