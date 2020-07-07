var token=window.parent.app.userToken
var app=new Vue({
	el:"#app",
	data:{
		user:window.parent.app.user,
	},
})
$(function(){
	init();
})

function init(){
	// $.ajax({
	// 	url:"/gateway/teachers",
	// 	type:"GET",
	// 	dataType:"json",
	// 	beforeSend:function(r){
	// 		r.setRequestHeader("user-token", token);  
	// 	},
	// 	success:function(r){
	// 		switch(r.code){
	// 			case "200":
	// 				app.teacher=r.data;
	// 				break;
	// 			default:
	// 				//alert("数据加载失败,"+r.data);
	// 				break;
				
	// 		}
	// 	},
	// 	error:function(e){
			
	// 	}
	// })
}