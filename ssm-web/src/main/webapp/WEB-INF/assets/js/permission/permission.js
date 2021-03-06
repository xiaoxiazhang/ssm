$(function(){
	var permission = {
		//添加变量
		saveURL : "savePermission",
		updateURL : "updatePermission",
		deleteURL : "deletePermission",	
		searchTableURL: "listAuthPermission",
		validatorOption : {
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
	        }
		},
		getFormAddValidator : function(selector){
			this.validatorOption.rules={
				permission : {
					required: true,
	                remote : {
						url: "checkPermission",     //后台处理程序
						type: "post",               //数据发送方式
						dataType: "json",           //接受数据格式   
						cache:false,				
				        async:false,				//同步校验
						data: {                     //要传递的数据
						    permission: function() {
						        return $("#addDialog form input[name='permission']").val();
						    }
						}
					},
				},
				description : {
					required: true,
				},
				orderNum : {
					digits : true
				}
			};
			this.validatorOption.messages =   {
				permission : {
					remote : '该权限已经存在！'
				}
			};
			return $(selector).validate(this.validatorOption);
		},
		
		getFormEditValidator : function(selector){
			this.validatorOption.rules={
				permission : {
					required: true,
					remote : {
						url: "checkPermission",     //后台处理程序
						type: "post",               //数据发送方式
						dataType: "json",           //接受数据格式   
						cache:false,				
						async:false,				//同步校验
						data: {                     //要传递的数据
							permission: function() {
								return $("#addDialog form input[name='permission']").val();
							},
							id : function(){
								return $("#editDialog>form input[name='id']").val();
							}
						}
					},
				},
				description : {
					required: true,
				},
				orderNum : {
					digits : true
				}
			};
			this.validatorOption.messages =   {
				permission : {
					remote : '该权限已经存在！'
				}
			};
			return $(selector).validate(this.validatorOption);
			//$("#editDialog>form")
		},
		
		init : function(){
			this.initSelect2($('#searchForm select[name="parentId"'),{multiple : true},null);
			this.initBootstrapTable();
			this.bindEvents();
		},
		
		initSelect2 :function(selector,option){
			var select2Option = {
				ajax: {
				     type:'GET',
				     url: "getSelect2ParentNode",
				     dataType: 'json',
				     delay: 400,
				     data: function (params) {
				         return {
				             searchStr: params.term, // search term 请求参数 ， 请求框中输入的参数
				         };
				     },
				     processResults: function (data, params) {
				    	 var result = [];
				         for(var i=0 ; i<data.length; i++){
				        	 var item = {};
				        	 item.id= data[i].id;
				        	 item.text=data[i].description;
				        	 result[i]=item;
				         }
					     return {results: result}
				     },
				     cache: true
				 },
				 placeholder:'请选择父节点',//默认文字提示
				 language: "zh-CN",
				 allowClear: true,//允许清空
				 //multiple : true,
				 escapeMarkup: function (markup) { return markup; }, // 自定义格式化防止xss注入
				 minimumInputLength: 1,//最少输入多少个字符后开始查询
				 formatResult: function formatRepo(repo){return repo.text;}, // 函数用来渲染结果
				 formatSelection: function formatRepoSelection(repo){return repo.text;} // 函数用于呈现当前的选择
			};
			for(var key in option){
				select2Option[key] = option[key];
			}
			selector.select2(select2Option);
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
					field : 'permission',
					title : '权限标识',
					align : 'center',
					valign:'middle',
				},{
					field : 'permUrl',
					title : '权限URL',
					align : 'left',
					valign:'middle',
				},{
					field : 'permissionLevel',
					title : '权限级别',
					align : 'center',
					valign:'middle',
				},{
					field : 'orderNum',
					title : '菜单序号',
					align : 'center',
					valign:'middle',
				},{
					field : 'menuIcon',
					title : '菜单icon',
					align : 'center',
					valign:'middle',
				},{
					field : 'description',
					title : '权限描述',
					align : 'center',
					valign:'middle',
				},{
					field : 'activeStatus',
					title : '是否启用',
					align : 'center',
					valign:'middle',
				}, { //添加需要展示列
					title : '操作',
					align : 'left',
					valign:'middle',
					width : '20%',
					formatter: function (value, row, index) {
						var viewOporator = "<a href='#' class='btn btn-primary btn-xs view'><span class='fa fa-eye'></span>查看</a>";
						var updateOperator = "<button class='btn btn-info btn-xs edit'><span class='fa fa-edit'></span>编辑</button>";
						var removeOperator = "<button  class='btn btn-warning btn-xs cancel'><span class='fa fa-trash'></span>删除</button> "
						var htmlStr =updateOperator +removeOperator;
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
            			            	$("#"+dialogRef.getId()).removeAttr("tabindex"); //显示搜索框
            			            	$("#editDialog>form input[name='id']").val(row.id);
                            			$("#editDialog>form input[name='permission']").val(row.permission);
                            			$("#editDialog>form input[name='orderNum']").val(row.orderNum);
                            			$("#editDialog>form input[name='permUrl']").val(row.permUrl);
                            			$("#editDialog>form input[name='menuIcon']").val(row.menuIcon);
                            			$("#editDialog>form input[name='description']").val(row.description);
                            			$('#editDialog>form select[name="level"] option[value="'+ row.level+'"]').prop("selected","selected");
                            			$("#editDialog>form input[name='isActive'][value="+row.isActive+"]").prop("checked",true);
                            			var data  = {parentId:row.parentId};
                            			that.buildParentNodeSelect2(data);
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
            			                	var $btn = this;
            			    				if(that.getFormEditValidator("#editDialog>form").form()){
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
						permission :  $("#searchForm input[name='permission']").val(),
						permUrl :  $("#searchForm input[name='permUrl']").val(),
						description : $("#searchForm input[name='description']").val(),
						parentNodes :  $("#searchForm select[name='parentId']").val(),
						level :  $("#searchForm select[name='level']").val(),
						isActive :  $("#searchForm select[name='isActive']").val(),
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
			            onshown: function(dialogRef){
			            	$("#"+dialogRef.getId()).removeAttr("tabindex"); //显示搜索框
			            	that.initSelect2($("#addDialog>form select[name='parentId']"),null,null);
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
			                	var $btn = this;
			                	var validator = that.getFormAddValidator("#addDialog>form");
			    				if(validator.form()){
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
				$("#tableList").bootstrapTable('refresh' , {pageNumber:1});
			});
			
			//刷新事件
			$("#refleshBtn").on('click', function() {
				//清空查询表单内容
				$("#searchForm")[0].reset();
				that.initSelect2($('#searchForm select[name="parentId"'),{multiple : true});
				$("#tableList").bootstrapTable('refresh' , {pageNumber:1});

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
		
		buildParentNodeSelect2 : function(data){
			var that = this;
			$.ajax({
				type : "get",
	        	url : "getPermissionByParentId",
	        	data : data,
	        	success : function(data) {
	        		if(data.code==200){
	        			var perm = data.data;
	        			var optionHtml = "<option value='" + perm.id +"' selected>"+ perm.description + "</option>";
	        			$('#editDialog>form select[name="parentId"]').html(optionHtml);
	        			that.initSelect2($('#editDialog>form select[name="parentId"'),null);
	        		}else{
	        			console.error("初始化父节点失败");
	        		}
	        		 
	        	},
	        	error : function(){
	        		console.error("初始化父节点失败");
	        	} 
	        });
		},
		
	};
	permission.init();
});