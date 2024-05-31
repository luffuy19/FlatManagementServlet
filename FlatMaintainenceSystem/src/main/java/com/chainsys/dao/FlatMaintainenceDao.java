package com.chainsys.dao;

import java.sql.SQLException;

import com.chainsys.dto.TrancistionDto;
import com.chainsys.model.User;

public class FlatMaintainenceDao {
	public int registerDao(User user) throws ClassNotFoundException, SQLException {
		TrancistionDto trancistionDto = new TrancistionDto();
		return trancistionDto.registerUser(user);
	}
	public int loginCheck(User user) throws ClassNotFoundException, SQLException {
		TrancistionDto trancistionDto = new TrancistionDto();
		User loginDetails = trancistionDto.loginDetails(user.getEmail());
		if(loginDetails!=null) {
			if((loginDetails.getEmail().equals(user.getEmail())) && (loginDetails.getPassword().equals(user.getPassword()))) {
				return 1;
			}
		}
		return 0;	
	}
}
