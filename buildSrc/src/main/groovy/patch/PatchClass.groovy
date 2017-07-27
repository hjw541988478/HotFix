package patch;

import javassist.ClassPool
import javassist.CtClass

public class PatchClass {
    /**
     * 植入代码
     * @param injectPath 需要注入的class所在路径
     * @param stubPath HotFixStub类的class文件所在路径
     */
    public static void process(String injectPath, String stubPath) {

        println(stubPath)
        ClassPool classes = ClassPool.getDefault()
        classes.appendClassPath(injectPath)
        classes.appendClassPath(stubPath)

        //下面的操作比较容易理解,在将需要关联的类的构造方法中插入引用代码
        CtClass c = classes.getCtClass("cn.huangjiawen.hotfixproj.Bug")
        if (c.isFrozen()) {
            c.defrost()
        }
        println("====添加构造方法====")
        def constructor = c.getConstructors()[0];
        constructor.insertBefore("System.out.println(cn.huangjiawen.hotfixstub.HotFixStub.class);")
        c.writeFile(injectPath)



        CtClass c1 = classes.getCtClass("cn.huangjiawen.hotfixproj.LoadedBug")
        if (c1.isFrozen()) {
            c1.defrost()
        }
        println("====添加构造方法====")
        def constructor1 = c1.getConstructors()[0];
        constructor1.insertBefore("System.out.println(cn.huangjiawen.hotfixstub.HotFixStub.class);")
        c1.writeFile(injectPath)


    }

}
