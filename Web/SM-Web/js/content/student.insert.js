var token=window.parent.app.userToken
const app=new Vue({
	el:"#app",
	data:{
		student:{
			name:null,
			sex:'男',
			grade:new Date().getFullYear(),
		},
		clazzs:null,
		insts:null,
	},
	methods:{
		filter:filterEvent,
	}
});
$(function(){
	init(token);
	$(".insert-button").click(dataInsert)
})

function init(token){
	initData();
}

function initData(){
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
					var grade=$(".grade").val();
					loadClazzs(app.insts[0].iid,grade)
					break;
				default:
					console.log(r);
			}
		},
		error:function(e){
			alert("请求错误");
		}
	})
	

	
}

function filterEvent(){
	var iid=$(".inst").val();
	var grade=$(".grade").val();
	loadClazzs(iid,grade)
}

function loadClazzs(iid,grade){
	$.ajax({
		url:"/gateway/clazzs",
		type:"get",
		data:{iid:iid,grade:grade},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.clazzs=r.data;
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

function dataInsert(){
	app.student.iid=$(".inst").val()
	app.student.cid=$(".clazz").val()
	var data=app.student;
	$.ajax({
		url:"/gateway/students",
		type:"POST",
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
