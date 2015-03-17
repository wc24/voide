/**
 * Created by lime on 15/3/16.
 */
public class Tool {
    public static int parseInt(String value) {
        int out=0;
        try {
            out=Integer.parseInt(value);
        }catch (NumberFormatException e){

        }
        return out;
    }

    public static String JsonToLua(String json) {
        String out;
        out=json.replaceAll("\"(\\w+)\"\\s*:\\s*", "$1=");
        out=out.replaceAll("\\[","{");
        out=out.replaceAll("\\]","}");
        out="local root=\n"+out+"\nreturn root";
        return out;
    }
}
