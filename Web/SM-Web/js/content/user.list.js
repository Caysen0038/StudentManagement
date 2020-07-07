var token=window.parent.app.userToken
var dataTable;
var editPanel;
var appEdit;
var appEditUser;
var editUserPanel;
$(function(){
	init(token);
	initEditPanel();
	initEditUserPanel();
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
			user:{
				username:"",
				aid:"",
				rid:"",
			},
			edit:false,
			roles:null,
			
		},
		methods:{
			save:function(e){
				if(this.user.username==""){
					return;
				}
				if(this.user.rid=="" || this.user.rid==null){
					return;
				}
				$.ajax({
					url:"/gateway/users",
					type:"post",
					data:this.user,
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
			
			
		}
	})
	$(".insert-data").bind({
		click:function(){
			appEdit.edit=true;
			if(appEdit.roles==null){
				loadRoles()
			}
			editPanel.open();
		}
	})
}

function initEditUserPanel(){
	editUserPanel=new SlipPanel({
		target:".edit-user-container",
		orientation:"right",
		interval:300,
		background:"#146ED7",
		autoClose:false,
	});
	appEditUser=new Vue({
		el:"#app-edit-user",
		data:{
			user:{
				id:null,
				rid:"",
			},
			edit:false,
			roles:null,
			
		},
		methods:{
			save:function(e){
				$.ajax({
					url:"/gateway/users",
					type:"PUT",
					data:{id:this.user.id,rid:this.user.rid,},
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
				editUserPanel.close()
			},
			close:function(e){
				editUserPanel.close();
			},
		}
	})
}

function loadData(page,per){
	$.ajax({
		url:"/gateway/users/"+page+"/"+per,
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data.users);
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
		url:"/gateway/users",
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
		url:"/gateway/users",
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

function resetPassword(src){
	var dialog=new Confirm({
		title:"请确认",
		content:"确定重置该用户密码?",
		onOK:function(){
				var uid=$(src).attr("target-uid");
				$.ajax({
				url:"/gateway/users/"+uid,
				type:"patch",
				dataType:"json",
				beforeSend:function(r){
					r.setRequestHeader("user-token", token);  
				},
				success:function(r){
					switch(r.code){
						case "200":
							alert("重置成功")
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
		}).show();
}

function editRole(src){
	appEditUser.edit=true
	appEditUser.user.id=$(src).attr("user-id");
	appEditUser.user.rid=$(src).attr("user-rid");
	editUserPanel.open()
	if(appEditUser.roles==null){
		loadRoles();
	}
}

function loadRoles(){
	$.ajax({
		url:"/gateway/roles",
		type:"get",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appEdit.roles=r.data;
					appEditUser.roles=r.data;
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