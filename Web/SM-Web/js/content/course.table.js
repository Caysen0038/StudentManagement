const token=window.parent.app.userToken
const app=new Vue({
	el:"#app",
	data:{
		courses:[],
		type:0,
		term:"2020.2",
	},
	methods:{
		
	},
	watch:{
		type:function(){
			switchType();
		},
		term:function(){
			loadData(0,0)
		}
	}
})
const courseTable=new CourseTable({
		mode:"show",
	});
$(function(){
	init(token);
})

function init(token){
	//var n=$(".table-container .page-selector").val();
	loadData(0,0);
	switchType();
}
function switchType(){
	if(app.type==0){
		$(".course-table").show();
		$(".course-list").hide();
	}
	if(app.type==1){
		$(".course-table").hide();
		$(".course-list").show();
	}
}

function loadData(page,per){
	courseTable.clear();
	$.ajax({
		url:"/gateway/courses/",
		type:"GET",
		data:{term:app.term},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					for(var i=0;i<r.data.length;i++){
						r.data[i].name=r.data[i].course
						r.data[i].week=r.data[i].week+"周"
					}
					app.courses=r.data;
					courseTable.load(r.data)
					break;
				default:
					alert("数据加载失败");
			}
			
		},
		error:function(e){
			
		}
	})
	
}