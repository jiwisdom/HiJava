package Main;

import DB.ReservationDB;

public class ReservationMain {

	public static void main(String[] args) throws Exception {
		ReservationDB db = new ReservationDB();		
		System.out.println("����:2�� �� ���� \n�Ⱓ :6�� 30�� ~ 7�� 3��");
		
		
		db.All();
		

	}

}
