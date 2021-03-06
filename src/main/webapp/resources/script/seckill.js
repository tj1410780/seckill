//存放主要交互逻辑js代码
// javascript 模块化(package.类.方法)

//逻辑是：首先执行detail：{}里的init初始化函数，在这里面验证是否手机号登录，如果登录了，调用countDown进行倒计时判断
//countDown里判断秒杀是已经结束了还是未开始还是正在进行中
//如果countDown里判断秒杀未开始，则进入倒计时，倒计时结束时调用handlerSeckill暴露秒杀地址，暴露秒杀地址是访问链接/{seckillId}/exposer调用后端控制器函数exposer
//如果countDown里判断秒杀已经开始，则直接调用handlerSeckill暴露秒杀地址
//在handlerSeckill里执行秒杀操作，即访问链接/{seckillId}/{md5}/execution调用后端控制器的秒杀函数execute
var seckill = {
		//封装秒杀相关ajax的url
		URL : {
			now : function() {
				return '/seckill/time/now';
			},
			exposer : function(seckillId) {
				return '/seckill/' + seckillId + '/exposer';
			},
			execution : function(seckillId, md5) {
				return '/seckill/' + seckillId + '/' + md5 + '/execution';
			}
		},
		//验证手机号
		validatePhone : function(phone) {
			if (phone && phone.length == 11 && !isNaN(phone)) {  //直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
				return true;
			} else {
				return false;
			}
		},
		
		handlerSeckill: function (seckillId, node) {
	        //获取秒杀地址,控制显示器,执行秒杀
	        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
	        $.post(seckill.URL.exposer(seckillId), {}, function(result) {
	            //在回调函数种执行交互流程
	            if (result && result['success']) {
	                var exposer = result['data'];
	                if (exposer['exposed']) {
	                    //开启秒杀
	                    //获取秒杀地址
	                    var md5 = exposer['md5'];
	                    //绑定一次点击事件
	                    $('#killBtn').one('click', function () {
	                        //执行秒杀请求
	                        //1.先禁用按钮
	                        $(this).addClass('disabled');//,<-$(this)===('#killBtn')->
	                        //2.发送秒杀请求执行秒杀
	                        $.post(seckill.URL.execution(seckillId, md5), {}, function (result) {
	                            if (result && result['success']) {
	                                var killResult = result['data'];
	                                var state = killResult['state'];
	                                var stateInfo = killResult['stateInfo'];
	                                //显示秒杀结果
	                                node.html('<span class="label label-success">' + stateInfo + '</span>');
	                            }
	                        });
	                    });
	                    node.show();
	                } else {
	                    //未开启秒杀(浏览器计时偏差)
	                    var now = exposer['now'];
	                    var start = exposer['start'];
	                    var end = exposer['end'];
	                    seckill.countDown(seckillId, now, start, end);
	                }
	            } else {
	                console.log('result: ' + result);  //网页控制台打印
	            }
	        });

	    },
		
		countDown : function(seckillId, startTime, endTime, nowTime) {
			var seckillBox = $('#seckill-box');
			if (nowTime > endTime) {
				seckillBox.html('秒杀已结束！');
			} else if (nowTime < startTime) {
				//秒杀未开始，计时事件绑定
				var killTime = new Date(startTime + 1000);  //防止时间偏移
				seckillBox.countdown(killTime, function(event) {
					//时间格式
					var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
					seckillBox.html(format);
				}).on('finish.countdown',function(){
					//时间完成后回调事件					
					seckill.handlerSeckill(seckillId, seckillBox);
				});
			} else {
				seckill.handlerSeckill(seckillId, seckillBox);
			}
			
		},
		//详情页秒杀逻辑
		detail : {
			//详情页初始化
			init : function(params) {
				//手机验证和登录
				//规划我们的交互流程
				//在cookie中查找手机号码
				var killPhone = $.cookie('killPhone');
				if (!seckill.validatePhone(killPhone)) {
					//绑定phone
					//控制输出
					var killPhoneModal = $('#killPhoneModal');
					//显示弹出层
					killPhoneModal.modal({
						show:true,  //显示弹出层
						backdrop:'static', //禁止位置关闭
						keyboard:false  //关闭键盘事件
					});
					$('#killPhoneBtn').click(function(){
						var inputPhone = $('#killPhoneKey').val();
						if (seckill.validatePhone(inputPhone)) {
							$.cookie('killPhone',inputPhone,{expires:1,path:'/seckill'});  //电话号码放入cookie，1天后过期
							//验证通过，刷新页面  
	                        window.location.reload();  
	                    } else {  
	                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);  
						} 
						
					})
				}
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				var seckillId = params['seckillId'];
				//已经登录，计时交互
				$.get(seckill.URL.now(), {}, function(result){
					if (result && result['success']) {
						var nowTime = result['data'];
						seckill.countDown(seckillId, startTime, endTime, nowTime);
					} else {
						console.log('result:' + result);
						alert('result' + result);
					}
				})
			
			}
		}
}		
