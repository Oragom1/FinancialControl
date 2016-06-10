package br.diogo_julia.financialcontrol.app.preparadoresdelista;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.diogo_julia.financialcontrol.application.menu.MenuP;

public class AutoPrepareListMenus {
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private void prepareMenu()
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // adicionando os dados principais

        Iterator<MenuP> menus = EnumSet.allOf(MenuP.class).iterator();
        while(menus.hasNext()) {
            List<String> sub = new ArrayList<>();
            MenuP menu = menus.next();
            listDataHeader.add(menu.getNomeTabela());
            Iterator subMenus = EnumSet.allOf(menu.getEnumeracao()).iterator();
            while(subMenus.hasNext()) {
                Object enumSubmenu = subMenus.next();

                sub.add(enumSubmenu.toString());

            }
            listDataChild.put(listDataHeader.get(menu.getNum()), sub);
        }

    }
}
