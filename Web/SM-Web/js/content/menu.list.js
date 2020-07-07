var token=window.parent.app.userToken
var dataTable;
var appEdit;
var editPanel;
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
	// $(".table-container .delete-data").click(dataDelete);
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
			menu:{
				id:"",
				auid:"",
			},
			auths:null,
			edit:false,
		},
		methods:{
			save:function(e){
				if(this.menu.auid==""){
					return;
				}
				$.ajax({
					url:"/gateway/menus",
					type:"PUT",
					data:{id:this.menu.id,auid:this.menu.auid,},
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
			dataChange:function(){}
		}
	})
}

function loadData(page,per){
	$.ajax({
		url:"/gateway/menus/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.menus);
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
		url:"/gateway/menus",
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
					//HTTPUtil.refresh()
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


function dataDrop(data){
	$.ajax({
		url:"/gateway/menus",
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

function authManage(src){
	appEdit.edit=true;
	appEdit.menu.auid=$(src).attr("target-auid");
	appEdit.menu.id=$(src).attr("target-id");
	if(appEdit.auths==null){
		loadAuthsList();
	}
	editPanel.open()
}

function loadAuthsList(){
	$.ajax({
		url:"/gateway/auths",
		type:"get",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appEdit.auths=r.data;
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