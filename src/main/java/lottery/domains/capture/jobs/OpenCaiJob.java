//package lottery.domains.capture.jobs;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javautils.date.Moment;
//import javautils.http.HttpClientUtil;
//import lottery.domains.capture.sites.opencai.OpenCaiBean;
//import lottery.domains.capture.utils.CodeValidate;
//import lottery.domains.capture.utils.ExpectValidate;
//import lottery.domains.content.biz.LotteryOpenCodeService;
//import lottery.domains.content.entity.LotteryOpenCode;
//import lottery.utils.StringUtil;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class OpenCaiJob
//{
//    private static final Logger logger = LoggerFactory.getLogger(OpenCaiJob.class);
//    private static final String SUB_URL = "/newly.do?token=aaa&rows=5&format=json";
//    private static final String[] SITES = { "http://wd.apiplus.net" };
//    @Autowired
//    private LotteryOpenCodeService lotteryOpenCodeService;
//    private static boolean isRuning = false;
//
//    /* Error */
//    @Scheduled(cron="0/10 * * * * *")
//    public void schedule()
//    {
//        synchronized (OpenCaiJob.class)
//        {
//            if (isRuning == true) {
//                return;
//            }
//            isRuning = true;
//        }
//        try
//        {
//            logger.debug("开始抓取open彩数据");
//
//            long start = System.currentTimeMillis();
//
//            start();
//            long spend = System.currentTimeMillis() - start;
//
//            logger.debug("完成open彩数据>>>>>>>>>>>>>>>>耗时{}", Long.valueOf(spend));
//        }
//        catch (Exception e)
//        {
//            logger.error("完成抓取OpenCai[{}]开奖数据，但处理失败，耗时{}", e);
//        }
//        finally
//        {
//            isRuning = false;
//        }
//    }
//
//    public void start()
//    {
//        for (int i = 0; i < SITES.length; i++)
//        {
//            String site = SITES[i];
//
//            logger.debug("采集开始：");
//            logger.debug("开始抓取OpenCai[{}]开奖数据", site);
//            long start = System.currentTimeMillis();
//            String url = site + "/newly.do?token=aaa&rows=5&format=json" + "&_=" + System.currentTimeMillis();
//            String result = getHttpResult(url);
//            boolean succeed = false;
//            if (StringUtils.isNotEmpty(result)) {
//                succeed = handleData(result, site);
//            }
//            long spend = System.currentTimeMillis() - start;
//            if (succeed)
//            {
//                logger.debug("成功抓取OpenCai[{}]开奖数据，并处理成功，耗时{}", site, Long.valueOf(spend));
//                break;
//            }
//            logger.warn("完成抓取OpenCai[{}]开奖数据，但处理失败，耗时{}", site, Long.valueOf(spend));
//        }
//    }
//
//    private boolean handleData(String data, String site)
//    {
//        try
//        {
//            if (!StringUtil.isNotNull(data)) {
//                return false;
//            }
//            //String code = (String) JSONObject.fromObject(data).get("code");
//            Object object = JSONObject.fromObject(data).get("data");
//            //if(object instanceof JSONArray) {
//            //JSONArray jsonArray = (JSONArray)object;
//            //foreach
//            //jsonArray.forEach((Object obj)->{
//            // if(obj instanceof JSONObject) {
//            //	  ((JSONObject)obj).put("code", code);
//            //  }
//
//            // });
//            //}
//            JSONArray array = JSONArray.fromObject(object);
//            List<OpenCaiBean> list = new ArrayList();
//            Iterator iter = array.iterator();
//            while (iter.hasNext())
//            {
//                JSONObject jsonObject = (JSONObject)iter.next();
//                OpenCaiBean bean = (OpenCaiBean)JSONObject.toBean(jsonObject,OpenCaiBean.class);
//                list.add(bean);
//            }
//            Collections.reverse(list);
//            Iterator var11 = list.iterator();
//            while (var11.hasNext())
//            {
//                OpenCaiBean bean = (OpenCaiBean)var11.next();
//                handleOpenCaiBean(bean);
//            }
//            return true;
//        }
//        catch (Exception var9)
//        {
//            logger.error("����OpenCai����������" + data + "��URL��" + site, var9);
//        }
//        return false;
//    }
//
//    private boolean handleOpenCaiBean(OpenCaiBean bean)
//    {
//
//        LotteryOpenCode lotteryOpenCode = null;
//        if(bean.getCode().contains("txffc")){
//            //腾讯分分彩
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            lotteryOpenCode = new LotteryOpenCode("txffc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("qqffc")){
//            //QQ分分彩
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("fgffc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("jxk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("jxk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("hebk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("hebk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//
//        }else if(bean.getCode().contains("gxk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("hebk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("jsk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("jsk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("shk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("shk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("hbk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("hbk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("ahk3")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("ahk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("xj115")){
//            //平台没有此奖
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("sc11xx5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("ah115")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("ah11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("sh115")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("sh11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("jx115")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("jx11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("sd115")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("sd11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("gd11x5")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + "0"+expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("gd11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("bjpk10")){
//            String expect = bean.getExpect();
////          expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("bjpk10", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("hgydwfc")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("hg1d5fc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("djydwfc")){
//            //东京1.5分彩
////          String expect = bean.getExpect();
////          expect = expect.substring(0, 8) + "-" + expect.substring(8);
////          //前端code fgffc
////          lotteryOpenCode = new LotteryOpenCode("hbk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("xjssc")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("xjssc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("cqssc")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("cqssc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }else if(bean.getCode().contains("tjssc")){
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("tjssc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//
//        }else if(bean.getCode().contains("fc3d")){
//            String expect = bean.getExpect();
//            //前端code fc3d
//            expect = expect.substring(2, 7);
//            lotteryOpenCode = new LotteryOpenCode("fc3d", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//
//        }
//        else if(bean.getCode().contains("pl3")){
//            String expect = bean.getExpect();
//            //前端code fc3d
//            expect = expect.substring(2, 7) ;
//            lotteryOpenCode = new LotteryOpenCode("pl3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//
//        }
//
//        if (lotteryOpenCode == null) {
//            return false;
//        }
//        if (!CodeValidate.validate(lotteryOpenCode.getLottery(), lotteryOpenCode.getCode()))
//        {
//            logger.error("������" + lotteryOpenCode.getLottery() + "��������" + lotteryOpenCode.getCode() + "����");
//            return false;
//        }
//        if (!ExpectValidate.validate(lotteryOpenCode.getLottery(), lotteryOpenCode.getExpect()))
//        {
//            logger.error("������" + lotteryOpenCode.getLottery() + "��������" + lotteryOpenCode.getExpect() + "����");
//            return false;
//        }
//        // lotteryOpenCode.setRemarks(site);
//        lotteryOpenCode.setTime(new Moment().toSimpleTime());
//        if (StringUtils.isNotEmpty(bean.getOpentime())) {
//            lotteryOpenCode.setInterfaceTime(bean.getOpentime());
//        } else {
//            lotteryOpenCode.setInterfaceTime(new Moment().toSimpleTime());
//        }
//        boolean add = this.lotteryOpenCodeService.add(lotteryOpenCode, false);
//        if ((add) && ("bj5fc".equals(lotteryOpenCode.getLottery())))
//        {
//            LotteryOpenCode bjkl8Code = new LotteryOpenCode("bjkl8", lotteryOpenCode.getExpect(), bean.getOpencode().split("\\+")[0], new Moment().toSimpleTime(), Integer.valueOf(0), null, "");
//            bjkl8Code.setInterfaceTime(lotteryOpenCode.getInterfaceTime());
//            this.lotteryOpenCodeService.add(bjkl8Code, false);
//        }
//        return add;
//    }
//
//    public static String getHttpResult(String url)
//    {
//        try
//        {
//            Map<String, String> header = new HashMap();
//            header.put("referer", "http://www.baidu.com/");
//            header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
//            return HttpClientUtil.post(url, null, header, 10000);
//        }
//        catch (Exception var3)
//        {
//            logger.error("����OpenCai����,URL��" + url, var3);
//        }
//        return null;
//    }
//
//    private String convertFFCCode(String openCode)
//    {
//        String[] codes = openCode.split(",");
//        String code1 = mergeFFCCode(codes[0], codes[1], codes[2], codes[3]);
//        String code2 = mergeFFCCode(codes[4], codes[5], codes[6], codes[7]);
//        String code3 = mergeFFCCode(codes[8], codes[9], codes[10], codes[11]);
//        String code4 = mergeFFCCode(codes[12], codes[13], codes[14], codes[15]);
//        String code5 = mergeFFCCode(codes[16], codes[17], codes[18], codes[19]);
//        return code1 + "," + code2 + "," + code3 + "," + code4 + "," + code5;
//    }
//
//    private String mergeFFCCode(String code1, String code2, String code3, String code4)
//    {
//        int codeInt1 = Integer.valueOf(code1).intValue();
//        int codeInt2 = Integer.valueOf(code2).intValue();
//        int codeInt3 = Integer.valueOf(code3).intValue();
//        int codeInt4 = Integer.valueOf(code4).intValue();
//        int codeSum = codeInt1 + codeInt2 + codeInt3 + codeInt4;
//        String codeSumStr = String.valueOf(codeSum);
//        String finalCode = codeSumStr.substring(codeSumStr.length() - 1);
//        return finalCode;
//    }
//}
