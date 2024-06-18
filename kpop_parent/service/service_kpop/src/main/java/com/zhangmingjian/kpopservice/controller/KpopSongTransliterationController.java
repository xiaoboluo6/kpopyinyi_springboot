package com.zhangmingjian.kpopservice.controller;


import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopSongTransliteration;
import com.zhangmingjian.kpopservice.service.KpopSongTransliterationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * KPOP歌曲音译表 前端控制器
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@RestController
@RequestMapping("/kpopservice/songtransliteration")
@CrossOrigin
public class KpopSongTransliterationController {

    @Autowired
    private KpopSongTransliterationService transliterationService;

    // 1. 新增歌曲音译
    @PostMapping("addTrans")
    public R addTrans(@RequestBody KpopSongTransliteration songTransliteration){
        transliterationService.save(songTransliteration);
        return R.ok().message("新增音译文本成功");
    }

    // 2. 根据songId获取音译文本
    @GetMapping("getTransBySongId/{songId}")
    public R getTransBySongId(@PathVariable String songId){
        KpopSongTransliteration songTransInfo = transliterationService.getTransBySongId(songId);
        // 这里要做判断是否能够搜索到
        if(songTransInfo==null){
            // 没有找到
            return R.ok().data("count",0);
        }else{
            // 找到了
            return R.ok().data("item",songTransInfo).data("count",1);
        }
    }

    // 3. 修改音译文本
    @PostMapping("updateTrans")
    public R updateTrans(@RequestBody KpopSongTransliteration songTransliteration){
        transliterationService.updateById(songTransliteration);
        return R.ok().message("修改音译文本成功");
    }

}

