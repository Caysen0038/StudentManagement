const COOKIE_USER_TOKEN="user-token";
function login(m){
	loading(true)
	$("#login-result").text("");
	var uid=$("#login-username").val();
	var param={};
	param.password=new Digest().MD5($("#login-password").val());
	$.ajax({
		url:"/gateway/users/"+uid,
		type:"POST",
		data:param,
		dataType:"json",
		//async:false,
		success:function(r){
			loading(false)
			switch(r.code){
				case "200":
					 $.cookie(COOKIE_USER_TOKEN,r.data);
					 window.location="/"
					break;
				default:
					$("#login-result").text("登录失败,"+r.data);
					break;
				
			}
			
		},
		error:function(e){
			loading(false)
		}
	})
}

function loading(flag){
	if(flag){
		$(".login-container .operate").hide();
		$(".login-container .load-gif").show();
	}else{
		$(".login-container .operate").show();
		$(".login-container .load-gif").hide();
	}
	
}