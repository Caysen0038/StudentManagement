var token=window.parent.app.userToken
var dataTable;
$(function(){
	init(token);
})

function init(token){
	dataTable=new DataTable({
		target:"#data-table",
		flip:loadData,
		drop:dataDrop
	})
	var n=$(".table-container .page-selector").val();
	loadData(1,n);
	
}

function loadData(page,per){
	$.ajax({
		url:"/gateway/logs/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.logs);
					$(r.data.logs).each(function(i){
						dataTable.records()[i].time=new Date(parseInt(r.data.logs[i].time)).toLocaleString();
					})
					
					dataTable.page(r.data.page);
					break;
				default:
					alert("数据加载失败");
			}
			
		},
		error:function(e){
			
		}
	})
}


function dataDrop(data){
	$.ajax({
		url:"/gateway/logs",
		type:"delete",
		data:{id:data.id},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					HTTPUtil.refresh()
					break;
				default:
					alert(r.code);
					console.log(r);
			}
		},
		error:function(e){
			alert("请求错误");
		}
	})
}

function dataDelete(){
	var data=dataTable.selectData();
	if(data==null){
		alert("未选择数据");
		return;
	}
	var dialog=new Confirm({
		title:"请确认",
		content:"确定删除?",
		onOK:function(){
			dataDrop(data);
			},
		}).show();
}
