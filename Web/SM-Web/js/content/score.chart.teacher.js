
const token=window.parent.app.userToken;
const app=new Vue({
	el:"#app",
	data:{
		courseMap:null,
		teachers:[],
		courses:[],
		scores:[],
		tid:"",
		coid:"",
		term:"2020.2",
		init:false,
	},
	methods:{
		
	},
	watch:{
		coid:function(){
			var app=this;
			app.teachers.length=0;
			var map=this.courseMap.get(this.coid);
			if(map!=null){
				map.forEach(function(value,key){
　　　　　　　　　　app.teachers.push(value)
　　　　　　　　 });
			}
			tid="";
			loadScoreList();
		},
		tid:function(){
			loadScoreList()
		},
		term:function(){
			loadScoreList();
		},
		scores:function(){
			loadAllChart();
		}
	}
})

$(function(){
	loadCourses();
})

function loadCourses(){
	$.ajax({
		url:"/gateway/teachers/courses",
		type:"get",
		data:{term:app.term},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			if(r.code=="200"){
				var arr=r.data;
				app.courseMap=new Map();
				app.courses.length=0;
				app.teachers.length=0;
				for(var i=0;i<arr.length;i++){
					var map=null;
					if((map=app.courseMap.get(arr[i].coid))==null){
						map=new Map();
						map.set(arr[i].tid,{
							tid:arr[i].tid,
							name:arr[i].teacher,
							})
						app.courseMap.set(arr[i].coid,map);
						
						app.courses.push({
							coid:arr[i].coid,
							name:arr[i].course,
						})
					}else{
						if(map.get(arr[i].tid)==null){
							map.set(arr[i].tid,{
								tid:arr[i].tid,
								name:arr[i].teacher,
							})
						}
					}
				}
			}else{
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
	data.tid=app.tid;
	data.coid=app.coid;
	data.status=2;
	if(data.coid==null || data.coid==""){
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

var scoreChart;
var pieChart;
function loadAllChart(){
	loadScoreChart();
	loadPieChart();
	loadMaxMinAvg();
}
function loadMaxMinAvg(){
	if(app.scores.length==0){
		$(".score-count-table").hide();
		return;
	}
	var max=app.scores[0];
	var min=app.scores[0];
	var avg=app.scores[0].score;
	for(var i=1;i<app.scores.length;i++){
		if(app.scores[i].score>max.score){
			max=app.scores[i];
		}
		if(app.scores[i].score<min.score){
			min=app.scores[i];
		}
		avg+=app.scores[i].score;
	}
	avg=(avg/app.scores.length).toFixed(1);
	console.log(avg)
	$("#score-avg").html(avg+"分");
	$("#score-max").html(max.student+"<br>"+max.score+"分");
	$("#score-min").html(min.student+"<br>"+min.score+"分");
	$(".score-count-table").show();
}

function loadScoreChart(){
	if(scoreChart==null){
		scoreChart=echarts.init(document.getElementById('score-chart'));
	}
	var option={
			title: {
				text: '课程成绩'
			},
			legend: {
				data:['成绩']
			},
			xAxis: {
				data: []
			},
			yAxis: {},
			series: [{
				name: '成绩',
				type: 'bar',
				barWidth:10,
				barMaxWidth:20,
				data: []
			}]
		};
	var xdata=[],sdata=[];
	for(var i=0;i<app.scores.length;i++){
		xdata.push(app.scores[i].student);
		sdata.push(app.scores[i].score);
	}
	option.xAxis.data=xdata;
	option.series[0].data=sdata;
	scoreChart.setOption(option)
}
function loadPieChart(){
	if(pieChart==null){
		pieChart=echarts.init(document.getElementById('pie-chart'));
	}
	var option={
			title: {
				text: '成绩分布'
			},
			series: [{
				name: '访问来源',
				type: 'pie',
				radius: '50%',
				data:[],
			}]
		};
	var map=new Map();
	for(var i=0;i<app.scores.length;i++){
		var key=Math.floor(app.scores[i].score/10);
		if(map.get(key)==null){
			map.set(key,{
				count:1,
				rate:(1/app.scores.length).toFixed(2)+"%",
			});
		}else{
			var value=map.get(key);
			map.set(key,{
				count:value.count+1,
				rate:((value.count+1)/app.scores.length).toFixed(2)+"%",
			});
		}
	}
	var dataMap=new Map();
	map.forEach(function(value,key){
		switch(parseInt(key)){
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				setResult("不及格 ",value,dataMap);
				break
			case 6:
			case 7:
				setResult("60-79  ",value,dataMap);
				break
			case 8:
				setResult("80-89  ",value,dataMap);
				break
			case 9:
				setResult("90-99  ",value,dataMap);
				break
			case 10:
				setResult("满分 ",value,dataMap);
				break
		}
	})
	var sdata=[];
	dataMap.forEach(function(value,key){
		sdata.push(value);
	})
	option.series[0].data=sdata;
	pieChart.setOption(option);
	
	
	function setResult(key,value,map){
		if(map.get(key)==null){
			map.set(key,{
				value:value.count,
				name:key+(value.count/app.scores.length).toFixed(2)*100+"%",
			})
		}else{
			var temp=map.get(key);
			map.set(key,{
				value:value.count+temp.value,
				name:key+((value.count+temp.value)/app.scores.length).toFixed(2)*100+"%",
			})
		}
	}
}