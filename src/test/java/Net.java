import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author : ddv
 * @since : 2019/12/11 10:35 AM
 */

public class Net {
    @Test
    public void ping() {
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec("ping -c 3 -i 1 " + "14.215.177.39");
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                System.out.println(getCheckResult(line));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static boolean getCheckResult(String line) { // System.out.println("控制台输出的结果为:"+line);
        // Pattern pattern = Pattern.compile("(\\s+)(ttl=\\d+)(\\d+ms)", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("(ms)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Test
    public void test() throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();// 获取本地所有网络接口
            while (en.hasMoreElements()) {// 遍历枚举中的每一个元素
                NetworkInterface ni = (NetworkInterface)en.nextElement();
                Enumeration<InetAddress> enumInetAddr = ni.getInetAddresses();
                while (enumInetAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumInetAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                        && inetAddress.isSiteLocalAddress()) {
                        sb.append("ip:" + inetAddress.getHostAddress().toString() + "\n");
                    }
                }
            }
        } catch (SocketException e) {
        }
        System.out.println(sb.toString());

    }

    @Test
    public void breaktest() {
        end:
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if (i == 5) {
				break end;
            }
        }

        System.out.println("final");
    }
}
