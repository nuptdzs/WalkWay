package com.zk.walkwayapp.utils;
/**
 * 工具类
 * @author Administrator
 * 专门用来处理文本字符串
 */
public class Util 
{
	/**
	 * 	检查处理关键字的方法
	 * @param word
	 * @return
	 */
	public static String getkeywords(String word)
	{
		//去除两端空白
		word = word.trim();
		StringBuilder keywords = new StringBuilder(); 
		//检查是否包含逗号
		if(word.contains("，"))
		{//此处包含逗号
			
			String[] words = word.split("，");
			if(words.length<=5)//检查关键字的数量
			{
				for (int i = 0; i < words.length; i++) 
				{
					if(i!=words.length-1)
					{
						//关键字用英文逗号连接
						keywords.append(words[i]+",");
					}else 
					{
						//最后一个关键字不用逗号
						keywords.append(words[i]);
					}
				}
			}
			else 
			{//关键字数量大于5，返回错误代码“1”
				keywords.append("1");
			}
		}
		else
		{//关键字没有逗号，以只有一个关键字处理
			keywords.append(word);
		}
			
			
		return keywords.toString();
		
	}
}
