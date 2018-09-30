package com.rd.channel.common.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FormatUtil {

	public FormatUtil() {
	}

	// Switch String to Date
	public static Date String2Date(String pattern, String date) {

		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.parse(date, new ParsePosition(0));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	// Switch Date to String
	public static String Date2String(String pattern, Date date) {
		try {

			SimpleDateFormat df = new SimpleDateFormat(pattern);

			return df.format(date);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String numberFormat(double num) {
		return numberFormat("###,###", '.', ',', num);
	}

	public static String amountFormat(double amount) {
		return numberFormat("###,##0.00", '.', ',', amount);
	}

	public static String amountFormatII(double amount) {
		return numberFormat("##0.00", '.', ',', amount);
	}
	public static String percentFormat(double amount) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		return nf.format(amount);
	}

	/**
	 * 
	 * @param format
	 * @param decimalSep
	 * @param groupSep
	 * @param num
	 * @return
	 */
	public static String numberFormat(String format, char decimalSep,
			char groupSep, double num) {
		try {
			DecimalFormat df = new DecimalFormat(format);
			DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
			dfs.setDecimalSeparator(decimalSep);
			dfs.setGroupingSeparator(groupSep);
			df.setDecimalFormatSymbols(dfs);
			return df.format(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String round(double v, int scale) {
		String temp = "#,##0.";
		for (int i = 0; i < scale; i++) {
			temp += "0";
		}
		return new java.text.DecimalFormat(temp).format(v);
	}

	public static String getNowTime(String pattern) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		return sdf.format(cal.getTime());
	} // End of getNowTime()

	/**
	 * 格式化金额（单位为元）10-》10.00  0.1=》0.10
	 * @param money 金额，以元为单位
	 * @return
	 */
	public static String formatMoneyOfYuan(String money){
		if (money == null || money.length() < 1) {  
            return "";  
        }
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
		Matcher isNum = pattern.matcher(money);
		if(!isNum.matches()){
			return "";
		}
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("#.00");
		double num = Double.parseDouble(money);
		money = myformat.format(num);
		if(money.indexOf(".")==0){
			money = "0"+money;
		}
		return money;
	}
	
	/**
	 * 把元转为分，如9.5元->950分
	 * 
	 * @param money
	 * @return
	 */
	public static String yuanToFen(String money) {
		String result = null;
		try {
			if (money == null || money.equals(""))
				result = "";
			else {
				int index = money.indexOf(".");
				if (index > 0) {
					String mantissa = money.substring(index + 1);
					if (mantissa.length() > 2) {
						String tmp = mantissa.substring(0, 2);
						String byte3 = mantissa.substring(2, 3);
						if (Integer.parseInt(byte3) > 5) {
							tmp = new Integer(Integer.parseInt(tmp) + 1)
									.toString();
						}
						result = money.substring(0, index) + tmp;
						result = Long.valueOf(result).toString();
					} else if(mantissa.length() ==2){
						result = money.substring(0,index) + money.substring(index + 1);
						result = Long.valueOf(result).toString();
					} else{
						result = money.substring(0, index)
								+ money.substring(index + 1) + "0";
						result = Long.valueOf(result).toString();
					}
				} else {
					result = money + "00";
					result = Long.valueOf(result).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String fenToYuan(String money){
		if(money == null || "".equals(money))return "";
		String result = "";
		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher matcher = pattern.matcher(money);
		if(!matcher.find()) return "";
		try{
			if(money.indexOf(".") >= 0)
				result = money;
			else if(money != null && !money.equals("")){
				if(money.length() > 2)
					result = money.substring(0,money.length() - 2) + "." + money.substring(money.length() - 2);
				else if(money.length() == 2 )
					result = "0." + money;
				else if(money.length() == 1 )
					result = "0.0" + money;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <p>
	 * Title: money
	 * </p>
	 * <p>
	 * Description: *该类是把阿拉伯数字转换成中文大写的类。根据王大庆兄的C++程序稍做修改而成，后面附有王大庆兄的C++
	 * *程序，如果对所附的C++程序有什么问题请与王大庆兄联系email: wang_daqing@21cn.com
	 * *如果对java部分有什么看法和建议请与小弟联系，杨璇 email：netfalcon@263.net *类名：money *函数：String
	 * PositiveIntegerToHanStr(String NumStr) 负责把小数点前面的数转换为大写中文 *函数:String
	 * NumToRMBStr(double val) 负责把输入的double型的数转换为大写中文
	 * *注意java程序转换的范围是：小数点前面15位（已测试通过），C＋＋程序是24位（我没有测试）
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2003 你可以对本程序随意修改，复制，使用，但请保留这里注释声明！！！
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author 王大庆、杨璇
	 * @version 1.0
	 */

	private final static String HanDigiStr[] = new String[] { "零", "壹", "贰",
			"叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private final static String HanDiviStr[] = new String[] { "", "拾", "佰",
			"仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟",
			"亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

	public static String PositiveIntegerToHanStr(String NumStr) { // 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15)
			return "数值过大!";
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "输入含非数字字符!";

			if (n != 0) {
				if (lastzero)
					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) //
				// 如十进位前有零也不发壹音用此行
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // 十进位处于第一位不发壹音
					RMBStr += HanDigiStr[n];
				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
					RMBStr += HanDiviStr[i]; // “亿”或“万”
			}
			if (i % 8 == 0)
				hasvalue = false; // 万进位前有值标记逢亿复位
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		
		return RMBStr;
	}

	public static String NumToRMBStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "负";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999)
			return "数值位数过大!";
		// 四舍五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "角";
			if (integer == 0 && jiao == 0) // 零元后不写零几分
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "分";
		}

		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		// if( !integer ) return SignStr+TailStr;

		// return "￥" + SignStr +
		// PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;

		String RMBStr = SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元"
				+ TailStr;
		
		if (RMBStr.startsWith("拾")) {
			RMBStr = "壹" + RMBStr;
		}
		
		return RMBStr;
	}

	public static byte[] int2hex(String bytes) 
	{ 
		String  hexString = "0123456789ABCDEF";
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2); 
		//将每2位16进制整数组装成一个字节 
		for(int i=0;i <bytes.length();i+=2) 
		baos.write((hexString.indexOf(bytes.charAt(i)) <<4 |hexString.indexOf(bytes.charAt(i+1)))); 
		return baos.toByteArray();
	} 
	
	/**
	 * BCD码转为10进制串(阿拉伯数据) 
	 * @param bytes BCD码 
	 * @param off
	 * @param len
	 * @return
	 */
	public static String bcd2Str(byte[] bytes,int off,int len){
		byte[] data=Arrays.copyOfRange(bytes, off, off+len);
		return bcd2Str(data);
	}
	
	
	 /** 
     * @功能: BCD码转为10进制串(阿拉伯数据) 
     * @参数: BCD码 
     * @结果: 10进制串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
    }
    
    /** 
     * @功能: 10进制串转为BCD码 
     * @参数: 10进制串 
     * @参数: isRight==true 表示右靠，isRight==false 表示左靠
     * @结果: BCD码 
     */  
    public static byte[] str2Bcd(String asc , boolean isRight) {
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {
        	if(isRight){
        		 asc = "0" + asc;  
                 len = asc.length();
        	}else{
        		 asc = asc + "0";  
                 len = asc.length();
        	}
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }
    
    /**
     * bcd码压缩，原字符串长度为偶数使用该方法，如果是奇数，默认是左补零
     * @param asc 需要bcd码压缩的字段
     * @return
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {
        	asc = "0" + asc;  
            len = asc.length();
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }
    	
	/**
	 * BCD码转ASCII码
	 * @param bcd_buf
	 * @param len
	 * @return
	 */
	public static byte[] BCD2ASCII(byte [] bcd_buf,int len){
		int i,n;
		n=len;
		ByteBuffer asc_buf=ByteBuffer.allocate(n);
		byte tmp;
		for (i = 0; i < n/2; i++) {
			tmp = (byte) ((bcd_buf[i] & 0xf0) >> 4);
			tmp = abcd_to_asc(tmp);
			asc_buf.put(tmp);
			tmp = (byte) (bcd_buf[i] & 0x0f);
			tmp = abcd_to_asc(tmp);
			asc_buf.put(tmp);
		}
		if (n % 2!=0) {
			tmp = (byte) ((bcd_buf[i] & 0xf0) >> 4);
			tmp = abcd_to_asc(tmp);
			asc_buf.put(tmp);
		}
		asc_buf.flip();
	    byte[] res=null;
    	res=new byte[asc_buf.remaining()];
    	asc_buf.get(res, 0, res.length);
    	return res;
	
	}
	
	private static byte abcd_to_asc(byte abyte){
		if (abyte <= 9)
			abyte = (byte) (abyte + '0');
		else
			abyte = (byte) (abyte + 'A' - 10);
		return (abyte);
	}
	
	/**
	 * ASCII码转BCD码
	 * @param asc_buf
	 * @return
	 */
	public static byte[] ASCII2BCD(byte[] asc_buf){
		int j=0;
		int n=asc_buf.length;
		byte []bcd_buf=new byte[(n+1)/2];
		for (int i = 0; i < (n+1)/2; i++) {
			bcd_buf[i] = aasc_to_bcd(asc_buf[j++]);
			bcd_buf[i] = (byte) (((j >= n) ? 0x00 : aasc_to_bcd(asc_buf[j++]))+ (bcd_buf[i] << 4));
		}
		return bcd_buf;
	}
	
	private static byte aasc_to_bcd(byte asc){
		byte bcd;
		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else if ((asc > 0x39) && (asc <= 0x3f))
			bcd = (byte) (asc - '0');
		else {
			bcd = 0x0f;
		}
		return bcd;
	}
	
	/**
	 * ASCII码转BCD码
	 * @param asc_buf
	 * @param off
	 * @param len
	 * @return
	 */
	public static byte[] ASCII2BCD(byte[] asc_buf,int off,int len){
		byte[] data=Arrays.copyOfRange(asc_buf, off, off+len);
		
		return ASCII2BCD(data);
	}
	
	/**
	 * ASCII码转16进制
	 * 
	 * */
	public static String ASCII2Hex(String asciiValue){
		char[] chars = asciiValue.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}
	
	/**
	 * 将字符串转换为byte数组
	 * 
	 * @param str
	 *            待转换的字符串
	 * @return 转换结果
	 */
	public static byte[] Str2Hex(String str) {
		char[] ch = str.toCharArray();
		byte[] b = new byte[ch.length / 2];
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] == 0) {
				break;
			}
			if (ch[i] >= '0' && ch[i] <= '9') {
				ch[i] = (char) (ch[i] - '0');
			} else if (ch[i] >= 'A' && ch[i] <= 'F') {
				ch[i] = (char) (ch[i] - 'A' + 10);
			}
		}
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (((ch[2 * i] << 4) & 0xf0) + (ch[2 * i + 1] & 0x0f));
		}
		return b;
	}
	
	
	/**
	 * 将byte数组转换为可见的字符串
	 * 
	 * @param b
	 *            待转换的byte数组
	 * @param len
	 *            转换长度
	 * @return
	 */
	public static String Hex2Str(byte[] b, int len) {
		String str = "";
		char[] ch = new char[len * 2];

		for (int i = 0; i < len; i++) {
			if (((b[i] >> 4) & 0x0f) < 0x0a) {
				ch[i * 2] = (char) (((b[i] >> 4) & 0x0f) + '0');
			} else {
				ch[i * 2] = (char) (((b[i] >> 4) & 0x0f) + 'A' - 10);
			}

			if (((b[i]) & 0x0f) < 0x0a) {
				ch[i * 2 + 1] = (char) (((b[i]) & 0x0f) + '0');
			} else {
				ch[i * 2 + 1] = (char) (((b[i]) & 0x0f) + 'A' - 10);
			}

		}
		str = new String(ch);
		return str;
	}
	
	//HexStringToByte
	public static byte[] hexStringToByte(String hex) {
		hex=hex.toUpperCase();
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	public static String byte2hex(byte[] b,int off,int len) {
		
		byte[] data=Arrays.copyOfRange(b, off, off+len);
		
		return byte2hex(data);
	}
	
	/**
	 * 将byte数组转换为可见的大写字符串
	 * 
	 * @param b
	 *            待转换的byte数组
	 * @return 转换后的结果
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			/*if (n < b.length - 1) {
				hs = hs + ":";
			}*/
		}
		return hs.toUpperCase();
	}
	
	/**
	 * 计算MAC
	 * 
	 * @param inputByte
	 *            待计算数据
	 * @param inputkey
	 *            密钥
	 * @return 计算出的MAC值
	 * @throws Exception
	 */
	public String genmac(byte[] inputByte, byte[] inputkey) throws Exception {
		try {
			Mac mac = Mac.getInstance("HmacMD5");
			SecretKey key = new SecretKeySpec(inputkey, "DES");
			mac.init(key);

			byte[] macCode = mac.doFinal(inputByte);
			String strMac = this.byte2hex(macCode);
			return strMac;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	/**
	 * MAC校验
	 * 
	 * @param inputByte
	 *            待计算的数据
	 * @param inputkey
	 *            密钥
	 * @param inputmac
	 *            比较MAC
	 * @return 校验结果
	 * @throws Exception
	 */
	public boolean checkmac(byte[] inputByte, byte[] inputkey, String inputmac)
			throws Exception {
		try {
			Mac mac = Mac.getInstance("HmacMD5");
			SecretKey key = new SecretKeySpec(inputkey, "DES");
			mac.init(key);

			byte[] macCode = mac.doFinal(inputByte);
			String strMacCode = this.byte2hex(macCode);

			if (strMacCode.equals(inputmac)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	
	/**
	 * 字符串填充
	 * 
	 * @param string
	 *            源串
	 * @param filler
	 *            填充值
	 * @param totalLength
	 *            填充总长度
	 * @param atEnd
	 *            头尾填充表示,true - 尾部填充;false - 头部填充
	 * @return
	 */
	public static String fillString(String string, char filler,
			int totalLength, boolean atEnd) {
		byte[] tempbyte = string.getBytes();
		int currentLength = tempbyte.length;
		int delta = totalLength - currentLength;

		for (int i = 0; i < delta; i++) {
			if (atEnd) {
				string += filler;
			} else {
				string = filler + string;
			}
		}
		return string;
	}
	
	/**
	 * 以16进制对照的方式打印byte数组
	 * 
	 * @param inBytes
	 * @return
	 */
	public static String trace(byte[] inBytes) {
		int i, j = 0;
		byte[] temp = new byte[76];
		bytesSet(temp, ' ');
		StringBuffer strc = new StringBuffer("");
		strc
				.append("----------------------------------------------------------------------------"
						+ "\n");
		for (i = 0; i < inBytes.length; i++) {
			if (j == 0) {
				System.arraycopy(String.format("%03d: ", i).getBytes(), 0,
						temp, 0, 5);
				System.arraycopy(String.format(":%03d", i + 15).getBytes(), 0,
						temp, 72, 4);
			}
			System.arraycopy(String.format("%02X ", inBytes[i]).getBytes(), 0,
					temp, j * 3 + 5 + (j > 7 ? 1 : 0), 3);
			if (inBytes[i] == 0x00) {
				temp[j + 55 + ((j > 7 ? 1 : 0))] = '.';
			} else {
				temp[j + 55 + ((j > 7 ? 1 : 0))] = inBytes[i];
			}
			j++;
			if (j == 16) {
				strc.append(new String(temp)).append("\n");
				bytesSet(temp, ' ');
				j = 0;
			}
		}
		if (j != 0) {
			strc.append(new String(temp)).append("\n");
			bytesSet(temp, ' ');
		}
		strc
				.append("----------------------------------------------------------------------------"
						+ "\n");
		return strc.toString();
	}
	
	/**
	 * 
	 * @param inBytes
	 * @param fill
	 */
	private static void bytesSet(byte[] inBytes, char fill) {
		if (inBytes.length == 0) {
			return;
		}
		for (int i = 0; i < inBytes.length; i++) {
			inBytes[i] = (byte) fill;
		}
	}
	
	/**
	 * unicode转中文
	 * @param str
	 * @return
	 */
	public String Unicode2Str(String str) {
		str = (str == null ? "" : str);
		if (str.indexOf("\\u") == -1)// 如果不是unicode码则原样返回
			return str;

		StringBuffer sb = new StringBuffer(1000);

		for (int i = 0; i < str.length() - 6;) {
			String strTemp = str.substring(i, i + 6);
			String value = strTemp.substring(2);
			int c = 0;
			for (int j = 0; j < value.length(); j++) {
				char tempChar = value.charAt(j);
				int t = 0;
				switch (tempChar) {
				case 'a':
					t = 10;
					break;
				case 'b':
					t = 11;
					break;
				case 'c':
					t = 12;
					break;
				case 'd':
					t = 13;
					break;
				case 'e':
					t = 14;
					break;
				case 'f':
					t = 15;
					break;
				default:
					t = tempChar - 48;
					break;
				}

				c += t * ((int) Math.pow(16, (value.length() - j - 1)));
			}
			sb.append((char) c);
			i = i + 6;
		}
		return sb.toString();
	}
	
}
