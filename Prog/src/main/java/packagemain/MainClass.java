package packagemain;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import packageone.InterfaceOne;

/**
 *
 * @author michael
 */
public class MainClass {
    
    private ClassLoaderFirst clFirst;
    private ClassLoaderSecond clSecond;
    
    public static void main(String args[]) {
        ClassLoaderFirst clFirst = new ClassLoaderFirst();
        ClassLoaderSecond clSecond = new ClassLoaderSecond();
        
        Object cFirst;
        Object cSecond;
        //Try to load first class
        try {
            cFirst = clFirst.findClass("packageone.ClassOne").
                    getConstructor().newInstance();
            System.out.println("ClasOne was found and created");                        
            
            System.out.println(InterfaceOne.class.cast(cFirst).printStr());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("First class wasn't found");
            return;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class is not ours");
            return;
        }
        
        //Try to load second class
        try {
            cSecond = clSecond.findClass("packagetwo.ClassTwo").
                    getConstructor().newInstance();
            System.out.println("ClassTwo was found and created");
            
            cSecond.getClass().getDeclaredMethod("methodWithoutParam").invoke(cSecond);
            ///FIXME probably I can't use InterfaceOne here
            //Method[] m = cSecond.getClass().getDeclaredMethods();
            //System.out.print(m[1].getParameterTypes()[0].getName() + " ");
            //System.out.println(m[1].getParameterTypes()[0].getClassLoader().getName());
            //cSecond.getClass().getDeclaredMethod("methodWithParam", cFirst.getClass()).invoke(cSecond, cFirst);            
            cSecond.getClass().getDeclaredMethod("methodWithParam", 
                    InterfaceOne.class).invoke(cSecond, cFirst);            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Second class wasn't found");
            return;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class is not ours");            
            return;
        }
        
        ReflectionClass reflectionClass = new ReflectionClass();
        reflectionClass.printClass(new MainClass().getClass());
    }
}
