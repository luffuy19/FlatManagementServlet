package com.chainsys.dao;

import java.sql.SQLException;

import com.chainsys.dto.TrancistionDto;
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
}
