package cn.readshu.shu.task;

import cn.readshu.shu.pojo.ShuInfo;
import cn.readshu.shu.service.ShuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component
public class SpringDataPipeline implements Pipeline {

    @Autowired
    private ShuInfoService shuInfoService;


    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取我们封装好的书籍详情对象
        ShuInfo shuInfo=resultItems.get("shuInfo");

        //判断数据不为空

        if (shuInfo !=null){
            //不为空就保存到数据库中
            this.shuInfoService.save(shuInfo);
        }


    }
}
