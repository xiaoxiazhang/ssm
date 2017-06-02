$(function() {
	var emp = {
		// 变量
		listUrl : "listEmps", 
		saveUrl : "saveEmp",
		updateUrl : "updateEmp",
		deleteUrl : "deleteEmp",
		multiDeleteUrl : "multiDeleteEmps",
		checkAddUniqueUrl : "checkEmpName",
		currentPage : 1, //默认当前页
		
		// 初始化
		init : function() {
			// 初始化表格数据
			this.buildPageData(1,true);
			
			this.bindEvent();

		},
		bindEvent : function(){
			var that = this;
			
			//添加表单校验
			var addFormValidator=$("#addForm").validate({
				rules : {
					empName : {
						required: true,
						rangelength: [4,10],
	                    remote : {
							url: that.checkAddUniqueUrl,//后台处理程序
						    type: "post",               //数据发送方式
						    dataType: "json",           //接受数据格式   
						},
					},
					email : {
						required: true,
						email : true,
						
					},
					gender : {
						required: true
						
					},
					deptId : {
						required: true
					}
				},
				messages : {
					empName : {
						required: '员工名不能为空',
						rangelength : '员工名字符范围【4-10】',
						remote : '员工名已存在'
					},
					email : {
						required: '请输入邮件名',
						email : '请输入正确的邮件名格式',
					},
					gender : {
						required: '性别不能为空',
					},
					deptId : {
						required: '部门不能为空',
					}
				},
			});
			
			//保存
			$("#save_btn").on('click',function(){
				var btn = $(this);
				if(addFormValidator.form()){
					btn.button('loading');
					$.ajax({
						url : that.saveUrl,
						method : "POST",
						data : $("#addForm").serialize(),
						dataType : "json",
						success : function(data){
							
							if(data.code===200){
								btn.button('reset');
								$("#addModal").modal('hide');
								that.buildPageData(that.currentPage,true);
								that.showSuccess();
							}else{
								that.showError(data.message);
							}
						},
					
					});
				}
				
			});
			
			//弹出窗隐藏式清空弹出窗表单内容
			$("#addModal").on("hidden.bs.modal", function() {  
				$('#addForm')[0].reset();
				addFormValidator.resetForm();
				
			});
			
			//修改按钮绑定事件
			$("tbody").on('click','.btn-update',function(){
				var id = $(this).attr("btn_id");
				$.getJSON('getEmp/'+ id,function(data){
					//获取指定员工的值，并设置给updateModal
					$("#updateForm input[name='id']").val(data.data.empId);
					$("#updateForm input[name='empName']").val(data.data.empName);
					$("#updateForm input[name='email']").val(data.data.email);
					$("#updateForm input[name='gender'][value='" + data.data.gender + "']").prop("checked", "checked");
					$("#updateForm select[name='deptId']").val(data.data.deptId);
					$("#updateModal").modal('show');
				});
				
			});
			
			//更新表单校验
			var updateFormValidator=$("#updateForm").validate({
				rules : {
					email : {
						required: true,
						email : true,
					},
					gender : {
						required: true
						
					},
					deptId : {
						required: true
					}
				},
				messages : {
					email : {
						required: '请输入邮件名',
						email : '请输入正确的邮件名格式',
					},
					gender : {
						required: '性别不能为空',
					},
					deptId : {
						required: '部门不能为空',
					}
				},
			});
			
			//更新
			$("#update_btn").on('click',function(){
				var btn = $(this);
				if(updateFormValidator.form()){
					btn.button('loading');
					$.ajax({
						url : that.updateUrl,
						type : "PUT",
						data : $("#updateForm").serialize(),
						dateType : "json",
						success : function(data){
							if(data.code===200){
								btn.button('reset');
								$("#updateModal").modal('hide');
								that.buildPageData(that.currentPage,true);
								that.showSuccess();
								
							}else{
								that.showError(data.message);
							}
						}
					});
				}
			});
			
			$("#updateModal").on("hidden.bs.modal", function() {  
				updateFormValidator.resetForm();
				
			});
			
			//删除按钮绑定事件
			$("tbody").on('click','.btn-delete',function(){
				var id = $(this).attr("btn_id");
				BootstrapDialog.show({
        		    title: '提示',
        			closable:false ,
        			message : "你确定要删除员工",
        			type : BootstrapDialog.TYPE_WARNING,
        		    buttons: [{            
        		        label: '取消',
        		        action: function(dialogRef){    
        		            dialogRef.close(); 
        		        }
        		    },{            
        		        label: '确定',
        		        cssClass: 'btn-primary', 
        		        action: function(dialogRef){   
        		        	$.ajax({
        			        	url : that.deleteUrl + "/" + id,
        			        	type : "DELETE",
        			        	success : function(data) {
        			        		dialogRef.close();
        			        		if(data.code===200){
        			        			that.buildPageData(that.currentPage,true);
        			        			that.showSuccess();
        			        			
        			        		}else{
        			        			that.showError(data.message);
        			        		}
        			        		
        			        	},
        			        });
        		            
        		        }
        		    }]
        		});
				
			});
			
			//全选/全不选功能
			$("#check_all").click(function(){
				$(".check_item").prop("checked",$(this).prop("checked"));
			});
			
			$("tbody").on("click",".check_item",function(){
				$("#check_all").prop("checked",$(".check_item:checked").length == 10);
			});
			
			//批量删除
			$("#multi_delete_btn").on('click',function(){
				var ids = [];
				$.each($(".check_item:checked"),function(){
					var id = $(this).parents("tr").find("button.btn-update").attr("btn_id");
					ids.push(id);
				});
				if(! ids.length){
					alert("未选中删除的元素");
					return ;
				}
				BootstrapDialog.show({
        		    title: '提示',
        			closable:false ,
        			message : "你确定要删除员工们",
        			type : BootstrapDialog.TYPE_WARNING,
        		    buttons: [{            
        		        label: '取消',
        		        action: function(dialogRef){    
        		            dialogRef.close(); 
        		        }
        		    },{            
        		        label: '确定',
        		        cssClass: 'btn-primary', 
        		        action: function(dialogRef){   
        		        	var data = ids;
        		        	$.ajax({
        			        	url : that.multiDeleteUrl ,
        			        	type : "POST",
        			        	//data : {"ids" : ids.join(",")},
        			        	data : {"ids" : ids},
        			        	dataType : "json",
        			        	success : function(data) {
        			        		dialogRef.close();
        			        		that.buildPageData(that.currentPage,true);
        			        		that.showSuccess();
        			        	},
        			        });
        		            
        		        }
        		    }]
        		});
			});
			
		},
		getPaginatorOption :function(pages){
			var that = this;
			var paginatorOption = {
					bootstrapMajorVersion:3,					
					currentPage: that.currentPage, // 当前页
					totalPages: pages, // 总页数
					numberofPages: 5, // 显示的页数
					itemTexts: function(type, page, current) { // 修改显示文字
						switch(type) {
							case "first":
								return "第一页";
							case "prev":
								return "上一页";
							case "next":
								return "下一页";
							case "last":
								return "最后一页";
							case "page":
								return page;
						}
					},
					onPageClicked: function(event, originalEvent, type, page) { // 异步换页
						that.buildPageData(page);
						that.currentPage = page; //设置当前页
					},
					
				}; 
			return paginatorOption;
			
		},
		
		buildPageData : function(pageNum,isFirst){
			var that = this;
			$.getJSON(this.listUrl, {pageNum:pageNum}, function(data,status) {
				if(status ==="success"){
					//第一次访问数据的时候初始化分页插件
					if(isFirst){
						// 初始化分页标签
						$("#pagination").bootstrapPaginator(that.getPaginatorOption(data.pages));
					}
					
					//清空原来的内容
					$("#tableList tbody").empty();
					$.each(data.list,function(index,item){
						var checkBoxTd = $("<td><input type='checkbox' name='item' class='check_item'/></td>");
						var empNameTd = $("<td></td>").append(item.empName);
						var genderTd = $("<td></td>").append(item.gender=='M'?"男":"女");
						var emailTd = $("<td></td>").append(item.email);
						var deptNameTd = $("<td></td>").append(item.deptName);
						var opratorTd=$("<button class='btn btn-info btn-sm btn-update' btn_id='"+ item.empId +"'><span class='fa fa-edit'></span>修改</button>" +
								"<button  class='btn btn-warning btn-sm btn-delete' btn_id='"+ item.empId +"'><span class='fa fa-trash'></span>删除</button>");
						
						$("<tr></tr>").append(checkBoxTd)
						.append(empNameTd)
						.append(genderTd)
						.append(emailTd)
						.append(deptNameTd)
						.append(opratorTd)
						.appendTo("#tableList tbody");
					});
					
					$("#page_info_area").empty();
					$("#page_info_area").append("当前第"+data.pageNum+"页,总"+
							data.pages+"页,总"+
							data.total+"条记录");
				}else{
					that.showError(data.message);
				}

			});
			
		},
		
	    showSuccess : function(){
	    	new PNotify({
    		    title: '成功!',
    			styling : 'bootstrap3',
    		    text: '操作成功',
    		    type: 'success',
    			delay:1500,
    		});
	    },
	    
	    showError : function(message){
	    	new PNotify({
    		    title: '错误!',
    			styling : 'bootstrap3',
    		    text: message,
    		    type: 'error',
    			delay:3000
    		});
	    }

	};
	
	emp.init();

});