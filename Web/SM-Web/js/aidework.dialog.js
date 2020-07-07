const css={
		conainer:{
			"background":"rgba(255,255,255,0.4)",
			"position":"absolute",
			"display":"none",
			"align-items":"center",
			"align-content":"center",
			"width":"100%",
			"height":"100vh",
			"left":"0",
			"right":"0",
			"top":"0",
			"bottom":"0",
		},
		dialog:{
			"width":"450px",
			// "min-height":"200px",
			// "max-height":"300px",
			// "height":"160px",
			"background":"#ffffff",
			"margin":"0 auto",
			"overflow":"hidden",
			"font-size":"14px",
			"color":"#606060",
		},
		title:{
			"width":"410px",
			"height":"40px",
			"line-height":"40px",
			"padding":"0 20px",
			"background":"#268FF1",
			"color":"#ffffff",
		},
		content:{
			"width":"410px",
			"min-height":"60px",
			// "max-height":"215px",
			// "height":"100px",
			"padding":"10px 20px",
			"text-align":"center",
		},
		buttons:{
			"width":"430px",
			"height":"40px",
			"padding":"0 10px",
		},
		OKButton:{
			"width":"70px",
			"height":"25px",
			"background":"#268FF1",
			"margin":"7px 10px",
			"cursor":"pointer",
			"text-align":"center",
			"line-height":"25px",
			
			"color":"#ffffff",
			"float":"right",
		},
		cancelButton:{
			"width":"70px",
			"height":"25px",
			"background":"#8F8F8F",
			"margin":"7px 10px",
			"cursor":"pointer",
			"text-align":"center",
			"line-height":"25px",
			"color":"#ffffff",
			"float":"right",
		}
	}
function Confirm(ops){
	const defaults={
		title:"确认框",
		content:"",
		onOK:function(){},
		onCancel:function(){},
		onClose:function(){},
		onShow:function(){},
		onDismiss:function(){},
	}
	const src=this;
	const config=cover(defaults,ops);
	const id="a-dialog-"+new Date().getTime();
	
	const container=document.createElement("div");
	document.body.appendChild(container)
	container.setAttribute("id",id);
	for(var key in css.conainer){
		document.getElementById(id).style[key]=css.conainer[key]
	}
	var dialog=document.createElement("div");
	var title=document.createElement("div");
	var content=document.createElement("div");
	var buttons=document.createElement("div");
	dialog.className="dialog";
	dialog.appendChild(title);
	dialog.appendChild(content);
	dialog.appendChild(buttons);
	for(var key in css.dialog){
		dialog.style[key]=css.dialog[key]
	}
	
	// 标题
	title.className="title";
	title.innerText=config.title
	for(var key in css.title){
		title.style[key]=css.title[key]
	}
	
	// 内容
	content.className="content";
	content.innerText=config.content;
	for(var key in css.content){
		content.style[key]=css.content[key]
	}
	
	// 按钮组
	buttons.className="buttons";
	for(var key in css.buttons){
		buttons.style[key]=css.buttons[key]
	}
	
	// 取消按钮
	var cancelButton=document.createElement("div");
	cancelButton.innerText="取消";
	for(var key in css.cancelButton){
		cancelButton.style[key]=css.cancelButton[key]
	}
	cancelButton.onclick=onCancel;
	buttons.appendChild(cancelButton)
	
	// 确认按钮
	var okbutton=document.createElement("div");
	okbutton.innerText="确认";
	for(var key in css.OKButton){
		okbutton.style[key]=css.OKButton[key]
	}
	okbutton.onclick=onOK;
	buttons.appendChild(okbutton)
	
	// 添加对话框
	container.appendChild(dialog)
	
	// 展示
	this.show=function(){
		container.style["display"]="flex";
		config.onShow()
	}
	
	// 撤销
	this.dismiss=function(){
		container.style["display"]="none";
		document.body.removeChild(container)
	}
	
	// 确认事件
	function onOK(){
		config.onOK();
		src.dismiss()
	}
	
	// 取消事件
	function onCancel(){
		config.onCancel();
		src.dismiss()
	}
	
	return this;
}



function cover(ops1,ops2){
	for(var key in ops1){
		try{
			if(ops2[key]!=null){
				ops1[key]=ops2[key];
			}
		}catch(e){
			continue
		}
		
	}
	return ops1;
}