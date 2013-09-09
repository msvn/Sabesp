package com.prime.app.agvirtual.web.jsf.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author felipepm
 */
public class ReflectionFacade {
	@SuppressWarnings("unchecked")
	private static HashMap<Class, HashMap<Object, Method>> cacheMetodos = new HashMap<Class, HashMap<Object, Method>>();
	private static final Logger logger = LoggerFactory.getLogger(ReflectionFacade.class);

	public static ReflectionFacade instance(final Object o) {
		ReflectionFacade reflectionFacade;
		reflectionFacade = new ReflectionFacade(o);
		return reflectionFacade;
	}

	@SuppressWarnings("unchecked")
	private Class klass;

	private Object obj;

	@SuppressWarnings("unchecked")
	public ReflectionFacade(final Class klass, final Object obj) {
		super();
		this.klass = klass;
		this.obj = obj;
	}

	@SuppressWarnings("unchecked")
	private ReflectionFacade(final Object obj) {
		this.obj = obj;

		if (obj != null) {
			if (obj instanceof Class<?>) {
				this.klass = (Class) obj;
			} else {
				this.klass = obj.getClass();
			}
		} else {
			throw new IllegalArgumentException("Não é possivel criar ReflectionFacade para obj == null");
		}
	}


	public Object chamaMetodo(final Method method, final Object parametros[]) throws ReflectionFacadeException {
		Object o = null;
		try {
			method.setAccessible(true);
			o = method.invoke(this.obj, parametros);
		} catch (final IllegalAccessException e) {
			logger.info("Erro ao invocar o método " + e.getMessage());
			throw new ReflectionFacadeException(e);
		} catch (final IllegalArgumentException e) {
			logger.info("Erro ao invocar o método " + e.getMessage());
			throw new ReflectionFacadeException(e);
		} catch (final InvocationTargetException e) {
			logger.info("Erro ao invocar o método " + e.getMessage());
			throw new ReflectionFacadeException(e);
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	public Method getMetodo(final String nome) {
		HashMap<Object, Method> hashMap = ReflectionFacade.cacheMetodos.get(this.klass);
		final Class<? extends Object> class1 = this.klass;
		if (hashMap != null) {
			final Method method = hashMap.get(nome);
			if (method != null) {
				return method;
			} else {
				final Method[] methods = class1.getMethods();
				for (final Method m : methods) {
					if (m.getName().equalsIgnoreCase(nome)) {
						ReflectionFacade.cacheMetodos.put(class1, hashMap);
						hashMap.put(nome, m);
						return m;
					}
				}
			}
		} else {
			hashMap = new HashMap<Object, Method>();
		}
		final Method[] methods = class1.getMethods();
		for (final Method m : methods) {
			if (m.getName().equalsIgnoreCase(nome)) {
				ReflectionFacade.cacheMetodos.put(class1, hashMap);
				hashMap.put(nome, m);
				return m;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class getKlass() {
		return klass;
	}

	@SuppressWarnings("unchecked")
	public void setKlass(Class klass) {
		this.klass = klass;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
