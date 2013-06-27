package kr.pe.kwonnam.reflectioninjector;

/**
 * ReflectionInjector
 *
 * @author 손권남 kwon37xi@gmail.com
 *
 */
public interface ReflectionInjector {

	Object getTarget();

	ReflectionInjector set(Object value);

	ReflectionInjector set(Class<?> clazz, Object value);

	ReflectionInjector set(String fieldName, Object value);

	<T> T get(Class<T> clazz);

	<T> T get(Class<T> clazz, String fieldName);
}
