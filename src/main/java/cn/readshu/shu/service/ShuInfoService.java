package cn.readshu.shu.service;

import cn.readshu.shu.pojo.ShuInfo;

import java.util.List;

public interface ShuInfoService {
    /**
     * 保存书的信息
     * @param shuInfo
     */
    public void  save(ShuInfo shuInfo);


    /**
     * 根据条件查询书的信息
     * @param shuInfo
     * @return
     */
    public List<ShuInfo>  findShuInfo(ShuInfo shuInfo);
}
