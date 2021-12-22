package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = Util.getConnection();
            st = con.createStatement();
            st.executeUpdate("create table if not exists Users " +
                    "(id int not null auto_increment, " +
                    "name VARCHAR(30) not null, " +
                    "lastName VARCHAR(30) not null, " +
                    "age int not null, " +
                    "PRIMARY KEY(id));");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public void dropUsersTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = Util.getConnection();
            st = con.createStatement();
            st.executeUpdate("drop table if exists Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Util.getConnection();
            ps = con.prepareStatement(
                    "insert into Users (name, lastName, age) values (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public void removeUserById(long id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Util.getConnection();
            ps = con.prepareStatement("delete from Users where id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public List<User> getAllUsers() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        User user;
        try {
            con = Util.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from Users;");
            while (rs.next()) {
                user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignore) {
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = Util.getConnection();
            st = con.createStatement();
            ;
            st.executeUpdate("truncate table Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }
}