package CSCT321.ProjectAqua.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.beans.Introspector;
import java.util.Arrays;
import java.util.Set;

// A service class for updating entities
@Service
public class EntityUpdateService {

    // Generic method for partially updating an entity with another entity of the same type
    public <T> T partialUpdate(T oldEntity, T newEntity) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        // Get an array of property descriptors for the old entity's class
        PropertyDescriptor[] descriptors = Introspector.getBeanInfo(oldEntity.getClass(), Object.class).getPropertyDescriptors();

        // Loop over the property descriptors
        for (PropertyDescriptor descriptor : descriptors) {
            // Get the property name
            String fieldName = descriptor.getName();

            // Get the new value by invoking the read method (getter) on the new entity
            Object newValue = descriptor.getReadMethod().invoke(newEntity);

            // If the new value is not null, proceed to update the old entity
            if (newValue != null) {
                // If the property is a Set (for example, a Set of associated entities),
                // merge the new set into the old one
                if (Set.class.isAssignableFrom(descriptor.getPropertyType())) {
                    Set oldSet = (Set) descriptor.getReadMethod().invoke(oldEntity);
                    Set newSet = (Set) newValue;
                    oldSet.addAll(newSet); // Merge the old and new sets
                }
                // If the property is another entity, recursively call partialUpdate
                else if (Arrays.stream(newValue.getClass().getAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().getSimpleName().equals("Entity"))) {
                    Object oldNestedEntity = descriptor.getReadMethod().invoke(oldEntity);
                    partialUpdate(oldNestedEntity, newValue);
                }
                // For other property types, replace the old value with the new one
                else {
                    BeanUtils.setProperty(oldEntity, fieldName, newValue);
                }
            }
        }

        // Return the old entity, which has now been updated with the new values
        return oldEntity;
    }
}


