package com.chainsys.model;

import java.util.Arrays;

public class Tenant {
	int id ; 
	String name;
	String phoneNo;
	String email;
	String aadhaarNumber; 
	byte[] photo;
	int familyNembers;
	String flatType;
	String flatFloor;
	int advanceAmount;
	String advanceStatus;
	int rentAmount;
	String rentAmountStatus;
	int ebBill;
	String ebBillStatus;
	String dateOfJoining;
	String dateOfEnding;
	String deleteUser;
	int user_id;
	
	
	public Tenant(int id, String name, String phoneNo, String email, String aadhaarNumber, byte[] photo,
			int familyNembers, String flatType, String flatFloor, int advanceAmount, String advanceStatus,
			int rentAmount, String rentAmountStatus, int ebBill, String ebBillStatus, String dateOfJoining,
			String dateOfEnding, String deleteUser, int user_id) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNo = phoneNo;
		this.email = email;
		this.aadhaarNumber = aadhaarNumber;
		this.photo = photo;
		this.familyNembers = familyNembers;
		this.flatType = flatType;
		this.flatFloor = flatFloor;
		this.advanceAmount = advanceAmount;
		this.advanceStatus = advanceStatus;
		this.rentAmount = rentAmount;
		this.rentAmountStatus = rentAmountStatus;
		this.ebBill = ebBill;
		this.ebBillStatus = ebBillStatus;
		this.dateOfJoining = dateOfJoining;
		this.dateOfEnding = dateOfEnding;
		this.deleteUser = deleteUser;
		this.user_id = user_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAadhaarNumber() {
		return aadhaarNumber;
	}
	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public int getFamilyNembers() {
		return familyNembers;
	}
	public void setFamilyNembers(int familyNembers) {
		this.familyNembers = familyNembers;
	}
	public String getFlatType() {
		return flatType;
	}
	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}
	public String getFlatFloor() {
		return flatFloor;
	}
	public void setFlatFloor(String flatFloor) {
		this.flatFloor = flatFloor;
	}
	public int getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(int advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	public String getAdvanceStatus() {
		return advanceStatus;
	}
	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}
	public int getRentAmount() {
		return rentAmount;
	}
	public void setRentAmount(int rentAmount) {
		this.rentAmount = rentAmount;
	}
	public String getRentAmountStatus() {
		return rentAmountStatus;
	}
	public void setRentAmountStatus(String rentAmountStatus) {
		this.rentAmountStatus = rentAmountStatus;
	}
	public int getEbBill() {
		return ebBill;
	}
	public void setEbBill(int ebBill) {
		this.ebBill = ebBill;
	}
	public String getEbBillStatus() {
		return ebBillStatus;
	}
	public void setEbBillStatus(String ebBillStatus) {
		this.ebBillStatus = ebBillStatus;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getDateOfEnding() {
		return dateOfEnding;
	}
	public void setDateOfEnding(String dateOfEnding) {
		this.dateOfEnding = dateOfEnding;
	}
	public String getDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Tenant [id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + ", email=" + email + ", aadhaarNumber="
				+ aadhaarNumber + ", photo=" + Arrays.toString(photo) + ", familyNembers=" + familyNembers
				+ ", flatType=" + flatType + ", flatFloor=" + flatFloor + ", advanceAmount=" + advanceAmount
				+ ", advanceStatus=" + advanceStatus + ", rentAmount=" + rentAmount + ", rentAmountStatus="
				+ rentAmountStatus + ", ebBill=" + ebBill + ", ebBillStatus=" + ebBillStatus + ", dateOfJoining="
				+ dateOfJoining + ", dateOfEnding=" + dateOfEnding + ", deleteUser=" + deleteUser + ", user_id="
				+ user_id + "]";
	}
		
}
