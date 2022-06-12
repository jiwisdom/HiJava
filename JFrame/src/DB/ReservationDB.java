package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import VO.ReservationChangeVO;
import VO.ReservationVO;

public class ReservationDB {
	Scanner sc = new Scanner(System.in);
	private final String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private final String id = "green01";
	private final String pw = "1234";
	

	// �α���/ȸ������
	public void All() throws Exception {
		System.out.println("1.�α��� 2.ȸ������");
		String sel = sc.nextLine();
		if (sel.equals("1")) {
			login();
		} else if (sel.equals("2")) {
			regist();
			login();

		}
	}

	// ȸ������
	public void regist() throws Exception {
		String query = "insert into inform values(?,?,?,?)";
		PreparedStatement pstmt = dbCon().prepareStatement(query);

		System.out.print("�̸�>>");
		String name = sc.nextLine();
		System.out.print("����ó>>");
		String phone = sc.nextLine();
		System.out.print("id>>");
		String id = sc.nextLine();
				
		//id �ߺ� �ȵǰ� ��
		String sameId = "select count(*) from inform where id= '"+id+"'";
		Statement stmt = dbCon().createStatement();		
		ResultSet  rs2 = stmt.executeQuery(sameId);
		
		while(rs2.next()) {
			
			
			if(rs2.getString(1).equals("0")) {
				
				System.out.print("pw>>");
				String pw = sc.nextLine();

				pstmt.setString(1, name);
				pstmt.setString(2, phone);
				pstmt.setString(3, id);
				pstmt.setString(4, pw);

				int rs = pstmt.executeUpdate();

				if (rs == 1) {
					System.out.println(name + "�� ���ԵǼ̽��ϴ�\n�α��� ȭ������ �̵��մϴ�");

				}
								
			}else {
				System.out.println("�����ϴ� id�Դϴ�");
				System.out.print("id>>");
				String id2 = sc.nextLine();
				System.out.print("pw>>");
				String pw = sc.nextLine();
				
				pstmt.setString(1, name);
				pstmt.setString(2, phone);
				pstmt.setString(3, id2);
				pstmt.setString(4, pw);

				int rs = pstmt.executeUpdate();

				if (rs == 1) {
					System.out.println(name + "�� ���ԵǼ̽��ϴ�\n�α��� ȭ������ �̵��մϴ�");

				}
			}
		}
				
		
		dbCon().close();
	}

