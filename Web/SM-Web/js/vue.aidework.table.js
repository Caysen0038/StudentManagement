
function DataTable(ops){
	const defaults={
			target:"#app-table",
			edit:function(data,i){},
			flip:function(page,per){},
			drop:function(data){},
			insert:function(data){},
			select:function(data,i){},
			search:function(keyword){},
		}
	const config=cover(defaults,ops);
	const appTable=new Vue({
		el:config.target,
		data:{
			records:null,
			page:{
				current:1,
				total:0,
				per:5,
			},
			selectRow:-1,
			keyword:'',
		},
		methods:{
			select:function(e){
				if(this.selectRow!=-1){
					document.getElementsByClassName("record")[this.selectRow].classList.remove("record-select");
				}
				if(e.target.tagName.toLowerCase()!="td"){
					return;
				}
				var item=e.target.parentElement;
				var n=Array.from(item.parentElement.children).indexOf(item);
				if(this.selectRow==n-1){
					this.selectRow=-1;
					return;
				}
				this.selectRow=n-1;
				config.select(this.records[this.selectRow],this.selectRow);
				item.classList.add('record-select');
			},
			edit:function(e){
				if(e.target.tagName.toLowerCase()!="td"){
					return;
				}
				if(e.target.getAttribute("editable")=="false"){
					return;
				}
				var item=e.target.parentElement;
				var n=Array.from(item.parentElement.children).indexOf(item);
				if(e.target.getAttribute("on-edit")!=null && e.target.getAttribute("on-edit").length>0 ){
					customEdit(e.target,n-1);
				}else{
					editData(e.target,n-1)
				}
			},
			pre:function(e){
				if(this.page.current>1){
					this.page.current--;
					config.flip(this.page.current,this.page.per)
					if(this.selectRow!=-1){
						document.getElementsByClassName("record")[this.selectRow].classList.remove("record-select");
						this.selectRow=-1;
					}
				}
			},
			next:function(e){
				if(this.page.current<this.page.total){
					this.page.current++;
					config.flip(this.page.current,this.page.per)
					if(this.selectRow!=-1){
						document.getElementsByClassName("record")[this.selectRow].classList.remove("record-select");
						this.selectRow=-1;
					}
				}
				
			},
			perChanged:function(e){
				this.page.per=e.target.value;
				config.flip(this.page.current,this.page.per)
			},
			drop:function(e){
				var target=e.target.parentElement.parentElement
				var n=Array.from(target.parentElement.children).indexOf(target);
				var app=this;
				if(this.records.length>=n){
					var dialog=new Confirm({
						title:"请确认",
						content:"确定删除?",
						onOK:function(){
							var data=app.records.splice(n-1,1);
							config.drop(data[0]);
							
							}
						}).show();
					
				}
			},
			insert:function(e){
				config.insert()
			},
			search:function(){
				config.search(this.keyword);
			}
		}
	})
	
	this.records=function(r){
		if(r!=null){
			appTable.records=r;
		}
		return appTable.records;
	}
	
	this.page=function(p){
		if(p!=null){
			appTable.page=p;
		}
		return appTable.page;
	}
	
	
	
	this.current=function(){
		return appTable.page.current;
	}
	
	this.per=function(){
		return appTable.page.per;
	}
	
	this.total=function(){
		return appTable.page.total;
	}
	
	this.selectData=function(){
		if(appTable.selectRow!=-1){
			return appTable.records[appTable.selectRow];
		}
		return null;
	}
	
	function cover(ops1,ops2){
		for(var key in ops1){
			try{
				if(ops2[key]!=null){
					ops1[key]=ops2[key];
				}
			}catch(e){
				continue;
			}
			
		}
		return ops1;
	}
	
	
	function editData(src,i){
		var orginal=src.innerHTML;
		var col=src.getAttribute("column");
		src.innerHTML="<input type='text' id='data-edit' class='input-text' value='"+appTable.records[i][col]+"' />";
		var editor=src.childNodes[0]
		editor.focus()
		editor.onblur=function(){
			var val=this.value;
			var change=false;
			change=val!=appTable.records[i][col];
			appTable.records[i][col]=val;
			var type=src.getAttribute("refresh");
			if(type==null || type.length==0 || type=="manual"){
				src.innerHTML=val;
			}else if(type=="auto"){
				src.innerHTML=orginal;
			}
			if(change){
				config.edit(appTable.records[i],i);
			}
		}
	}
	
	function customEdit(src,i){
		var fun=src.getAttribute("on-edit");
		if(fun.endsWith(")")){
			eval(fun.substring(0,fun.length-1)+i+")");
		}else{
			eval(fun+"("+i+")");
		}
	}
}

