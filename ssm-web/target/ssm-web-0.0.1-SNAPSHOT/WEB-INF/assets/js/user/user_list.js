$(function() {
	var user = {
		// 定义变量
		$searchItem : $("#searchBtn"),
		addURL : "",
		editURL : "",
		deleteURL : "",
		// 初始化
		init : function() {
			
			//开始和结束时间
			var startTime = $('#startTime').datetimepicker({
				format : 'yyyy-mm-dd hh:ii',
				language : 'zh_CN',
				autoclose : true,
				showClear : true,
				todayBtn : true,
			// minView: "month",
			});
			var endTime = $('#endTime').datetimepicker({
				format : 'yyyy-mm-dd hh:ii',
				language : 'zh_CN',
				autoclose : true,
				showClear : true,
			});

			
			//select2多选框
			$("#grade").select2({
				placeholder : '请选择',
				allowClear : true,
				multiple : true,
				maximumSelectionLength : 2
			});
			

			//bootTable
			$('#tableList').bootstrapTable({
				url : 'list', // 服务器数据的加载地址
				method : 'post',
				columns : [ {
					title : '序号',
					align : 'center',
					valign:'middle',
					formatter: function (value, row, index) {  
                        return index+1;  
                    },
                    
				}, {
					field : 'username',
					title : '用户名',
					align : 'center',
					valign:'middle',
					sortable : true,
					cellStyle :function(value, row, index){
						var specialCss ={"color": "red"};
						if(row.username==="18868801131"){
							return {
								css : specialCss
							};
						}
						return "";
						
					},
					
				},  {
					field : 'email',
					title : '邮件名',
					align : 'center',
					valign:'middle',
					sortable : true,
				},{
					field : 'gmtCreate',
					title : '开始时间',
					align : 'center',
					valign:'middle',
					sortable: true, 
				}, {
					title : '操作',
					align : 'left',
					valign:'middle',
					width : '20%',
					formatter: function (value, row, index) {
						var viewOporator = "<a href='#' class='btn btn-primary btn-sm view'><span class='fa fa-eye'></span>查看</a> ";
						var updateOperator = "<button class='btn btn-info btn-sm edit'><span class='fa fa-edit'></span>修改</button> ";
						var removeOperator = "<button  class='btn btn-warning btn-sm cancel'><span class='fa fa-trash'></span>删除</button> "
						var htmlStr = viewOporator + updateOperator +removeOperator;
                        return htmlStr;  
                    },
                    events : {
                    	'click .edit' : function(e,value,row,index){
                    		$("#userEditModal").modal('show');
                    		$("#userEditForm  input[name='id']").val(row.id);
                    		$("#userEditForm  input[name='username']").val(row.username);
                    		$("#userEditForm  input[name='email']").val(row.email);
                    		$("#userEditForm  input[name='auFlag'][value="+row.auFlag+"]").attr("checked",true); 
                    	},
                    	'click .cancel' : function(e,value,row,index){
                    		BootstrapDialog.show({
                    		    title: '提示',
                    			closable:false ,
                    			message : "你确定要删除用户【"+ row.username +"】",
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
                    			        	url : "deleteUser/"+row.id,
                    			        	type : "POST",
                    			        	success : function(data) {
                    			        		dialogRef.close();
                    			        		new PNotify({
                    			        		    title: '成功!',
                    			        			styling : 'bootstrap3',
                    			        		    text: '操作成功',
                    			        		    type: 'success',
                    			        			delay:1000,
                    			        		});
                    			        		
                    			    			$("#tableList").bootstrapTable('refresh');
                    			        		
                    			        	},
                    			        	error : function(){
                    			        		dialogRef.close();
                    			        		new PNotify({
                    			        		    title: '错误!',
                    			        			styling : 'bootstrap3',
                    			        		    text: '服务器异常',
                    			        		    type: 'error',
                    			        			delay:5000
                    			        		});
                    			        	} 
                    			        });
                    		            
                    		        }
                    		    }]
                    		});
                    	}
                    },
				} ],// 页面需要展示的列，后端交互对象的属性
				cache : false, // 是否使用缓存 
				sortable: true, 
				sortOrder: "id desc", //排序方式
				pagination : true, // 开启分页
				sidePagination : 'server',// 服务器端分页
				pageNumber : 1,// 默认加载页
				pageSize : 10,// 每页数据
				pageList : [ 10, 20, 50, 100 ],// 可选的每页数据
				queryParams : function(params) {
					return {
						username : $("#username").val(),
						limit : params.limit,
						offset : params.offset,
						sortName : params.sort ,
						sortOrder: params.order ,
					}
				}
			});
			
			this.bindEvents();
		},

		// 绑定参数
		bindEvents : function() {
			
			var userAddFormValidator=$("#userAddForm").validate({
				rules : {
					username : {
						required: true,
	                    minlength: 2,
	                    remote : {
							url: "checkUsername",     //后台处理程序
						    type: "post",               //数据发送方式
						    dataType: "json",           //接受数据格式   
						    data: {                     //要传递的数据
						        username: function() {
						            return $("#userAddForm  input[name='username']").val();
						        }
						    }
						},
					},
					email : {
						required: true,
						email : true,
						
					}
				},
				messages : {
					username : {
						required: '请输入用户名',
						minlength : '用户名不能小于2个字符',
						remote : '用户名已存在'
					},
					email : {
						required: '请输入邮件名',
						email : '请输入正确的邮件名格式',
					}
				},
				
				
			});
			
			var userEditFormValidator=$("#userEditForm").validate({
				rules : {
					email : {
						required: true,
						email : true,
					}
				},
				messages : {
					email : {
						required: '请输入邮件名',
						email : '请输入正确的邮件名格式',
					}
				},
				
				 
			});
			
			
			//刷新事件
			$("#userReflesh").on('click', function() {
				//清空查询表单内容
				$("#userSearchForm")[0].reset();
				$("#tableList").bootstrapTable('refresh');

			});
			
			//搜索事件
			$("#userSearch").on('click',function(){
				$("#tableList").bootstrapTable('refresh');
				
			});
			
			
			//保存事件
			$("#userSave").on('click',function(){
				if(userAddFormValidator.form()){
					$("#userAddModal").modal('hide');
					$.ajax({
						url : "saveUser",
			        	type : "POST",
			        	data : $("#userAddForm").serialize(),
			        	success : function(data) {
			        		new PNotify({
			        		    title: '成功!',
			        			styling : 'bootstrap3',
			        		    text: '操作成功',
			        		    type: 'success',
			        			delay:1000,
			        		});
			        		
			    			$("#tableList").bootstrapTable('refresh');
			        		
			        	},
						error : function(){
							new PNotify({
			        		    title: '错误!',
			        			styling : 'bootstrap3',
			        		    text: '服务器异常',
			        		    type: 'error',
			        			delay:5000
			        		});
						}
					});
				}
			});
			
			$("#editUser").on("click",function(){
				if(userEditFormValidator.form()){
					$("#userEditModal").modal('hide');
					$.ajax({
						url : "editUser",
			        	type : "POST",
			        	data : $("#userEditForm").serialize(),
			        	success : function(data) {
			        		new PNotify({
			        		    title: '成功!',
			        			styling : 'bootstrap3',
			        		    text: '操作成功',
			        		    type: 'success',
			        			delay:1000,
			        		});
			        		
			    			$("#tableList").bootstrapTable('refresh');
			        		
			        	},
						error : function(){
							new PNotify({
			        		    title: '错误!',
			        			styling : 'bootstrap3',
			        		    text: '服务器异常',
			        		    type: 'error',
			        			delay:5000
			        		});
						}
					});
				}
				
			});
			
			//弹出窗隐藏式清空弹出窗表单内容
			$("#userAddModal").on("hidden.bs.modal", function() {  
				$('#userAddForm')[0].reset();
				userAddFormValidator.resetForm();
				
			});  
			
			$("#userEditModal").on("hidden.bs.modal", function() {  
				$('#userEditForm')[0].reset();
				userEditFormValidator.resetForm();
			});  
			
		},

	};

	user.init();

});