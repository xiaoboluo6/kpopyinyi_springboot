package com.zhangmingjian.kpopservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSingerFavourite;
import com.zhangmingjian.kpopservice.entity.KpopSongSinger;
import com.zhangmingjian.kpopservice.entity.vo.SingerQuery;
import com.zhangmingjian.kpopservice.mapper.KpopSingerMapper;
import com.zhangmingjian.kpopservice.service.KpopSingerFavouriteService;
import com.zhangmingjian.kpopservice.service.KpopSingerService;
import com.zhangmingjian.kpopservice.service.KpopSongService;
import com.zhangmingjian.kpopservice.service.KpopSongSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 歌手表 服务实现类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Service
public class KpopSingerServiceImpl extends ServiceImpl<KpopSingerMapper, KpopSinger> implements KpopSingerService {

    @Value("${avatar.loaction}")
    private String avatarLocation;

    @Autowired
    private KpopSongSingerService songSingerService;

    @Autowired
    private KpopSingerFavouriteService singerFavouriteService;

    // 歌手列表 分页条件查询
    @Override
    public Page<KpopSinger> pageQuery(int page, int limit, SingerQuery singerQuery) {
        // 分页
        Page<KpopSinger> pageSinger = new Page<>(page,limit);

        // 查询条件
        LambdaQueryWrapper<KpopSinger> lqw = new LambdaQueryWrapper<>();
        lqw.eq(singerQuery.getCategoryId()!=null && singerQuery.getCategoryId().trim() != "" ,KpopSinger::getCategoryId,singerQuery.getCategoryId());
        lqw.like(singerQuery.getName()!=null && singerQuery.getName().trim() != "" ,KpopSinger::getName,singerQuery.getName());
        lqw.orderByDesc(KpopSinger::getPopularity);  // 默认按热度值降序排序

        this.page(pageSinger,lqw);
        return pageSinger;
    }


    // 上传歌手图片，并返回存储地址的字符串
    @Override
    public String avatarUpload(MultipartFile file) {
        // 上传文件原始名称
        String fileName= file.getOriginalFilename();
        // 获取文件后缀名
        String extension = fileName.substring(fileName.lastIndexOf("."));
        // 生成新的文件名称 利用时间戳和UUID 生成新名称
        String newFileName= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + UUID.randomUUID().toString().replace("-","")
                + extension;
        // 根据日期生成目录
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = avatarLocation + File.separator + format;

        // 新建或者找到当天记录的文件夹
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }

        try {
            // 完整保存路径+文件名称
            Path filepath = Paths.get(dateDirPath, newFileName);
            // 将 MultipartFile 的内容保存到指定位置
            file.transferTo(filepath.toFile());
            // 返回文件保存的路径 文件夹+文件名
            return format+"/"+newFileName;
        } catch (IOException e) {
            // 处理文件传输失败的异常
            e.printStackTrace();
            // 根据应用程序的要求，你可能希望抛出自定义异常或以其他方式处理异常
            return null;
        }
    }

    // 歌手列表 只做条件查询
    @Override
    public List<KpopSinger> onlyQuery(SingerQuery singerQuery) {
        // 查询条件
        LambdaQueryWrapper<KpopSinger> lqw = new LambdaQueryWrapper<>();
        lqw.eq(singerQuery.getCategoryId()!=null,KpopSinger::getCategoryId,singerQuery.getCategoryId());
        lqw.like(singerQuery.getName()!=null,KpopSinger::getName,singerQuery.getName());
        return this.list(lqw);
    }



    // 根据歌手id删除歌手 但必须先判断有没有属于这名歌手的歌曲song
    @Override
    public boolean deleteSinger(String deleteSingerId) {
        // 去songSinger关联表里面检查一下是否有 有这位歌手的歌曲(如果他的歌曲的is_deleted都是1,也可以删除)
        LambdaQueryWrapper<KpopSongSinger> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(KpopSongSinger::getSingerId,deleteSingerId);
        lqw1.eq(KpopSongSinger::getIsDeleted,0);
        KpopSongSinger one1 = songSingerService.getOne(lqw1);

        // 去singerFavourite关联表里面查一下 是否是推荐歌手 如果是推荐歌手 不能删除
        // 如果存在这个歌手id  但是is_deleted=1 也允许删除
        LambdaQueryWrapper<KpopSingerFavourite> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(KpopSingerFavourite::getSingerId,deleteSingerId);
        lqw2.eq(KpopSingerFavourite::getIsDeleted,0);
        KpopSingerFavourite one2 = singerFavouriteService.getOne(lqw2);
        
        // 如果不存在对应的歌曲 且 不是推荐歌手 则可以删除
        // 判断能否删除
        if(one1 == null && one2 == null){
            // 可以删除
            this.removeById(deleteSingerId);
            return true;
        }else{
            // 不可以删除 因为有歌曲没删除
            return false;
        }
    }


    // 2023.02 开始修改多对多的底层逻辑
    // 拼接歌手名字(可能存在合唱的情况)
    @Override
    public String createSingerName(List<KpopSinger> singerList) {
        // 使用Stream API获取所有歌手的名字并拼接
        String singerName = singerList.stream()
                .map(KpopSinger::getName)
                .collect(Collectors.joining("/")); // 可以根据需要修改拼接的分隔符

        return singerName;
    }

    // 根据歌曲id 去查询关联表songSinger 然后再去查找singer表 返回singerList
    @Override
    public List<KpopSinger> getSingerListBySongId(String songId) {
        List<KpopSinger> singerList = baseMapper.getSingerListBySongId(songId);
        return singerList;
    }

    // 访问歌手详情页的时候 热度+1
    @Override
    public void updatePopularity(String singerId) {
        baseMapper.updateSingerPopularity(singerId);
    }

    // 前端首页展示 站长推荐歌手 这里返回KpopSinger的list
    @Override
    public List<KpopSinger> getSingerFavouriteFront() {
        List<KpopSinger> singerFavouriteListFront= baseMapper.getSingerFavouriteFront();
        return singerFavouriteListFront;
    }


}
