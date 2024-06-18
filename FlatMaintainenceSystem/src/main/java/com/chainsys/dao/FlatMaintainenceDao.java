package com.chainsys.dao;

import java.sql.SQLException;

import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.EBbillResponse;
import com.chainsys.model.Tenant;
import com.chainsys.model.User;

public class FlatMaintainenceDao {
	public int registerDao(User user) throws ClassNotFoundException, SQLException {
		TrancistionDto trancistionDto = new TrancistionDto();
		return trancistionDto.registerUser(user);
	}
	public User loginCheck(User user) throws ClassNotFoundException, SQLException {
		TrancistionDto trancistionDto = new TrancistionDto();
		User loginDetails = trancistionDto.loginDetails(user.getEmail());
		if(loginDetails!=null) {
			if((loginDetails.getEmail().equals(user.getEmail())) && (loginDetails.getPassword().equals(user.getPassword()))) {
				return loginDetails;
			}
		}
		return null;	
	}
	public int addTenant(Tenant tenant) throws ClassNotFoundException, SQLException {
		TrancistionDto trancistionDto = new TrancistionDto();
		return trancistionDto.addTenant(tenant);
	}
	public EBbillResponse addEbBill(int id,int unit) throws ClassNotFoundException, Exception {
		int price ;
		String status;
		if(unit>100) {
			price=unit*40;
			status="no";
		}
		else if(unit>400) {
			price=unit*50;
			status="no";
		}
		else {
			price=0;
			status="yes";
		}
		TrancistionDto trancistionDto = new TrancistionDto();
		return trancistionDto.addEbBill(id, price, status);
	}
	 public static boolean tenantMatchesQuery(Tenant tenant, String query) {
	        return tenant.getName().toLowerCase().contains(query.toLowerCase()) ||
	               tenant.getPhoneNo().contains(query) ||
	               tenant.getEmail().toLowerCase().contains(query.toLowerCase());
	    }
}
