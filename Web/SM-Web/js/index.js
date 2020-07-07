const COOKIE_USER_TOKEN="user-token";
const COOKIE_HISTORY_SRC="history-src";
var app=new Vue({
			el:"#app",
			data:{
				userToken:null,
				user:null,
				menus:null,
			},
			methods:{
				logout:logout,
				login:login,
				navClick:navClickEvent,
			},
			watch:{
				userToken:init,
			},
			
		});
		
$(function(){
	
	var target=$.cookie(COOKIE_HISTORY_SRC);
	if(target!=null && target!=""){
		loadContent(target);
	}
	
	app.userToken=$.cookie(COOKIE_USER_TOKEN);
	
})

/**
 * 初始化
 * @param {Object} token
 */
function init(){
	console.log(app.userToken)
	if(app.userToken!=null){
		loadUserInfo();
		loadMenu();
	}else{
		login()
	}
	
	// 重置iframe高度,使其高度随内容自适应
	setInterval(function(){
		resizeFrame();
	},1000)
}


function loadMenu(){
	$.ajax({
		url:"/gateway/menu",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", app.userToken);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.menus=parseMenus(r.data);
					break;
				default:
					alert("菜单加载失败,"+r.data);
					break;
				
			}
		},
		error:function(e){
			
		}
	})
	
	
	/**
	 * 解析menus为json
	 * @param {Object} menus
	 */
	function parseMenus(json){
		var menus=new Array();
		var menu;
		var routes=new Array();
		for(var i=0;i<json.length;i++){
			// 扫描主菜单
			if(json[i].parentMid==0){
				menu=json[i];
				menu.sub=new Array();
				// 扫描子菜单
				for(var j=0;j<json.length;j++){
					if(json[j].parentMid==json[i].mid){
						menu.sub.push(json[j]);
					}
				}
				menus.push(menu);
			}
		}
		return menus;
	}
}


/**
 * 请求用户信息
 */
function loadUserInfo(){
	$.ajax({
		url:"/gateway/users",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", app.userToken);  
		},
		success:function(r){
			app.user=r.data;
		},
		error:function(e){
			
		}
	})
}

/**
 * 导航点击事件
 * @param {Object} 
 */
function navClickEvent(e){
	var target=$(e.target).attr("menu-target")
	
	loadContent(target);
}

function loadContent(src){
	// var ori=$("#content-frame").attr("src");
	// if(ori!=src){
		$.cookie(COOKIE_HISTORY_SRC,src)
		$("#content-frame").attr("src",src);
		
		
	// }
	
	
}

/**
 * 登录
 */
function login(){
	console.log("登录")
	window.location="/login";
}

/**
 * 登出
 */
function logout(){
	$.cookie(COOKIE_HISTORY_SRC,"")
	app.userToken=null;
	app.user==null;
	window.location="/login";
}


function resizeFrame(){
	// var iframe = document.getElementById("content-frame");
	// if(iframe.attachEvent){
	// 	iframe.attachEvent("onload", function(){
	// 		iframe.height =  iframe.contentWindow.document.documentElement.scrollHeight;
	// 	});
	// }else{
	// 	iframe.onload = function(){
	// 		iframe.height = iframe.contentDocument.body.scrollHeight;
	// 	};
	// }
}