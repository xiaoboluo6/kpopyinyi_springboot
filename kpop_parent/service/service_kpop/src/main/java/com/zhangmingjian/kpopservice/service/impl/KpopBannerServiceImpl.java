package com.zhangmingjian.kpopservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.kpopservice.entity.KpopBanner;
import com.zhangmingjian.kpopservice.entity.vo.BannerListVo;
import com.zhangmingjian.kpopservice.mapper.KpopBannerMapper;
import com.zhangmingjian.kpopservice.service.KpopBannerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

@Service
public class KpopBannerServiceImpl extends ServiceImpl<KpopBannerMapper, KpopBanner> implements KpopBannerService {

    @Value("${banner.loaction}")
    private String bannerLocation;


    // 【前台！！】 查询isShow=1(表示可以展示) 的 banner轮播图   并放入redis
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<KpopBanner> getFrontShow() {
        LambdaQueryWrapper<KpopBanner> lqwBanner = new LambdaQueryWrapper<>();
        lqwBanner.eq(KpopBanner::getIsShow,1);
        return this.list(lqwBanner);
    }


    // 获取全部banner信息  封装到BannerListVo中  给后台admin进行显示
    @Override
    public List<BannerListVo> getBannerInfoList() {
        List<BannerListVo> list= baseMapper.getBannerInfoListShow();
        return list;
    }

    // 上传banner图片，返回图片存储地址
    @Override
    public String bannerPictureSave(MultipartFile file) {
        // 上传文件原始名称
        String fileName= file.getOriginalFilename();
        // 获取文件后缀名
        String extension = fileName.substring(fileName.lastIndexOf("."));
        // 生成新的文件名称 利用时间戳和UUID 生成新名称
        String newFileName= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + UUID.randomUUID().toString().replace("-","")
                +extension;
        // 根据日期生成目录
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = bannerLocation + File.separator + format;

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

    // 后续如果需要进行 save update delete 操作的时候  清楚缓存
    // @CacheEvict(value = "banner", allEntries=true)
    // 新增banner 更新redis
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void saveBanner(KpopBanner banner) {
        baseMapper.insert(banner);
    }


    // 修改banner 更新redis
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(KpopBanner banner) {
        baseMapper.updateById(banner);
    }


    // 删除banner 更新redis
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }


    // 切换显示状态 根据id  将banner的isShow变成0
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void turnOff(String bannerId) {
        baseMapper.turnOffById(bannerId);
    }

    // 切换显示状态 根据id  将banner的isShow变成1
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void turnOn(String bannerId) {
        baseMapper.turnOnById(bannerId);
    }


    // 根据bannerId获取歌曲的status
    @Override
    public int getSongStatusByBannerId(String bannerId) {
        int status = baseMapper.getSongStatusByBannerId(bannerId);
        return status;
    }


}
