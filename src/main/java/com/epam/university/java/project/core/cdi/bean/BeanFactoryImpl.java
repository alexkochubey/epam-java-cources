package com.epam.university.java.project.core.cdi.bean;

import com.epam.university.java.project.core.cdi.structure.ListDefinition;
import com.epam.university.java.project.core.cdi.structure.MapDefinition;
import com.epam.university.java.project.core.cdi.structure.StructureDefinition;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;
import org.clapper.util.classutil.SubclassClassFilter;
import sun.reflect.ReflectionFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class BeanFactoryImpl implements BeanFactory {
    private HashMap<String, Object> singletonObjects = new HashMap<>();
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public BeanFactoryImpl(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        //Bad idea, but it works.
        if (beanClass.isInterface()) {
            return (T) getBeanByInterface(beanClass);
        }

        String id = getIdOfNameClass(beanClass.getSimpleName());
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(id);
        Object object;
        try {
            object = getBean(beanDefinition);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in getBeanByClass");
        }
        return (T) object;
    }

    @Override
    public Object getBean(String beanName) {
        String id = getIdOfNameClass(beanName);
        Object object;
        try {
            object = getBean(beanDefinitionRegistry.getBeanDefinition(id));
        } catch (Exception e) {
            throw new RuntimeException("Error in getBeanByName");
        }
        return object;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        Object object1 = getBean(beanName);
        //TODO BeanClass that, why?
        return (T) object1;
    }


    /**
     * Get Bean by BeanDefinition.
     * <p>
     * Main method of this Factory. Other method have link for this method.
     * This method create object of necessary class,
     * and match object params this beanDefinition params,
     * in finally we have object create by beanDefinition information.
     * </p>
     *
     * @param beanDefinition information about bean from XML file.
     * @param <T>            Generic because we can return any class object.
     * @return new Object necessary class from beanDefinition.
     * @throws Exception about Read beanDefinition or work with singleton bean.
     */
    public <T> T getBean(BeanDefinition beanDefinition) throws Exception {
        if (beanDefinition == null) {
            throw new IllegalArgumentException("BeanDefinition is Null!");
        }
        String id = beanDefinition.getId();
        String className = beanDefinition.getClassName();
        String init = beanDefinition.getPostConstruct();
        //TODO what param init do?
        String scope = beanDefinition.getScope();
        Collection<BeanPropertyDefinition> properties = beanDefinition.getProperties();
        Class<T> someClass = (Class<T>) Class.forName(className);
        T bean = someClass.getDeclaredConstructor().newInstance();
        Field[] fields = bean.getClass().getDeclaredFields();

        if ("singleton".equals(scope)) {
            if (singletonObjects.containsKey(id)) {
                return (T) singletonObjects.get(id);
            }
        }

        if (properties != null) {
            for (BeanPropertyDefinition property : properties) {
                Field field = getFieldOfName(property.getName(), fields);
                String typeOfField = field.getGenericType().getTypeName();
                field.setAccessible(true);

                if (property.getRef() != null) {
                    BeanDefinition other = beanDefinitionRegistry
                            .getBeanDefinition(property.getRef());
                    Object object = getBean(other);
                    field.set(bean, object);
                }

                if (typeOfField.equals(String.class.getTypeName())) {
                    field.set(bean, property.getValue());
                    continue;
                }

                if (typeOfField.equals(int.class.getTypeName())
                        || typeOfField.equals(Integer.class.getTypeName())) {
                    Integer value = Integer.parseInt(property.getValue());
                    field.set(bean, value);
                    continue;
                }

                if (property.getData() != null) {
                    if (property.getData() instanceof ListDefinition) {
                        Collection<String> collection = getCollectionOfData(property.getData());
                        field.set(bean, collection);
                    }
                    if (property.getData() instanceof MapDefinition) {
                        StructureDefinition data = property.getData();
                        if (isMapOfStrings(data)) {
                            field.set(bean, getMapOfStringsData(data));
                        } else {
                            field.set(bean, getMapOfObjectsData(data));
                        }
                    }

                }
            }
        }

        if ("singleton".equals(scope)) {
            if (!singletonObjects.containsKey(id)) {
                singletonObjects.put(id, bean);
            } else {
                throw new IllegalArgumentException("Object is singleton, "
                        + "but we can find it in singletonList");
            }
        }

        return bean;
    }


    private Field getFieldOfName(String propertyName, Field[] fields) {
        for (Field field : fields) {
            if (propertyName.equals(field.getName())) {
                return field;
            }
        }
        throw new IllegalArgumentException(
                "Field doesn't find in class of propertyName: " + propertyName);
    }


    private String getIdOfNameClass(String unCorrectName) {
        String firstPart = unCorrectName.substring(0, 1);
        String secondPart = unCorrectName.substring(1);
        return firstPart.toLowerCase() + secondPart;
    }

    private Object getBeanByInterface(Class interfaceClass) {
        String className = null;
        try {
            ClassFinder finder = new ClassFinder();
            finder.clear();
            File currentClass = new File(URLDecoder.decode(interfaceClass
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath(), "UTF-8"));
            finder.add(currentClass);
            ClassFilter filter = new SubclassClassFilter(Class.forName(interfaceClass.getName()));

            Collection<ClassInfo> foundClasses = new ArrayList<>();
            finder.findClasses(foundClasses, filter);
            if (foundClasses.size() > 1) {
                throw new IllegalArgumentException("A lot of classes that implements"
                        + " Interface, count classes are " + foundClasses.size());
            }

            for (ClassInfo classInfo : foundClasses) {
                Class clazz = Class.forName(classInfo.getClassName());
                className = clazz.getSimpleName().replaceAll("Impl","");
                break;
            }

            if (foundClasses.size() == 0) {
                //TODO This method is not correct, by work. I don't know why...
                className = interfaceClass.getSimpleName();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (className == null) {

            throw new IllegalArgumentException("no class that implement"
                    + " Interface " + interfaceClass.getSimpleName());
        }

        System.out.println(className);

        return getBean(getIdOfNameClass(className));
    }


    private Collection<String> getCollectionOfData(StructureDefinition data) {
        Collection<String> collection = new ArrayList<>();
        ListDefinition listDefinition = (ListDefinition) data;
        Collection<ListDefinition.ListItemDefinition> list = listDefinition.getItems();
        for (ListDefinition.ListItemDefinition item : list) {
            collection.add(item.getValue());
        }
        return collection;
    }


    private boolean isMapOfStrings(StructureDefinition data) {
        MapDefinition mapDefinition = (MapDefinition) data;
        Collection<MapDefinition.MapEntryDefinition> values = mapDefinition.getValues();
        for (MapDefinition.MapEntryDefinition pair : values) {
            String ref = pair.getRef();
            if (ref != null) {
                return false;
            }
        }
        return true;
    }

    private Map<String, String> getMapOfStringsData(StructureDefinition data) {
        Map<String, String> map = new HashMap<>();
        MapDefinition mapDefinition = (MapDefinition) data;
        Collection<MapDefinition.MapEntryDefinition> values = mapDefinition.getValues();
        for (MapDefinition.MapEntryDefinition pair : values) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (value != null) {
                map.put(key, value);
            } else {
                throw new NullPointerException("getMapOfData, didn't find value of Key");
            }
        }
        return map;
    }

    private Map<String, Object> getMapOfObjectsData(StructureDefinition data) {
        Map<String, Object> map = new HashMap<>();
        MapDefinition mapDefinition = (MapDefinition) data;
        Collection<MapDefinition.MapEntryDefinition> values = mapDefinition.getValues();
        for (MapDefinition.MapEntryDefinition pair : values) {
            String key = pair.getKey();
            String ref = pair.getRef();
            if (ref != null) {
                map.put(key, getBean(ref));
            } else {
                throw new NullPointerException("getMapOfData, didn't find value of Key");
            }
        }
        return map;
    }
}