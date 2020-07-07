
const token=window.parent.app.userToken;
const app=new Vue({
	el:"#app",
	data:{
		teacher:{},
		insts:[],
		teachers:[],
		iid:"",
		term:"2020.2",
	},
	methods:{
		instsChanged:function(){
			if(this.iid!=null && this.iid!=""){
				loadTeachers(this.iid);
			}
		},
		teachersChanged:function(){
			var i=$(".teacher-selector").val();
			if(i!=-1){
				this.teacher=this.teachers[i]
			}
		},
		save:function(){
			var data=table.export();
			var n=0;
			for(var i=0;i<data.length;i++){
				data[i].tid=this.teacher.tid;
				data[i].term=this.term;
				$.ajax({
					url:"/gateway/courses/teachers",
					type:"post",
					data:data[i],
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
				alert("排课成功")
			}else{
				alert(n+"节课排课成功");
			}
			
			
		},
		clear:function(){
			table.clear();
		}
	}
})
const editPanel=new SlipPanel({
		target:".edit-container",
		orientation:"left",
		interval:300,
		background:"#146ED7",
		autoClose:false,
		onClose:function(){
			$(".edit-container .course-selector").val("-1");
		}
	});
const appEdit=new Vue({
		el:"#app-edit",
		data:{
			course:{
				coid:"",
				name:"",
				teacher:"",
				tid:"",
				week:"",
				section:"",
				sectionDesc:"",
				cid:"",
			},
			edit:false,
			courses:[],
			insts:[],
			clazzs:[],
			clazz:{},
			cid:"",
			iid:"",
		},
		methods:{
			save:function(e){
				if(this.course.name==""){
					table.drop(this.course.section)
				}else{
					this.course.cid=this.cid;
					table.add(this.course);
				}
				editPanel.close()
			},
			close:function(e){
				editPanel.close();
			},
			courseChanged:function(){
				var i=$(".edit-container .course-selector").val();
				if(i!=-1){
					this.course.name=this.courses[i].name;
					this.course.coid=this.courses[i].coid;
				}else{
					this.course.name="";
				}
				
			},
			instChanged:function(){
				loadClazzs(this.iid,this.grade)
			},
			gradeChanged:function(){
				loadClazzs(this.iid,this.grade)
			}
		}
	})
	$(".insert-data").bind({
		click:function(){
			
		}
	})
const table=new CourseTable({
		mode:"edit",
		onEdit:function(data,p){
			if(app.teacher.name!=null){
				if(appEdit.courses.length==0){
					loadCourses();
				}
				appEdit.course.teacher=app.teacher.name;
				appEdit.course.tid=app.teacher.tid;
				editCourse(data,p)
			}else{
				alert("请先选择授课教师")
			}
		},
	});
$(function(){
	loadInsts();
})

function editCourse(data,p){
	appEdit.edit=true;
	if(data!=null){
		appEdit.course=data;
	}else{
		appEdit.course.name="";
		appEdit.course.week="";
		appEdit.course.section=p;
		appEdit.course.sectionDesc=getSectionDesc(p);
	}
	editPanel.open();
}

function getSectionDesc(p){
	var day=Math.floor(p/10)+1;
	var num=p%10+1;
	return "周"+day+" 第"+num+"节";
}

function loadInsts(){
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
					appEdit.insts=r.data;
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
function loadTeachers(iid){
	$.ajax({
		url:"/gateway/teachers/filter",
		type:"get",
		data:{iid:iid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.teachers=r.data;
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
function loadCourses(){
	$.ajax({
		url:"/gateway/courses/filter",
		type:"get",
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			console.log(r)
			switch(r.code){
				case "200":
					appEdit.courses=r.data;
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
function loadClazzs(iid){
	console.log(iid)
	$.ajax({
		url:"/gateway/clazzs",
		type:"get",
		data:{iid:iid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					appEdit.clazzs=r.data;
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