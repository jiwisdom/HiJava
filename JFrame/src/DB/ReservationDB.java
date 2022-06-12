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
	

	// 로그인/회원가입
	public void All() throws Exception {
		System.out.println("1.로그인 2.회원가입");
		String sel = sc.nextLine();
		if (sel.equals("1")) {
			login();
		} else if (sel.equals("2")) {
			regist();
			login();

		}
	}

	// 회원가입
	public void regist() throws Exception {
		String query = "insert into inform values(?,?,?,?)";
		PreparedStatement pstmt = dbCon().prepareStatement(query);

		System.out.print("이름>>");
		String name = sc.nextLine();
		System.out.print("연락처>>");
		String phone = sc.nextLine();
		System.out.print("id>>");
		String id = sc.nextLine();
				
		//id 중복 안되게 함
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
					System.out.println(name + "님 가입되셨습니다\n로그인 화면으로 이동합니다");

				}
								
			}else {
				System.out.println("존재하는 id입니다");
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
					System.out.println(name + "님 가입되셨습니다\n로그인 화면으로 이동합니다");

				}
			}
		}
				
		
		dbCon().close();
	}

	// 로그인>>관리자 / 사용자 나누기
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

				System.out.println("관리자 계정입니다\n");
				forManager();

			} else {
				query = "select name from inform where id=?";
				pstmt = dbCon().prepareStatement(query);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString(1) + "님 로그인 되셨습니다\n");
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
		
	// 관리자 
		
	public void forManager() throws Exception {

		while (true) {
			System.out.println("1.전체 예약 조회 2.개별 예약 조회 3.예약 변경 확인 0.나가기");
			String sel = sc.nextLine();
			if (sel.equals("1")) {
				selectAll();
			} else if (sel.equals("2")) {
				System.out.println("\n1.날짜별 조회 2.방별 조회");
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

	//관리자 : 예약변경 확인
	
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

	//관리자- 예약내역 전체조회
	
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

	//관리자 - 방별 예약 현황 조회
		
	public void findRoom() throws Exception {
		String query = "select * from reservation where room_num=?";
		PreparedStatement pstmt = dbCon().prepareStatement(query);

		System.out.print("조회할 방 번호>>");
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
	
	
	//관리자 >> 날짜 별 예약 조회
	public void findDate() throws Exception {
		String query = "select * from reservation where reserve_date =?";

		System.out.print("조회할 날 예)0606\n>>");
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

	
	//관리자 >> 방 수리로 예약 불가 알림
	public void repairRoom() throws Exception {
		
		
		
		String q1="insert into reservation values(2,'20220630','공사중',0)";
		String q2="insert into reservation values(2,'20220701','공사중',0)";
		String q3="insert into reservation values(2,'20220702','공사중',0)";
		String q4="insert into reservation values(2,'20220703','공사중',0)";
		
		Statement stmt = dbCon().createStatement();
		
		int rs1 = stmt.executeUpdate(q1);
		int rs2 = stmt.executeUpdate(q2);
		int rs3 = stmt.executeUpdate(q3);
		int rs4 = stmt.executeUpdate(q4);	
		
	}
		
	//사용자
	
	public void forUser() throws Exception {

		while (true) {
			System.out.println("1.예약 2.예약 변경 3.예약 취소 4.예약 조회 0.나가기");
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

	
	//사용자 >> 예약(예약번호/이미 예약된 방 보여줌)
	public void insert() throws Exception {
		int count = 0;
		System.out.println("예약을 해봅시다~~");
		System.out.println("방은 총 3개가 있습니다");
		System.out.print("원하시는 예약일을 입력하세요 예)0601\n>>");
		String date = sc.nextLine();
		
		//이미 예약된 방 보여줌 
		
		  if(EnabledRoom(date)==1) {
			  return;
		  }else {
			  String query = "insert into reservation values(?,?,?,seq_res.nextval)";
				PreparedStatement pstmt = dbCon().prepareStatement(query);
				

				System.out.print("예약할 방 번호>>");
				String room = sc.nextLine();
				System.out.print("에약자 이름>>");
				String name = sc.nextLine();

				pstmt.setString(1, room);
				pstmt.setString(2, 2022 + date);
				pstmt.setString(3, name);

				int rs2 = pstmt.executeUpdate();
				if (rs2 == 1) {
					System.out.println(name + "님 예약되셨습니다");
					
					String reserve_num = "select * from reservation where room_num=? and reserve_date=?";
					PreparedStatement pstmt2 = dbCon().prepareStatement(reserve_num);
					pstmt2.setString(1,room);
					pstmt2.setString(2, 2022+date);
					ResultSet rs3 = pstmt2.executeQuery();
					while(rs3.next()) {
						ReservationVO vo = new ReservationVO();
						vo.setReserve_num(rs3.getString(4));
						
						System.out.println("예약번호 :"+vo.getReserve_num());
					}
				}
		  }
		
			dbCon().close();
		}

	
	//사용자 >> 예약 변경
	public void update() throws Exception {
		int count=0;
		System.out.print("\n예약번호 입력>>");
		String reserve_num = sc.nextLine();
		String q = "select * from reservation where reserve_num=?";
		
		PreparedStatement pstmt = dbCon().prepareStatement(q);
		pstmt.setString(1, reserve_num);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.print("\n1.방 변경 2.날짜 변경 3.전체 변경 ");
			String sel = sc.nextLine();

			if (sel.equals("3")) {
				String query = "update reservation set room_num=? ,reserve_date=? where reserve_num=?";
				pstmt = dbCon().prepareStatement(query);
				
				System.out.print("새 예약일 입력 예)0601 \n >>");
				String date = sc.nextLine();
				
				//방확인
				if(EnabledRoom(date)==1) {
					break;
				}else {
					System.out.print("\n새로 예약할 방 번호>>");
					String room = sc.nextLine();


					pstmt.setString(1, room);
					pstmt.setString(2, 2022 + date);
					pstmt.setString(3, reserve_num);

					int rs1 = pstmt.executeUpdate();
					if (rs1 == 1) {
						ReservationVO vo = new ReservationVO();
						vo.setName(rs.getString(3));

						System.out.println(vo.getName() + "님 예약이 변경되었습니다\n");
					}
				}

			} else if (sel.equals("1")) {
				String query = "update reservation set room_num=?  where reserve_num=?";
				pstmt = dbCon().prepareStatement(query);
				System.out.print("\n새로 예약할 방 번호>>");
				String room = sc.nextLine();
				
				System.out.print("예약일>>");
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

						System.out.println(vo.getName() + "님 예약이 변경되었습니다\n");

					}
					
				}
								

			} else if (sel.equals("2")) {
				String query = "update reservation set reserve_date=? where reserve_num=?";
				pstmt = dbCon().prepareStatement(query);

				System.out.print("\n새로 예약일 입력 예)0607\n>>");
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

						System.out.println(vo.getName() + "님 예약이 변경되었습니다\n");
					}
				}

				
			}
		}
		dbCon().close();

	}
	
	
	//사용자 >> 예약 취소
	
	public void delete() throws Exception {
		System.out.println("\n예약 번호 입력>>");
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

			System.out.println(vo.getName() + "님 예약이 취소되었습니다.");
		}
		dbCon().close();
	}

	// 사용자 - 예약 조회
		
	public void find() throws Exception {
		String query = "select * from reservation where reserve_num=?";
		PreparedStatement pstmt = dbCon().prepareStatement(query);

		System.out.println("\n예약 번호 입력>>");
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

	//이미 예약된 방들 보여줌
	public int EnabledRoom(String date) throws Exception {
		int count=0;
		String ableRoom = "select room_num from reservation where reserve_date='" + 2022 + date + "' order by room_num";
		Statement stmt = dbCon().createStatement();

		ResultSet rs = stmt.executeQuery(ableRoom);
		while (rs.next()) {
			ReservationVO vo = new ReservationVO();
			vo.setRoom(rs.getString(1));

			System.out.println(vo.getRoom() + "호실 예약불가");
			count++;
			
			if (count >= 3) {
				System.out.println("모든 방 예약이 되었습니다");
				return 1;
			}
		}
		return 0;
	}
}
