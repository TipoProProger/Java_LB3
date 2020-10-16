package packagemain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class ClassLoaderSecond extends ClassLoader{
    private HashMap<String, Class<?> > cash;
    
    ClassLoaderSecond() {
        cash = new HashMap<>();
    }
    
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException{
        if (cash.containsKey(name))
            return cash.get(name);
       try {
            File classFile = new File("target/classes/" + name.replace('.', '/') + ".class");
            if (!classFile.exists() | !classFile.canRead())
                throw new FileNotFoundException();
            
            loadClassData(name, classFile);
                       
            return cash.get(name);            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex);
            //Force parent to find
            return super.findClass(name);                       
        } catch (IOException ex) {
            Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex);
            //Class was found, but file is broken
            throw new ClassNotFoundException();
        }   
    }   
    
    private void loadClassData(String name, File classFile) throws IOException {        
        InputStream classFileStream = new FileInputStream(classFile);
        ByteBuffer b = ByteBuffer.wrap(classFileStream.readAllBytes());
        cash.put(name, defineClass(name, b, null));//use default ProtectionDomain?                 
    }
}
