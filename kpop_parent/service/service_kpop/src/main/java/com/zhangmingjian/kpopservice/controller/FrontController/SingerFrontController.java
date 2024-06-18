package com.zhangmingjian.kpopservice.controller.FrontController;


import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.zhangmingjian.kpopservice.service.KpopSingerService;
import com.zhangmingjian.kpopservice.service.KpopSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/singerfront")
@CrossOrigin
public class SingerFrontController {

    @Autowired
    private KpopSingerService singerService;

    @Autowired
    private KpopSongService songService;

    // 1.根据singerId返回 歌手基本信息和该歌手的歌曲 singer/_id.vue
    @GetMapping("getSingerDetailInfo/{singerId}")
    public R getSingerDetailInfo(@PathVariable String singerId){
        // 歌手流行度+1
        singerService.updatePopularity(singerId);

        // 先获取歌手基本信息
        KpopSinger singer = singerService.getById(singerId);

        // 然后获取歌手的歌曲
        // 通过songSinger关联表查询所有所属的歌曲  并且附加条件status=1 并且 is_deleted=0
        List<KpopSong> songList = songService.getSongListBySingerId(singerId);

        return R.ok().data("singerInfo",singer).data("songList",songList);
    }



}
