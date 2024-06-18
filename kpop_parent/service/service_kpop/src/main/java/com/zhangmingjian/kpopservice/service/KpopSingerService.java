package com.zhangmingjian.kpopservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.kpopservice.entity.vo.SingerQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌手表 服务类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
public interface KpopSingerService extends IService<KpopSinger> {

    // 歌手列表 分页条件查询
    Page<KpopSinger> pageQuery(int page, int limit, SingerQuery singerQuery);

    // 上传歌手图片，并返回存储地址的字符串
    String avatarUpload(MultipartFile file);

    // 根据歌手id删除歌手 但必须先判断有没有属于这名歌手的歌曲song
    boolean deleteSinger(String deleteSingerId);

    // 歌手列表 只做条件查询
    List<KpopSinger> onlyQuery(SingerQuery singerQuery);

    // 拼接歌手名字(可能存在合唱的情况)
    String createSingerName(List<KpopSinger> singerList);

    // 根据歌曲id 去查询关联表 然后再去查找singer表 返回singerList
    List<KpopSinger> getSingerListBySongId(String songId);


    // 前端访问歌手详情页的时候 热度+1
    void updatePopularity(String singerId);

    // 前端首页展示 站长推荐歌手 这里返回KpopSinger的list
    List<KpopSinger> getSingerFavouriteFront();
}
