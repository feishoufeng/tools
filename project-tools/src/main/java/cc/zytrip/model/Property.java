package cc.zytrip.model;

import java.util.ArrayList;
import java.util.List;

public class Property {
    private String menuName;
    private String url;
    private List<String> names = new ArrayList<>();

    public void addNames(String name){
        names.add(name);
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
