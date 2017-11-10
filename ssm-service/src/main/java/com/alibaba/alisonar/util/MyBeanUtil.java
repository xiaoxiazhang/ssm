/**
 * 
 */
package com.alibaba.alisonar.util;

import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserDTO;

import net.sf.cglib.beans.BeanCopier;

/**
 * @author wb-zxx263018
 *
 */
public class MyBeanUtil {
	
	private static ConcurrentHashMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<String, BeanCopier>();
    private static final Object lock = new Object();

    public static void copyProperties(Object source, Object target) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = beanCopierMap.get(beanKey);
        if (copier == null) {
            synchronized (lock) {
                copier = beanCopierMap.get(beanKey);
                if (copier == null) {
                    copier = BeanCopier.create(source.getClass(), target.getClass(), false);
                    beanCopierMap.putIfAbsent(beanKey, copier);// putIfAbsent已经实现原子操作了。
                }
            }

        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
    
    public static void main(String[] args) {
		AuthUserDTO dto = new AuthUserDTO();
		dto.setEmail("hello@163.com");
		
		AuthUser entity = new AuthUser();
		entity.setId(2);
		
		copyProperties(dto, entity);
		System.out.println(entity);
		
	}


}
