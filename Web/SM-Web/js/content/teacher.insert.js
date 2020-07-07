var token=window.parent.app.userToken
const app=new Vue({
	el:"#app",
	data:{
		teacher:{
			name:null,
			sex:'男',
			iid:null,
		},
		insts:null,
	},
	methods:{
		
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

function dataInsert(){
	app.teacher.iid=$(".inst").val()
	var data=app.teacher;
	$.ajax({
		url:"/gateway/teachers",
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
