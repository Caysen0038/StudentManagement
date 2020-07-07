
const token=window.parent.app.userToken;
const app=new Vue({
	el:"#app",
	data:{
		status:0,
		scores:[],
		term:"2020.2",
		max:{
			course:"",
			score:0,
		},
		min:{
			course:"",
			score:0,
		},
		avg:{
			score:0,
		},
	},
	methods:{
		termChanged:function(){
			loadScoreList();
		},
	},
	watch:{
		scores:function(){
			if(this.scores.length==0){
				this.max={
					course:"",
					score:0,
				}
				this.min={
					course:"",
					score:0,
				}
				this.avg={
					score:0,
				}
			}
			var max=this.scores[0];
			var min=this.scores[0];
			var avg=this.scores[0].score;
			for(var i=1;i<this.scores.length;i++){
				if(this.scores[i].score>max.score){
					max=this.scores[i];
				}
				if(this.scores[i].score<min.score){
					min=this.scores[i];
				}
				avg+=this.scores[i].score;
			}
			this.avg.score=(avg/this.scores.length).toFixed(1);
			this.max=max;
			this.min=min;
		}
	}
})

$(function(){
	loadScoreList();
})

function loadScoreList(){
	$.ajax({
		url:"/gateway/students/scores",
		type:"get",
		data:{term:app.term},
		dataType:"json",
		beforeSend:function(r){
			r.setRequestHeader("user-token", token);  
		},
		success:function(r){
			console.log(r)
			switch(r.code){
				case "200":
					app.status=1;
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
