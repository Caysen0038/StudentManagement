
function CourseTable(ops){
	const defaults={
		target:".course-table",
		mode:"show",
		onEdit:function(data,p){},
	};
	var config=$.extend(defaults,ops);
	var target=$(config.target);
	var sectionMap=new Map();
	init();
	
	function init(){
		if(config.mode=="edit"){
			target.find(".course-row td").bind("click",onClick)
		}
	}
	
	
	this.load=function(array){
		jsonToTable(array)
	}
	
	this.add=function(data){
		addCourseToTable(data);
	}
	
	this.export=function(){
		return tableToJson();
	}
	
	this.drop=function(p){
		var i=parseInt(p)
		var length=target.find(".course-row").length
		var tr=i%length;
		var td=Math.floor(i/length);
		var temp=target.find(".course-row:eq("+tr+")").find("td:eq("+td+")")
		temp.html("");
	}
	
	this.clear=function(){
		target.find(".course-row").each(function(i){
			$(this).find("td").each(function(j){
				$(this).html("");
			})
		})
		sectionMap.clear();
	}
	
	function tableToJson(){
		var map=new Map();
		var n=target.find(".course-row").length;
		target.find(".course-row").each(function(i){
			$(this).find("td").each(function(j){
				var info=$(this).html();
				if(info!=null && info!=""){
					var base=j*n+i;
					var course=sectionMap.get(base);
					//console.log(course)
					// var temp=info.split("<br>");
					var key=course.name+"-"+course.cid;
					if(map.get(key)!=null){
						map.get(key).section+=","+base;
					}else{
						// var course={}
						// course.name=temp[0];
						// course.teacher=temp[1];
						// course.week=temp[2];
						course.section=base;
						// course.cid=sectionMap.get(base).cid;
						map.set(key,course);
					}
					
				}
			})
		})
		var list=[];
		map.forEach(function(c){
			list.push(c);
		})
		return list;
	}
	
	function jsonToTable(json){
		json.forEach(function(data){
			addCourseToTable(data);
		})
	}
	
	function addCourseToTable(data){
		var section=new String(data.section).split(",");
		section.forEach(function(p){
			if(!isNaN(p)){
				var i=parseInt(p);
				sectionMap.set(i,copyCourse(data));
				var length=target.find(".course-row").length
				var tr=i%length;
				var td=Math.floor(i/length);
				var temp=target.find(".course-row:eq("+tr+")").find("td:eq("+td+")")
				temp.html(data.name+"<br>"+data.teacher+"<br>"+data.week)
			}
		})
	}
	
	function onClick(e){
		var html=$(this).html();
		var p=getTdPoint($(this));
		if(html==null || html==""){
			config.onEdit(null,p);
		}else{
			var temp=html.split("<br>")
			var course={}
			course.name=temp[0];
			course.teacher=temp[1];
			course.week=temp[2];
			course.section=p
			config.onEdit(course,p)
		}
	}
	
	function getTdPoint(td){
		var tr=$(td).closest(".course-row");
		var i=$(tr).index()-1;
		if(i>10){
			i--;
		}
		if(i>5){
			i--;
		}
		return i+$(td).index()*target.find(".course-row").length;
	}
	
	function copyCourse(data){
		var course={}
		for(var key in data){
			course[key]=data[key];
		}
		return course;
	}
}