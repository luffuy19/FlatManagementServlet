package com.chainsys.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.dao.FlatMaintainenceDao;
import com.chainsys.model.Complain;
import com.chainsys.model.EBbillResponse;
import com.chainsys.model.Employee;
import com.chainsys.model.Task;
import com.chainsys.model.Tenant;
import com.chainsys.model.User;
import com.chainsys.model.Visitor;
import com.mysql.cj.jdbc.CallableStatement;
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
	    Connection con = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    User user = null;
	    
	    try {
	        con = Connectionutil.getConnections();
	        String query = "SELECT id, email, password, role FROM users WHERE email=?";
	        statement = con.prepareStatement(query);
	        statement.setString(1, email);
	        rs = statement.executeQuery();
	        
	        if (rs.next()) {
	            user = new User();
	            int id = rs.getInt("id");
	            user.setId(id);
	            user.setEmail(rs.getString("email"));
	            user.setPassword(rs.getString("password"));
	            user.setRole(rs.getString("role"));
	        }
	        
	    } finally {
	        // Close resources in finally block to ensure they are always closed
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    return user;
	}


	public int addTenant(Tenant tenant) throws ClassNotFoundException, SQLException {
		Connection con = Connectionutil.getConnections();
		String query = "INSERT INTO users_details (name, phone_no,email,aadhaar_number, photo,family_members,flat_type,flat_floor,room_no,advance_amount, advance_status, rent_amount, date_of_joining, users_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			statement.setString(9, tenant.getFlatFloor());
			statement.setDouble(10, tenant.getAdvanceAmount());
			statement.setString(11, tenant.getAdvanceStatus());
			statement.setDouble(12, tenant.getRentAmount());
			statement.setString(13, tenant.getDateOfJoining());// Assuming getDateOfJoining returns a LocalDate
			statement.setInt(14, userId);
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
		if (rs.next()) {
			return Integer.parseInt(rs.getString("id"));
		}
		return 0;
	}

	public List<Tenant> searchTenants(String query) {
		List<Tenant> tenantList = new ArrayList<>();
		try (Connection connection = Connectionutil.getConnections()) {
			String sql = "SELECT id, name, phone_no, email, aadhaar_number, photo, family_members, flat_type, flat_floor, room_no, advance_amount, advance_status, rent_amount, rent_amount_status, eb_bill, eb_bill_status, date_of_joining, date_of_ending, delete_user, users_id FROM users_details Where delete_user=0 ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tenant tenant = new Tenant(rs.getInt("id"), rs.getString("name"), rs.getString("phone_no"),
						rs.getString("email"), rs.getString("aadhaar_number"), rs.getBytes("photo"),
						rs.getInt("family_members"), rs.getString("flat_type"), rs.getString("flat_floor"),
						rs.getString("room_no"), rs.getInt("advance_amount"), rs.getString("advance_status"),
						rs.getInt("rent_amount"), rs.getString("rent_amount_status"), rs.getInt("eb_bill"),
						rs.getString("eb_bill_status"), rs.getString("date_of_joining"), rs.getString("date_of_ending"),
						rs.getString("delete_user"), rs.getInt("users_id"));
				// Filter based on search criteria
				if (FlatMaintainenceDao.tenantMatchesQuery(tenant, query)) {
					tenantList.add(tenant);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenantList;
	}

	public List<Tenant> getAllTenants() {
		List<Tenant> tenantList = new ArrayList<>();
		try (Connection connection = Connectionutil.getConnections()) {
			String sql = "SELECT id, name, phone_no, email, aadhaar_number, photo, family_members, flat_type, flat_floor, room_no, advance_amount, advance_status, rent_amount, rent_amount_status, eb_bill, eb_bill_status, date_of_joining, date_of_ending, delete_user, users_id FROM users_details Where delete_user=0 ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tenant tenant = new Tenant(rs.getInt("id"), rs.getString("name"), rs.getString("phone_no"),
						rs.getString("email"), rs.getString("aadhaar_number"), rs.getBytes("photo"),
						rs.getInt("family_members"), rs.getString("flat_type"), rs.getString("flat_floor"),
						rs.getString("room_no"), rs.getInt("advance_amount"), rs.getString("advance_status"),
						rs.getInt("rent_amount"), rs.getString("rent_amount_status"), rs.getInt("eb_bill"),
						rs.getString("eb_bill_status"), rs.getString("date_of_joining"), rs.getString("date_of_ending"),
						rs.getString("delete_user"), rs.getInt("users_id"));
				// Filter based on search criteria
				tenantList.add(tenant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenantList;
	}
	public Tenant getSpecficTenants(int id) {
		Tenant tenant = null;
		try (Connection connection = Connectionutil.getConnections()) {
			String sql = "SELECT id, name, phone_no, email, aadhaar_number, photo, family_members, flat_type, flat_floor, room_no, advance_amount, advance_status, rent_amount, rent_amount_status, eb_bill, eb_bill_status, date_of_joining, date_of_ending, delete_user, users_id FROM users_details Where delete_user=0 AND users_id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tenant = new Tenant(rs.getInt("id"), rs.getString("name"), rs.getString("phone_no"),
						rs.getString("email"), rs.getString("aadhaar_number"), rs.getBytes("photo"),
						rs.getInt("family_members"), rs.getString("flat_type"), rs.getString("flat_floor"),
						rs.getString("room_no"), rs.getInt("advance_amount"), rs.getString("advance_status"),
						rs.getInt("rent_amount"), rs.getString("rent_amount_status"), rs.getInt("eb_bill"),
						rs.getString("eb_bill_status"), rs.getString("date_of_joining"), rs.getString("date_of_ending"),
						rs.getString("delete_user"), rs.getInt("users_id"));
				// Filter based on search criteria
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenant;
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

	public EBbillResponse addEbBill(int id, int newEbBill, String newEbBillStatus)
			throws Exception {
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
		String query = "INSERT INTO visitors (visitor_name, in_time, in_date,flat_floor,door_no) VALUES (?,?,?,?,?)";
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, visitor.getVisitorName());
			statement.setString(2, visitor.getInTime());
			statement.setString(3, visitor.getInDate());
			statement.setInt(4, visitor.getFlatFloor());
			statement.setString(5, visitor.getRoomNo());
			int executeUpdate = statement.executeUpdate();
			System.out.println(executeUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void outVisitor(Visitor visitor) throws ClassNotFoundException {
		String query = "UPDATE visitors SET out_time = ?, out_date = ? WHERE id = ? ";
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, visitor.getOutTime());
			statement.setString(2, visitor.getOutDate());
			statement.setInt(3, visitor.getId());
			int executeUpdate = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Visitor> viewVisitor(String days) throws SQLException, ClassNotFoundException {
		String query = null;
		if (days != null) {
			if (days.equals("30")) {
				query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 30 DAY";
			} else if (days.equals("7")) {
				query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 7 DAY";
			} else if (days.equals("1")) {
				query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 1 DAY";
			}
		} else {
			query = "SELECT * FROM visitors WHERE timestamp >= NOW() - INTERVAL 1 DAY";
		}
		List<Visitor> al = new ArrayList<>();
		try (Connection connection = Connectionutil.getConnections()) {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Visitor visitor = new Visitor(resultSet.getInt("id"), resultSet.getString("visitor_name"),
						resultSet.getString("in_time"), resultSet.getString("out_time"), resultSet.getString("in_date"),
						resultSet.getString("out_date"), resultSet.getInt("flat_floor"), resultSet.getString("door_no"),
						resultSet.getString("timestamp"));
				al.add(visitor);
			}
			return al;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

	public List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
		List<Employee> employees = new ArrayList<>();
		String query = "SELECT id, name, phone_number, department FROM employee";
		Connection connection = Connectionutil.getConnections();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getString("phone_number"), resultSet.getString("department"));
				employees.add(employee);
			}
		}
		return employees;
	}

	public int AddEmployee(Employee emp) throws SQLException, ClassNotFoundException {

		// Database query to insert a new employee
		String query = "INSERT INTO employee (name, phone_number,department) VALUES (?, ?, ?)";
		// Use try-with-resources to ensure the connection and statement are closed
		// properly
		try (Connection connection = Connectionutil.getConnections();
				PreparedStatement statement = connection.prepareStatement(query)) {
			// Set the parameters for the query
			statement.setString(1, emp.getName());
			statement.setString(2, emp.getPhoneNumber());
			statement.setString(3, emp.getDepartment());

			// Execute the query
			return statement.executeUpdate();
		}
	}

	public boolean deleteEmployee(int employeeId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM employee WHERE id = ?";
		try (Connection conn = Connectionutil.getConnections(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, employeeId);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		}
	}

	public List<Complain> getAllComplaints() throws SQLException, ClassNotFoundException {
		List<Complain> complaints = new ArrayList<>();
		String query = "SELECT c.id, c.complain_type, c.comments, c.complain_date, c.complain_status, c.user_id, u.room_no, u.flat_floor "
				+ "FROM complain c " + "JOIN users_details u ON c.user_id = u.id";
		Connection connection = Connectionutil.getConnections();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Complain complain = new Complain(resultSet.getInt("id"), resultSet.getString("complain_type"),
						resultSet.getString("comments"), resultSet.getString("complain_date"),
						resultSet.getString("complain_status"), resultSet.getInt("user_id"),
						resultSet.getString("room_no"), resultSet.getString("flat_floor"));
				complaints.add(complain);
			}
		}
		return complaints;
	}

	public void addComplaint(Complain complaint) throws SQLException, ClassNotFoundException {
		String query = "INSERT INTO complain (complain_type, comments, complain_date, user_id) VALUES (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = Connectionutil.getConnections();
			statement = connection.prepareStatement(query);
			statement.setString(1, complaint.getComplainType());
			statement.setString(2, complaint.getComments());
			statement.setString(3, complaint.getComplainDate());
			statement.setInt(4, complaint.getUserId());
			statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void deleteComplaint(int complaintId) throws SQLException, ClassNotFoundException {
		String query = "DELETE FROM complain WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = Connectionutil.getConnections();
			statement = connection.prepareStatement(query);
			statement.setInt(1, complaintId);
			statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void addTask(int workerId, int complaintId) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO task (employee_id, complain_id) VALUES (?, ?)";
		try (Connection conn = Connectionutil.getConnections(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, workerId);
			ps.setInt(2, complaintId);
			ps.executeUpdate();
		}
	}

	public List<Task> getAllTasks() throws Exception {
		List<Task> tasks = new ArrayList<>();
		String sql = "SELECT t.id, t.employee_id, t.complain_id, c.complain_type, c.comments, c.complain_date, c.complain_status, c.user_id "
				+ "FROM task t " + "JOIN complain c ON t.complain_id = c.id";

		try (Connection conn = Connectionutil.getConnections();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Task task = new Task(rs.getInt("id"), rs.getInt("employee_id"), rs.getInt("complain_id"),
						rs.getString("complain_type"), rs.getString("comments"), rs.getString("complain_date"),
						rs.getString("complain_status"), rs.getInt("user_id"));
				tasks.add(task);
			}
		}
		return tasks;
	}

	public void deleteComplain(int complainId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM complain WHERE id = ?";

		try (Connection conn = Connectionutil.getConnections(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, complainId);
			ps.executeUpdate();
		}
	}

	public void deleteTasksByComplainId(int complainId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM task WHERE complain_id = ?";

		try (Connection conn = Connectionutil.getConnections(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, complainId);
			ps.executeUpdate();
		}
	}
	public boolean checkUserPayment(int userId) throws ClassNotFoundException {
        boolean hasPaid = false;
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            conn = Connectionutil.getConnections();
            String query = "{CALL user_payments(?,?)}";
            stmt = (CallableStatement) conn.prepareCall(query);
            stmt.setInt(1, userId);
            stmt.registerOutParameter(2, java.sql.Types.BOOLEAN); // Registering the output parameter
            stmt.execute();

            hasPaid = stmt.getBoolean(2);
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            try { 
            	if (stmt != null) stmt.close(); 
            } 
            catch (SQLException e) { 
            	e.printStackTrace(); 
            }
            try { 
            	if (conn != null) conn.close(); 
            } 
            catch (SQLException e) { 
            	e.printStackTrace(); 
            }
        }
        return hasPaid;
    }
	public boolean processPayment(int userId) throws ClassNotFoundException, SQLException {
        boolean success = false;
        PreparedStatement statement=null;
        Connection conn = Connectionutil.getConnections();
        String query = "INSERT INTO user_payments (user_id, payment_date) VALUES (?, ?)";
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            int rowAffected = statement.executeUpdate();

            if (rowAffected > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception e) { e.printStackTrace(); }
            try {  conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return success;
    }
}
