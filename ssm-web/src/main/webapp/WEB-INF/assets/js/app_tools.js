$.NOTIFY = {
	show : function(title , text ,type){
		var title = title || "Done" ;
		var text = text || "操作成功" ;
		var type = type || "success"
		PNotify.removeAll(); //删除其他通知
		new PNotify({
		    title: title,
			styling : 'bootstrap3',
		    text: text ,
		    type: type ,
			delay:1000
		});
		
	},
		
};