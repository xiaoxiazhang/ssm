$(function(){
	var permission = {
		
		//添加变量
		saveURL : "savePermission",
		$addBtn : $("#savePermissionBtn"),
		updateURL : "updatePermission",
		$editBtn : $("#updatePermissionBtn"),
		deleteURL : "deletePermission",	
		$tableList : $("#tableList"),
		searchTableURL: "listAuthPermission",
			
		init : function(){
			
			this.initBootstrapTable();
			this.bindEvents();
		},
		initBootstrapTable : function(){
			var that = this;
			that.$tableList.bootstrapTable({
				url : that.searchTableURL, // 服务器数据的加载地址
				method : 'post',
				columns : [{
					title : '序号',
					align : 'center',
					valign:'middle',
					formatter: function (value, row, index) {  
                        return index+1;  
                    },
                    
				}, {
					field : 'permission',
					title : '权限标识',
					align : 'center',
					valign:'middle',
					
				},{
					field : 'description',
					title : '权限描述',
					align : 'center',
					valign:'middle',
					
				}, {
					title : '操作',
					align : 'left',
					valign:'middle',
					width : '20%',
					formatter: function (value, row, index) {
						var viewOporator = "<a href='#' class='btn btn-primary btn-xs view'><span class='fa fa-eye'></span>查看</a> ";
						var updateOperator = "<button class='btn btn-info btn-xs edit' data-toggle='modal' " +
								"data-target='#editModal' data-backdrop='static'><span class='fa fa-edit'></span>修改</button> ";
						var removeOperator = "<button  class='btn btn-warning btn-xs cancel'><span class='fa fa-trash'></span>删除</button> "
						var htmlStr = viewOporator + updateOperator +removeOperator;
                        return htmlStr;  
                    },
                    events : {
                    	'click .edit' : function(e,value,row,index){
                    		 that.buildOldData(row);
                    	},
                    	'click .cancel' : function(e,value,row,index){
                    		var message = "你确定要删除权限【"+ row.permission +"】吗？"
                    		var data = {id : row.id};
                    		BootstrapDialog.show({
                    		    title: '提示',
                    			closable:false ,
                    			message : message,
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
                    		        	that.doDelete(dialogRef,data);
                    		            
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
						
						//添加查询属性值
						permission :  $("#permissionForm input[name='permission']").val(),
						description : $("#permissionForm input[name='description']").val(),
						limit : params.limit,
						offset : params.offset,
						sortName : params.sort ,
						sortOrder: params.order ,
					}
				}
			});
		},
		
		bindEvents : function(){
			
			var that = this;
			//增加前端校验
			var addFormValidator=$("#addForm").validate({
				rules : {
					permission : {
						required: true,
	                    remote : {
							url: "checkPermission",     //后台处理程序
						    type: "post",               //数据发送方式
						    dataType: "json",           //接受数据格式   
						    data: {                     //要传递的数据
						        permission: function() {
						            return $("#addForm  input[name='permission']").val();
						        }
						    }
						},
					},
					description : {
						required: true,
					}
				},
				messages : {
					permission : {
						remote : '该权限已经存在！'
					}
				},
				
			});
			
			var editFormValidator=$("#editForm").validate({
				rules : {
					permission : {
						required: true,
	                    remote : {
							url: "checkPermission",     //后台处理程序
						    type: "post",               //数据发送方式
						    dataType: "json",           //接受数据格式   
						    data: {                     //要传递的数据
						    	permission: function() {
						            return $("#editForm input[name='permission']").val();
						        },
						        id : function(){
						        	return $("#editForm input[name='id']").val();
						        }
						    }
						},
					},
					description : {
						required: true,
					}
				},
				messages : {
					permission : {
						remote : '该权限已经存在！'
					}
				},
			});
			
			//保存事件
			that.$addBtn.on('click',function(){
				var $btn = $(this);
				$btn.button('loading');
				if(addFormValidator.form()){
					that.doSave($btn);
				}
				
			});
			
			//搜索事件
			$("#searchBtn").on('click',function(){
				that.$tableList.bootstrapTable('refresh');
				
			});
			
			//弹出窗隐藏式清空弹出窗表单内容
			$("#addModal").on("hidden.bs.modal", function() {  
				$('#addForm')[0].reset();
				addFormValidator.resetForm();
				
			});  
			
			$("#editModal").on("hidden.bs.modal", function() {  
				$('#editForm')[0].reset();
				editFormValidator.resetForm();
			});  
			
			
		},
		
		doSave : function($btn){
			var that = this;
			$.ajax({
				url : that.saveURL,
	        	type : "POST",
	        	data : $("#addForm").serialize(),
	        	success : function(data) {
	        		$("#addModal").modal('hide');
	        		$btn.button('reset');
	        		$.NOTIFY.show();
	    			that.$tableList.bootstrapTable('refresh');
	        		
	        	},
				error : function(){
					$("#addModal").modal('hide');
	        		$btn.button('reset');
					$.NOTIFY.show('保存错误','服务器异常！','danger')
				}
			});
			
			
		},
		
		buildOldData : function(row){
			$("#editForm input[name='id']").val(row.id);
			$("#editForm input[name='permission']").val(row.permission);
			$("#editForm input[name='description']").val(row.description);
		},
		
		doUpdate : function(){
			
			
			
		},
		
		doDelete :function(dialogRef,data){
			var that = this;
			$.ajax({
	        	url : that.deleteURL,
	        	type : "POST",
	        	data : data,
	        	success : function(data) {
	        		dialogRef.close();
	        		if(data.code==200){
	        			$.NOTIFY.show();
	        			that.$tableList.bootstrapTable('refresh');
	        		}else{
	        			$.NOTIFY.show('删除失败','服务器异常！','dange');
	        		}
	        		
	        	},
	        	error : function(){
	        		dialogRef.close();
	        		$.NOTIFY.show('删除失败','服务器异常！','dange');
	        	} 
	        });
		}
			
	};
	permission.init();
	
});