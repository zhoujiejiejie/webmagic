package cn.readshu.shu.task;

import cn.readshu.shu.pojo.ShuInfo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

//Spring来管理类的创建以及销毁
@Component

//PageProcess页面解析
public class ShuProcessor implements PageProcessor {
//    private String url = "http://www.dushu369.com/zhongguomingzhu/fjczpj/a/";         //经典主页
      private String url = "http://www.dushu369.com/book/A/";                           //A开头

    @Override
    public void process(Page page) {

        //将拿出来的url放入任务队列中
        page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/table/tbody/tr/td/table[3]/tbody/tr[3]/td//*").links().all());


        //获取标题下的所有书籍的url  （还需要再抓的url）
//        List<Selectable> list1 = page.getHtml().xpath("/html/body/div[1]/table/tbody/tr/td/table[2]/tbody/tr/td//*").links().nodes();
//        for (Selectable selectable : list1) {
//            //获取url地址
//            String shumuluUrl = selectable.toString();  //抽取出链接
////                System.out.println(shuInfoUrl);
////                //吧获取到的url地址放到任务队列中
//            page.addTargetRequest(shumuluUrl);
//        }

        //解析页面，获取书籍详情的url地址
        List<Selectable> list = page.getHtml().xpath("/html/body/div[1]/table/tbody/tr/td/table[3]/tbody/tr[6]/td/table/tbody//*").nodes();             //主页

        //判断获取到的集合是否为空
        if (list.size() == 0){
            //如果为空，表示为书籍的详情页,解析页面，获取书籍详情信息，并保存数据
            this.saveShuInfo(page);    //用saveShuInfo方法来完成

        }else {
            //如果不为空则为列表页,解析出详情页的url然后放到任务队列中
            for (Selectable selectable : list) {
                //获取url地址
                String shuInfoUrl = selectable.links().toString();  //抽取出链接
                System.out.println(shuInfoUrl);
//                //吧获取到的url地址放到任务队列中
                page.addTargetRequest(shuInfoUrl);
            }


        }
//        String html = page.getHtml().toString();

        //将拿出来的url放入任务队列中
        page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/table/tbody/tr/td/table[2]/tbody/tr/td/table[2]//*").links().all());

    }



    //,解析页面，获取书籍详情信息，并保存数据
    private void saveShuInfo(Page page) {
        //创建书籍详情对象
        ShuInfo shuInfo= new ShuInfo();

        //解析页面
        Html html = page.getHtml();

        //获取数据，封装到对象中

//        shuInfo.getId();
        shuInfo.setShuname(html.css("title","text").regex(".*?》").toString());
        shuInfo.setShuInfo(Jsoup.parse(html.xpath("/html/body/div[1]/table/tbody/tr/td/table[3]/tbody/tr[3]/td").toString()).text());                                                               //(Jsoup.parse(html.css("div.").toString()).text());
        shuInfo.setShuzuozhe(html.css("title","text").regex("(?<=,).*?(?=-)").toString());
        shuInfo.setShuzhangjie(Jsoup.parse(html.xpath("/html/body/div[1]/table/tbody/tr/td/table[3]/tbody/tr[1]/td").toString()).text());


        page.putField("shuInfo",shuInfo);   //保存结果(到resultItems)


    }


     private Site site= Site.me()
        .setCharset("gbk")  //编码
        .setTimeOut(10*1000)//超时时间
        .setRetrySleepTime(3000)//重试的间隔时间
        .setRetryTimes(3);//重试的次数
    @Override
    public Site getSite() {
        return site;
    }





    @Autowired
    private SpringDataPipeline springDataPipeline;

    //initialDelay当任务启动够等待多久执行方法
    //fixedDelay每隔多久执行这个方法
    @Scheduled(initialDelay = 1000,fixedDelay = 10000*100)   //定时执行

    public void process(){
        Spider.create(new ShuProcessor())
                .addUrl(url)

                //将还需抓取的url放到QueueScheduler（内存）中      然后bloom过滤器去重
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                //设置多线程
                .thread(10)
                              //输出到数据库
                .addPipeline(this.springDataPipeline)
                .run();


    }
}
