var token=window.parent.app.userToken
var dataTable;
var appEdit;
var EditPanel;
$(function(){
	init(token);
	initEditPanel();
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

function initEditPanel(){
	editPanel=new SlipPanel({
		target:".edit-container",
		orientation:"right",
		interval:300,
		background:"#146ED7",
		autoClose:false,
	});
	appEdit=new Vue({
		el:"#app-edit",
		data:{
			student:{
				id:null,
				sid:null,
				iid:null,
				cid:null,
			},
			insts:null,
			clazzs:null,
			edit:false,
			
		},
		methods:{
			save:function(e){
				$.ajax({
					url:"/gateway/students/"+this.student.sid,
					type:"PUT",
					data:{id:this.student.id,
						iid:this.student.iid,
						cid:this.student.cid,},
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
				editPanel.close()
			},
			close:function(e){
				editPanel.close();
			},
		},
	})
}

function loadData(page,per){
	$.ajax({
		url:"/gateway/students/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.students);
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
		url:"/gateway/students/"+data.sid,
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
		url:"/gateway/students",
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
		url:"/gateway/students",
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
	window.location="/content/student.insert.html"
}

function editInfo(src){
	appEdit.edit=true;
	appEdit.student.id=$(src).parent().attr("student-id");
	appEdit.student.iid=$(src).parent().attr("student-iid");
	appEdit.student.cid=$(src).parent().attr("student-cid");
	appEdit.student.sid=$(src).parent().attr("student-sid");
	if(appEdit.insts==null || appEdit.clazzs==null){
		loadEditData();
	}
	editPanel.open();
}

function loadEditData(){
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
					appEdit.insts=r.data;
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
	$.ajax({
		url:"/gateway/clazzs",
		type:"get",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appEdit.clazzs=r.data;
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

function test(){
	alert(1)
}