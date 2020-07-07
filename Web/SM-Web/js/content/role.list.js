var token=window.parent.app.userToken
var dataTable;
var editPanel;
var appEdit;
var authPanel;
var appAuth;
$(function(){
	init(token);
	initEditPanel();
	initAuthPanel();
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
	});
	appEdit=new Vue({
		el:"#app-edit",
		data:{
			role:{
				name:"",
			},
			edit:false,
			
		},
		methods:{
			save:function(e){
				if(this.role.name==""){
					return;
				}
				$.ajax({
					url:"/gateway/roles",
					type:"post",
					data:this.role,
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
			appEdit.edit=true;
			editPanel.open();
		}
	})
}

function initAuthPanel(){
	authPanel=new SlipPanel({
		target:".auth-container",
		orientation:"right",
		interval:300,
		background:"#146ED7",
		autoClose:false,
	});
	appAuth=new Vue({
		el:"#app-auth",
		data:{
			rid:null,
			roleAuths:null,
			edit:false,
			selectAuth:null,
			auths:null,
		},
		methods:{
			close:function(e){
				authPanel.close();
			},
			drop:function(e){
				dropAuth($(e.target.parentElement).attr("auid"));
			},
			select:function(e){
				this.selectAuth={
					name:$(e.target).find("option:selected").text(),
					auid:$(e.target).val(),
				};
			},
			add:function(e){
				if(this.selectAuth!=null){
					addAuth(this.selectAuth.auid);
				}
			},
		}
	})
}

function loadData(page,per){
	$.ajax({
		url:"/gateway/roles/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.roles);
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
		url:"/gateway/roles",
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


function dataDrop(data){
	$.ajax({
		url:"/gateway/roles",
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
	var rid=$(src).attr("target-id");
	appAuth.rid=rid;
	appAuth.roleAuths=null
	loadRoleAuthList(rid)
	if(appAuth.auths==null){
		loadAllAuthList();
	}
	if(authPanel.show){
		authPanel.close();
		setTimeout(function(){
			authPanel.open();
		},400)
	}else{
		authPanel.open();
	}
	appAuth.edit=true
}

function loadRoleAuthList(rid){
	$.ajax({
		url:"/gateway/roles/"+rid+"/auths",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appAuth.roleAuths=r.data;
					break;
				default:
					alert("数据加载失败");
			}
		},
		error:function(e){
			
		}
	})
}

function dropAuth(auid){
	$.ajax({
		url:"/gateway/roles/"+appAuth.rid+"/auths",
		type:"DELETE",
		data:{auid:auid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("权限删除w成功")
					//loadRoleAuthList()
					break;
				default:
					alert("权限数据加载失败");
			}
			
		},
		error:function(e){
			
		}
	})
}
function loadAllAuthList(){
	$.ajax({
		url:"/gateway/auths/",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appAuth.auths=r.data;
					break;
				default:
					alert("权限数据加载失败");
			}
		},
		error:function(e){
			
		}
	})
}
function addAuth(auid){
	$.ajax({
		url:"/gateway/roles/"+appAuth.rid+"/auths",
		type:"POST",
		data:{auid:auid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("权限添加成功")
					//loadRoleAuthList()
					break;
				default:
					alert("权限数据加载失败");
			}
			
		},
		error:function(e){
			
		}
	})
}