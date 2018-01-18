package com.mengdi.web;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mengdi.dto.Exposer;
import com.mengdi.dto.SeckillExecution;
import com.mengdi.dto.SeckillResult;
import com.mengdi.entity.Seckill;
import com.mengdi.enums.SeckillStateEnum;
import com.mengdi.exception.RepeatKillException;
import com.mengdi.exception.SeckillCloseException;
import com.mengdi.exception.SeckillException;
import com.mengdi.service.SeckillService;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SeckillService seckillService;
		
	//获取列表页
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		//list.jsp + model = ModelAndView
		List<Seckill> seckills = seckillService.getSeckillList();
		model.addAttribute("list", seckills);
		return "list";		///WEB-INF/jsp/list.jsp
	}
	
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {		
		if (seckillId == null) {
			return "redirect:/seckill/list";			
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}		
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	//是否暴露秒杀地址
	@RequestMapping(value = "/{seckillId}/exposer", 
			method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody   //千万不能遗忘，否则报404错误
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
			return result;		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
			return result;
		} 	
	}
	
	//执行秒杀
	@RequestMapping(value = "/{seckillId}/{md5}/execution", 
			method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
			@PathVariable("seckillId") long seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value = "killPhone", required = false) Long userPhone) {
		if (userPhone == null) {
			return new SeckillResult<SeckillExecution>(false, " user don't login in");
		}
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		} catch (RepeatKillException e) {
			logger.error(e.getMessage(), e);
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage(), e);
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.End);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		} catch (SeckillException e) {
			logger.error(e.getMessage(), e);
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.Inner_ERROR);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		}		
	}
	//获取系统时间
	@RequestMapping(value = "/time/now", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
}
