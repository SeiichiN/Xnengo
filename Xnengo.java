// x2nengo -- 西暦から年号を求めるプログラム
import java.io.*;
import java.io.IOException;
import java.util.*;

public class Xnengo {

    // クラス定数
    static final String FILENAME = "yearx.dat";
    static final String VERSION = "0.1";

    // クラス変数
    // LinkedHashMap -- 入力した順番を保持する
    Map <String, Integer> yeardata = new LinkedHashMap<>();

    /**
     * setMap -- 年号データファイルを読み取り、それを
     *           Mapにセットする。
     * 
     * @param: FILENAME -- 年号データのファイル名(csvファイル)
     *                     形式 -- 年号,1923
     */
    private void setMap (String FILENAME) throws IOException {
        File file = new File (FILENAME);
        try (BufferedReader br = new BufferedReader (new FileReader (file))) {
            String data, nengo;
            int year;
            
            while ((data = br.readLine()) != null) {
                String array[] = data.split(",");
                if (array.length != 2) throw new NumberFormatException();
                nengo = array[0];
                year = Integer.parseInt (array[1]);
                yeardata.put(nengo, year);
                // System.out.println (nengo + " : " + year);  // チェック用
            }
        }
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
     *
     */
    private int getSeireki (String[] nengo) {
        int yearIt = 0;
        
        for (Map.Entry<String, Integer> s : yeardata.entrySet()) {
            if (nengo[0].equals(s.getKey())) {
                yearIt = Integer.parseInt(nengo[1]) + s.getValue();
            }
        }
        return yearIt;
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
        String mes = "java Xnengo -g => 西暦を求める\njava Xnengo -n => 和暦を求める";
        String helpMes = "文禄,慶長,元和,寛永,正保,慶安,承応,明暦,万治,寛文,延宝,天和,貞享,元禄,宝永,正徳,享保,元文,寛保,延享,寛延,宝歴,明和,安永,天明,寛政,享和,文化,文政,天保,弘化,嘉永,安政,万延,文久,元治,慶応,明治,大正,昭和,平成,令和,未来";
        String verMes = "西暦<=>和暦 変換プログラム Java版 ver_" + VERSION + "\nCopyright 2019 Seiichi Nukayama";
        
        // 年号データの読み取り
        try {
            job.setMap(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 引数チェック
        switch (n = args.length) {
        case 0:
            System.out.println(mes);
            System.exit(1);
            break;
        case 1:
            // g の場合 -- 西暦を求める処理
            if ("-g".equals(args[0])) {
                do {
                    nengo[0] = job.getUserInput("和暦を入力 (help: 和暦一覧)> ");
                    if ("help".equals(nengo[0])) {
                        System.out.println(helpMes);
                    }
                } while ("help".equals(nengo[0]));
                nengo[1] = job.getUserInput("年を半角数字で入力 > ");
                // System.out.println(nengo[0] + " " + nengo[1]);
                if (nengo[0] != null && nengo[1] != null) {
                    year = job.getSeireki(nengo);
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
                year = Integer.parseInt(job.getUserInput("西暦を半角数字で入力 > "));
                if (year < 0 || year > 2100) {
                    System.out.println("引数で指定できる西暦年は、0 < 数字 < 2100 の範囲です。");
                    System.exit(1);
                }
                nengo = job.nengoArray(year);
                System.out.println("西暦 " + year + "年は、" + nengo[0] + " " + nengo[1] + "年です。");
            }
            // v の場合
            else if ("-v".equals(args[0])) {
                System.out.println(verMes);
            }
            else {
                System.out.println(mes);
                System.exit(1);
            }
            break;
        default:
            System.out.println(mes);
            System.exit(1);
        }

    }
}
