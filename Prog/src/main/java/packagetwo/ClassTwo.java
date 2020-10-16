package packagetwo;

import packageone.ClassOne;
import packageone.InterfaceOne;

/**
 *
 * @author michael
 */
public class ClassTwo {
    public void methodWithParam(InterfaceOne c) {
        System.out.print("ClassTwo(withParam):");
        System.out.println(c.printStr());
    }
    
    public void methodWithoutParam() {        
        ClassOne c = new ClassOne();        
        System.out.print("ClassTwo(withoutParam):");
        System.out.println(c.printStr());
    }
}