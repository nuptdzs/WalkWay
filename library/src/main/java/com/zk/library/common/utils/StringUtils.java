package com.zk.library.common.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final long ONE_MINUTE = 60000L;
    public static final long ONE_HOUR = 3600000L;
    public static final long ONE_DAY = 86400000L;
    public static final long ONE_MONTH = 2592000000L;
    public static final long ONE_YEAR = 31104000000L;
    private static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String HEX_STRING = "0123456789ABCDEF";
    private static String upOrderStr;
    private static String downOrderStr;

    public StringUtils() {
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !"".equals(string.trim()) && !"null".equals(string.trim());
    }

    public static boolean isEmpty(String string) {
        return !isNotEmpty(string);
    }

    public static String trim(String string) {
        return string != null && !string.equals("null")?string.trim():"";
    }

    public static byte[] hexStringToByte(String hex) {
        if(hex == null) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] result = new byte[len];
            char[] achar = hex.toCharArray();

            for(int i = 0; i < len; ++i) {
                int pos = i * 2;
                result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
            }

            return result;
        }
    }

    private static int toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static String bytes2HexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            builder.append(HEX_CHAR[(b & 240) >>> 4]);
            builder.append(HEX_CHAR[b & 15]);
        }

        return builder.toString();
    }

    public static String toString(Object o) {
        return o == null?"":o.toString();
    }

    public static String formatString(String string, String replace) {
        if(string == null) {
            return "";
        } else {
            String newString = string.replaceAll(replace, "");
            return newString;
        }
    }

    public static String formatStringVague(String string, int start, int end) {
        if(isEmpty(string)) {
            return string;
        } else if(start >= 0 && end <= string.length() - 1) {
            StringBuilder sb = new StringBuilder();
            char[] c = string.toCharArray();

            for(int i = 0; i < c.length; ++i) {
                if(i >= start && i <= end) {
                    sb.append("*");
                } else {
                    sb.append(c[i]);
                }
            }

            return sb.toString();
        } else {
            return string;
        }
    }

    public static String formatString(String string) {
        if(string == null) {
            return "";
        } else {
            String newString = string.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
            return newString;
        }
    }

    public static String formatFileContent(String string) {
        if(string == null) {
            return "";
        } else {
            String newString = string.replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "");
            return newString;
        }
    }

    public static String suffixSpaceToString(String string, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = string.length();
        int appendCount = length < len?len - length:0;

        for(int i = 0; i < appendCount; ++i) {
            stringBuilder.append("　");
        }

        return string + stringBuilder.toString();
    }

    public static String addSpaceToStringFront(String string, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = string.length();
        int appendCount = length < len?len - length:0;

        for(int i = 0; i < appendCount; ++i) {
            stringBuilder.append("　");
        }

        return stringBuilder.toString() + string;
    }

    public static String formatAmount(String s) {
        return formatAmount(s, false);
    }

    public static String formatAmount(String s, boolean isInitNull) {
        String result = "";
        if(!isNotEmpty(s)) {
            return "";
        } else {
            String temp = s;
            s = formatString(s);
            double num = 0.0D;

            try {
                num = Double.parseDouble(s);
            } catch (NumberFormatException var7) {
                ;
            }

            if(num == 0.0D) {
                return isInitNull?"":"0.00";
            } else {
                if(num < 1.0D) {
                    if(s.length() == 4) {
                        return temp;
                    }

                    if(s.length() == 3) {
                        return temp + "0";
                    }
                }

                DecimalFormat forMater = new DecimalFormat("#,###.00");
                result = forMater.format(num);
                if(result.startsWith(".")) {
                    result = "0" + result;
                }

                return result;
            }
        }
    }

    public static boolean isURL(String urlString) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern patt = Pattern.compile(regex);
        Matcher matcher = patt.matcher(urlString);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    public static int StringToInt(String number, int defaultValue) {
        if(isEmpty(number)) {
            return defaultValue;
        } else {
            try {
                return Integer.valueOf(number).intValue();
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    public static Float StringTofloat(String number, float defaultValue) {
        if(isEmpty(number)) {
            return Float.valueOf(defaultValue);
        } else {
            try {
                return Float.valueOf(number);
            } catch (Exception var3) {
                return Float.valueOf(defaultValue);
            }
        }
    }

    public static SpannableStringBuilder formatNumberColor(String text, int color) {
        return formatTextColor(text, "[0-9]+\\.*[0-9]*", color);
    }

    public static SpannableStringBuilder formatTextColor(String text, String patternString, int color) {
        if(text == null) {
            return new SpannableStringBuilder("");
        } else {
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            if(patternString == null) {
                return style;
            } else {
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(text);

                while(matcher.find()) {
                    style.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), 33);
                }

                return style;
            }
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isLetters(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static boolean isSeriesSame(String str) {
        return isSeriesSame(str, 4);
    }

    public static boolean isSeriesSame(String str, int MAX_SAME_LENGTH) {
        boolean same = false;
        int MAX = MAX_SAME_LENGTH + 1;

        for(int i = 0; i < str.length() && str.length() - i >= MAX; ++i) {
            String regex = getSeriesString(str.substring(i, i + 1), MAX);
            String temp = str.substring(i, MAX + i);
            same = temp.equals(regex);
            if(same) {
                break;
            }
        }

        return same;
    }

    private static String getSeriesString(String item, int len) {
        String temp = "";

        for(int i = 0; i < len; ++i) {
            temp = temp.concat(item);
        }

        return temp;
    }

    public static boolean isOrder(String str) {
        return !str.matches("((\\d)|([a-z])|([A-Z]))+")?false:upOrderStr.contains(str) || downOrderStr.contains(str);
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();

        for(int i = 0; i < name.length(); ++i) {
            if(!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }

        return res;
    }

    public static String getRandom(int strLength) {
        Random rm = new Random();
        double pross = (1.0D + rm.nextDouble()) * Math.pow(10.0D, (double)strLength);
        String fixLenthString = String.valueOf(pross);
        return fixLenthString.substring(1, strLength + 1);
    }


    public static String bytesToLongStr(byte[] var0, boolean isSigned) {
        long result = bytesToLong(var0, isSigned);
        return result == -1L?"":result + "";
    }

    public static long bytesToLong(byte[] var0, boolean isSigned) {
        if(var0 == null) {
            return -1L;
        } else if(var0.length < 1) {
            return -1L;
        } else if(var0.length == 1) {
            return (long)(var0[0] & 255);
        } else {
            long var2 = 0L;
            int var4;
            if(isSigned) {
                var2 = (long)(var0[0] & 255);

                for(var4 = 1; var4 < var0.length; ++var4) {
                    var2 <<= 8;
                    var2 |= (long)(var0[var4] & 255);
                }
            } else {
                var2 = (long)var0[var0.length - 1];

                for(var4 = var0.length - 2; var4 >= 0; --var4) {
                    var2 <<= 8;
                    var2 |= (long)(var0[var4] & 255);
                }
            }

            return var2;
        }
    }

    public static long bytesToLong(byte[] var0) {
        try {
            return bytesToLong(var0, true);
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static byte[] longToBytes(long var0, int var2, boolean isSigned) {
        try {
            if(var2 < 1) {
                return null;
            } else {
                long var8 = var0;
                byte[] var6 = new byte[var2];
                int var7;
                if(isSigned) {
                    for(var7 = var6.length - 1; var7 > -1; --var7) {
                        var6[var7] = Long.valueOf(var8 & 255L).byteValue();
                        var8 >>= 8;
                    }
                } else {
                    for(var7 = 0; var7 < var6.length; ++var7) {
                        var6[var7] = Long.valueOf(var8 & 255L).byteValue();
                        var8 >>= 8;
                    }
                }

                return var6;
            }
        } catch (Exception var81) {
            return null;
        }
    }

    public static byte[] longToBytes(long var0, int var2) {
        try {
            return longToBytes(var0, var2, true);
        } catch (Exception var4) {
            return null;
        }
    }

    static {
        for(int i = 33; i < 127; ++i) {
            upOrderStr = upOrderStr + Character.toChars(i)[0];
        }

        downOrderStr = (new StringBuilder(upOrderStr)).reverse().toString();
    }
}
