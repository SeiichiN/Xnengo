// x2nengo -- 西暦から年号を求めるプログラム
import java.io.*;
import java.io.IOException;
import java.util.*;

public class Xnengo {

    // クラス定数
    // static final String FILENAME = "yearx.dat";
    static final String VERSION = "0.1";

    static final Map<String, Integer> yeardata = new LinkedHashMap<String, Integer>() {{
        put ("文禄", 1591);
        put ("慶長", 1594);
        put ("元和", 1614);
        put ("寛永", 1623);
        put ("正保", 1643);
        put ("慶安", 1647);
        put ("承応", 1651);
        put ("明暦", 1654);
        put ("万治", 1657);
        put ("寛文", 1660);
        put ("延宝", 1672);
        put ("天和", 1680);
        put ("貞享", 1683);
        put ("元禄", 1687);
        put ("宝永", 1703);
        put ("正徳", 1710);
        put ("享保", 1715);
        put ("元文", 1735);
        put ("寛保", 1740);
        put ("延享", 1743);
        put ("寛延", 1747);
        put ("宝歴", 1750);
        put ("明和", 1763);
        put ("安永", 1771);
        put ("天明", 1780);
        put ("寛政", 1788);
        put ("享和", 1800);
        put ("文化", 1803);
        put ("文政", 1817);
        put ("天保", 1829);
        put ("弘化", 1843);
        put ("嘉永", 1847);
        put ("安政", 1853);
        put ("万延", 1859);
        put ("文久", 1860);
        put ("元治", 1863);
        put ("慶応", 1864);
        put ("明治", 1866);
        put ("大正", 1911);
        put ("昭和", 1925);
        put ("平成", 1988);
        put ("令和", 2018);
        put ("未来", 2100);
        }};

        static final String MES = "java Xnengo -g => 西暦を求める\njava Xnengo -n => 和暦を求める";
        static final String VERMES = "西暦<=>和暦 変換プログラム Java版 ver_"
            + VERSION
            + "\nCopyright 2019 Seiichi Nukayama";

    /**
     * 年号の一覧を作る
     *
     * @return -- String 年号を " " で連結した文字列。
     */
    private String makeHelp () {
        String text = "";
        for (Map.Entry<String, Integer> s : yeardata.entrySet()) {
            text = text + s.getKey() + " ";
        }
        return text;
    }
    
    /**
     * nengoArray -- 西暦の数字から、年号と元号年を求める。
     * @param: year -- 0 < year < 2100 の数字
     * 
     * @return: String[] -- [0] 年号  [1] 元年からの数字の文字
     */
    private String[] nengoArray (int year) {
        String nengoIt = null;
        int yearIt = 0, gengoYear = 0;
        String[] data = {null, null};
        
        for (Map.Entry<String, Integer> s : yeardata.entrySet()) {
            // System.out.println(s.getKey() + ":" + s.getValue());  // チェック用
            if (year > s.getValue()) {
                nengoIt = s.getKey();
                yearIt = s.getValue();
                // System.out.println(nengoIt + yearIt);  // チェック用
            } else {
                gengoYear = year - yearIt;
                break;
            }
        }
        data[0] = nengoIt;
        data[1] = gengoYear == 1 ? "元" : String.valueOf(gengoYear);
        // System.out.println(data[0] + ":" + data[1]);  // チェック用
        return data;
    }

    /**
     * ユーザーに文字列の入力をしてもらい、それを返す
     * 
     * @param: prompt -- ユーザーに表示する文字列
     *                   （例）"入力 > "
     *
     * @raturn: String -- 半角英数字を想定。日本語の場合はどうなるか？
     */
    private String getUserInput (String prompt) {
        String inputLine = null;
        System.out.print (prompt + " ");
        try {
            BufferedReader is = new BufferedReader(
                new InputStreamReader (System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0) return null;
        } catch (IOException e) {
            System.out.println ("IOException: " + e);
        }
        return inputLine.toLowerCase ();
    }
    
    public static void main (String [] args) {
        String[] nengo = {null, null};
        int n = 0, year = 0;
        Xnengo job = new Xnengo();

        String helpMES = job.makeHelp();
        
        
        // 引数チェック
        switch (n = args.length) {
        case 0:
            System.out.println(MES);
            System.exit(1);
            break;
        case 1:
            // g の場合 -- 西暦を求める処理
            if ("-g".equals(args[0])) {
                do {
                    nengo[0] = job.getUserInput("和暦を入力 (help: 和暦一覧)> ");
                    if ("help".equals(nengo[0])) {
                        System.out.println(helpMES);
                    }
                } while ("help".equals(nengo[0]));
                nengo[1] = job.getUserInput("年を半角数字で入力 > ");
                // 年号も数字も null で無ければ
                if (nengo[0] != null && nengo[1] != null) {
                    // Mapの getメソッドで一発で検索できる
                    year = job.yeardata.get(nengo[0]) + Integer.parseInt(nengo[1]);
                    System.out.println(nengo[0] + nengo[1] + "年は、西暦 " + year + "年です。");
                } else {
                    if (nengo[0] == null) {
                        System.out.println("和暦がnull値です。");
                    } else {
                        System.out.println("年がnull値です。");
                    }
                    System.exit(1);
                }
            }
            // n の場合 -- 年号を求める処理
            else if ("-n".equals(args[0])) {
                String text = job.getUserInput("西暦を半角数字で入力 > ");
                if (text != null) {
                    year = Integer.parseInt(text);
                } else {
                    System.out.println ("西暦が null値です。");
                }
                if (year < 1592 || year > 2100) {
                    System.out.println("引数で指定できる西暦年は、1592 <= 数字 <= 2100 の範囲です。");
                    System.exit(1);
                } else { 
                    nengo = job.nengoArray(year);
                    System.out.println("西暦 " + year + "年は、" + nengo[0] + " " + nengo[1] + "年です。");
                }
            }
            // v の場合
            else if ("-v".equals(args[0])) {
                System.out.println(VERMES);
            }
            else {
                System.out.println(MES);
                System.exit(1);
            }
            break;
        default:
            System.out.println(MES);
            System.exit(1);
        }

    }
}
