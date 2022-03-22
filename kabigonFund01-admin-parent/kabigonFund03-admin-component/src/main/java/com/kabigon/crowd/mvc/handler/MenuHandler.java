package com.kabigon.crowd.mvc.handler;

import com.kabigon.crowd.service.api.MenuService;
import com.kabigon.crowd.util.ResultEntity;
import com.kabigon.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew(){
        //查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

        Menu root=null;

        Map<Integer,Menu> menuMap=new HashMap<>();

        for (Menu menu:menuList){
            Integer id=menu.getId();

            menuMap.put(id,menu);
        }
        for (Menu menu:menuList){
            Integer pid = menu.getPid();
            if (pid==null){
                root=menu;
                continue;
            }
            Menu father=menuMap.get(pid);
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);

    }


    @ResponseBody
    @RequestMapping("/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }
}
