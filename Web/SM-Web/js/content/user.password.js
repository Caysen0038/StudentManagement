const token=window.parent.app.userToken;
const app=new Vue({
	el:"#app",
	data:{
		password:"",
		ensure:"",
		cue:"",
	},
	methods:{
		save:modifyPassword,
	}
})

function modifyPassword(){
	app.cue="";
	if(app.password=="" 
		|| app.password!=app.ensure){
		app.cue="请正确输入密码";
		return;
	}
	if(app.password.length<6){
		app.cue="密码长度应大于6";
		return;
	}
	let pwd=new Digest().MD5(app.password);
	$.ajax({
		url:"/gateway/users",
		type:"patch",
		data:{password:pwd,},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("修改成功");
					app.password="";
					app.ensure="";
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