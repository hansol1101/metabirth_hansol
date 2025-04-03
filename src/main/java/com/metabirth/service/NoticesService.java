package com.metabirth.service;

import com.metabirth.dao.NoticesDao;
import com.metabirth.model.Notices;
import com.mysql.cj.protocol.x.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class NoticesService {
    private static final Logger log = LoggerFactory.getLogger(NoticesService.class);

    private final NoticesDao noticeDAO;
    private final Connection connection;

    public NoticesService(Connection connection) {
        this.noticeDAO = new NoticesDao(connection);
        this.connection = connection;
    }


    /*
    모든 공지사항 조회
     */
    public List<Notices> getAllNotices() throws SQLException {
        // user객체를 담은 리스트를 반환하는 메소드를 생성하고 해당 메서드를 호출하는 쪽에서 SQLException 오류를 처리하도록함
        List<Notices> notices = noticeDAO.getAllNotices();
        // userDao에 있는 해당 메소드를 사용해서 모든 사용자를 users에 담음
        if(notices == null) {
            // 담은 users에 갚이 없다면 해당 오류 로그를 보여줌
            log.error("조회한 공지사항의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            // log.error는 해당 메세지만 알려주고 프로그램은 계속 진행됨 즉 null이 반환됨
            return null;
        }

        return noticeDAO.getAllNotices();
        //값이 있다면 다시 값을 반환함
    }

    /*
    특정 제목을 가진 nocices조회
    주어진 제목을 기준으로 강의 조회
     */
    public Notices getNoticeById(int noticeId) throws SQLException {
        // dao에 보내서 값을 받아오고 null인지 검증을 진행함
        Notices notice = noticeDAO.getNoticeById(noticeId);

        if (notice == null) {
            throw new IllegalArgumentException("해당 ID의 공지사항을 찾을 수 없습니다.");
        }

        return notice;
    }

    /*
    공지사항 등록 제목이 같을 수 있기떄문에 중복검사는 하지 않음
     */
    public boolean addNotice(Notices notice) throws SQLException {
        // 공지사항 등록을 위해 DAO에 보내고 false를 리턴 받으면 오류를 발생시킴
        boolean result = noticeDAO.addNotice(notice);

        if (!result) {
            throw new SQLException("공지사항 등록 중 오류가 발생했습니다.");
        }

        return true;
    }


    /*
    공지사항 업데이트
     */
        public boolean updateNotices (Notices notices) throws SQLException {
            //기존 공지사항 존재 여부 확인을 위해 받아옴
            Notices existingNotice = getNoticeById(notices.getNotice_id());

            // 값이 없으면 오류발생
            if (existingNotice == null) {
                throw new IllegalArgumentException("수정할 공지사항을 찾을 수 없습니다.");
            }

            //값이 있다면 업데이트 수행
            boolean result = noticeDAO.updateNotice(notices);

            if (!result) {
                throw new SQLException("수정하는 과정에서 오류가 발생했습니다.");
            }

            return true;
        }

    /*
    공지사항 삭제
     */



    public boolean deleteNotice(int noticeId) throws SQLException {
        // 입력한 id에 맞는 공지사항을 찾기위해 getnoticebyid호출 값이 없다면 오류
        Notices existingNotice = getNoticeById(noticeId);
            if (existingNotice == null) {
                throw new IllegalArgumentException("삭제할 공지사항를 찾을 수 없습니다.");
            }
            // 값이 있다면 dao에 보내서 삭제후 리턴
            return noticeDAO.deleteNotice(noticeId);
        }





}

