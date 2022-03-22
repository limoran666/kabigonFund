package com.kabigon.crowd.service.api;

import com.kabigon.entity.Menu;

import java.util.List;

public interface MenuService {
   List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
