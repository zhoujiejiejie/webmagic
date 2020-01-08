package cn.readshu.shu.pojo;

import javax.persistence.*;

//声明实体
@Entity
public class ShuInfo {

    //主键
    @Id
    //自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private long id;
    @Column(name = "shu_name")
    private String shuname;
    @Column(name = "shu_zhangjie")
    private String shuzhangjie;
    @Column(name = "shu_zuozhe")
    private String shuzuozhe;
    @Column(name = "shu_info")
    private String shuInfo;


    public String getShuname() {
        return shuname;
    }

    public void setShuname(String shuname) {
        this.shuname = shuname;
    }

    public String getShuInfo() {
        return shuInfo;
    }

    public void setShuInfo(String shuInfo) {
        this.shuInfo = shuInfo;
    }


    public String getShuzuozhe() {
        return shuzuozhe;
    }

    public void setShuzuozhe(String shuzuozhe) {
        this.shuzuozhe = shuzuozhe;
    }

    public String getShuzhangjie() { return shuzhangjie; }

    public void setShuzhangjie(String zhangjie) { this.shuzhangjie = zhangjie; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    @Override
    public String toString() {
        return "ShuInfo{" +
                "id=" + id +
                ", shuname='" + shuname + '\'' +
                ", zhangjie='" + shuzhangjie + '\'' +
                ", shuzuozhe='" + shuzuozhe + '\'' +
                ", shuInfo='" + shuInfo + '\'' +
                '}';
    }
}