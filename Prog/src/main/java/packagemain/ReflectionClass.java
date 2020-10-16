package packagemain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author michael
 */
public class ReflectionClass {

    public void printClass(Class<?> c) {
        System.out.println("__________CLASS " + c.getName().toUpperCase() + "__________");
        if (c.getClassLoader() == null)
            System.out.println("ClassLoader: MACHINE GOD");
        else 
            System.out.println("ClassLoader: " + c.getClassLoader().getName());

        if (c.getDeclaredMethods().length == 0) {
            System.out.println("No methods");
        } else {
            System.out.println("Methods:");
            printClassMethods(c);
        }

        if (c.getDeclaredFields().length == 0) {
            System.out.println("No fields");
        } else {
            System.out.println("Fields:");
            printClassFields(c);
        }

        System.out.println("Parent:");
        if (c.getSuperclass() == null) {
            System.out.println("NONE");
        } else {
            System.out.println(c.getSuperclass().getName());
            printClass(c.getSuperclass());
        }

    }

    private void printClassMethods(Class<?> c) {
        for (Method method : c.getMethods()) {
            System.out.print("    " + method.getReturnType().getName());
            System.out.println(" " + method.getName());

            if (method.getExceptionTypes().length == 0) {
                System.out.println("        no throwing exceptions");
            } else {
                System.out.println("        throwing exceptions:");
                for (Class exception : method.getExceptionTypes()) {
                    System.out.println("            " + exception.getName());
                }
            }

            if (method.getParameterTypes().length == 0) {
                System.out.println("        no params");
            } else {
                System.out.println("        params types:");
                for (Class param : method.getParameterTypes()) {
                    System.out.println("            " + param.getName());
                }
            }
        }
    }

    private void printClassFields(Class<?> c) {
        for (Field field : c.getDeclaredFields()) {
            System.out.print("    " + field.getClass());
            System.out.println(" " + field.getName());
        }
    }
}
