package Main;

import java.util.Scanner;

import DBManager.InformDbManager;
import DBVO.InformVO;

public class informMain {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		InformVO vo = new InformVO();
		InformDbManager db = new InformDbManager();

		while (true) {
			System.out.println("1.전체 공지사항 조회 2.개별 공지사항 조회 3.관리자 계정 로그인 0.종료");
			String sel = sc.nextLine();
			if (sel.equals("1")) {

				db.getList();

			} else if (sel.equals("2")) {

				db.find();

			} else if (sel.equals("3")) {

				System.out.println("id를 입력하세요");
				String id = sc.nextLine();

				System.out.println("pw를 입력하세요");
				String pw = sc.nextLine();

				if (vo.login(id, pw) == 1) {
					System.out.println("관리자 게시판 시스템 시작");
					while (true) {
						System.out.println("1.공지 등록 2.공지 수정 3.공지 삭제 4.공지 확인 0.종료");
						String sel2 = sc.nextLine();
						if (sel2.equals("1")) {
							db.insert();

							while (true) {
								System.out.println("계속 등록하실래여? y/n");
								String sel3 = sc.nextLine();
								if (sel3.equals("n")) {
									break;
								} else if (sel3.equals("y")) {
									db.insert();

								}
							}
						} else if (sel2.equals("2")) {

							db.update();

						} else if (sel2.equals("3")) {

							db.delete();
						} else if (sel2.equals("4")) {
							db.check();

						} else if (sel2.equals("0")) {
							break;
						}
					}

				}
			} else if (sel.equals("0")) {
				return;
			}
		}

	}

}
