$(function(){
	var user = {
		//添加变量
		saveURL : "saveUser",
		updateURL : "updateUser",
		deleteURL : "deleteUser",	
		searchTableURL: "listAuthUser",
		init : function(){
			//角色
			$('#searchForm select[name="roles"').select2({
				placeholder : '请选择角色',
				allowClear : true,
				multiple : true,
			}).val(null).trigger("change");
			
			//开始和结束时间
			$('#searchForm input[name="csDate"').datetimepicker({
				format : 'yyyy-mm-dd',
				language : 'zh_CN',
				minView : 2,  //最精确的时间:0->分，2->日
				autoclose : true,
				clearBtn:true,
				//todayBtn : true,
			}).on('changeDate',function(e){  
				$('#searchForm input[name="ceDate"').datetimepicker('setStartDate',e.date);  
			}); 
			
			$('#searchForm input[name="ceDate"').datetimepicker({
				format : 'yyyy-mm-dd',
				language : 'zh_CN',
				minView : 2,
				autoclose : true,
				clearBtn:true,
				//todayBtn : true,
			}).on('changeDate',function(e){  
				$('#searchForm input[name="csDate"').datetimepicker('setEndDate',e.date);  
			});
			
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
					field : 'username',
					title : '用户名',
					align : 'center',
					valign:'middle',
					
				},{
					field : 'email',
					title : '邮件名',
					align : 'center',
					valign:'middle',
					sortable : true,
				},{
					field : 'createDate',
					title : '创建时间',
					align : 'center',
					valign:'middle',
					sortable: true, 
				},{
					field : 'rolesDesc',
					title : '角色类型',
					align : 'center',
					valign:'middle',
					
				},{
					field : 'isDeleted',
					title : '用户状态',
					align : 'center',
					valign:'middle',
					formatter: function (value, row, index) {
						if(row.isDeleted=="1") return "禁用";
						return "启用";
					}
					
				}, { //添加需要展示列
					title : '操作',
					align : 'left',
					valign:'middle',
					formatter: function (value, row, index) {
						/*var viewOporator = "<a href='#' class='btn btn-primary btn-xs'><span class='fa fa-wrench'></span>授权</a>";*/
						var updateOperator = "<button class='btn btn-info btn-xs edit'><span class='fa fa-edit'></span>编辑</button> ";
						var removeOperator = "<button  class='btn btn-warning btn-xs cancel'><span class='fa fa-trash'></span>删除</button> "
						var resetPass = "<button  class='btn btn-primary btn-xs reset'><span class='fa fa-unlock-alt'></span>重置密码</button> ";
						var htmlStr = updateOperator + resetPass;
						
						if(row.isDeleted=="0"){
							htmlStr += removeOperator;
						}
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
                            			$("#editDialog>form input[name='username']").val(row.username);
                            			$("#editDialog>form input[name='email']").val(row.email);
                            			$("#editDialog>form input[name='isDeleted'][value="+row.isDeleted+"]").prop("checked",true);
                            			var roles =row.rolesDesc == null ? null : row.rolesDesc.split(",");
                            			$('#editDialog select[name="roles"').select2({
                            				placeholder : '请选择角色',
                            				allowClear : true,
                            				multiple : true,
                            			}).val(roles).trigger("change");
                            			
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
            			        					username : {
            			        						required: true,
            			        	                    remote : {
            			        							url: "checkUsername",     //后台处理程序
            			        						    type: "post",               //数据发送方式
            			        						    dataType: "json",           //接受数据格式   
            			        						    cache:false,				
            			        				            async:false,				//同步校验
            			        						    data: {                     //要传递的数据
            			        						    	username: function() {
            			        						            return $("#editDialog>form input[name='username']").val();
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
            			        					username : {
            			        						remote : '该用户已经存在！'
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
                    		var message = "你确定要删除用户【"+ row.username +"】吗？"
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
                    	'click .reset' : function(e,value,row,index){
                    		//添加删除message
                    		var message = "确定要重置用户【"+ row.username +"】的密码？\n重置后的密码为:123456！"
                    		var data = {id : row.id};
                    		BootstrapDialog.show({
                    		    title: '提示',
                    			closable:false ,
                    			message : message,
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
                    		        	that.resetPassword(dialogRef,data);
                    		        }
                    		    }]
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
						username :  $('#searchForm input[name="username"').val(),
						email :  $('#searchForm input[name="email"').val(),
						isDeleted :  $('#searchForm input:radio[name="isDeleted"]:checked').val(),//单选框的值
						csDate : $('#searchForm input[name="csDate"').val(), 
						ceDate :  $('#searchForm input[name="ceDate"').val(),
						roles :  $('#searchForm select[name="roles"').select2("val"),
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
			//添加事件
			$("#addBtn").on('click',function(){
				var message = $("#addFormHtml").html();
				message = message.trim().replace(/\n/g,'');
				message = "<div id='addDialog'>"+message + "</div>"
				BootstrapDialog.show({
			            title: '添加',
			            message: message,
			            onshown: function(dialogRef){
			            	
			            	$("#addDialog>form select[name='roles']").select2({
			    				placeholder : '请选择角色',
			    				allowClear : true,
			    				multiple : true,
			    			}).val(null).trigger("change");
			            	
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
			        			var addFormValidator=$("#addDialog > form").validate({
			        				rules : {
			        					
			        					username : {
			        						required: true,
			        	                    remote : {
			        							url: "checkUsername",     //后台处理程序
			        						    type: "post",               //数据发送方式
			        						    dataType: "json",           //接受数据格式   
			        						    cache:false,				
			        				            async:false,				//同步校验
			        						    data: {                     //要传递的数据
			        						        username: function() {
			        						            return $("#addDialog form input[name='username']").val();
			        						        }
			        						    }
			        						},
			        					},
			        					email : {
			        						required: true,
			        						email :true,
			        					}
			        					
			        				},
			        				messages : {
			        					username : {
			        						remote : '该用户已经存在！'
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
			
			//刷新事件
			$("#refleshBtn").on('click', function() {
				//清空查询表单内容
				$("#searchForm")[0].reset();
				$('#searchForm select[name="roles"').select2({
					placeholder : '请选择角色',
					allowClear : true,
					multiple : true,
				}).val(null).trigger("change");
				$("#tableList").bootstrapTable('refresh');

			});
			
			$("#uploadBtn").on('click', function() {
				var message = '<a href="getTemplateExcel" >下载导入模板</a>'+
					'<input type="file" name="fileInput" id="fileInput"  class="file-loading" />'
				BootstrapDialog.show({
		            title: '文件上传',
		            message: message,
		            onshown: function(dialog){
		            	$("#fileInput").fileinput({
		                    language: 'zh', //设置语言
		                    uploadUrl:"saveAuthUserByExcel", //上传的地址
		                    allowedFileExtensions: ['xls', 'xlsx'],//接收的文件后缀
		                    uploadAsync: true, //默认异步上传
		                    showUpload:true, //是否显示上传按钮
		                    showRemove :true, //显示移除按钮
		                    showPreview :true, //是否显示预览
		                    showCaption:false,//是否显示标题
		                    browseClass:"btn btn-primary", //按钮样式    
		                    dropZoneEnabled: false,//是否显示拖拽区域
		                   //minImageWidth: 50, //图片的最小宽度
		                   //minImageHeight: 50,//图片的最小高度
		                   //maxImageWidth: 1000,//图片的最大宽度
		                   //maxImageHeight: 1000,//图片的最大高度
		                   //maxFileSize:0,//单位为kb，如果为0表示不限制文件大小
		                   //minFileCount: 0,
		                    maxFileCount:1, //表示允许同时上传的最大文件个数
		                    enctype:'multipart/form-data',
		                    validateInitialCount:true,
		                    previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",
		                    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		               }).on("fileuploaded", function (event, data, previewId, index){
		            	   dialog.close();
		            	   if(data.code==200){
			            	   $("#tableList").bootstrapTable('refresh');
		            	   }else{
		            		   $.NOTIFY.show('保存失败',data.message,'danger')
		            	   }
		               });
		            }
		        });
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
		
		resetPassword :function(dialogRef,data){
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
		}
	};
	user.init();
});