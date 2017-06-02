$('#tableList').bootstrapTable({  
    columns: [{  
        field: 'id',  
        title: '序号',  
    }, {  
        field: 'year',  
        title: '年度',  
    }, {  
        field: 'month',  
        title: '月份',  
    },{  
        field: 'creDate',  
        title: '日期',  
    },{  
        field: 'merBasicId',  
        title: '商户id',  
    },{  
        field: 'merName',  
        title: '商户名称',  
    },{  
        field: 'categoryTypeName',  
        title: '商户类型',  
    },{  
        field: 'city',  
        title: '城市',  
    },{  
        field: 'area',  
        title: '区域',  
    },{  
        field: 'tradeAreaName',  
        title: '商圈',  
    }],//页面需要展示的列，后端交互对象的属性  
    pagination: true,  //开启分页  
    sidePagination: 'server',//服务器端分页  
    pageNumber: 1,//默认加载页  
    pageSize: 20,//每页数据  
    pageList: [20, 50, 100, 500],//可选的每页数据  
    queryParams: function (params) {  
        return {  
        	startDate: $("#txtStartDate").val(),  
        	endDate: $("#txtEndDate").val(),  
        	merName: $("#txtMerName").val(),  
            pageSize: params.limit,  
            offset: params.offset  
        }  
    },//请求服务器数据时的参数  
    url: '' //服务器数据的加载地址  
}); 