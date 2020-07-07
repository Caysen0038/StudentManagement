var token=window.parent.app.userToken
var app=new Vue({
	el:"#app",
	data:{
		teacher:null,
		edit:false,
	},
	methods:{
		save:modify,
	}
})
$(function(){
	init();
})

function init(){
	$.ajax({
		url:"/gateway/teachers",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.teacher=r.data;
					break;
				default:
					//alert("数据加载失败,"+r.data);
					break;
				
			}
		},
		error:function(e){
			
		}
	})
}

function edit(src){
	var data=$(src).text();
	var col=$(src).attr("column");
	var content="<input type='text' value='"+data+"' orig='"+data+"' class='edit-input' "+
		"onblur='editBlur(this)' column='"+col+"'/>"
	$(src).html(content);
	$(src).find("input").focus();
	app.edit=true;
}

function editBlur(src){
	var col=$(src).attr("column");
	var data=$(src).val();
	var orig=$(src).attr("orig");
	if(data==orig){
		app.edit=false;
	}else{
		app.teacher[col]=data;
	}
	$(src).parent().text(data);
}

function modify(){
	$.ajax({
		url:"/gateway/teachers",
		type:"PUT",
		data:app.teacher,
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					alert("修改成功")
					break;
				default:
					alert("修改失败,"+r.data);
					console.log(r);
					break;
				
			}
			app.edit=false;
		},
		error:function(e){
			
		}
	})
}