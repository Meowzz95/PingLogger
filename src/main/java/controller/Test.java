package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jjzzz on 10/2/2016.
 */
public class Test {
    public static void main(String[] args) {
        Runtime runtime=Runtime.getRuntime();
        try {
            Process pingProcess=runtime.exec("ping 192.168.99.1 -t");
            BufferedReader reader=new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));
            String line;
            while ((line=reader.readLine())!=null)
                System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
