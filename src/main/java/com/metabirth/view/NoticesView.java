package com.metabirth.view;

import com.metabirth.model.Notices;
import com.metabirth.service.NoticesService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;



public class NoticesView {
    private final NoticesService noticesService;
    private final Scanner scanner;
    private final int admin_id;


    public NoticesView(Connection connection, int admin_id) {
        this.noticesService = new NoticesService(connection);
        this.admin_id = admin_id;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(){
        while(true){
            System.out.println("=== 공지사항 관리 ===");
            System.out.println("1. 공지사항 조회");
            System.out.println("2. 공지사항 추가");
            System.out.println("3. 공지사항 수정");
            System.out.println("4. 공지사항 삭제");
            System.out.println("0. 로그아웃");
            System.out.println("선택 : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1 -> showNotices();
                case 2 -> registerNotice();
                case 3 -> updateNotice();
                case 4 -> deleteNotices();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요");
            }
        }
    }

    /*
    모든 공지사항 조회
     */
    public void showNotices(){
        try {
            List<Notices> notices = noticesService.getAllNotices();
            //getallnotices를 실행시켜서 모든 공지사항들을 리스트 형식의 notices에 담음

            if (notices.isEmpty()) {
                System.out.println("📌 조회된 공지사항이 없습니다.");
                // 담은 notices에 값이 없다면 해당 코드 실행
            } else {
                System.out.println("\n📌 공지사항 목록:");
                for (Notices notice : notices) {
                    System.out.println(notice);
                    //리스트에 값이 있다면 해당값들 전부 출력
                }
            }
        } catch (SQLException e) {
            System.out.println("🚨 강의 목록 조회 중 오류 발생: " + e.getMessage());
            //sql 오류시 해당 코드 실행
        }

    }
    /*
    공지사항 추가
     */
    public void registerNotice() {
        Scanner scanner = new Scanner(System.in);
        // 사용자에게 입력을 받을 객체 생성


        System.out.print("공지사항 제목을 입력하세요: ");
        String title = scanner.nextLine();
        // 제목 입력


        System.out.print("공지사항 내용을 입력하세요: ");
        String content = scanner.nextLine();
        // 내용 입력

        // Notices 객체 생성
        Notices notice = new Notices(0, admin_id, title, content, (byte) 0, null, null, null);
        //입력값을 담을  Notices 객체 성성
        try {

            boolean success = noticesService.addNotice(notice);
            // 공지사항 등록 서비스 호출후 입력값을 담은 객체를 매개변수로 보냄
            if (success) {
                System.out.println("공지사항이 성공적으로 등록되었습니다.");
                //가져온 값이 true라면 해당 코드 실행
            } else {
                System.out.println("공지사항 등록에 실패하였습니다.");
                //가져온 값이 false라면 해당 코드실행
            }
        } catch (SQLException e) {
            System.out.println("공지사항 등록 중 오류가 발생했습니다.");
            //sql 오류 발생시 해당 코드실행
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // 잘못된 인수가 전달되면 해당 코드 실행
        }
    }

    /*
    공지사항 수정
     */
    private void updateNotice() {
        System.out.print("수정할 공지사항 ID를 입력하세요: ");
        int noticeId = scanner.nextInt();
        // 수정할 공지사항 id 입력받음
        scanner.nextLine(); // 개행 문자 처리
        System.out.print("새로운 제목을 입력하세요: ");
        String title = scanner.nextLine();
        // 수정할 공지사항 제목 입력받음
        System.out.print("새로운 내용을 입력하세요: ");
        String content = scanner.nextLine();
        // 수정할 공지사항 내용 입력받음


        Notices notice = new Notices(
                noticeId,                      // notice_id
                admin_id,                       // admin_id
                title,                          // title
                content,                        // content
                (byte) 0,                         // status
                null,                           // created_at
                null,                           // updated_at (DB에서 자동 처리)
                null                            // deleted_at
        );
        // 입력한 내용들을 담을 객체 생성
        try {
            boolean success = noticesService.updateNotices(notice);
            //만든 객체를 updatenotices에 매개변수로 보냄
            if (success) {
                System.out.println("공지사항이 성공적으로 수정되었습니다.");
                //가져온 값이 true라면 해당 코드 실행
            } else {
                System.out.println("공지사항 수정에 실패하였습니다.");
                //가져온 값이 false면 해당 코드 실행
            }
        } catch (SQLException e) {
            System.out.println("공지사항 수정 중 오류가 발생했습니다.");
            //sql 오류 발생시 해당 코드실행
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // 잘못된 인수가 전달되면 해당 코드 실행
        }
    }

    /*

    공지사항 삭제
     */

    private void deleteNotices() {
        System.out.print("삭제할 공지사항 ID를 입력하세요: ");
        int noticeId = scanner.nextInt();
        // 삭제할 공지사항 id 입력받음
        scanner.nextLine(); // 개행 문자 처리

        try {
            boolean success = noticesService.deleteNotice(noticeId);
            // deletenotice에 입력받은 id를 매개변수로 보냄
            if (success) {
                System.out.println("공지사항이 성공적으로 삭제되었습니다.");
                //가져온 값이 true라면 해당 코드 실행
            } else {
                System.out.println("공지사항 삭제에 실패하였습니다.");
                //가져온 값이 false면 해당 코드 실행
            }
        } catch (SQLException e) {
            System.out.println("공지사항 삭제 중 오류가 발생했습니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // 잘못된 인수가 전달되면 해당 코드 실행
        }
    }





}
