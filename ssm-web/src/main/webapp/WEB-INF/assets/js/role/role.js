$(function(){
	var role = {
		//添加变量
		saveURL : "saveRole",
		updateURL : "updateRole",
		deleteURL : "deleteRole",	
		searchTableURL: "listAuthRole",
		authorizeURL: "roleAuthorization",
			
		init : function(){
			this.initBootstrapTable();
			this.bindEvents();
		},
		initBootstrapTable : function(){
			var that = this;
			$("#tableList").bootstrapTable({
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
					field : 'role',
					title : '角色标识',
					align : 'center',
					valign:'middle',
					
				},{
					field : 'description',
					title : '角色描述',
					align : 'center',
					valign:'middle',
					
				}, { //添加需要展示列
					title : '操作',
					align : 'left',
					valign:'middle',
					width : '20%',
					formatter: function (value, row, index) {
						var viewOporator = "<button class='btn btn-primary btn-xs authorization'><span class='fa fa-wrench'></span>授权</button>";
						var updateOperator = "<button class='btn btn-info btn-xs edit'><span class='fa fa-edit'></span>修改</button>";
						var removeOperator = "<button  class='btn btn-warning btn-xs cancel'><span class='fa fa-trash'></span>删除</button> "
						var htmlStr = viewOporator + updateOperator +removeOperator;
                        return htmlStr;  
                    },
                    events : {
                    	'click .edit' : function(e,value,row,index){
                    		//添加历史数据
                			var message = $("#editFormHtml").html();
            				message = message.trim().replace(/\n/g,'');
            				message = "<div id='editDialog'>"+message + "</div>"
            				BootstrapDialog.show({
            			            title: '修改记录',
            			            message: message,
            			            onshown: function(dialogRef){
            			            	$("#editDialog>form input[name='id']").val(row.id);
                            			$("#editDialog>form input[name='role']").val(row.role);
                            			$("#editDialog>form input[name='description']").val(row.description);
            			            },
            			            buttons: [{
            			                label: '取消',
            			                action: function(dialog) {
            			                    dialog.close();
            			                }
            			            }, {
            			                label: '保存',
            			                cssClass: 'btn-primary	',
            			                action: function(dialog) {
            			                	//添加前端校验
            			        			var editFormValidator=$("#editDialog>form").validate({
            			        				rules : {
            			        					role : {
            			        						required: true,
            			        	                    remote : {
            			        							url: "checkRole",     //后台处理程序
            			        						    type: "post",               //数据发送方式
            			        						    dataType: "json",           //接受数据格式   
            			        						    cache:false,				
            			        				            async:false,				//同步校验
            			        						    data: {                     //要传递的数据
            			        						    	role: function() {
            			        						            return $("#editDialog>form input[name='role']").val();
            			        						        },
            			        						        id : function(){
            			        						        	return $("#editDialog>form input[name='id']").val();
            			        						        }
            			        						    }
            			        						},
            			        					},
            			        					description : {
            			        						required: true,
            			        					}
            			        				},
            			        				messages : {
            			        					role : {
            			        						remote : '该角色已经存在！'
            			        					}
            			        				},
            			        				errorPlacement : function(error, element) {  
            			        		            element.next().remove(); 
            			        		            element.after(error); //错误在下一行显示
            			        		            element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');  
            			        		            //element.closest('.form-group').append(error);   //错误信息在同一行显示
            			        		        },  
            			        		        highlight : function(element) {  
            			        		            $(element).closest('.form-group').addClass('has-error has-feedback');  
            			        		        },  
            			        		        success : function(label) {  
            			        		            var el=label.closest('.form-group').find("input");  
            			        		            el.next().remove();  
            			        		            el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');  
            			        		            label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");  
            			        		            label.remove();  
            			        		        },  
            			        			});
            			                	
            			                	var $btn = this;
            			                	editFormValidator.valid();
            			    				if(editFormValidator.form()){
            			    					$btn.disable();
            			    					that.doUpdate();
            			    					dialog.close();
            			    				}
            			                }
            			            }]
            			        });
                    	},
                    	'click .cancel' : function(e,value,row,index){
                    		//添加删除message
                    		var message = "你确定要删除角色【"+ row.role +"】吗？"
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
                    	},
                    	'click .authorization' : function(e,value,row,index){
                    		$.ajax({
                    			url : "getAllPermissionByRoleId",
                    			type : "get",
                    			data : {id : row.id},
                    			dataType : "json",
                    			success : function(data){
                    				if(data.code==200){
                    					var message = '<div class="tree well"><ul>';
                    					var level1 = data.data;
                    					for(var i=0 ; i<level1.length;i++){
                    						if(level1[i].checked){
                    							message = message + '<li class="f1"><span><i class="icon-folder-open"></i><input type="checkbox" value="'+ level1[i].permission+'" name="permission" class="level1" checked/>'+level1[i].description +'</span><ul>';
                    						}else{
                    							message = message + '<li class="f1"><span><i class="icon-folder-open"></i><input type="checkbox" value="'+ level1[i].permission+'" name="permission" class="level1" />'+level1[i].description +'</span><ul>';
                    						}
                    						var level2 = level1[i].childNodes;
                    						for(var j=0 ; j<level2.length;j++){
                    							if(level2[j].checked){
                    								message = message + '<li class="f2"> <span><i class="icon-minus-sign"></i><input type="checkbox" value="'+ level2[j].permission+'" name="permission" class="level2" checked/>'+level2[j].description +'</span> <ul><li class="f3">';
                    							}else{
                    								message = message + '<li class="f2"> <span><i class="icon-minus-sign"></i><input type="checkbox" value="'+ level2[j].permission+'" name="permission" class="level2" />'+level2[j].description +'</span> <ul><li class="f3">';
                    							}
                    							
                    							
                    							var level3 = level2[j].childNodes;
                    							for(var k=0 ; k<level3.length;k++){
                    								if(level3[k].checked){
                    									message = message + '<span><i class="icon-leaf"></i><input type="checkbox" value="'+ level3[k].permission+'" name="permission" class="level3" checked/>'+level3[k].description +'</span> ';
                    								}else{
                    									message = message + '<span><i class="icon-leaf"></i><input type="checkbox" value="'+ level3[k].permission+'" name="permission" class="level3" />'+level3[k].description +'</span> ';
                    								}
                    							}
                    							message = message + '</li></ul></li>';
                    						}
                    						message = message + '</ul></li>';
                    					}
                    					message += '</ul></div>';
                    					//var message = $("#authorizationFormHtml").html();
                        				message = message.trim().replace(/\n/g,'');
                        				message = "<div id='authorizationDialog'>"+message + "</div>"
                                		var data = {id : row.id};
                        				BootstrapDialog.show({
                                		    title: '授权',
                                			closable:false ,
                                			message : message,
                                			cssClass: 'authorizationDialog',
                                			type : BootstrapDialog.TYPE_PRIMARY,
                                		    buttons: [{            
                                		        label: '取消',
                                		        action: function(dialogRef){    
                                		            dialogRef.close(); 
                                		        }
                                		    },{            
                                		        label: '确定',
                                		        cssClass: 'btn-primary', 
                                		        action: function(dialogRef){
                                		        	var permissions =[]; 
                                		        	$('input[name="permission"]:checked').each(function(){ 
                                		        		permissions.push($(this).val()); 
                                		        	}); 
                                		        	var data = {id:row.id,permissions: permissions};
                                		        	that.doAuthorization(dialogRef,data);
                                		        }
                                		    }]
                                		});
                    				}
                    			},
                    			error :function(){
                    				
                    			}
                    		});
                    		
                    	},
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
						role :  $("#searchForm input[name='role']").val(),
						description : $("#searchForm input[name='description']").val(),
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
			//保存事件
			$("#addBtn").on('click',function(){
				var message = $("#addFormHtml").html();
				message = message.trim().replace(/\n/g,'');
				message = "<div id='addDialog'>"+message + "</div>"
				BootstrapDialog.show({
			            title: '添加',
			            message: message,
			            buttons: [{
			                label: '取消',
			                action: function(dialog) {
			                    dialog.close();
			                }
			            }, {
			                label: '保存',
			                cssClass: 'btn-primary	',
			                action: function(dialog) {
			                	//添加前端校验
			        			var addFormValidator=$("#addDialog > form").validate({
			        				rules : {
			        					role : {
			        						required: true,
			        	                    remote : {
			        							url: "checkRole",     //后台处理程序
			        						    type: "post",               //数据发送方式
			        						    dataType: "json",           //接受数据格式   
			        						    cache:false,				
			        				            async:false,				//同步校验
			        						    data: {                     //要传递的数据
			        						        role: function() {
			        						            return $("#addDialog form input[name='role']").val();
			        						        }
			        						    }
			        						},
			        					},
			        					description : {
			        						required: true,
			        					}
			        				},
			        				messages : {
			        					role : {
			        						remote : '该权限已经存在！'
			        					}
			        				},
			        				errorPlacement : function(error, element) {  
			        		            element.next().remove(); 
			        		            element.after(error); //错误在下一行显示
			        		            element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');  
			        		            //element.closest('.form-group').append(error);   //错误信息在同一行显示
			        		        },  
			        		        highlight : function(element) {  
			        		            $(element).closest('.form-group').addClass('has-error has-feedback');  
			        		        },  
			        		        success : function(label) {  
			        		            var el=label.closest('.form-group').find("input");  
			        		            el.next().remove();  
			        		            el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');  
			        		            label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");  
			        		            label.remove();  
			        		        },  
			        			});
			                	var $btn = this;
			    				if(addFormValidator.form()){
			    					$btn.disable();
			    					that.doSave();
			    					dialog.close();
			    				}
			                }
			            }]
			        });
			});
			
			//搜索事件
			$("#searchBtn").on('click',function(){
				$("#tableList").bootstrapTable('refresh');
			});
			
		},
		
		doSave : function(){
			var that = this;
			$.ajax({
				url : that.saveURL,
	        	type : "POST",
	        	data : $("#addDialog > form").serialize(),
	        	success : function(data) {
	        		$.NOTIFY.show();
	    			$("#tableList").bootstrapTable('refresh');
	        	},
				error : function(){
					$.NOTIFY.show('保存失败','服务器异常！','danger')
				}
			});
		},
		
		doUpdate : function(){
			var that = this;
			$.ajax({
				url : that.updateURL,
	        	type : "POST",
	        	data : $("#editDialog>form").serialize(),
	        	success : function(data) {
	        		$.NOTIFY.show();
	    			$("#tableList").bootstrapTable('refresh');
	        		
	        	},
				error : function(){
					$.NOTIFY.show('更新失败','服务器异常！','danger')
				}
			});
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
	        			$("#tableList").bootstrapTable('refresh');
	        		}else{
	        			$.NOTIFY.show('删除失败','服务器异常！','dange');
	        		}
	        	},
	        	error : function(){
	        		dialogRef.close();
	        		$.NOTIFY.show('删除失败','服务器异常！','dange');
	        	} 
	        });
		},
		
		doAuthorization:function(dialogRef,data){
			var that = this;
			$.ajax({
	        	url : that.authorizeURL,
	        	type : "POST",
	        	data : JSON.stringify(data),
	        	contentType:"application/json", // 指定这个协议很重要
	        	success : function(data) {
	        		dialogRef.close();
	        		if(data.code==200){
	        			$.NOTIFY.show();
	        			$("#tableList").bootstrapTable('refresh');
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
	role.init();
});