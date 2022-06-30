package com.jaw.framework.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	static Logger log = Logger.getLogger(LoggingAspect.class.getName());

	// for classes with four package structure
	@Before("execution(* com.jaw.*.*.*.*(..))")
	public void before(JoinPoint point) {
		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName()
				+ ",Calling " + point.getSignature().getName() + "() method.");

	}

	@After("execution(* com.jaw.*.*.*.*(..))")
	public void afterMethodCalled(JoinPoint point) {
		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has finished execution.");

	}

	@AfterThrowing(pointcut = "execution(* com.jaw.*.*.*.*(..))", throwing = "e")
	public void throwsAspect(JoinPoint point, Throwable e) {
		log.error("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has throwed " + e
				+ ",cause is :" + e.getCause() + ".");
		// log.log(Level.ERROR, e.getMessage(), e);
		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has throwed " + e
				+ ",cause is :" + e.getCause() + ".");
		// log.log(Level.DEBUG, e.getMessage(), e);
		log.info("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has throwed " + e
				+ ",cause is :" + e.getCause() + ".");
		// log.log(Level.DEBUG, e.getMessage(), e);

	}

	@AfterReturning(pointcut = "execution(* com.jaw.*.*.*.*(..))", returning = "ret")
	public void returnValues(JoinPoint point, Object ret) {

		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " return "
				+ point.getSignature().getDeclaringType().getName() + ".");

	}

	// For classes with five package structure
	@Before("execution(* com.jaw.*.*.*.*.*(..))")
	public void before1(JoinPoint point) {
		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName()
				+ ",Calling " + point.getSignature().getName() + "() method.");

	}

	@After("execution(* com.jaw.*.*.*.*.*(..))")
	public void afterMethodCalled1(JoinPoint point) {
		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has finished execution.");

	}

	@AfterThrowing(pointcut = "execution(* com.jaw.*.*.*.*.*(..))", throwing = "e")
	public void throwsAspect1(JoinPoint point, Throwable e) {
		log.error("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has throwed " + e
				+ ",cause is :" + e.getCause() + ".");
		// log.log(Level.ERROR, e.getMessage(), e);
		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has throwed " + e
				+ ",cause is :" + e.getCause() + ".");
		// log.log(Level.DEBUG, e.getMessage(), e);
		log.info("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " has throwed " + e
				+ ",cause is :" + e.getCause() + ".");
		// log.log(Level.DEBUG, e.getMessage(), e);
	}

	@AfterReturning(pointcut = "execution(* com.jaw.*.*.*.*.*(..))", returning = "ret")
	public void returnValues1(JoinPoint point, Object ret) {

		log.debug("Class Name :"
				+ point.getSignature().getDeclaringType().getName() + ","
				+ point.getSignature().getName() + " return "
				+ point.getSignature().getDeclaringType().getName() + ".");

	}

}
