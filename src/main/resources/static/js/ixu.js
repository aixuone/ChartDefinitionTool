var _base = {
	basePath : "http://192.168.60.185:8080/",//服务器地址
	userId : 1, //用户id
	dataSetID:'',
	xinfo :[], //保存 候选字段列表

}
var CHART;//表单

$(function(){
	getSYS();
});

	var getSYS = function(){
		//登录成功 获取系统DBS列表
		$.ajax({
			type: 'GET',
			url: _base.basePath + "dataSets/"+_base.userId,
			dataType: 'json',
			data:{
				token: 'atoken'
			},
			success: function(res) { //返回list数据并循环获取
				
				if(res.success!='true'){
					alert('获取数据集列表失败。');
					return;
				}
				console.log('根据用户权限获取数据集列表成功');
				console.log(res);
				// 隐藏loading
				$("#_loading").hide();
				var select = $("#DBSselect");
				var options = '<option>请选择数据集</option>';
				var data = res.data;
				for (var i = 0; i < data.length; i++) {
					options += ("<option value='" + data[i].dataSetID + "'>" +
						data[i].dataSetName + "："+data[i].dataSetType+"</option>");
				}
				select.html(options);
			},
			error: function(err) {
				console.log('获取数据集列表失败,原因:' + err);
			}
		});
	}

	//选择DBS后 出现系统下层表  onchange事件
	// $("#DBSselect").bind('onchange',DBSselect());
	function DBSselect() {
		//出现loading
		$("#_loading").show();
		var e = $("#DBSselect");
		$('#DBSselect option:eq(0)').attr('disabled', 'disabled').addClass('disabeld');
		 dataSetID = e.val();
		$.ajax({
			type: 'GET',
			url: _base.basePath + "dataSets/"+dataSetID+"/columns",
				dataType: 'json',
			data: {
				dataSetID: dataSetID
			},
			success: function(res) { //返回list数据并循环获取
				if(!res){
					alert('获取数据集字段列表失败。');
					return;
				}			
				console.log('获取数据集字段列表成功');
				console.log(res);
				_base.xinfo = res;//保存字段列表
				// 隐藏loading
				$("#_loading").hide();
				
			},
			error: function(err) {
				console.error('获取数据表失败,原因:' + err);
			}
		});
		
	}


	// 添加候选字段列表
	var INFOLIST = function(e){
			var XINFO = $(e);
			var options = '<select name="" id="XINFOselect" class="span12" onchange="XINFOselect(this)"><option>请选择数据表</option>';
			for (var i = 0; i < _base.xinfo.length; i++) {
				options += ("<option value='" + _base.xinfo[i].columnID + "'>" +
					_base.xinfo[i].columnName + "</option>");
			}
			options += '</select>';
			XINFO.removeClass("hide").html(options);	
	}





