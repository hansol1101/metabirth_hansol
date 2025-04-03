package com.metabirth.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.metabirth.model.Admin;


public class AdminDao {
    private final Connection connection;

    public AdminDao(Connection connection) {
        this.connection = connection;
    }
    /*
    관리자 등록
     */
    public boolean addAdmin(Admin admin) {
        // boolean 타입을 리턴하는 메소드를 생성하고 user타입의 user를 매개변수로 받음
        String query = "INSERT INTO admins (email, password, admin_name, level,status ,created_at) VALUES (?, ?, ?, ?,0, now())";
        //addUser의 쿼리를 query에 넣음

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // db하고 연결을 한 객체를 ps에 담은 그리고 key를 부여함
            ps.setString(1, admin.getEmail());
            //get으로 해당 email를  가져오고 set으로 새로씀
            ps.setString(2, admin.getPassword());
            //get으로 해당 password 가져오고 set으로 새로씀
            ps.setString(3, admin.getAdmin_name());
            ps.setInt(4, admin.getLevel());
            int affectedRows = ps.executeUpdate();  //실행
            // 행이 업데이트되면 1 아니면 0 의 값을 변수에 넣음
            return affectedRows > 0;
            // 값이 0이상 즉 업데이트가 진행된 1이면 리턴함

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // 구문이 정상적으로 실행되면 false를 리턴함
    }

    /*
    모든 관리자를 가져옴
     */
    public List<Admin> getAlladmin() {
        // 유저 타입의 리스트를 반환하는 메소드 생성
        List<Admin> admin = new ArrayList<>();
        // 유저 타입의 리스트를 생성함
        String query =  "SELECT admin_id, admin_name ,email, password, created_at FROM admins WHERE status = 0";// XML에서 쿼리 로드
        // xml에서 쿼리를 가져와서 query에 담음
        try (Statement stmt = connection.createStatement();
             // db하고 연결을 한 객체 ps를 생성
             ResultSet rs = stmt.executeQuery(query)) {
            // query에서 실행된 결과를 rs에 담음
            while (rs.next()) {
                // 담긴 값이 다끝날떄까지 해당 while 문을 실행함
                admin.add(new Admin(
                        // 나온 값을 user에 담음
                        rs.getInt("admin_id"),
                        // rs에 userid를 담음
                        rs.getString("admin_name"),
                        rs.getByte("level"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getTimestamp("deleted_at"),
                        // rs에 username 담음
                        rs.getString("email"),
                        // rs에 email 담음
                        rs.getString("password")
                        // rs에 password 담음

                        // rs에 created_at 담음
                ));
            }
        } catch (SQLException e) {
            // sql 오류가 생기면 catch함
            e.printStackTrace();
        }
        return admin;
        // 담긴 값을 다시 반환함 list에 해당 테이블의 모든 user가 들어감
    }


    /*
    로그인에 필요한 이메일과 password를 가져옴
    필요한 관리자 조회
     */
    public Admin login(String email, String password) {
        String query = "SELECT * FROM admins WHERE email = ? AND password = ? AND status = 0";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1,email);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("admin_name"),
                        rs.getByte("level"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getTimestamp("deleted_at"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



    // 관리자 업데이트

    public boolean updateAdmin(Admin admin) {
        // boolean 타입을 리턴하는 메소드를 생성하고 user타입의 user를 매개변수로 받음
        String query = "UPDATE admins SET admin_name = ?, level = ?, email = ?, password = ?, updated_at = NOW() WHERE admin_id = ? AND status = 0";
        //addUser의 쿼리를 query에 넣음

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, admin.getAdmin_name());
            // db하고 연결을 한 객체를 ps에 담음
            ps.setInt(2, admin.getLevel());
            ps.setString(3, admin.getEmail());
            //get으로 해당 email를  가져오고 set으로 새로씀
            ps.setString(4, admin.getPassword());
            //get으로 해당 password 가져오고 set으로 새로씀
            ps.setInt(5, admin.getAdmin_id());
            //get으로 해당 Admin 가져오고 set으로 새로씀
            int affectedRows = ps.executeUpdate();
            // 행이 업데이트되면 1 아니면 0 의 값을 변수에 넣음
            return affectedRows > 0;
            // 값이 0이상 즉 업데이트가 진행된 1이면 리턴함

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // 구문이 정상적으로 실행되면 false를 리턴함
    }

    // 관리자 삭제
    public boolean deleteAdmin(int admin_id) {
        String query = "UPDATE admins SET status = 1, deleted_at = NOW() WHERE admin_id = ? AND status = 0";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, admin_id);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // 업데이트된 행이 1개 이상이면 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    단일 관리자 조회
     */
    public Admin getAdminById(int admin_id) {
        // user 타입을 리턴하는 메소드를 생성하고 매개변수로 userid를 받음
        String query = "SELECT * FROM admins WHERE admin_id = ? AND status = 0";
        // xml에서 쿼리를 가져와서 query에 담음
        Admin admin = null;
        //user를 초기화함

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // query에 있는 sql문을 db에 실행 시킨 객체 ps
            ps.setInt(1, admin_id);
            // 조회를 할 user를 찾음
            ResultSet rs = ps.executeQuery();
            // 연결을 한 쿼리의 실행 결과를 rs에 담음
            if (rs.next()) {
                // user의 id 이름 이메일등을 다 보여줌
                admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("admin_name"),
                        rs.getByte("level"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getTimestamp("deleted_at"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
        // 내가 조회한 admin의 값을 다시 리턴해줌
    }






}
