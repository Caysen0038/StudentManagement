

function SlipPanel(ops){
	this.defaults={
		target:".a-slip-panle",
		orientation:"right",
		background:"#F0F4F8",
		interval:500,
		autoClose:true,
		onOpen:defaultOpenPanelEvent,
		onClose:defaultClosePanelEvent,
		onDestory:defaultDestoryPanelEvent,
	}
	this.id="a-slip-"+new Date().getTime();
	this.containerHtml="<div id='{{id}}'></div>";
	this.container=null;
	// 覆盖默认参数
	this.config=$.extend(this.defaults,ops);
	
	// 目标容器
	this.target=$(this.config.target).show();
	
	this.containerCSS={
		general:{
			"position":"fixed",
			"z-index":"3000",
		},
	}
	
	
	// 初始化
	this.containerHtml=this.containerHtml.replace("{{id}}",this.id);
	// 加入至document
	$("body").append(this.containerHtml);
	// panel容器
	this.container=$("body #"+this.id);
	this.container.append($(this.target).prop("outerHTML")).css({
		"background":this.config.background
	}).css(this.containerCSS.general);
	
	$(this.target).remove();
	this.target=$(this.container).find(this.config.target);
	var mt=parseInt((this.target).css("margin-top").replace("px"));
	var mb=parseInt((this.target).css("margin-bottom").replace("px"));
	var ml=parseInt((this.target).css("margin-left").replace("px"));
	var mr=parseInt((this.target).css("margin-right").replace("px"));
	var temp={
		width:$(this.target).width()+ml+mr,
		height:$(this.target).height()+mt+mb,
		orien:this.config.orientation.toLowerCase()
	}
	this.containerCSS.custom=getConfigCSS(temp);
	this.container.css(this.containerCSS.custom.normal);
	//this.container.css(this.css.open)
	
	this.show=false;
	this.running=false;
	
	if(this.config.autoClose){
		var src=this
		$(document).click(function(e){
			if(src.show){
				var w=$(src.container).width();
				var h=$(src.container).height();
				var left = $(src.container).offset().left;
				var top = $(src.container).offset().top;
				var x=e.clientX;
				var y=e.clientY;
				//console.log(w+"\t"+h+"\n"+left+"\t"+top+"\n"+x+"\t"+y)
				if(x>left && x<left+w && y>top && y<top+h){
					//console.log(w+"\t"+h+"\n"+left+"\t"+top+"\n"+x+"\t"+y)
				}else{
					src.close()
				}
			}
		})
	}
	
	// 打开
	this.open=function(){
		if(this.running || this.show)
			return;
		this.running=true;
		var src=this;
		this.show=true;
		$(this.container).animate(this.containerCSS.custom.open,this.config.interval,function(){
			src.running=false;
			src.config.onOpen()
		})
		
	}
	
	// 关闭
	this.close=function(){
		if(this.running || !this.show)
			return;
		this.running=true;
		this.show=false;
		var src=this;
		$(this.container).animate(this.containerCSS.custom.close,this.config.interval,function(){
			src.running=false;
			src.config.onClose()
		})
		
	}
	
	// 销毁
	this.destory=function(){
		if(this.running)
			return;
		this.running=true;
		$(this.container).remove();
		this.config.onDestory()
		this.running=false;
		
	}
	
	// 判断是否打开
	this.isOpen=function(){
		return this.show;
	}
	
	// 根据方向获取数据
	function getConfigCSS(ops){
		var param={};
		var w=document.body.clientWidth  ;
		var h=document.body.clientHieght  ;
		switch(ops.orien){
			case "top":
				param.normal={
					"left":"0",
					"right":"0",
					"top":(-ops.height)+"px",
					"height":(ops.height)+"px"
				},
				param.open={
					"top":0+"px"
				},
				param.close={
					"top":(-ops.height)+"px"
				}
				break;
			case "bottom":
				param.normal={
					"left":"0",
					"right":"0",
					"bottom":(-ops.height)+"px",
					"height":(ops.height)+"px",
				},
				param.open={
					bottom:0+"px"
				},
				param.close={
					bottom:("-"+ops.height)+"px"
				}
				break;
			case "left":
				param.normal={
					"top":"0",
					"bottom":"0",
					"left":(-ops.width)+"px",
					"width":(ops.width)+"px"
				},
				param.open={
					"left":0+"px"
				},
				param.close={
					"left":(-ops.width)+"px"
				}
				break;
			case "right":
				param.normal={
					"top":"0",
					"bottom":"0",
					"right":(-ops.width)+"px",
					"width":ops.width+"px"
				},
				param.open={
					"right":0+"px"
				},
				param.close={
					"right":(-ops.width)+"px"
				}
				break;
			default:
				param.normal={
					"top":"0",
					"bottom":"0",
					"right":(-ops.width)+"px",
					"width":ops.width+"px"
				},
				param.open={
					"right":0+"px"
				},
				param.close={
					"right":(-ops.width)+"px"
				}
		}
		return param;
	}
	
	
	// 默认打开事件
	function defaultOpenPanelEvent(){
		//console.log("打开")
	}
	// 默认关闭事件
	function defaultClosePanelEvent(){
		//console.log("关闭")
	}
	
	// 默认销毁事件
	function defaultDestoryPanelEvent(){
		//console.log("销毁")
	}
}