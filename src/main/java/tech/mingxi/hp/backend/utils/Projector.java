package tech.mingxi.hp.backend.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class Projector {
	final SpelAwareProxyProjectionFactory factory = new SpelAwareProxyProjectionFactory();

	public <T> List<T> getProjectedList(List<?> list, Class<T> projectionType) {
		return list.stream().map(o -> factory.createProjection(projectionType, o)).collect(Collectors.toList());
	}

	public <T> T getProjectedObject(Object obj, Class<T> projectionType) {
		return factory.createProjection(projectionType, obj);
	}
}
