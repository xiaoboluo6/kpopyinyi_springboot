package com.zhangmingjian.kpopservice.controller;


import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.utils.MakeRomaUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/kpopservice/transMaker")
@CrossOrigin
public class TransMakerController {

    // 1. 根据 file1歌词lrc和file2的RomaLrc 制作音译文本(带有时间戳)
    @PostMapping("makeRomaEnglishWithTime")
    public R makeRomaEnglishWithTime(MultipartFile file1,MultipartFile file2){
        String trans = MakeRomaUtils.getTransWithTime(file1, file2);
        return R.ok().data("trans",trans);
    }

    // 2. 根据 file1歌词lrc和file2的RomaLrc 制作音译文本(带有时间戳)
    @PostMapping("makeRomaEnglishWithoutTime")
    public R makeRomaEnglishWithoutTime(MultipartFile file1,MultipartFile file2){
        String trans = MakeRomaUtils.getTransWithoutTime(file1, file2);
        return R.ok().data("trans",trans);
    }
}
