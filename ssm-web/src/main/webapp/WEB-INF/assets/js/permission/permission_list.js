$(function(){
	var permission = {
		
		//添加URL
		addURL : "",
		updateURL : "",
		deleteURL : "",	
		searchTableURL: "listPermission",
			
		init : function(){
			
			this.initBootstrapTable();
			this.bindEvents();
		},
		initBootstrapTable : function(){
			var that = this;
			$('#tableList').bootstrapTable({
				url : that.searchTableURL, // 服务器数据的加载地址
				method : 'post',
				columns : [ {
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
					
				}, {
					title : '操作',
					align : 'left',
					valign:'middle',
					width : '20%',
					formatter: function (value, row, index) {
						var viewOporator = "<a href='#' class='btn btn-primary btn-xs view'><span class='fa fa-eye'></span>查看</a> ";
						var updateOperator = "<button class='btn btn-info btn-xs edit'><span class='fa fa-edit'></span>修改</button> ";
						var removeOperator = "<button  class='btn btn-warning btn-xs cancel'><span class='fa fa-trash'></span>删除</button> "
						var htmlStr = viewOporator + updateOperator +removeOperator;
                        return htmlStr;  
                    },
                    events : {
                    	'click .edit' : function(e,value,row,index){
                    		 
                    		
                    	},
                    	'click .cancel' : function(e,value,row,index){
                    		var message = "你确定要删除权限【"+ row.permission +"】吗？"
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
                    		        	that.doDelete(dialogRef,url);
                    		            
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
						permission :  "",
						description : "",
						limit : params.limit,
						offset : params.offset,
						sortName : params.sort ,
						sortOrder: params.order ,
					}
				}
			});
			
			
			
		},
		bindEvents : function(){
			
			
			
		},
		doSave : function(){
			
			
			
		},
		buildOldData : function(){
			
			
		},
		
		doUpdate : function(){
			
			
			
		},
		
		doDelete :function(dialogRef,url){
			$.ajax({
	        	url : url,
	        	type : "POST",
	        	success : function(data) {
	        		dialogRef.close();
	        		$.NOTIFY.show();
	    			$("#tableList").bootstrapTable('refresh');
	        		
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