package com.lifuz.seckill.web;

import com.lifuz.seckill.dto.Exposer;
import com.lifuz.seckill.dto.SeckillExecution;
import com.lifuz.seckill.dto.SeckillResult;
import com.lifuz.seckill.entity.Seckill;
import com.lifuz.seckill.enums.SeckillStatEnum;
import com.lifuz.seckill.exception.RepeatKillException;
import com.lifuz.seckill.exception.SeckillCloseException;
import com.lifuz.seckill.exception.SeckillException;
import com.lifuz.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 秒杀控制器
 * <p>
 * 作者：李富
 * 邮箱：lifuzz@163.com
 * 时间：2016/7/18 15:29
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String List(Model model) {

        List<Seckill> seckills = seckillService.getSeckillList();
        model.addAttribute("list", seckills);

        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable("seckillId") Long seckillId) {

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

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId) {

        SeckillResult<Exposer> result = null;

//        logger.error(seckillId + "");

        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());

        }

        return result;

    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable("seckillId") Long seckillId,
                                                     @PathVariable("md5") String md5,
                                                     @CookieValue(value = "phone", required = false) Long phone) {

        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e1) {

            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEATE_KILL);
            return new SeckillResult<SeckillExecution>(false, execution);

        } catch (SeckillCloseException e) {

            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(false, execution);

        } catch (SeckillException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);

            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false, execution);


        }


    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time() {
        Date nowTime = new Date();
        return new SeckillResult<Long>(true, nowTime.getTime());
    }

}
