package constant;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by jjzzz on 10/18/2016.
 */
public class Config {
    private static HashMap<String,String> mapKeyWord;
    static{
        mapKeyWord=new HashMap<>();
        mapKeyWord.put("PING_START_en","Pinging");
        mapKeyWord.put("TIME_en","time");
        mapKeyWord.put("TIME_OFFSET_en","5");

        mapKeyWord.put("PING_START_zh","正在");
        mapKeyWord.put("TIME_zh","时间");
        mapKeyWord.put("TIME_OFFSET_zh","3");
    }
    private static String locale;
    static{
        locale= Locale.getDefault().getLanguage();
        System.out.println("Current locale="+locale);
    }
    public static String getKeyWord(String key){
        return mapKeyWord.get(key+"_"+locale);
    }
}