	// �α���>>������ / ����� ������
	public void login() throws Exception {
		
		System.out.print("id :");
		String id = sc.nextLine();
		System.out.print("pw :");
		String pw = sc.nextLine();

		String query = "select func_login(?,?) from dual ";
		PreparedStatement pstmt = dbCon().prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getString(1).equals("admin")) {

				System.out.println("������ �����Դϴ�\n");
				forManager();

			} else {
				query = "select name from inform where id=?";
				pstmt = dbCon().prepareStatement(query);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString(1) + "�� �α��� �Ǽ̽��ϴ�\n");
				}
				
				forUser();

			}
		}
		dbCon().close();
	}
	
	public Connection dbCon() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection(url, id, pw);
						
			return conn;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
		
	// ������ 
		
	public void forManager() throws Exception {

		while (true) {
			System.out.println("1.��ü ���� ��ȸ 2.���� ���� ��ȸ 3.���� ���� Ȯ�� 0.������");
			String sel = sc.nextLine();
			if (sel.equals("1")) {
				selectAll();
			} else if (sel.equals("2")) {
				System.out.println("\n1.��¥�� ��ȸ 2.�溰 ��ȸ");
				String sel2 = sc.nextLine();

				if (sel2.equals("1")) {

					findDate();

				} else if (sel2.equals("2")) {

					findRoom();
				}

			} else if (sel.equals("3")) {
				checkChange();
			} else if (sel.equals("0")) {
				break;
			}
		}
		dbCon().close();
	}

	//������ : ���ຯ�� Ȯ��
	
	public void checkChange() throws Exception {

		String query = "select * from tri";
		PreparedStatement pstmt = dbCon().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			ReservationChangeVO vo = new ReservationChangeVO();

			vo.setRoom_num(rs.getString(1));
			vo.setReserve_date(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setReserve_num(rs.getString(4));
			vo.setNew_reserve_num(rs.getString(5));
			vo.setNew_reserve_date(rs.getString(6));
			vo.setNew_name(rs.getString(7));
			vo.setNew_reserve_num(rs.getString(8));
			vo.setBigo(rs.getString(9));

			System.out.println(vo.toString());
		}
		dbCon().close();
	}

	//������- ���೻�� ��ü��ȸ
	
	public void selectAll() throws Exception {
		String query = "select * from reservation";
		PreparedStatement pstmt = dbCon().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			ReservationVO vo = new ReservationVO();

			vo.setRoom(rs.getString(1));
			vo.setDate(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setReserve_num(rs.getString(4));

			System.out.println(vo.toString());

		}
		dbCon().close();
	}

	//������ - �溰 ���� ��Ȳ ��ȸ
		
	public void findRoom() throws Exception {
		String query = "select * from reservation where room_num=?";
		PreparedStatement pstmt = dbCon().prepareStatement(query);

		System.out.print("��ȸ�� �� ��ȣ>>");
		String num = sc.nextLine();
		pstmt.setString(1, num);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			ReservationVO vo = new ReservationVO();

			vo.setRoom(rs.getString(1));
			vo.setDate(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setReserve_num(rs.getString(4));

			System.out.println(vo.toString());

		}
		dbCon().close();
	}
	
	
	//������ >> ��¥ �� ���� ��ȸ
	public void findDate() throws Exception {
		String query = "select * from reservation where reserve_date =?";

		System.out.print("��ȸ�� �� ��)0606\n>>");
		String date = sc.nextLine();

		PreparedStatement pstmt = dbCon().prepareStatement(query);
		pstmt.setString(1, 2022+date);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			ReservationVO vo = new ReservationVO();

			vo.setRoom(rs.getString(1));
			vo.setDate(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setReserve_num(rs.getString(4));

			System.out.println(vo.toString());

		}
		dbCon().close();
	}

	
	//������ >> �� ������ ���� �Ұ� �˸�
	public void repairRoom() throws Exception {
		
		
		
		String q1="insert into reservation values(2,'20220630','������',0)";
		String q2="insert into reservation values(2,'20220701','������',0)";
		String q3="insert into reservation values(2,'20220702','������',0)";
		String q4="insert into reservation values(2,'20220703','������',0)";
		
		Statement stmt = dbCon().createStatement();
		
		int rs1 = stmt.executeUpdate(q1);
		int rs2 = stmt.executeUpdate(q2);
		int rs3 = stmt.executeUpdate(q3);
		int rs4 = stmt.executeUpdate(q4);	
		
	}
		
	//�����
	
	public void forUser() throws Exception {

		while (true) {
			System.out.println("1.���� 2.���� ���� 3.���� ��� 4.���� ��ȸ 0.������");
			String sel = sc.nextLine();
			if (sel.equals("1")) {
				insert();
			} else if (sel.equals("2")) {
				update();
			} else if (sel.equals("3")) {
				delete();
			} else if (sel.equals("4")) {
				find();
			} else if (sel.equals("0")) {
				break;
			}
		}
		

	}

	
	//����� >> ����(�����ȣ/�̹� ����� �� ������)
	public void insert() throws Exception {
		int count = 0;
		System.out.println("������ �غ��ô�~~");
		System.out.println("���� �� 3���� �ֽ��ϴ�");
		System.out.print("���Ͻô� �������� �Է��ϼ��� ��)0601\n>>");
		String date = sc.nextLine();
		
		//�̹� ����� �� ������ 
		
		  if(EnabledRoom(date)==1) {
			  return;
		  }else {
			  String query = "insert into reservation values(?,?,?,seq_res.nextval)";
				PreparedStatement pstmt = dbCon().prepareStatement(query);
				

				System.out.print("������ �� ��ȣ>>");
				String room = sc.nextLine();
				System.out.print("������ �̸�>>");
				String name = sc.nextLine();

				pstmt.setString(1, room);
				pstmt.setString(2, 2022 + date);
				pstmt.setString(3, name);

				int rs2 = pstmt.executeUpdate();
				if (rs2 == 1) {
					System.out.println(name + "�� ����Ǽ̽��ϴ�");
					
					String reserve_num = "select * from reservation where room_num=? and reserve_date=?";
					PreparedStatement pstmt2 = dbCon().prepareStatement(reserve_num);
					pstmt2.setString(1,room);
					pstmt2.setString(2, 2022+date);
					ResultSet rs3 = pstmt2.executeQuery();
					while(rs3.next()) {
						ReservationVO vo = new ReservationVO();
						vo.setReserve_num(rs3.getString(4));
						
						System.out.println("�����ȣ :"+vo.getReserve_num());
					}
				}
		  }
		
			dbCon().close();
		}

	
	//����� >> ���� ����
	public void update() throws Exception {
		int count=0;
		System.out.print("\n�����ȣ �Է�>>");
		String reserve_num = sc.nextLine();
		String q = "select * from reservation where reserve_num=?";
		
		PreparedStatement pstmt = dbCon().prepareStatement(q);
		pstmt.setString(1, reserve_num);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.print("\n1.�� ���� 2.��¥ ���� 3.��ü ���� ");
			String sel = sc.nextLine();

			if (sel.equals("3")) {
				String query = "update reservation set room_num=? ,reserve_date=? where reserve_num=?";
				pstmt = dbCon().prepareStatement(query);
				
				System.out.print("�� ������ �Է� ��)0601 \n >>");
				String date = sc.nextLine();
				
				//��Ȯ��
				if(EnabledRoom(date)==1) {
					break;
				}else {
					System.out.print("\n���� ������ �� ��ȣ>>");
					String room = sc.nextLine();


					pstmt.setString(1, room);
					pstmt.setString(2, 2022 + date);
					pstmt.setString(3, reserve_num);

					int rs1 = pstmt.executeUpdate();
					if (rs1 == 1) {
						ReservationVO vo = new ReservationVO();
						vo.setName(rs.getString(3));

						System.out.println(vo.getName() + "�� ������ ����Ǿ����ϴ�\n");
					}
				}

			} else if (sel.equals("1")) {
				String query = "update reservation set room_num=?  where reserve_num=?";
				pstmt = dbCon().prepareStatement(query);
				System.out.print("\n���� ������ �� ��ȣ>>");
				String room = sc.nextLine();
				
				System.out.print("������>>");
				String date = sc.nextLine();
				
				if(EnabledRoom(date)==1) {
					return;
				}else {
					
					pstmt.setString(1, room);
					pstmt.setString(2, reserve_num);

					int rs1 = pstmt.executeUpdate();
					if (rs1 == 1) {
						ReservationVO vo = new ReservationVO();
						vo.setName(rs.getString(3));

						System.out.println(vo.getName() + "�� ������ ����Ǿ����ϴ�\n");

					}
					
				}
								

			} else if (sel.equals("2")) {
				String query = "update reservation set reserve_date=? where reserve_num=?";
				pstmt = dbCon().prepareStatement(query);

				System.out.print("\n���� ������ �Է� ��)0607\n>>");
				String date = sc.nextLine();
				
				if(EnabledRoom(date)==1) {
					return;
				}else {
					
					
					pstmt.setString(1, 2022 + date);
					pstmt.setString(2, reserve_num);

					int rs1 = pstmt.executeUpdate();
					if (rs1 == 1) {

						ReservationVO vo = new ReservationVO();
						vo.setName(rs.getString(3));

						System.out.println(vo.getName() + "�� ������ ����Ǿ����ϴ�\n");
					}
				}

				
			}
		}
		dbCon().close();

	}
	
	
	//����� >> ���� ���
	
	public void delete() throws Exception {
		System.out.println("\n���� ��ȣ �Է�>>");
		String num = sc.nextLine();

		String q = "select * from reservation where reserve_num=?";
		PreparedStatement pstmt = dbCon().prepareStatement(q);
		pstmt.setString(1, num);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {

			ReservationVO vo = new ReservationVO();

			vo.setName(rs.getString(3));

			String query = "delete reservation where reserve_num=?";
			pstmt = dbCon().prepareStatement(query);
			pstmt.setString(1, num);
			pstmt.executeUpdate();

			System.out.println(vo.getName() + "�� ������ ��ҵǾ����ϴ�.");
		}
		dbCon().close();
	}

	// ����� - ���� ��ȸ
		
	public void find() throws Exception {
		String query = "select * from reservation where reserve_num=?";
		PreparedStatement pstmt = dbCon().prepareStatement(query);

		System.out.println("\n���� ��ȣ �Է�>>");
		String num = sc.nextLine();
		pstmt.setString(1, num);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			ReservationVO vo = new ReservationVO();

			vo.setRoom(rs.getString(1));
			vo.setDate(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setReserve_num(rs.getString(4));

			System.out.println(vo.toString());

		}
		dbCon().close();
	}

	//�̹� ����� ��� ������
	public int EnabledRoom(String date) throws Exception {
		int count=0;
		String ableRoom = "select room_num from reservation where reserve_date='" + 2022 + date + "' order by room_num";
		Statement stmt = dbCon().createStatement();

		ResultSet rs = stmt.executeQuery(ableRoom);
		while (rs.next()) {
			ReservationVO vo = new ReservationVO();
			vo.setRoom(rs.getString(1));

			System.out.println(vo.getRoom() + "ȣ�� ����Ұ�");
			count++;
			
			if (count >= 3) {
				System.out.println("��� �� ������ �Ǿ����ϴ�");
				return 1;
			}
		}
		return 0;
	}
}
