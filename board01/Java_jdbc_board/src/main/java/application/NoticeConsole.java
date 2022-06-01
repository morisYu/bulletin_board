package application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.NoticeService;
import dto.Notice;

public class NoticeConsole {

	private NoticeService service;
	private int page;
	private String searchWord;
	private String searchField;

	public NoticeConsole() {
		service = new NoticeService();
		page = 1;
		searchWord = "";
		searchField = "";
	}

	// 전체 게시물 목록 출력
	public void printNoticeList() throws ClassNotFoundException, SQLException {

		List<Notice> list = service.getListPage(page);
		int count = service.getCount();
		int lastPage = count % 10 == 0 ? count / 10 : count / 10 + 1;

		System.out.println("───────────────────────────────────────────────");
		System.out.printf("<공지사항> 총 %d 게시글\n", count);
		System.out.println("───────────────────────────────────────────────");

		for (Notice n : list) {
			System.out.printf("%d. %s / %s / %s\n", n.getId(), n.getTitle(), n.getWriterID(), n.getRegdate());
		}
		System.out.println("───────────────────────────────────────────────");
		System.out.printf("            %d/%d pages         \n", page, lastPage);
	}

	// 메뉴 번호 입력
	public int inputNoticeMenu() {

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		System.out.println("1.상세조회/ 2.이전/ 3.다음/ 4.검색");
		System.out.println("5.글쓰기/ 6.수정하기/ 7.삭제하기/ 8.종료");
		System.out.print("메뉴 선택 > ");
		String menu_ = scan.nextLine();
		int menu = Integer.parseInt(menu_);

		return menu;
	}
	
	// 1. 상세 조회
		public void viewContent() throws ClassNotFoundException, SQLException {

			List<Notice> list = service.getListAll();

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

			System.out.print("상세조회 할 id 를 입력하세요 > ");
			int searchId = scanner.nextInt();

			Notice notice = null;
			for (Notice n : list) {
				if (searchId == n.getId()) {
					notice = n;
					break;
				}
			}

			// 삭제된 게시글 id 를 조회할 때 예외 발생함
			try {
			System.out.println("=================================");
			System.out.println("<상세조회> ID : " + notice.getId());
			System.out.println("=================================");
			System.out.println("제목 : " + notice.getTitle());
			System.out.println("작성일자 : " + notice.getRegdate());
			System.out.println("작성자 : " + notice.getWriterID() + " 좋아요 : " + notice.getHit());
			System.out.println("내용");
			System.out.println(notice.getContent());
			} catch(NullPointerException e) {
				System.out.println("해당 게시글이 없습니다.");
				return;
			}
			
			System.out.println();
			System.out.println("1.홈으로 > ");

			while (true) {
				int home = scanner.nextInt();
				if(home == 1) {
					return;
				}
			}
		}

	// 2. 이전 페이지로 이동
	public void movePrevList() {
		if (page == 1) {
			System.out.println("======================");
			System.out.println("[ 이전 페이지가 없습니다. ]");
			System.out.println("======================");

			return;
		}
		page--;
	}

	// 3. 다음 페이지로 이동
	public void moveNextList() throws ClassNotFoundException, SQLException {
		int count = service.getCount();
		int lastPage;
		if(count < 10) {
			lastPage = 1;
		} else {
			lastPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
		}
		
		if (page == lastPage) {
			System.out.println("======================");
			System.out.println("[ 다음 페이지가 없습니다. ]");
			System.out.println("======================");

			return;
		}
		page++;
	}

	// 4. 키워드로 검색
	public void inputSearchWord() throws ClassNotFoundException, SQLException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("검색 범주(title/content/writer_ID)");
		System.out.print("중에 하나를 입력하세요 > ");
		searchField = scan.nextLine();

		System.out.print("검색어 > ");
		searchWord = scan.nextLine();

		List<Notice> list = service.getListSearch(page, searchField, searchWord);
		int count = list.size();
		int lastPage;
		if(count < 10) {
			lastPage = 1;
		} else {
			lastPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
		}
		page = 1;

		while (true) {

			list = service.getListSearch(page, searchField, searchWord);

			System.out.println("───────────────────────────────────────────────");
			System.out.printf("<공지사항> 총 %d 게시글\n", count);
			System.out.println("───────────────────────────────────────────────");

			for (Notice n : list) {
				System.out.printf("%d. %s / %s / %s\n", n.getId(), n.getTitle(), n.getWriterID(), n.getRegdate());
			}
			System.out.println("───────────────────────────────────────────────");
			System.out.printf("            %d/%d pages         \n", page, lastPage);

			System.out.print("1.이전/ 2.다음/ 3.홈 > ");
			String menu_ = scan.nextLine();
			int menu = Integer.parseInt(menu_);

			if (menu == 1) {
				if (page == 1) {
					System.out.println("======================");
					System.out.println("[ 이전 페이지가 없습니다. ]");
					System.out.println("======================");
				} else {
					page--;
				}
			} else if (menu == 2) {
				if (page == lastPage) {
					System.out.println("======================");
					System.out.println("[ 다음 페이지가 없습니다. ]");
					System.out.println("======================");

				} else {
					page++;
				}
			} else if (menu == 3) {
				return;
			}
		}
	}
	
	//5. 글쓰기
	public void write() throws ClassNotFoundException, SQLException {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("");
		System.out.print("title 을 입력하세요 > ");
		String title = scanner.nextLine();
		System.out.print("writer_ID 를 입력하세요 > ");
		String writer_ID = scanner.nextLine();
		System.out.print("content 를 입력하세요 > ");
		String content = scanner.nextLine();
		System.out.print("files 를 입력하세요 > ");
		String files = scanner.nextLine();
		System.out.println();
		
		int result = service.insert(title, writer_ID, content, files);
		System.out.println(result + " 개의 글이 추가되었습니다.");
	}

	//6. 수정하기
	public void revise() throws ClassNotFoundException, SQLException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("수정할 id 를 입력하세요> ");
		int update_id = scanner.nextInt();
		System.out.print("title 을 입력하세요> ");
		String title = scanner.next();
		System.out.print("content 를 입력하세요> ");
		String content = scanner.next();
		System.out.print("files 를 입력하세요> ");
		String files = scanner.next();
		System.out.println();
		
		// 입력한 id 가 총 게시글의 숫자보다 클 경우
		if(update_id > service.getCount()) {
			System.out.println("!!! 게시글 id 가 잘못 입력되었습니다.");
			return;
		}
		
		int result = service.update(title, content, files, update_id);
		System.out.println(result + " 개의 글이 수정되었습니다.");
	}
	
	//7. 삭제하기
	public void remove() throws ClassNotFoundException, SQLException {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("삭제할 id 를 입력하세요> ");
		int delete_id = scanner.nextInt();
		
		// 입력한 id 가 총 게시글의 숫자보다 클 경우
		if(delete_id > service.getCount()) {
			System.out.println("!!! 게시글 id 가 잘못 입력되었습니다.");
			return;
		}
		
		int result = service.delete(delete_id);
		System.out.println(result + " 개의 글이 삭제되었습니다.");
	}
}
