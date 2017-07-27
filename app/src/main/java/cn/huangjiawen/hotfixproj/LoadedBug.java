package cn.huangjiawen.hotfixproj;

/**
 * Created by Administrator on 026 2017/7/26.
 */

public class LoadedBug {
    public String loadBug(){
        Bug bug = new Bug();
        return bug.get();
    }
}
