var token=window.parent.app.userToken
var dataTable;
var editPanel;
var appEdit;
var appInst;
var instPanel;
$(function(){
	init(token);
	initEditPanel();
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
	var n=$(".table-container .page-selector").val();
	loadData(1,n);
	
}

function initEditPanel(){
	editPanel=new SlipPanel({
		target:".edit-container",
		orientation:"left",
		interval:300,
		background:"#146ED7",
		autoClose:false,
	});
	appEdit=new Vue({
		el:"#app-edit",
		data:{
			clazz:{
				name:"",
				grade:"2014",
				iid:null,
			},
			insts:null,
			edit:false,
			
		},
		methods:{
			save:function(e){
				if(this.clazz.name==""){
					return;
				}
				this.clazz.iid=$(".insert-table .inst").val();
				$.ajax({
					url:"/gateway/clazzs",
					type:"post",
					data:this.clazz,
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
				editPanel.close()
			},
			close:function(e){
				editPanel.close();
			},
			dataChange:function(){}
		}
	})
	$(".insert-data").bind({
		click:function(){
			if(appEdit.insts==null){
				loadInstitute()
			}
			appEdit.edit=true;
			editPanel.open();
		}
	})
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
			clazz:{
				id:null,
				iid:null,
			},
			insts:null,
			edit:false,
			
		},
		methods:{
			save:function(e){
				$.ajax({
					url:"/gateway/clazzs",
					type:"PUT",
					data:{id:this.clazz.id,iid:this.clazz.iid},
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
		url:"/gateway/clazzs/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.clazzs);
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
		url:"/gateway/clazzs",
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

function loadInstitute(target){
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

function dataDrop(data){
	$.ajax({
		url:"/gateway/clazzs",
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

function editInst(src){
	appInst.edit=true;
	appInst.clazz.id=$(src).attr("clazz-id");
	appInst.clazz.iid=$(src).attr("clazz-iid");
	if(appInst.insts==null){
		loadInstitute();
	}
	instPanel.open();
}