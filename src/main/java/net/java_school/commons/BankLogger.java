package net.java_school.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class BankLogger {
  private Log log = LogFactory.getLog(this.getClass());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  @AfterThrowing(pointcut="execution(* net.java_school.bank.BankDao.*(..))",throwing="e")
  public void log(JoinPoint point,Throwable e) {
    if (log.isDebugEnabled()) {
      StringBuilder msg = new StringBuilder();
      msg.append(point.getSignature().getName() + " Method" + NEW_LINE);
      msg.append("Message : " + e.getMessage() + NEW_LINE);
      log.debug(msg);
    }
  }
}