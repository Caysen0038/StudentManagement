
const token=window.parent.app.userToken;
const app=new Vue({
	el:"#app",
	data:{
		teachers:[],
		students:[],
		insts:[],
		clazzs:[],
		courses:[],
		scores:[],
		type:0,
		iid:"",
		cid:"",
		sid:"",
		tid:"",
		coid:"",
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
			$.ajax({
				url:"/gateway/scores/rollback",
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
								data.status=1
							}else{
								alert("打回失败");
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
		},
		rollbackCanceled:function(e){
			var i=$(e.target).attr("index");
			var data=this.scores[i]
			$.ajax({
				url:"/gateway/scores/rollback",
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
							if(r.data){
								data.status=2
							}else{
								alert("拒绝失败");
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
	},
	watch:{
		type:function(){
			switch(parseInt(this.type)){
				case 0:
					
				case 1:
				case 2:
			}
		},
		iid:function(){
			
			switch(parseInt(this.type)){
				case 0:
					loadClazzs();
					break;
				case 1:
					loadTeachers();
					break;
			}
		},
		cid:function(){
			loadStudents()
		},
		coid:function(){
			loadScoreList()
		},
		tid:function(){
			loadScoreList()
		},
		sid:function(){
			loadScoreList()
		},
		term:function(){
			loadScoreList();
		}
	}
})

$(function(){
	loadInsts();
	loadCourses();
})

function loadInsts(){
	$.ajax({
		url:"/gateway/insts",
		type:"get",
		data:{},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.insts=r.data;
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

function loadCourses(){
	$.ajax({
		url:"/gateway/courses/filter",
		type:"get",
		data:{},
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

function loadTeachers(){
	$.ajax({
		url:"/gateway/teachers/filter",
		type:"get",
		data:{iid:app.iid},
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
			}
		},
		error:function(e){
			alert("请求错误");
		}
	})
}

function loadClazzs(){
	$.ajax({
		url:"/gateway/clazzs",
		type:"get",
		data:{iid:app.iid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.clazzs=r.data;
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

function loadStudents(){
	$.ajax({
		url:"/gateway/students/filter",
		type:"get",
		data:{iid:app.iid,cid:app.cid},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			switch(r.code){
				case "200":
					app.students=r.data;
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
	var data={term:app.term}
	var field="";
	switch(parseInt(app.type)){
		case 0:
			field="sid";
			break;
		case 1:
			field="tid";
			break;
		case 2:
			field="coid";
			break;
	}
	data[field]=app[field];
	if(data[field]==null || data[field]==""){
		return;
	}
	$.ajax({
		url:"/gateway/teachers/scores",
		type:"get",
		data:data,
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
