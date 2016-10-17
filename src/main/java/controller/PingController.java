package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jjzzz on 10/2/2016.
 */
public class PingController {
    private PingControllerListener listener;
    private String address;
    private int _0To30;
    private int _30To100;
    private int above100;
    private int failure;
    private Date startedTime;
    private List<String> results;

    private boolean replyStarted;

    public PingController(String address) {
        this.address = address;
        results=new ArrayList<String>();
    }
    public PingController setListener(PingControllerListener listener){
        this.listener=listener;
        return this;
    }

    public List<String> getResults() {
        return results;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void start() throws IOException {
        startedTime=new Date();
        String command="ping "+address+" -t";
        Runtime runtime=Runtime.getRuntime();
        Process pingProcess=runtime.exec(command);
        BufferedReader reader=new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));
        String line;
        while ((line=reader.readLine())!=null)
            processResult(line);
    }

    private void processResult(String line) {
        //skip first few lines
        if(!replyStarted){
            if(line.contains("Pinging"))
                replyStarted=true;
            return;
        }


        //start processing
        int resultTime=parseResult(line);
        if(resultTime<0)
            failure++;
        else {
            if(resultTime<=30)
                _0To30++;
            else if(resultTime<=100)
                _30To100++;
            else
                above100++;
        }
        //add into list
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        results.add(dateFormat.format(new Date())+" > "+line);
        System.out.println("Latest result added into list > "+results.get(results.size()-1));
        //remove oldest result if list length > 10k
        if(results.size()>10000)
            results.remove(0);
        //call back
        if(listener!=null)
            listener.onUpdate(_0To30,_30To100,above100,failure);
    }


    private int parseResult(String msg){
        System.out.println("controller.PingController.parseResult msg="+msg);

        int timeStartIndex=msg.indexOf("time")+5;
        if(!msg.contains("TTL"))
            return -1;
        else{
            int msStartIndex=msg.indexOf("ms");
            return Integer.parseInt(msg.substring(timeStartIndex,msStartIndex));
        }
    }

    public static void main(String[] args) {
        //System.out.println(new controller.PingController().parseResult("Reply from 192.168.99.1: bytes=32 time=552ms TTL=64"));

        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            new PingController("192.168.99.1").start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public interface PingControllerListener{
        void onUpdate(int c1,int c2,int c3,int c4);
    }

}
