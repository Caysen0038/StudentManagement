var token=window.parent.app.userToken
const app=new Vue({
	el:"#app",
	data:{
		status:0,
		courses:[],
		selected:[],
		student:null,
	},
	methods:{
		listSelect:function(){
			if(!selectPanel.isOpen()){
				selectPanel.open();
			}
		},
		select:function(e){
			var i=$(e.target).attr("index");
			var course=this.courses[i];
			this.courses.splice(i,1);
			this.selected.push(course);
		},
		unselect:function(e){
			var i=$(e.target).attr("index");
			var course=this.selected[i];
			this.selected.splice(i,1);
			this.courses.push(course);
		},
		reset:function(){
			loadData();
			this.selected=[]
		},
		submit:function(){
			var n=0;
			if(this.student==null){
				alert("学生信息无效")
				return;
			}
			for(var i=0;i<this.selected.length;i++){
				var data={}
				data.sid=this.student.sid;
				data.coid=this.selected[i].coid;
				data.tid=this.selected[i].tid;
				data.term=this.selected[i].term;
				data.week=this.selected[i].week;
				data.section=this.selected[i].section;
				data.status=0;
				data.type=this.selected[i].type;
				$.ajax({
					url:"/gateway/courses/select",
					type:"post",
					data:data,
					dataType:"json",
					async:false,
					beforeSend:function(r){
						r.setRequestHeader("user-token", token);  
					},
					success:function(r){
						switch(r.code){
							case "200":
								if(r.data==true){
									n++;
								}
								console.log(data[i])
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
			if(n==data.length){
				alert("选课成功")
			}else{
				alert(n+"节选课成功");
			}
		}
	},
	watch:{
		status:function(){
			if(this.status==1){
				loadData();
			}
		}
	}
})
const selectPanel=new SlipPanel({
		target:".selected-panel",
		orientation:"left",
		interval:300,
		background:"#146ED7",
		autoClose:true,
	});
$(function(){
	initStatus();
	loadStudent();
})

function initStatus(){
	$.ajax({
		url:"/gateway/properties/filter",
		type:"GET",
		data:{name:"COURSE_SELECT"},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					if(r.data.value==0){
						app.status=0;
					}else{
						app.status=1;
					}
					break;
			}
		},
	})
}

function loadData(){
	$.ajax({
		url:"/gateway/courses/select",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.courses=r.data;
					break;
				default:
					alert("数据加载失败");
			}
			
		},
		error:function(e){
			
		}
	})
}

function loadStudent(){
	$.ajax({
		url:"/gateway/students",
		type:"GET",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.student=r.data;
					break;
				default:
					app.status=2;
			}
			
		},
		error:function(e){
			
		}
	})
}