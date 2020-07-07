
const token=window.parent.app.userToken;
const app=new Vue({
	el:"#app",
	data:{
		teacher:null,
		courses:[],
		scores:[],
		iid:"",
		cid:"",
		sid:"",
		courseId:-1,
		term:"2020.2",
		init:false,
	},
	methods:{
		save:function(){
			var n=0;
			for(var i=0;i<this.scores.length;i++){
				var data=this.scores[i];
				if(data.status>=2){
					n++;
					continue;
				}
				$.ajax({
					url:"/gateway/teachers/scores",
					type:"patch",
					data:data,
					dataType:"json",
					async:false,
					beforeSend:function(r){
						r.setRequestHeader("user-token", token);  
					},
					success:function(r){
						switch(r.code){
							case "200":
								if(r.data){
									n++;
									data.status=1;
								}
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
			if(n==this.scores.length){
				alert("成绩保存成功")
			}else{
				alert(n+"条成绩保存成功,"+(this.scores.length-n)+"条成绩保存失败")
			}
		},
		submit:function(){
			var n=0;
			for(var i=0;i<this.scores.length;i++){
				var data=this.scores[i];
				if(data.status>=2){
					n++;
					continue;
				}
				$.ajax({
					url:"/gateway/teachers/scores",
					type:"put",
					data:data,
					dataType:"json",
					async:false,
					beforeSend:function(r){
						r.setRequestHeader("user-token", token);  
					},
					success:function(r){
						switch(r.code){
							case "200":
								if(r.data){
									n++
									data.status=2
								}
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
			if(n==this.scores.length){
				alert("成绩提交成功")
			}else{
				alert(n+"条成绩提交成功,"+(this.scores.length-n)+"条成绩提交失败")
			}
		},
		rollback:function(e){
			var i=$(e.target).attr("index");
			var data=this.scores[i]
			console.log(data)
			$.ajax({
				url:"/gateway/teachers/scores/rollback",
				type:"put",
				data:data,
				dataType:"json",
				async:false,
				beforeSend:function(r){
					r.setRequestHeader("user-token", token);  
				},
				success:function(r){
					switch(r.code){
						case "200":
							data.status=3
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
		},
		rollbackCanceled:function(e){
			var i=$(e.target).attr("index");
			var data=this.scores[i]
			console.log(data)
			$.ajax({
				url:"/gateway/teachers/scores/rollback",
				type:"delete",
				data:data,
				dataType:"json",
				async:false,
				beforeSend:function(r){
					r.setRequestHeader("user-token", token);  
				},
				success:function(r){
					switch(r.code){
						case "200":
							data.status=2
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
	},
	watch:{
		courseId:function(){
			if(this.courseId>=0 && !isNaN(this.courseId)){
				loadScoreList();
			}
		}
	}
})

$(function(){
	loadTeacher();
})

function loadTeacher(){
	$.ajax({
		url:"/gateway/teachers",
		type:"get",
		data:{},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.teacher=r.data;
					loadCourses();
					break;
				default:
					alert(r.code);
			}
		},
		error:function(e){
			alert("请求错误");
		}
	})
}

function getSectionDesc(p){
	var day=Math.floor(p/10)+1;
	var num=p%10+1;
	return "周"+day+" 第"+num+"节";
}

function loadCourses(){
	$.ajax({
		url:"/gateway/teachers/courses",
		type:"get",
		data:{tid:app.teacher.tid,term:app.term},
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
					alert(r.code);
					console.log(r);
			}
		},
		error:function(e){
			alert("请求错误");
		}
	})
}

function loadScoreList(){
	var cid=app.courses[app.courseId].cid;
	var coid=app.courses[app.courseId].coid;
	$.ajax({
		url:"/gateway/teachers/scores",
		type:"get",
		data:{coid:coid,term:app.term,cid:cid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.scores=r.data;
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
