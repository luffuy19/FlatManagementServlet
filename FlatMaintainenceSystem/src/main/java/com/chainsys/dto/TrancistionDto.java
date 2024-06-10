package com.chainsys.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.model.EBbillResponse;
import com.chainsys.model.Tenant;
import com.chainsys.model.User;
import com.chainsys.model.Visitor;
import com.studentcrud.util.Connectionutil;

public class TrancistionDto {
	public int registerUser(User user) throws ClassNotFoundException, SQLException {
		Connection con = Connectionutil.getConnections();
		String query = "INSERT INTO users (email, password,role) VALUES (?,?,?)";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, user.getEmail());
		statement.setString(2, user.getPassword());
		String role = "";
		if (user.getEmail().endsWith("@inam.com")) {
			role = "admin";
		} else {
			role = "user";
		}
		statement.setString(3, role);
		return statement.executeUpdate();
	}

	public User loginDetails(String email) throws SQLException, ClassNotFoundException {
		Connection con = Connectionutil.getConnections();
		String query = "SELECT id,email,password,role FROM users where email=?";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			User user = new User();
			int id = Integer.parseInt(rs.getString("id"));
			user.setId(id);
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			return user;
		}
		return null;
	}

	public int addTenant(Tenant tenant) throws ClassNotFoundException, SQLException {
		Connection con = Connectionutil.getConnections();
		String query = "INSERT INTO users_details (name, phone_no,email,aadhaar_number, photo,family_members,flat_type,flat_floor,advance_amount, advance_status, rent_amount, date_of_joining, users_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement statement = con.prepareStatement(query);
		System.out.println(tenant.getEmail());
		int userId = findUserId(tenant.getEmail());
		if (userId > 0) {
			statement.setString(1, tenant.getName());
			statement.setString(2, tenant.getPhoneNo());
			statement.setString(3, tenant.getEmail());
			statement.setString(4, tenant.getAadhaarNumber());
			statement.setBytes(5, tenant.getPhoto());
			statement.setInt(6, tenant.getFamilyNembers());
			statement.setString(7, tenant.getFlatType());
			statement.setString(8, tenant.getFlatFloor());
			statement.setDouble(9, tenant.getAdvanceAmount());
			statement.setString(10, tenant.getAdvanceStatus());
			statement.setDouble(11, tenant.getRentAmount());
			statement.setString(12, tenant.getDateOfJoining());// Assuming getDateOfJoining returns a LocalDate
			statement.setInt(13, userId);
			return statement.executeUpdate();
		} else {
			return 0;
		}
	}

	public static int findUserId(String email) throws ClassNotFoundException, SQLException {
		Connection con = Connectionutil.getConnections();
		String query = "SELECT id FROM users WHERE email=?";
		System.out.println(email);
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			return Integer.parseInt(rs.getString("id"));
		}
		return 0;
	}

	public List<Tenant> searchTenants(String query, int offset, int limit) {
		List<Tenant> tenantList = new ArrayList<>();
		try (Connection connection = Connectionutil.getConnections()) {
			String sql = "SELECT * FROM users_details WHERE (name LIKE ? OR phone_no LIKE ? OR email) AND delete_user = 0 LIKE ? LIMIT ? OFFSET ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ps.setString(2, "%" + query + "%");
			ps.setString(3, "%" + query + "%");
			ps.setInt(4, limit);
			ps.setInt(5, offset);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tenant tenant = new Tenant(rs.getInt("id"), rs.getString("name"), rs.getString("phone_no"),
						rs.getString("email"), rs.getString("aadhaar_number"), rs.getBytes("photo"),
						rs.getInt("family_members"), rs.getString("flat_type"), rs.getString("flat_floor"),
						rs.getInt("advance_amount"), rs.getString("advance_status"), rs.getInt("rent_amount"),
						rs.getString("rent_amount_status"), rs.getInt("eb_bill"), rs.getString("eb_bill_status"),
						rs.getString("date_of_joining"), rs.getString("date_of_ending"), rs.getString("delete_user"),
						rs.getInt("users_id"));
				tenantList.add(tenant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenantList;
	}

	public int getTenantCount(String query) {
		int count = 0;
		try (Connection connection = Connectionutil.getConnections()) {
			String sql = "SELECT COUNT(*) FROM users_details WHERE (name LIKE ? OR phone_no LIKE ? OR email LIKE ?) AND delete_user = 0 ;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ps.setString(2, "%" + query + "%");
			ps.setString(3, "%" + query + "%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public void deleteTenant(int tenantId) throws ClassNotFoundException {
		String sql = "UPDATE users_details SET delete_user = ?  WHERE id = ?";
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, 1);
			statement.setInt(2, tenantId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public EBbillResponse addEbBill(int id, int newEbBill, String newEbBillStatus) throws ClassNotFoundException, Exception {
		String query = "UPDATE users_details SET eb_bill = ?, eb_bill_status = ? WHERE id = ?";
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, newEbBill); // Assuming eb_bill is of type DECIMAL
			statement.setString(2, newEbBillStatus);
			statement.setInt(3, id); // Assuming id is of type INT
			int executeUpdate = statement.executeUpdate();
			System.out.println(executeUpdate);
			return new EBbillResponse(newEbBill, newEbBillStatus);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void addVisitor(Visitor visitor) throws ClassNotFoundException {
		String query="INSERT INTO visitors (visitor_name, in_time, in_date,flat_floor) VALUES (?,?,?,?)";
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,visitor.getVisitorName());
			statement.setString(2,visitor.getInTime());
			statement.setString(3,visitor.getInDate());
			statement.setInt(4,visitor.getFlat_floor());
			int executeUpdate = statement.executeUpdate();
			System.out.println(executeUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void outVisitor(Visitor visitor) throws ClassNotFoundException {
		String query="UPDATE visitors SET out_time = ?, out_date = ? WHERE id = ? ";
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,visitor.getOutTime());
			statement.setString(2,visitor.getOutDate());
			statement.setInt(3,visitor.getId());
			int executeUpdate = statement.executeUpdate();
			System.out.println(executeUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Visitor> viewVisitor(String days) throws SQLException, ClassNotFoundException {
		String query = null;
		if(days!=null) {
			if(days.equals("30")) {
				query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 30 DAY";
			}
			else if(days.equals("7")) {
				query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 7 DAY";
			}
		}
		else {
			query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 1 DAY";
		}
		List<Visitor> al = new ArrayList<Visitor>();
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Visitor visitor= new Visitor(resultSet.getInt("id"),resultSet.getString("visitor_name"),resultSet.getString("in_time"),resultSet.getString("out_time"),resultSet.getString("in_date"), resultSet.getString("out_date"),resultSet.getInt("flat_floor"),resultSet.getString("timestamp"));
				al.add(visitor);
			}
			return al;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
