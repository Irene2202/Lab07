package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutages> getPowerOutages(Nerc n){
		
		String sql="SELECT p.id, p.nerc_id, p.customers_affected, p.date_event_began, p.date_event_finished "
				+"FROM poweroutages p "
				+"WHERE p.nerc_id=?";
		List<PowerOutages> result=new ArrayList<>();
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				LocalDateTime inizio=rs.getTimestamp("p.date_event_began").toLocalDateTime();
				LocalDateTime fine=rs.getTimestamp("p.date_event_finished").toLocalDateTime();
				PowerOutages p=new PowerOutages(rs.getInt("p.id"), rs.getInt("p.nerc_id"), rs.getInt("p.customers_affected"), inizio, fine);
				result.add(p);
			}
			
			conn.close();
			return result;
		} catch (SQLException e) {
			throw new RuntimeException("ERRORE DB", e);
		}
		
	}
	

}