//表单设置窗口
	//打开表单设置窗口
	var showSetChartWin = function(showtype,option,filter){
		if (showtype){
			var $set = $('#chartset');
			if (showtype == 'add') {
				$set.empty();
				var chartset = document.getElementById('eg_chartset').innerHTML;
				$set.append(chartset);

				INFOLIST($(".chartValue"));
				INFOLIST($(".chartX"));
				INFOLIST($('.chartfilter'))
				$('.chartSeries').each(function(index, el) {
					INFOLIST($(this));
				});
				caozuo();
			}
		}
		if (option) {

		}
		if (filter) {

		}
		$('#chartset').slideDown();
	}
	//关闭 表单设置窗口
	var hideSetChartWin = function(){
		$('#chartset').hide();
	}
	//绑定 添加系列 添加筛选条件 操作
	var caozuo = function(){
		//添加 系列 绑定事件
		$('#seriesAdd').on('click',function(){
			var serires = document.getElementById('eg_series').innerHTML;
			$('#serieslist').append(serires);
			var e = $('#serieslist').find(".chartSeries").last();
			INFOLIST(e);
		});	
		//添加 筛选条件 绑定事件
		$('#filterAdd').on('click',function(){
			var serires = document.getElementById('eg_filter').innerHTML;
			$('#filterlist').append(serires);
			var e =$('#filterlist').find(".chartfilter").last();
			INFOLIST(e);
		});	
		//生成表单按钮 绑定事件
		$('#setChart').on('click', function(event) {
			event.preventDefault();
			/* Act on the event */
			setChart();
		});	
	}
	
	//点击 生成表单 按钮
	var setChart = function(){
		var $id = write_id;

		//整理 值 字段
		var value = [{
			columnID: $('.chartValue').val(),
			columnDispType: $('.typeValue').val(),
			columnPolymer:$('.chartValueOpt').val()
		}]
		 
		var x = [{
			columnID : $('.chartX').val(),
			columnPolymer : $('.chartXOpt').val()
		}];
		var series = [];
		$('#serieslist>div').each(function(index, el) {
				var _o = {
					columnID : $(this).find('.chartSeries')[0].value,
					columnPolymer : $(this).find('.chartSeriesOpt')[0].value
				};
				series.push(_o);
				
		});

		var o = {
			write_id : write_id,//正在编辑的表单DOM的id
			chart_id : '',
			userID: userId,
			dataSetID : dataSetID,
			title : $("#chartTitle").val(),
			theme : $('#chartTheme').val(),
			value : value,
			x : x,
			series: series,			
		};		
		var opt = isSave($id);
		if (opt.type=='edit') {
			CHART[index] = o;
		}
		if(opt.type=='add'){
			createChart(o);		
		}
		
	}
	//创建新的表单
	var createChart = function(o){
		$.ajax({
			type: 'POST',
			url: _base.basePath + "/charts/data",
			dataType: 'json',
			data: o,
			success: function(res) { //返回list数据并循环获取
				if(!res){
					alert('获取数据集字段列表失败。');
					return;
				}			
				console.log('获取数据集字段列表成功');
				console.log(res);
				o.chart_id = res.chartID;
				CHART.push(o);//本地存储				
			},
			error: function(err) {
				console.error('获取数据表失败,原因:' + err);
			}
		});
	}
	//修改表单 填写设置表单
	var editChartOpt = function(id){
		write_id = id;
		for(i in CHART){
			if (CHART[i].write_id == write_id){
				var o = CHART[i];
				$("#chartTitle").val(o.title);
			 	$('#chartTheme').val(o.theme);
			 	$('#DBSselect option').each(function(index, el) {
			 		if (o.dataSetID ==$(this).val()){
			 			$(this).attr('selected',true);
			 		}
			 	});
			 	$('.chartValue').val(o.value[0].columnID);
			 	$('.chartValueOpt').val(o.value[0].columnPolymer);
				$('.chartX').val(o.x[0].columnID)
				$('.chartXOpt').val(o.x[0].columnPolymer)
				for (var i in series) {
					if (i>0) {
						$('#seriesAdd').click();
					}
					$('#serieslist>div').last().find('.chartSeries').val(o.series[i].columnID);
					$('#serieslist>div').last().find('.chartSeriesOpt').val(o.series[i].columnPolymer);
				}

				return;
			}
		}
		console.log('本地查无此表单的保存数据');
	}

	// 判断本地是否存储了 该表单设置
	var isSave = function($id){
		for(i in CHART){
			if(CHART[i].id == $id){
				return {index : i , type : 'edit'};
			} 
			return { type : 'add'}
		}		
	} 



//数据穿透

var shoot={
	arr:[
		// {
		// 	element: ['dom','dom'],//绑定的DOM
		// 	value : 'value'//监听的字段
		// }
	],
	val :1
};
Object.defineProperties(shoot,{
	values:{
        configurable:true,
        enumerable:true,
        writable:true,
        value:1
    },
    elements:{
        configurable:true,
        enumerable:true,
        writable:true,
        value:1
    },
	setting :{
		enumerable:true,
        get:function (){
			chartRefresh(this.elements);
			return;
        },
        set:function (val){
			if(!val){
				console.log('没有穿透字段数据传入系统');
				return ;
			}
			this.elements = val;
			console.log(this.elements);
			chartRefresh(this.elements);
			return;
        }
	}
});
var chartRefresh = function(arr){
	console.log(++shoot.val);
}