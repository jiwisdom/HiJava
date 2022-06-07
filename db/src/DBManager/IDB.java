package DBManager;

import java.sql.Connection;

public interface IDB {

	public Connection dbConnet() throws Exception;
	
	public void selectAll() throws Exception;
	
	public void insert() throws Exception;
	
	public void update() throws Exception;
	
	public void delete() throws Exception;
	
	public void find() throws Exception ;
	
}
