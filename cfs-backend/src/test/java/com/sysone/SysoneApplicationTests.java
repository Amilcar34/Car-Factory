package com.sysone;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class SysoneApplicationTests {

	private static final Class<Entity> ENTITY_CLASS = Entity.class;
	private static final Class<Table> TABLE_CLASS = Table.class;
	private static final Class<Controller> CLASS_CONTROLLER = Controller.class;
	private static final Class<RestController> CLASS_REST_CONTROLLER = RestController.class;

	private static final String PACKAGE = "com.sysone";
	private static final String PACKAGE_REST = PACKAGE + ".rest";
	private static final String PACKAGE_ENTITY = PACKAGE + ".entities";

	@Test public void reviewAnnotationEntities() throws ClassNotFoundException {

		getClassesForPackage(PACKAGE_ENTITY).forEach(entity -> {
			assertNotNull(entity.getAnnotation(ENTITY_CLASS));
		});
	}

	@Test public void reviewAnnotationTable() throws ClassNotFoundException {
		getClassesForPackage(PACKAGE_ENTITY).forEach(entity -> {
			assertNotNull(entity.getAnnotation(TABLE_CLASS));
		});
	}

	@Test public void reviewAnnotationRestControllers() throws ClassNotFoundException {

		getClassesForPackage(PACKAGE_REST).stream().forEach(controller -> {
			assertNotNull(controller.getAnnotation(CLASS_REST_CONTROLLER));
		});
	}

	@Test public void reviewNameRestInControllers() throws ClassNotFoundException {

		getClassesForPackage(PACKAGE_REST).forEach(controller -> {
			assertNull(controller.getAnnotation(CLASS_CONTROLLER));
		});
	}

	private static List<Class> getClassesForPackage(String packageName) throws ClassNotFoundException {
		List<File> directories = new ArrayList<File>();
		try {
			ClassLoader cLoad = Thread.currentThread().getContextClassLoader();
			if (cLoad == null)
				throw new ClassNotFoundException("Can't get class loader.");
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = cLoad.getResources(path);
			while (resources.hasMoreElements())
				directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Null pointer exception)");
		} catch (UnsupportedEncodingException encex) {
			throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Unsupported encoding)");
		} catch (IOException ioex) {
			throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + packageName);
		}

		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : directories)
			if (directory.exists()) {
				String[] files = directory.list();
				for (String file : files)
					if (file.endsWith(".class") && !file.contains("Test"))
						classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
			} else {
				String pakage = packageName + " (" + directory.getPath() + ")";
				throw new ClassNotFoundException(pakage + "does not appear to be a valid package");
			}
		return classes;
	}
}
