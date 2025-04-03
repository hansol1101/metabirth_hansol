package com.metabirth.dao;


import com.metabirth.model.Notices;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
논리적 삭제
0 = 값이 있는 상태
1 = 삭제 상태

모든 메소드에서 0만 사용함
 */



public class NoticesDao {  private final Connection connection;

    public NoticesDao(Connection connection) {
        this.connection = connection;
    }

    /*
    단일 공지사항 조회
     */

    public Notices getNoticeById(int noticeId) throws SQLException {
        String query = "SELECT * FROM notices WHERE notice_id = ? AND status = 0";
        // 입력받은 한 공지사랑을 가져오는 쿼리 실행
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, noticeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Notices(
                        rs.getInt("notice_id"),
                        rs.getInt("admin_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getTimestamp("deleted_at")
                );
            }
        }
        return null; // 해당 ID의 공지사항이 없을 경우 null 반환
    }

    /*
    모든 공지사항 조회
    */

    public List<Notices> getAllNotices() {
        List<Notices> notices = new ArrayList<>();
        String query =  "SELECT * FROM notices WHERE status = 0";
        //입력 받은 모든 notice를 가져오는 쿼리 실행

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                notices.add(new Notices(
                        rs.getInt("notice_id"),
                        rs.getInt("admin_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getTimestamp("deleted_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notices;
    }

    /*
    공지사항 추가
    */
    public boolean addNotice(Notices notice) {
        String query = "INSERT INTO notices (admin_id, title, content, status) " +
                "VALUES (?, ?, ?, ?)";
        // 입력 받은 값들을 db에 작성하는 쿼리실행

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, notice.getAdmin_id());
            ps.setString(2, notice.getTitle());
            ps.setString(3, notice.getContent());
            ps.setByte(4, notice.getStatus());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;// 업데이트가 1개 이상이면 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    공지사항 수정
     */
    public boolean updateNotice(Notices notice) {
        String query = "UPDATE notices SET title = ?, content = ?, status = ?, updated_at = NOW() WHERE notice_id = ? AND status = 0";
        // 입력받은 값들을 덮어쓰는 쿼리 실행
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, notice.getTitle());
            ps.setString(2, notice.getContent());
            ps.setByte(3, notice.getStatus());
            ps.setInt(4, notice.getNotice_id());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;// 업데이트가 1개 이상이면 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // 수정 실패 시 false 반환
    }

    /*
    공지사항 삭제 논리적 삭제(status 변경)
    */
    public  boolean deleteNotice(int noticeId) {
        String query = "UPDATE notices SET status = 1, deleted_at = NOW() WHERE notice_id = ? AND status = 0";
        // 입력 받은 데이터의 status값을 1로 변경하는 쿼리
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, noticeId);
            int affectedRows = stmt.executeUpdate(); // 변경된 행 개수 반환

            return affectedRows > 0; // 업데이트가 1개 이상이면 true 반환
        } catch (SQLException e) {
            System.err.println("❌ 공지사항 삭제 중 오류 발생: " + e.getMessage());
        }
        return false; // 삭제 실패 시 false 반환
    }
}