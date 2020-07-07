const token=window.parent.app.userToken
const dataTable=new DataTable({
		target:"#data-table",
		flip:loadData,
		edit:dataChanged,
		drop:dataDrop
	});
const app=new Vue({
	el:"#header-control",
	data:{
		type:0,
		insts:[],
		clazzs:[],
		teachers:[],
		students:[],
		iid:"",
		cid:"",
		sid:"",
		tid:"",
		term:"2020.2",
	},
	methods:{
		
	},
	watch:{
		type:function(){
			loadAllData();
		},
		sid:function(){
			loadAllData();
		},
		tid:function(){
			loadAllData();
		},
		iid:function(){
			this.clazzs=[]
			this.students=[]
			this.teachers=[]
			switch(parseInt(this.type)){
				case 0:
					loadClazzs(this.iid);
					break;
				case 1:
					loadTeachers(this.iid);
					break;
				case 2:
					loadClazzs(this.iid);
					break;
			}
		},
		cid:function(){
			switch(parseInt(this.type)){
				case 0:
					loadStudents(this.cid);
					break;
				case 2:
					loadAllData();
					break;
			}
		}
	}
})

$(function(){
	init(token);
	loadInsts()
})

function init(token){
	var n=$(".table-container .page-selector").val();
	loadData(1,n);
}

function loadInsts(){
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
					app.insts=r.data;
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

function loadClazzs(iid){
	$.ajax({
		url:"/gateway/clazzs",
		type:"get",
		data:{iid:iid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.clazzs=r.data
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

function loadTeachers(iid){
	$.ajax({
		url:"/gateway/teachers/filter",
		type:"get",
		data:{iid:iid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.teachers=r.data;
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

function loadStudents(cid){
	$.ajax({
		url:"/gateway/students/filter",
		type:"get",
		data:{cid:cid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.students=r.data;
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

function loadAllData(){
	var n=$(".table-container .page-selector").val();
	loadData(1,n)
}

function loadData(page,per){
	switch(parseInt(app.type)){
		case 0:
			if(app.sid!=null && app.sid!=""){
				requestDataWithOutPage({
					sid:app.sid
				},"/gateway/courses/students");
			}else{
				dataTable.records(new Array())
			}
			break;
		case 1:
			if(app.tid!=null && app.tid!=""){
				requestDataWithOutPage({
					tid:app.tid
				},"/gateway/courses/teachers");
			}else{
				dataTable.records(new Array())
			}
			break;
		case 2:
			if(app.cid!=null && app.cid!=""){
				requestDataWithOutPage({
					cid:app.cid
				},"/gateway/courses/clazzs");
			}else{
				dataTable.records(new Array())
			}
			break;
	}
	
}

function requestDataWithOutPage(data,url){
	$.ajax({
		url:url,
		type:"GET",
		data:data,
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					dataTable.records(r.data);
					dataTable.page({
						page:1,
						total:1,
						per:r.data.length,
					});
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
		url:"/gateway/courses",
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
	var data={
		id:data.id
	};
	var url;
	switch(parseInt(app.type)){
		case 0:
				url="/gateway/courses/students";
			break;
		case 1:
				url="/gateway/courses/teachers";
			break;
		case 2:
				url="/gateway/courses/clazzs";
			break;
	}
	$.ajax({
		url:url,
		type:"delete",
		data:data,
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("删除成功");
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
