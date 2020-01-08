package cn.readshu.shu.service.impl;

import cn.readshu.shu.dao.ShuInfoDao;
import cn.readshu.shu.pojo.ShuInfo;
import cn.readshu.shu.service.ShuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShuInfoServiceImpl implements ShuInfoService  {

    //注入书籍的dao
@Autowired
private ShuInfoDao shuInfoDao;


@Override
@Transactional
//保存的实现
    public void save(ShuInfo shuInfo) {
    //根据书名来查询数据看是否抓过
    ShuInfo param = new ShuInfo();
    param.setShuname(shuInfo.getShuname());
//    param.setShuzhangjie(shuInfo.getShuzhangjie());

    //执行查询
    List<ShuInfo> list = this.findShuInfo(param);

    //判断查询结果是否为空
    if (list.size()==0){
        //如果为空表示数据不存在，或者已经更新了  执行保存
        this.shuInfoDao.saveAndFlush(shuInfo);
    }
    }

    @Override
    //查找接口的实现shu_name
    public List<ShuInfo> findShuInfo(ShuInfo shuInfo) {
    //声明查询条件
    Example example=Example.of(shuInfo);
        //根据查询条件查询数据PRIMARY
        List<ShuInfo>list = this.shuInfoDao.findAll(example);

        return list;

    }





//    this.shuInfoDao.find



}
