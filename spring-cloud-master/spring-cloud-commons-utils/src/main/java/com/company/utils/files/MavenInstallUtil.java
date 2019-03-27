package com.company.utils.files;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * 
* @ClassName: MavenInstallUtil 
* @Description: TODO(生成maven批量安装jar的语句  及pom依赖) 
* @author Fengjie
* @date 2018年7月9日 上午8:36:07 
*
 */
public class MavenInstallUtil {

	/**
	 * 批量安装jar所在目录
	 */
	public static String filePath = "C:\\Users\\FengJie\\Desktop\\lib";

	public static void main(String[] args) { 
	LinkedList<File> linkedList = new LinkedList<>(); 
	File f = new File(filePath); 
	File[] file = f.listFiles(); 
	for (int i = 0; i < file.length; i++) { 
	linkedList.add(file[i]); 
	} 
	//printFiles(linkedList); 
	//printFile(linkedList);
		printFiles(linkedList);
	} 

	/** 
	* 功能：批量安装maven的本地的jar 
	* @param linkedList
	*/ 
	public static void printFile(LinkedList<File> linkedList) { 
	String sb = "mvn install:install-file -Dfile="+filePath+"/AA.jar -DgroupId=ctyun-oos -DartifactId=CC -Dversion=5.0.0 -Dpackaging=jar";
	for (Iterator<File> iterator = linkedList.iterator(); iterator.hasNext();) { 
	File file = iterator.next(); 
	if(file.isFile()){ 
	String fileName = file.getName(); 
	String replace = sb.replace("AA",fileName.substring(0,fileName.lastIndexOf(".")));
	String replace2 = replace.replace("CC", fileName.subSequence(0, fileName.lastIndexOf("-5.0.0")));
	System.out.println(replace2); 
	linkedList.remove(file); 
	printFile(linkedList); 
	}else{ 
	break; 
	} 
	} 
	} 


	/** 
	* 功能：批量引入maven的pom 
	* @param linkedList
	*/ 
	public static void printFiles(LinkedList<File> linkedList) { 
	String sb = "<dependency><groupId>org.dcm4che</groupId><artifactId>BBB</artifactId><version>${dcm4che.version}</version><type>jar</type></dependency>";
	for (Iterator<File> iterator = linkedList.iterator(); iterator.hasNext();) { 
	File file = iterator.next(); 
	if(file.isFile()){ 
	String fileName = file.getName(); 
	String replace = sb.replace("BBB",fileName.substring(0,fileName.lastIndexOf(".")));
	String replace2 = replace.replace("-5.15.1", "");
	System.out.println(replace2); 
	linkedList.remove(file); 
	printFiles(linkedList); 
	}else{ 
	break; 
	} 
	} 
	} 
}
