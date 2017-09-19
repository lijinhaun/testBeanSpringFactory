package com.xzsoft.xip.seleniumtest.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzsoft.xip.seleniumtest.service.TestPlatFormService;
import com.xzsoft.xip.seleniumtest.serviceImpl.MyJob;

@Controller
public class TestPlatFormAction
{
	
	@Autowired
	TestPlatFormService testPlatFormService;
	
  @RequestMapping("/test.do")
  public void TestUpload(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger logger = Logger.getLogger("TestPlatFormAction");
    
    String filename = request.getParameter("jar_name");
    
    String xml_name = request.getParameter("xml_name");
    
    String comments = request.getParameter("comments");
//    
//    String msg = "";String IsNew = "";
//    JSONObject obj = new JSONObject();
//    List resultList = new ArrayList();
//    
//
//    int flag = 0;
//    if (filename.equals(""))
//    {
//      msg = "�ϴ���jar���ļ�����Ϊ��";
//      obj.put("flag", Integer.valueOf(flag));
//      obj.put("msg", msg);
//      PlatformUtil.outPrint(response, obj.toString());
//      throw new Exception(msg);
//    }
//    if (xml_name.equals(""))
//    {
//      msg = "�ϴ���xml�ļ�����Ϊ��";
//      obj.put("flag", Integer.valueOf(flag));
//      obj.put("msg", msg);
//      PlatformUtil.outPrint(response, obj.toString());
//      throw new Exception(msg);
//    }
//    String prefix = filename.substring(filename.lastIndexOf(".") + 1);
//    if (!prefix.equals("jar"))
//    {
//      msg = "�ļ��ϴ����Ͳ���ȷ�����ϴ�jar�ļ�";
//      obj.put("flag", Integer.valueOf(flag));
//      obj.put("msg", msg);
//      PlatformUtil.outPrint(response, obj.toString());
//      throw new Exception(msg);
//    }
    	File jarFile = new File("C:\\Users\\jinhuan\\Desktop\\����.txt");
    	InputStream instream = new BufferedInputStream(new FileInputStream(jarFile));
//    JarInputStream jarInput = new JarInputStream(instream);
//    ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
//    TestPlatFormService testPlatFormService = (TestPlatFormService)ac.getBean("testPlatFormService");
//    boolean flag_count = testPlatFormService.readEntries(jarInput, xml_name);
//    if (!flag_count)
//    {
//      msg = "�����xml�ļ������ڣ�����������!";
//      obj.put("flag", Integer.valueOf(flag));
//      obj.put("msg", msg);
//      PlatformUtil.outPrint(response, obj.toString());
//      throw new Exception(msg);
//    }
//    int xml_count = testPlatFormService.getXmlCount(xml_name);
//    if (xml_count > 0) {
//      IsNew = "N";
//    } else {
//      IsNew = "Y";
//    }
    boolean isUpload = testPlatFormService.upload("47.92.79.200", 22, "TestUser", "111111", "/usr/tmp/", xml_name, instream, "Y", comments);
  }
  
  @RequestMapping("testJob.do")
  public void TimeJob(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger logger = Logger.getLogger("TestPlatFormAction");
    

    String GetStyle = request.getParameter("getValue");
    String XmlName = request.getParameter("XmlName");
    

    SchedulerFactory schedulerfactory = new StdSchedulerFactory();
    Scheduler scheduler = schedulerfactory.getScheduler();
    scheduler.start();
    try
    {
      JobDetail jobDetail = new JobDetail("QuartzTestJob", 
        "QuartzTestJobGroup", MyJob.class);
      jobDetail.getJobDataMap().put("url", "47.92.79.200");
      jobDetail.getJobDataMap().put("port", "22");
      jobDetail.getJobDataMap().put("userName", "TestUser");
      jobDetail.getJobDataMap().put("password", "111111");
      jobDetail.getJobDataMap().put("path", "/usr/tmp/");
      jobDetail.getJobDataMap().put("xml_name", XmlName);
      jobDetail.getJobDataMap().put("is_new", "N");
      

      CronTrigger trigger = new CronTrigger("QuartzTestJob", 
        "QuartzTestJobGroup");
      
      trigger.setCronExpression("0/1 * * * * ? ");
      scheduler.scheduleJob(jobDetail, trigger);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
