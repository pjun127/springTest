package com.kh.spring.common.aop;




import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //빈객체등록
@Aspect //aop객체라고 선언 component, Aspect 둘다 등록 해야 aop 어노테이션이라고 명칭 하는 거임
public class LoggerAspect {
	
	private Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
	
	/*@Pointcut("execution(* com.kh.spring..*(..))")
	//myAround매소드가 선언적 xml에서 pointcut에 대한 id 값
	
	public void myAround() {}
	*/
	@Pointcut("execution(* com.kh.spring.memo..*(..))")
	
	public void myBefore() {}
	
	//ProceedingJoinPoint --> round():전후 매소드에 대한 객체이다  after,before매소드 안됨
	@Around("execution(* com.kh.spring..*(..))")
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		
		/*Object[] objs = joinPoint.getArgs();
		
		for(Object o : objs) logger.debug("매개변수"+o);
		*/
		//spring은 어디든 사용자가 입력한 값을 가지고 올수 있음!
		/*HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.getParameterMap();*/
		
		//joinPoint는 들어가는 매소드에 대한 정보를 가지고 온다
		Signature signature = joinPoint.getSignature();
		
		//객체명
		String type = signature.getDeclaringTypeName();
		
		//매소드 명
		String methodName=signature.getName();
		
		String componentName = "";
		if(type.indexOf("Controller")>-1) {
			componentName = "Controller \t :";
		}
		else if(type.indexOf("Service")>-1) {
			componentName = "Service \t :";
		}
		else if(type.indexOf("Dao")>-1) {
			componentName = "Dao \t :";
		}
		logger.debug("[Before]"+componentName+type+"."+methodName+"()");
		
		Object obj = joinPoint.proceed(); 
		
		logger.debug("[after]"+componentName+type+"."+methodName+"()");
		
		//전후의 기점이 joinPoint.proceed() : 전
		/*return joinPoint.proceed();*/
		return obj;
	}
	
	//before를 적용해보자!
	@Before("myBefore()")
	public void beforeAd(JoinPoint joinPoint) {
		
		Signature sig =  joinPoint.getSignature();
		
		logger.debug("before"+sig.getDeclaringTypeName()+" : "+sig.getName());
	}
}
