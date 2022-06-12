package Main;

import DB.ReservationDB;

public class ReservationMain {

	public static void main(String[] args) throws Exception {
		ReservationDB db = new ReservationDB();		
		System.out.println("공지:2번 방 수리 \n기간 :6월 30일 ~ 7월 3일");
		
		
		db.All();
		

	}

}
