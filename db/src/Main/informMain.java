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
			System.out.println("1.��ü �������� ��ȸ 2.���� �������� ��ȸ 3.������ ���� �α��� 0.����");
			String sel = sc.nextLine();
			if (sel.equals("1")) {

				db.getList();

			} else if (sel.equals("2")) {

				db.find();

			} else if (sel.equals("3")) {

				System.out.println("id�� �Է��ϼ���");
				String id = sc.nextLine();

				System.out.println("pw�� �Է��ϼ���");
				String pw = sc.nextLine();

				if (vo.login(id, pw) == 1) {
					System.out.println("������ �Խ��� �ý��� ����");
					while (true) {
						System.out.println("1.���� ��� 2.���� ���� 3.���� ���� 4.���� Ȯ�� 0.����");
						String sel2 = sc.nextLine();
						if (sel2.equals("1")) {
							db.insert();

							while (true) {
								System.out.println("��� ����ϽǷ���? y/n");
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
