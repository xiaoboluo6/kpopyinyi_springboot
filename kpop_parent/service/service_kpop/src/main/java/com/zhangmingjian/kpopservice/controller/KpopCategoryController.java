package com.zhangmingjian.kpopservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopCategory;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.service.KpopCategoryService;
import com.zhangmingjian.kpopservice.service.KpopSingerService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/category")
@CrossOrigin
public class KpopCategoryController {

    @Autowired
    private KpopCategoryService kpopCategoryService;

    @Autowired
    private KpopSingerService kpopSingerService;

    // 1.获取全部歌手种类
    @GetMapping("getAll")
    public R get(){
        return R.ok().data("items",kpopCategoryService.list(null));
    }

    // 2. 新增歌手种类
    @PostMapping("add")
    public R add(@RequestBody KpopCategory category){
        kpopCategoryService.save(category);
        return R.ok().message("添加成功");
    }

    // 3. 删除歌手种类
    // TODO 目前 前端直接不提供删除功能
    @DeleteMapping("{deleteId}")
    public R delete(@PathVariable String categoryId){
        // 先检查有没有对应的歌手 如果没有对应歌手  则可以删除
        LambdaQueryWrapper<KpopSinger> lqwSinger = new LambdaQueryWrapper<>();
        lqwSinger.eq(KpopSinger::getCategoryId,categoryId);
        KpopSinger one = kpopSingerService.getOne(lqwSinger);

        if(one == null){
            return R.error().message("该类目下有对应歌手，不可以删除");
        }else{
            kpopCategoryService.removeById(categoryId);
            return R.ok().message("删除成功");
        }
    }

    // 4. 新增歌手种类
    @PostMapping("update")
    public R update(@RequestBody KpopCategory category){
        kpopCategoryService.updateById(category);
        return R.ok().message("修改成功");
    }

    // 5. 根据id查询种类信息
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
        KpopCategory category = kpopCategoryService.getById(id);
        return R.ok().data("item",category);
    }

}
