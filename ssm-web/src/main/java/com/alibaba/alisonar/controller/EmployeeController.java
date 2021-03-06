/**
 * 
 */
package com.alibaba.alisonar.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.alisonar.domain.Department;
import com.alibaba.alisonar.domain.Employee;
import com.alibaba.alisonar.dto.EmployeeDTO;
import com.alibaba.alisonar.dto.ResultDTO;
import com.alibaba.alisonar.enumeration.IsDeletedEnum;
import com.alibaba.alisonar.factory.ResultDTOFactory;
import com.alibaba.alisonar.service.DepartmentService;
import com.alibaba.alisonar.service.EmployeeService;
import com.github.pagehelper.PageInfo;

/**
 * @author wb-zxx263018
 *
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;
	
	
	@ModelAttribute
	public void setEmployee(Integer id, Model model) {
		if (id != null) {
			model.addAttribute("employee", employeeService.findOne(id));
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String empPage(Model model) {
		List<Department> depts = departmentService.listAllDepts();
		logger.info("depts===>{}", depts);
		model.addAttribute("depts", depts);
		return "employee/emp_list";

	}

	@RequestMapping(value = "/listEmps", method = RequestMethod.GET)
	@ResponseBody
	public PageInfo<Employee> listEmps(Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		logger.info("pageIndex===>", pageNum);
		return employeeService.listEmpDTOByPage(pageNum);

	}

	@RequestMapping(value = "/saveEmp", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> saveEmp(@Valid Employee employee,BindingResult result) {
		logger.info("employee===>{}", employee);
		employee.setIsDeleted(IsDeletedEnum.NO.getCode());
		employeeService.saveEmp(employee);
		return ResultDTOFactory.toAck(null);

	}
	
	@RequestMapping(value = "/getEmp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<EmployeeDTO> getEmpById(@PathVariable Integer id){
		logger.info("id===>{}", id);
		return ResultDTOFactory.toAck(employeeService.getEmpById(id));
		
	}
	
	
	@RequestMapping(value = "/checkEmpName", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkEmpName(String empName){
		if(employeeService.countEmpByname(empName) > 0){
			return false ;
		}
		return true;
	}
	
	
	@RequestMapping(value = "/updateEmp", method = RequestMethod.PUT)
	@ResponseBody
	public ResultDTO<String> updateEmp(@ModelAttribute("employee") @Valid Employee employee){
		employeeService.updateEmp(employee);
		return ResultDTOFactory.toAck(null);
	}
	
	

	@RequestMapping(value = "/deleteEmp/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultDTO<String> deleteEmp(@PathVariable Integer id){
		employeeService.deleteEmp(id);
		return ResultDTOFactory.toAck(null); 
		
	}
	
	@RequestMapping(value = "/multiDeleteEmps", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> multiDeleteEmps(@RequestParam(value = "ids[]") Integer[] ids){
		employeeService.multiDeleteEmps(ids);
		return ResultDTOFactory.toAck(null); 
		
	}
	
	@RequestMapping(value = "/multiDeleteEmps2", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> multiDeleteEmps2(String ids){
		employeeService.multiDeleteEmps2(ids);
		return ResultDTOFactory.toAck(null); 
		
	}
	

}
