var token=window.parent.app.userToken
var dataTable;
var appInst;
var instPanel;
$(function(){
	init(token);
	initInstPanel();
})

function init(token){
	dataTable=new DataTable({
		target:"#data-table",
		flip:loadData,
		edit:dataChanged,
		drop:dataDrop
	})
	$(".table-container .delete-data").click(dataDelete);
	$(".table-container .insert-data").click(dataInsert);
	var n=$(".table-container .page-selector").val();
	loadData(1,n);
	
}

function initInstPanel(){
	instPanel=new SlipPanel({
		target:".inst-container",
		orientation:"right",
		interval:300,
		background:"#146ED7",
		autoClose:false,
	});
	appInst=new Vue({
		el:"#app-inst",
		data:{
			teacher:{
				id:null,
				tid:null,
				iid:null,
			},
			insts:null,
			edit:false,
			
		},
		methods:{
			save:function(e){
				$.ajax({
					url:"/gateway/teachers/"+this.teacher.tid,
					type:"PUT",
					data:{id:this.teacher.id,iid:this.teacher.iid},
					dataType:"json",
					beforeSend:function(r){
						r.setRequestHeader("user-token", token);  
					},
					success:function(r){
						switch(r.code){
							case "200":
								alert("修改成功");
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
				instPanel.close()
			},
			close:function(e){
				instPanel.close();
			},
			dataChange:function(){}
		}
	})
}

function loadData(page,per){
	$.ajax({
		url:"/gateway/teachers/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.teachers);
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


function dataChanged(data,i){
	$.ajax({
		url:"/gateway/teachers/"+data.tid,
		type:"PUT",
		data:data,
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("修改成功");
					HTTPUtil.refresh()
					break;
				default:
					alert(r.code);
					console.log(r);
			}
		},
		error:function(e){
			
		}
	})
}

function dataInsert(data){
	$.ajax({
		url:"/gateway/teachers",
		type:"post",
		data:data,
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("添加成功");
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

function dataDrop(data){
	$.ajax({
		url:"/gateway/teachers",
		type:"delete",
		data:{id:data.id},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					//HTTPUtil.refresh()
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

function dataInsert(){
	window.location="/content/teacher.insert.html"
}

function editInst(src){
	appInst.edit=true;
	appInst.teacher.id=$(src).attr("teacher-id");
	appInst.teacher.iid=$(src).attr("teacher-iid");
	appInst.teacher.tid=$(src).attr("teacher-tid");
	if(appInst.insts==null){
		loadInstitute();
	}
	instPanel.open();
}

function loadInstitute(){
	$.ajax({
		url:"/gateway/insts",
		type:"get",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appInst.insts=r.data;
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