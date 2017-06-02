/**
 * 
 */
package com.alibaba.alisonar.dto;

/**
 * @author wb-zxx263018
 *
 */
public class EmployeeDTO {

	private Integer empId;
	private Integer deptId;
	private String empName;
	private String gender;
	private String email;
	private String deptName;
	
	

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	
	

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [empId=" + empId + ", deptId=" + deptId + ", empName=" + empName + ", gender=" + gender
				+ ", email=" + email + ", deptName=" + deptName + "]";
	}
	
	

}
