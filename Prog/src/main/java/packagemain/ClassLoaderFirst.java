package packagemain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class ClassLoaderFirst extends ClassLoader {
    private HashMap<String, Class<?> > cash;
    
    ClassLoaderFirst() {
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
            
            InputStream classFileStream = new FileInputStream(classFile);
            loadClassData(name, classFileStream);
                       
            return cash.get(name);            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println("Try to find class in jar");
            //Try to find in jar file            
            try {
                final URL jarUrl = new URL("jar:file:jarWithClasses.jar!/" + name.replace('.', '/') + ".class");
                System.out.println(jarUrl.getFile());
                InputStream classFileStream = jarUrl.openStream();
                                
                loadClassData(name, classFileStream);
                
                return cash.get(name);
            } catch (MalformedURLException ex1) {
                Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex1);
            }
                        
            //Force parent to find            
            return super.findClass(name);                       
        } catch (IOException ex) {
            Logger.getLogger(ClassLoaderFirst.class.getName()).log(Level.SEVERE, null, ex);
            //Class was found, but file is broken
            throw new ClassNotFoundException();
        }   
    }   
    
    private void loadClassData(String name, InputStream classFileStream) throws IOException {        
        ByteBuffer b = ByteBuffer.wrap(classFileStream.readAllBytes());
        cash.put(name, defineClass(name, b, null));//use default ProtectionDomain?                 
    }   
}
