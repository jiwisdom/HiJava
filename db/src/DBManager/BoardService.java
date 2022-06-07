package DBManager;

import java.sql.Connection;
import java.util.Scanner;

public class BoardService implements IDB {

	BoardDB db = new BoardDB();
	Scanner sc = new Scanner(System.in);

	@Override
	public Connection dbConnet() throws Exception {

		return db.dbConnet();
	}

	@Override
	public void selectAll() throws Exception {

	}

	@Override
	public void insert() throws Exception {
		dbConnet();
		db.insert();
	}

	@Override
	public void update() throws Exception {
		dbConnet();
		db.update();
	}

	@Override
	public void delete() throws Exception {
		dbConnet();
		db.delete();
	}

	@Override
	public void find() throws Exception {
		dbConnet();
		db.find();
	}

	public void page(int n) throws Exception {
		dbConnet();
		db.page(n);
	}

	

	

}
