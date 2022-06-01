package application;

import java.sql.SQLException;

public class UserApp {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		NoticeConsole console = new NoticeConsole();

		EXIT:
		while (true) {
			console.printNoticeList();
			int menu = console.inputNoticeMenu();
			
			switch (menu) {
			// 상세조회
			case 1:
				console.viewContent();
				break;
			// 이전
			case 2:
				console.movePrevList();
				break;
			// 다음
			case 3:
				console.moveNextList();
				break;
			// 검색
			case 4:
				console.inputSearchWord();
				break;
			// 글쓰기
			case 5:
				console.write();
				break;
			// 수정하기
			case 6:
				console.revise();
				break;
			// 삭제하기
			case 7:
				console.remove();
				break;
			// 종료
			case 8:
				System.out.println("bye~~");
				break EXIT;
			default:
				System.out.println("메뉴는 1~4까지만 입력할 수 있습니다.");
				break;
			}
		}
	}

}
