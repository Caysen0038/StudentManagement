const token=window.parent.app.userToken;
var init={};
const properties=["COURSE_SELECT","CURRENT_TERM"];
const app=new Vue({
	el:"#app",
	data:{
		COURSE_SELECT:{
			id:-1,
			name:"COURSE_SELECT",
			value:0,
		},
		CURRENT_TERM:{
			id:-1,
			name:"CURRENT_TERM",
			value:2020.2,
		},
		switcher:{
			open:false,
			background:"gray",
			float:"left",
		},
	},
	methods:{
		courseSelectSwitcher:function(){
			this.COURSE_SELECT={
				id:this.COURSE_SELECT.id,
				name:this.COURSE_SELECT.name,
				value:this.COURSE_SELECT.value>0?0:1,
			}
			updateConfig("COURSE_SELECT")
		},
		termChanged:function(){
			updateConfig("CURRENT_TERM")
		},
	},
	watch:{
		COURSE_SELECT:function(){
			this.switcher.open=this.COURSE_SELECT.value>0?true:false;
			this.switcher.background=this.switcher.open?"#00BFFF":"gray";
			this.switcher.float=this.switcher.open?"right":"left";
		}
	}
})
$(function(){
	for(var i=0;i<properties.length;i++){
		getConfig(properties[i]);
	}
	
})

function getConfig(name){
	$.ajax({
		url:"/gateway/properties/filter",
		type:"get",
		data:{name:name},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app[name]=r.data;
					init[name]=true
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

function updateConfig(name){
	if(!init[name]){
		alert("该值尚未同步服务端")
		return;
	}
	$.ajax({
		url:"/gateway/properties",
		type:"put",
		data:app[name],
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					//app[name]=r.data;
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