package kr.pe.kwonnam.reflectioninjector;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * ReflectionInjectorUtils.inject(대상객체).set(fieldObject).set(fieldObject2)...
 * 형태로 사용한다.
 *
 * @author 손권남 kwon37xi@gmail.com
 *
 */
public class ReflectionInjectorUtils implements ReflectionInjector {

	public static ReflectionInjector injector(Object object) {
		return new ReflectionInjectorUtils(object);
	}

	/**
	 * 주입 대상
	 */
	private Object target;

	private Class<?> targetClass;

	private Set<Field> targetFields = new HashSet<Field>();

	public ReflectionInjectorUtils(Object target) {
		checkNotNull(target, "target");
		this.target = target;

		targetClass = target.getClass();

		populateOwnFields(targetClass);
		markFieldsAsAccessible();
	}

	/**
	 * 클래스 자신과 자신의 모든 부모 클래스를 돌면서 선언된 필드를 구한다.
	 * @param clazz
	 */
	private void populateOwnFields(Class<?> clazz) {
		if (clazz == Object.class) {
			return;
		}

		for (Field field: clazz.getDeclaredFields()) {
			targetFields.add(field);
		}

		populateOwnFields(clazz.getSuperclass());
	}

	private void markFieldsAsAccessible() {
		for (Field field : targetFields) {
			field.setAccessible(true);
		}
	}

	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public ReflectionInjector set(Object value) {
		checkNotNull(value, "value");
		for (Field field : targetFields) {
			if (field.getType().isAssignableFrom(value.getClass())) {
				setField(field, value);
				return this;
			}
		}
		throw new IllegalArgumentException(value.getClass().getCanonicalName()
				+ "을 주입할 수 있는 필드가 없습니다.");
	}

	private void setField(Field field, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@Override
	public ReflectionInjector set(Class<?> clazz, Object value) {
		checkNotNull(clazz, "clazz");
		checkNotNull(value, "value");

		for (Field field : targetFields) {
			if (field.getType() == clazz) {
				setField(field, value);
				return this;
			}
		}

		throw new IllegalArgumentException(clazz.getCanonicalName()
				+ "을 주입할 수 있는 필드가 없습니다.");
	}

	@Override
	public ReflectionInjector set(String fieldName, Object value) {
		checkNotNull(fieldName, "fieldName");
		checkNotNull(value, "value");

		for (Field field : targetFields) {
			if (field.getName().equals(fieldName)) {
				setField(field, value);
				return this;
			}
		}

		throw new IllegalArgumentException(String.format("[%s] 이라는 필드가 없습니다.",
				fieldName));
	}

	@Override
	public <T> T get(Class<T> clazz) {
		checkNotNull(clazz, "clazz");

		for (Field field : targetFields) {
			if (clazz.isAssignableFrom(field.getType())) {
				return getValue(clazz, field);
			}
		}

		return null;
	}

	private <T> T getValue(Class<T> clazz, Field field) {
		if (!clazz.isAssignableFrom(field.getType())) {
			throw new ClassCastException(String.format("[%s] 필드는 %s 타입이 아닙니다.",
					field.getName(), clazz.getCanonicalName()));
		}

		try {
			@SuppressWarnings("unchecked")
			T value = (T) field.get(target);
			return value;
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T get(Class<T> clazz, String fieldName) {
		checkNotNull(clazz, "clazz");
		checkNotNull(fieldName, "fieldName");

		for (Field field : targetFields) {
			if (field.getName().equals(fieldName)) {
				return getValue(clazz, field);
			}
		}

		return null;
	}

	/**
	 * 임시로 만든 Null검사 메소드. 원칙적으로 Guava Preconditions를 쓰는게 더 낫다.
	 *
	 * @param objectForCheck
	 * @param name
	 */
	private void checkNotNull(Object objectForCheck, String name) {
		if (objectForCheck == null) {
			throw new IllegalArgumentException(name + " 값은 null일 수 없습니다.");
		}
	}
}
